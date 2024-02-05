package com.techriseyou.taskmanager.service;

import com.techriseyou.taskmanager.dto.LoginRequest;
import com.techriseyou.taskmanager.dto.SignUpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.techriseyou.taskmanager.entity.User;
import com.techriseyou.taskmanager.exception.InvalidCredentialsException;
import com.techriseyou.taskmanager.exception.UserAlreadyExistsException;
import com.techriseyou.taskmanager.exception.UserNotFoundException;
import com.techriseyou.taskmanager.repository.UserRepository;
import com.techriseyou.taskmanager.security.JwtService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserInfoServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserInfoService userInfoService;

    private final Integer TEST_USER_ID = 1;


    private final String TEST_USER_NAME = "mail2agn@gmail.com";
    private final String TEST_PASSWORD = "password";

    private SignUpRequest signUpRequest;


    @BeforeEach
    public void setUp() {
        signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("mail2agn@gmail.com");
        signUpRequest.setPassword("testPassword");
        // Initialization code, if needed.
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername(TEST_USER_NAME)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userInfoService.loadUserByUsername(TEST_USER_NAME));
    }

    @Test
    void testSignUp_UserAlreadyExists() {
        when(userRepository.findByUsername(TEST_USER_NAME)).thenReturn(Optional.of(new User()));

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername(TEST_USER_NAME);

        assertThrows(UserAlreadyExistsException.class, () -> userInfoService.signUp(signUpRequest, "123456"));
    }

    @Test
    void testLogin_UserNotFound() {
        when(userRepository.findByUsername(TEST_USER_NAME)).thenReturn(Optional.empty());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(TEST_USER_NAME);

        assertThrows(UserNotFoundException.class, () -> userInfoService.login(loginRequest));
    }

    @Test
    void testLogin_InvalidCredentials() {
        User user = new User();
        user.setUsername(TEST_USER_NAME);
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername(TEST_USER_NAME)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(TEST_USER_NAME);
        loginRequest.setPassword(TEST_PASSWORD);

        assertThrows(InvalidCredentialsException.class, () -> userInfoService.login(loginRequest));
    }

    @Test
    void testValidateUser_UserNotFound() {
        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userInfoService.validateUser(TEST_USER_ID));
    }

    @Test
    void testValidateUser_UserFound() {
        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(new User()));

        assertDoesNotThrow(() -> userInfoService.validateUser(TEST_USER_ID));
    }

    @Test
    void testSignUp_ValidOtp() {
        // Given
        String validOtp = "123456";
        String encodedPassword = "encodedPassword";

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty()); // Assuming the user does not exist.
        when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L); // Set an ID to simulate saving.
            return savedUser;
        });

        // When
        User result = userInfoService.signUp(signUpRequest, validOtp);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId()); // User has been 'saved' so should have an ID.
        assertEquals(signUpRequest.getUsername(), result.getUsername()); // User's mobile should match the request.
        assertEquals(encodedPassword, result.getPassword()); // Password should have been encoded.
        assertEquals("USER", result.getRole()); // Role should be USER.
    }

    @Test
    void testSignUp_InvalidOtp() {
        assertThrows(RuntimeException.class, () -> userInfoService.signUp(signUpRequest, "wrongOtp"));
    }

    @Test
    void testLogin_SuccessfulLogin() {
        // Given
        String mockUsername = "mail2agn@gmail.com";
        String mockPassword = "password";
        String mockEncodedPassword = "encodedPassword";
        String mockToken = "mockToken";

        LoginRequest mockLoginRequest = new LoginRequest();
        mockLoginRequest.setUsername(mockUsername);
        mockLoginRequest.setPassword(mockPassword);

        User mockUser = new User();
        mockUser.setUsername(mockUsername);
        mockUser.setPassword(mockEncodedPassword);

        when(userRepository.findByUsername(mockUsername)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(mockPassword, mockEncodedPassword)).thenReturn(true);
        when(jwtService.generateToken(eq(mockUsername), any(Date.class))).thenReturn(mockToken);

        // When
        String resultToken = userInfoService.login(mockLoginRequest);

        // Then
        assertEquals(mockToken, resultToken);
        verify(jwtService, times(1)).generateToken(eq(mockUsername), any(Date.class)); // Verify that generateToken is called once with correct mobile and any Date
    }
}
