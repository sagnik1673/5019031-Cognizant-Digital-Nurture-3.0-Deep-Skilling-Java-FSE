package com.bookstoreapi.service;

import com.bookstoreapi.entity.Book;
import com.bookstoreapi.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) {
        Book existingBook = getBookById(id);
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPrice(book.getPrice());
        existingBook.setIsbn(book.getIsbn());
        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> filterBooks(String title, String author) {
        if (title != null && !title.isEmpty()) {
            return bookRepository.findByTitleContaining(title);
        } else if (author != null && !author.isEmpty()) {
            return bookRepository.findByAuthorContaining(author);
        } else {
            return bookRepository.findAll();
        }
    }
}