package com.techriseyou.taskmanager.entity;


 import jakarta.persistence.*;

 import java.time.LocalDateTime;

@Entity
@Table(name = "otp_data")
public class OTPData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 15)
    private String mobile;

    @Column(nullable = false, length = 6)
    private String otp;

    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OTPPurpose purpose;

    @Column(nullable = false)
    private Boolean used;

    // Getters, setters, and other methods
}

enum OTPPurpose {
    signup, forgot_password
}

