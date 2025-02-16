package com.project.vaccine.service;

import com.project.vaccine.dto.UserDTO;
import com.project.vaccine.entity.User;
import com.project.vaccine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    public boolean updateUser(long id, UserDTO userDTO){

        User user = userRepository.findUserById(id).orElse(null);
        if(user != null){
            return true;
        }
        return false;
    }
}
