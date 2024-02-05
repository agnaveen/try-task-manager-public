package com.techriseyou.taskmanager.controller;

import com.techriseyou.taskmanager.dto.ApiResponse;
import com.techriseyou.taskmanager.dto.LoginRequest;
import com.techriseyou.taskmanager.dto.SignUpRequest;
import com.techriseyou.taskmanager.dto.UserDTO;
import com.techriseyou.taskmanager.entity.User;
import com.techriseyou.taskmanager.mapper.UserMapper;
import com.techriseyou.taskmanager.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDTO>> signUp(@RequestBody SignUpRequest signUpRequest,
                                                       @RequestParam String otp) {
        User user = userInfoService.signUp(signUpRequest, otp);
        UserDTO userDTO = userMapper.toDto(user);
        return ResponseEntity.ok(ApiResponse.success(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest loginRequest) {
        String token = userInfoService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success(token));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<UserDTO>> getUserDetails() {
        UserDTO user = userInfoService.getUserDetails();
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<UserDTO>> saveUserDetails(@RequestBody UserDTO userRequest) {
        User user = userInfoService.saveUser(userRequest);
        UserDTO userdto = userMapper.toDto(user);
        return ResponseEntity.ok(ApiResponse.success(userdto));
    }
}
