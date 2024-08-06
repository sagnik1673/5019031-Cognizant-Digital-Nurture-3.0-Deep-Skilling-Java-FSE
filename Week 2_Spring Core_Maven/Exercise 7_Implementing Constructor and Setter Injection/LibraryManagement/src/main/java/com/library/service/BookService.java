package com.library.service;

import org.springframework.stereotype.Service;
import com.library.repository.BookRepository;

@Service
public class BookService {
    private BookRepository bookRepository;

    // Constructor for dependency injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Default constructor (required for setter injection)
    public BookService() {}

    // Setter method for dependency injection
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void saveBook() {
        bookRepository.save();
        System.out.println("BookService: saveBook method called.");
    }
}