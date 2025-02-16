package com.project.vaccine.controller;

import com.project.vaccine.dto.UserDTO;
import com.project.vaccine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/user")
public class UserAPI {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> viewProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        UserDTO userDTO = userService.getUserFromToken(userDetails);
        return ResponseEntity.ok(userDTO);
    }
}
