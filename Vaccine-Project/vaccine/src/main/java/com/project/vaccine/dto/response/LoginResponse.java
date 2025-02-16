package com.project.vaccine.dto.response;

import com.project.vaccine.enums.RoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    private String accessToken;

    private String refreshToken;

}
