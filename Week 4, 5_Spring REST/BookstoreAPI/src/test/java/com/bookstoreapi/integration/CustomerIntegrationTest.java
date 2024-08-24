package com.bookstoreapi.integration;

import com.bookstoreapi.model.Customer;
import com.bookstoreapi.repository.CustomerRepository;
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
public class CustomerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setup() {
        // Clean up the database before each test
        customerRepository.deleteAll();
    }

    @Test
    public void testCreateCustomer() throws Exception {
        Customer customer = new Customer(null, "John Doe", "john.doe@example.com");
        
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        Customer customer = new Customer(null, "Jane Doe", "jane.doe@example.com");
        customer = customerRepository.save(customer);

        mockMvc.perform(get("/api/customers/{id}", customer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer customer = new Customer(null, "Michael Smith", "michael.smith@example.com");
        customer = customerRepository.save(customer);

        customer.setName("Michael Johnson");
        customer.setEmail("michael.johnson@example.com");

        mockMvc.perform(put("/api/customers/{id}", customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Michael Johnson"))
                .andExpect(jsonPath("$.email").value("michael.johnson@example.com"));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        Customer customer = new Customer(null, "To be Deleted", "deleted@example.com");
        customer = customerRepository.save(customer);

        mockMvc.perform(delete("/api/customers/{id}", customer.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/customers/{id}", customer.getId()))
                .andExpect(status().isNotFound());
    }
}