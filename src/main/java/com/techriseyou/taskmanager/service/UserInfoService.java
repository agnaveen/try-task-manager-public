package com.techriseyou.taskmanager.service;


import com.techriseyou.taskmanager.UserAppDetails;
import com.techriseyou.taskmanager.dto.LoginRequest;
import com.techriseyou.taskmanager.dto.SignUpRequest;
import com.techriseyou.taskmanager.dto.UserDTO;
import com.techriseyou.taskmanager.exception.InvalidCredentialsException;
import com.techriseyou.taskmanager.exception.UserAlreadyExistsException;
import com.techriseyou.taskmanager.exception.UserNotFoundException;
import com.techriseyou.taskmanager.repository.UserRepository;
import com.techriseyou.taskmanager.security.JwtService;
import com.techriseyou.taskmanager.entity.User;
import com.techriseyou.taskmanager.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private PasswordEncoder passwordEncoder;


    @Autowired
    UserMapper userMapper;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(mobile);

        // Converting userDetail to UserDetails
        return user.map(UserAppDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + mobile));
    }


    private void validateOtp(String otp) {
        if (!"123456".equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }
    }

    private void validateUserDoesNotExist(String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User already found, please try logging in");
        }
    }


    public User signUp(SignUpRequest signUpRequest, String otp) {

        validateOtp(otp);
        validateUserDoesNotExist(signUpRequest.getUsername());

        return createUser(signUpRequest);
    }

    public User saveUser(UserDTO userRequest) {
        User user = userRepository.getReferenceById(userRequest.getId());
        user.setUsername(userRequest.getUsername());
        user.setRole("USER");
        return userRepository.save(user);
    }

    private User createUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);
    }

    public String login(LoginRequest loginRequest) {
        Optional<User> existingUser = userRepository.findByUsername(loginRequest.getUsername());
        if (!existingUser.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        if(existingUser.isPresent()) {
            User user = existingUser.get();
            System.out.println();
            if(!(user.getUsername().equalsIgnoreCase(loginRequest.getUsername()) && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))) {
                throw new InvalidCredentialsException("Invalid user credentials");
            }
        }
        return jwtService.generateToken(loginRequest.getUsername(),new Date(System.currentTimeMillis() + 1000 * 60 * 30));
    }

    public void validateUser(Integer userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()) {
            throw new UserNotFoundException("user not found, try creating from valid user");
        }
    }

    public UserDTO getUserDetails(){
        return userMapper.toDto(getCurrentUserDetails().get());
    }

    public String getCurrentUser() {
        String username = null;
        try {
            if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null &&
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
                if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
                    username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // "this actually inserts anonymousUser"
                } else if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
                    User userDetails = (User) SecurityContextHolder.getContext().getAuthentication()
                            .getPrincipal();
                    username = userDetails.getUsername();
                } else {
                    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                            .getPrincipal();
                    username = userDetails.getUsername();
                }
            }
        } catch (Exception e) {
            // may be for anonymous user this can get triggered with classnotfound exception
            throw new UserNotFoundException(e.getMessage());
        }
        return username;
    }

    public Optional<User> getCurrentUserDetails() {
        String mobile = getCurrentUser();
        return userRepository.findByUsername(mobile);
    }


}
