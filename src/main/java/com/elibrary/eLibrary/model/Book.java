package com.elibrary.eLibrary.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String author;
    private String category;

    @Column(nullable = false)
    private String filePath;     // server path

    private String fileName;     // original PDF name
    private Long fileSize;       // bytes

    private LocalDateTime uploadedAt;
}
