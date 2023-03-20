package com.example.backend_project.controller.user.service;


import com.example.backend_project.controller.user.dto.request.UserRegisterRequest;
import com.example.backend_project.entity.User;
import com.example.backend_project.enums.UserRole;
import com.example.backend_project.expection.UserRegisterException;
import com.example.backend_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegisterService {
    
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    
    public String register(UserRegisterRequest userRegisterRequest) {
        
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userRegisterRequest.getUsername()))) {
            throw new UserRegisterException();
        }
        User user = new User();
        user.setName(userRegisterRequest.getName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        user.setRole(UserRole.ADMIN);
        user.setUsername(userRegisterRequest.getUsername());
        user.setCreatedDate(LocalDateTime.now());
        user.setLastModifiedDate(LocalDateTime.now());
        userRepository.save(user);
        
        return "registration success ";
    }
}
