package com.elibrary.eLibrary.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elibrary.eLibrary.model.Book;
import com.elibrary.eLibrary.service.BookService;
import com.elibrary.eLibrary.security.JwtUtil;


@RestController
@RequestMapping("/api/books")
@CrossOrigin("*")
public class BookController {

    @Autowired
    private BookService service;
    
    @Autowired
    private JwtUtil jwt;

    @GetMapping
    public List<Book> all() {
        return service.getAll();
    }

    @GetMapping("/search")
    public List<Book> search(@RequestParam String q) {
        return service.search(q);
    }

    @GetMapping("/download/{id}")
public ResponseEntity<Resource> download(
        @RequestHeader("Authorization") String auth,
        @PathVariable Long id) throws IOException {

    String token = auth.replace("Bearer ", "");
    if (!jwt.validate(token)) {
        return ResponseEntity.status(401).build();
    }

    Book book = service.get(id);
    Path path = Paths.get(book.getFilePath());

    if (!Files.exists(path) || !path.toString().endsWith(".pdf")) {
        return ResponseEntity.badRequest().build();
    }

    Resource res = new UrlResource(path.toUri());

    return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + path.getFileName() + "\"")
            .body(res);
}

}
