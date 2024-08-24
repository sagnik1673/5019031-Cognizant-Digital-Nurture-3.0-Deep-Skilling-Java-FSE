package com.bookstoreapi.integration;

import com.bookstoreapi.model.Book;
import com.bookstoreapi.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test") // Ensure that the 'test' profile is used for the test environment
@Transactional
public class BookIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setup() {
        // Clean up the database before each test
        bookRepository.deleteAll();
    }

    @Test
    public void testCreateBook() throws Exception {
        Book book = new Book(null, "New Book Title", "New Author", 29.99, "0987654321");
        
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("New Book Title"))
                .andExpect(jsonPath("$.author").value("New Author"));
    }

    @Test
    public void testGetBookById() throws Exception {
        Book book = new Book(null, "Sample Book", "Sample Author", 15.99, "1234567890");
        book = bookRepository.save(book);

        mockMvc.perform(get("/api/books/{id}", book.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Sample Book"))
                .andExpect(jsonPath("$.author").value("Sample Author"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book book = new Book(null, "Old Title", "Old Author", 19.99, "1122334455");
        book = bookRepository.save(book);

        book.setTitle("Updated Title");
        book.setAuthor("Updated Author");

        mockMvc.perform(put("/api/books/{id}", book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.author").value("Updated Author"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        Book book = new Book(null, "To be Deleted", "Author", 12.99, "9876543210");
        book = bookRepository.save(book);

        mockMvc.perform(delete("/api/books/{id}", book.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/books/{id}", book.getId()))
                .andExpect(status().isNotFound());
    }
}