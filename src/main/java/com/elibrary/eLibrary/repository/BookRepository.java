package com.elibrary.eLibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elibrary.eLibrary.model.Book;



public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String keyword);
}
