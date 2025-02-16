package com.project.vaccine.dto;


import com.project.vaccine.enums.RoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;

    @Pattern(regexp = "^[0-9A-Za-z]{6,16}$", message = "Username must contain 6-16 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
            message = "Password must contain at least 1 uppercase letter, 1 lowercase letter, 1 number and 8 characters")
    private String password;

    @NotBlank(message = "Name is required")
    private String name;

    @Pattern(regexp = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email")
    private String email;

    @Pattern(regexp = "(84|0[35789])[0-9]{8}", message = "Invalid phone number")
    private String phone;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Invalid date of birth")
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    // Custom validation
    @AssertTrue(message = "You must be at least 18 years old to register")
    private boolean isAdult() {
        return dob != null && Period.between(dob, LocalDate.now()).getYears() >= 18;
    }
}
