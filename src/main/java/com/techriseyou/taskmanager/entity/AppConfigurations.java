package com.techriseyou.taskmanager.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "app_configurations")
public class AppConfigurations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String config_name;

    private String config_value;

    // Getters, setters, and other methods
}

