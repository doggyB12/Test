package com.project.vaccine.controller;



import com.project.vaccine.entity.Verification;
import com.project.vaccine.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/verification")
public class VerificationAPI {

    @Autowired
    private VerificationService verificationService;


    // Admin, Staff, User
    @GetMapping("/register/confirm")
    public ResponseEntity<String> sendVerificationEmail(@RequestParam String token) {
        try {
            String message = verificationService.verifyToken(token);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Staff, User
    @GetMapping("/register/verify")
    public ResponseEntity<String> registerVerification(@RequestParam String email) {
        try {
            String message = verificationService.registerVerification(email, "REGISTER");
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    // Staff, User
    @GetMapping("/register/re-verify")
    public ResponseEntity<String> resetVerification(@RequestParam String email) {
        try {
            String message = verificationService.resetVerification(email);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Verification> v = verificationService.getAllTokens();
        return ResponseEntity.ok(v);
    }
}
