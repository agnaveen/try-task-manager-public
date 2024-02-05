package com.techriseyou.taskmanager.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private String error;

    // Constructors, getters, setters, etc.

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public static <T> ApiResponse<T> error(String errorMessage) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setError(errorMessage);
        return response;
    }
}
