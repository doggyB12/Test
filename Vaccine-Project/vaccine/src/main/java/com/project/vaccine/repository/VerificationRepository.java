package com.project.vaccine.repository;


import com.project.vaccine.entity.User;
import com.project.vaccine.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    Optional<Verification> findByTokenValue(String tokenValue);
    Optional<Verification> findByUser(User user);
}
