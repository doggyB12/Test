package com.project.vaccine.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @Pattern(regexp = "^[0-9A-Za-z]{6,16}$", message = "Username must contain 6-16 characters")
    private String username;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
            message = "Password must contain at least 1 uppercase letter, 1 lowercase letter, 1 number and 8 characters")
    private String password;
}
