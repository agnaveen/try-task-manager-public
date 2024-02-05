package com.techriseyou.taskmanager.service;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.techriseyou.taskmanager.security.JwtService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void testGenerateToken() {
        String userName = "user";
        String token = jwtService.generateToken(userName,new Date(System.currentTimeMillis() + 1000 * 60 * 3));
        assertNotNull(token);
    }

    @Test
    void testExtractUsername() {
        String userName = "user";
        String token = jwtService.generateToken(userName,new Date(System.currentTimeMillis() + 1000 * 60 * 3));
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(userName, extractedUsername);
    }

    @Test
    void testExtractExpiration() {
        String userName = "user";
        String token = jwtService.generateToken(userName,new Date(System.currentTimeMillis() + 1000 * 60 * 3));
        Date expiration = jwtService.extractExpiration(token);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void testValidateToken() {
        String userName = "user";
        String token = jwtService.generateToken(userName,new Date(System.currentTimeMillis() + 1000 * 60 * 3));
        UserDetails userDetails = new User(userName, "password", Collections.emptyList());
        Boolean isValid = jwtService.validateToken(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void testValidateToken_Expired() throws InterruptedException {
        String userName = "user";
        String token = jwtService.generateToken(userName, new Date(System.currentTimeMillis() + 1000 * 10 * 1));
        Thread.sleep(1000 * 15 * 1);
        UserDetails userDetails = new User(userName, "password", Collections.emptyList());

        assertThrows(ExpiredJwtException.class, () -> {
            jwtService.validateToken(token, userDetails);
        });
    }

    // You can add more tests, especially negative cases to handle different scenarios.
}

