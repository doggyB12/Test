package com.project.vaccine.config;

import com.project.vaccine.dto.UserDTO;
import com.project.vaccine.service.AuthenticationService;
import com.project.vaccine.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class Filter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String token = null;

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }// token is null or not start with Bearer

        try {
            token = authorizationHeader.substring(7);
            UserDTO userDTO = tokenService.getUserFromToken(token);

            if (tokenService.isValidateToken(token, userDTO)
                    && !tokenService.isTokenExpired(token)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = User.builder()
                        .username(userDTO.getUsername())
                        .password("")
                        .roles(userDTO.getRole().toString())
                        .build();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } // set authentication
            else if (tokenService.isTokenExpired(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                return;
            }

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        filterChain.doFilter(request, response);
    }

}
