package com.bookstoreapi.security;

import com.bookstoreapi.service.UserService;
import com.bookstoreapi.util.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class JwtAuthenticationFilterTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(jwtAuthenticationFilter)
                .build();
    }

    @Test
    public void testDoFilterInternal_ValidToken_ShouldSetAuthentication() throws Exception {
        String token = "valid.jwt.token";
        Authentication auth = new UsernamePasswordAuthenticationToken("user", null, null);
        when(jwtTokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn(token);
        when(jwtTokenProvider.validateToken(any(String.class))).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(any(String.class))).thenReturn(auth);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        verify(jwtTokenProvider, times(1)).resolveToken(any(HttpServletRequest.class));
        verify(jwtTokenProvider, times(1)).validateToken(any(String.class));
        verify(jwtTokenProvider, times(1)).getAuthentication(any(String.class));

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        assertNotNull(authentication);
        assertEquals("user", authentication.getName());
    }

    @Test
    public void testDoFilterInternal_InvalidToken_ShouldReturnUnauthorized() throws Exception {
        String token = "invalid.jwt.token";
        when(jwtTokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn(token);
        when(jwtTokenProvider.validateToken(any(String.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());

        verify(jwtTokenProvider, times(1)).resolveToken(any(HttpServletRequest.class));
        verify(jwtTokenProvider, times(1)).validateToken(any(String.class));
    }

    @Test
    public void testDoFilterInternal_NoToken_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(status().isUnauthorized());

        verify(jwtTokenProvider, never()).resolveToken(any(HttpServletRequest.class));
        verify(jwtTokenProvider, never()).validateToken(any(String.class));
    }
}