package com.techriseyou.taskmanager.dto;


import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String mobile;
    private String name;
    private String email;
    private String bloodGroup;
    private String location;
    private String pincode;
    private Double latitude;
    private Double longitude;
 }

