package com.project.vaccine.entity;


import com.project.vaccine.enums.VerificationEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    private String tokenValue;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationEnum verificationMethod;  // verify, reset, update

    private int attemptCount; // Number of attempts to verify token, default 1 when token is created

    private LocalDateTime lockTime; // Lock time when user reach max attempt

}
