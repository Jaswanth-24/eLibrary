    package com.elibrary.eLibrary.service;

    import java.util.List;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import com.elibrary.eLibrary.model.Book;
    import com.elibrary.eLibrary.repository.BookRepository;

    @Service
    public class BookService {

        @Autowired
        private BookRepository repo;

        public List<Book> getAll() {
            return repo.findAll();
        }

        public List<Book> search(String q) {
            return repo.findByTitleContainingIgnoreCase(q);
        }

        public Book get(Long id) {
            return repo.findById(id).orElse(null);
        }
    }
