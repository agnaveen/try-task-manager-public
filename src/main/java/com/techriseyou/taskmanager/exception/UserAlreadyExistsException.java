package com.techriseyou.taskmanager.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    // You can add more constructors or methods if needed
}