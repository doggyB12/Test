package com.project.vaccine.service;


import com.project.vaccine.dto.UserDTO;
import com.project.vaccine.entity.User;
import com.project.vaccine.exception.NotFoundException;
import com.project.vaccine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDTO getUserFromToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));

        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setPhone(user.getPhone());
        userDTO.setAddress(user.getAddress());
        userDTO.setDob(user.getDob());
        userDTO.setGender(user.getGender());
        userDTO.setUsername(user.getUsername());

        return userDTO;
    }

}
