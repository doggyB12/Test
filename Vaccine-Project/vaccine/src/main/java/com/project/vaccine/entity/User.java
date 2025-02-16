package com.project.vaccine.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.project.vaccine.enums.RoleEnum;
import com.project.vaccine.enums.UserStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;


@Entity
@Data
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment ID
    private Long id;

    @Column(unique = true)
    @Pattern(regexp = "^[0-9A-Za-z]{6,16}$", message = "Username must contain 6-16 characters")
    private String username;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
            message = "Password must contain at least 1 uppercase letter, 1 lowercase letter, 1 number and 8 characters")
    private String password;

    @Column(unique = true)
    @Pattern(regexp = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email")
    private String email;


    @NotBlank(message = "Name is required")
    private String name;

    @Column(unique = true)
    @NotBlank(message = "Phone number is required")
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

    private LocalDateTime date_created;

    private String pendingEmail; // Can be null

    private UserStatusEnum status = UserStatusEnum.INACTIVE;

    @Enumerated(EnumType.STRING)
    private RoleEnum role = RoleEnum.USER;  // default role is USER


    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
    @JsonSubTypes({
            @Type(value = SimpleGrantedAuthority.class, name = "SimpleGrantedAuthority")
    })

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.role.toString()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
