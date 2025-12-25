package com.elibrary.eLibrary.controller;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.elibrary.eLibrary.model.Book;
import com.elibrary.eLibrary.service.BookService;
import com.elibrary.eLibrary.service.SupabaseStorageService;

@RestController
@RequestMapping("/api/books")
@CrossOrigin("*")
public class BookController {

    @Autowired
    private SupabaseStorageService storageService;

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
    public ResponseEntity<Void> download(@PathVariable Long id) {

        Book book = service.get(id);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(book.getFilePath()))
                .build();
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

        // Upload to Supabase
        String storedName =
                UUID.randomUUID() + "_" + file.getOriginalFilename();

        String publicUrl =
                storageService.upload(file, storedName);

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setFilePath(publicUrl);          // Supabase public URL
        book.setFileName(file.getOriginalFilename());
        book.setFileSize(file.getSize());
        book.setUploadedAt(LocalDateTime.now());

        service.save(book);

        return ResponseEntity.ok(
                Map.of("message", "PDF uploaded successfully")
        );
    }
}
