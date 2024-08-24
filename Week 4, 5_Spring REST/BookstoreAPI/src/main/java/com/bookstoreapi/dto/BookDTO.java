package com.bookstoreapi.dto;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private Double price;
    private String isbn;

    // No-args constructor
    public BookDTO() {
    }

    // All-args constructor
    public BookDTO(Long id, String title, String author, Double price, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.isbn = isbn;
    }
}