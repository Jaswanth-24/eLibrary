package com.elibrary.eLibrary.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.elibrary.eLibrary.model.Book;
import com.elibrary.eLibrary.service.BookService;

@RestController
@RequestMapping("/api/books")
@CrossOrigin("*")
public class BookController {

    private static final String BASE_DIR = "books/";

    @Autowired
    private BookService service;

    // ================= GET ALL BOOKS =================
    @GetMapping
    public List<Book> all() {
        return service.getAll();
    }

    // ================= SEARCH =================
    @GetMapping("/search")
    public List<Book> search(@RequestParam String q) {
        return service.search(q);
    }

    // ================= DOWNLOAD / VIEW PDF =================
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws IOException {

        Book book = service.get(id);
        Path path = Paths.get(book.getFilePath());

        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "inline; filename=\"" + book.getFileName() + "\""
                )
                .body(resource);
    }

    // ================= UPLOAD PDF =================
    @PostMapping("/upload")
    public ResponseEntity<?> uploadBook(
            @RequestParam("file") MultipartFile file,
            @RequestParam String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category
        ) throws IOException {

        if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", "Only PDF files are allowed"));
        }

        if (file.getSize() > 10 * 1024 * 1024) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", "File size must be under 10MB"));
        }

        Files.createDirectories(Paths.get(BASE_DIR));

        String storedName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(BASE_DIR, storedName);

        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setFilePath(path.toString());
        book.setFileName(file.getOriginalFilename());
        book.setFileSize(file.getSize());
        book.setUploadedAt(LocalDateTime.now());

        service.save(book);

        return ResponseEntity.ok(Map.of("message", "PDF uploaded successfully"));
    }
}

