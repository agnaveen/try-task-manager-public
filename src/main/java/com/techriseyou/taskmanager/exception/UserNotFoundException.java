package com.techriseyou.taskmanager.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    // You can add more constructors or methods if needed
}