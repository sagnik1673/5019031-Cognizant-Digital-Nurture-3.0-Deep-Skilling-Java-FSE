package com.bookstoreapi.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    // No-args constructor
    public CustomerDTO() {
    }

    // All-args constructor
    public CustomerDTO(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}