package com.techriseyou.taskmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;

    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL)
    private Set<Task> assignedTasks;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private Set<Task> createdTasks;
}

