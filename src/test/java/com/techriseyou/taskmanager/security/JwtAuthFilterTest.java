package com.techriseyou.taskmanager.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.techriseyou.taskmanager.service.UserInfoService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.*;

public class JwtAuthFilterTest {

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserInfoService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilterInternal_ValidToken() throws Exception {
        String mockToken = "mockToken";
        String mockUsername = "mockUsername";
        String authHeader = "Bearer " + mockToken;

        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(jwtService.extractUsername(mockToken)).thenReturn(mockUsername);
        UserDetails mockUserDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(mockUsername)).thenReturn(mockUserDetails);
        when(jwtService.validateToken(mockToken, mockUserDetails)).thenReturn(true);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assert(SecurityContextHolder.getContext().getAuthentication() != null);
    }

    @Test
    void testDoFilterInternal_InvalidToken() throws Exception {
        String mockToken = "mockToken";
        String authHeader = "Bearer " + mockToken;

        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(jwtService.extractUsername(mockToken)).thenReturn(null);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assert(SecurityContextHolder.getContext().getAuthentication() == null);
    }
}
