package com.example.backend_project.controller.user.service;


import com.example.backend_project.controller.user.dto.request.UserRegisterRequest;
import com.example.backend_project.entity.User;
import com.example.backend_project.expection.UserRegisterException;
import com.example.backend_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterService {
    
    private final UserRepository userRepository;
    
    public User register(UserRegisterRequest userRegisterRequest) {
        
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userRegisterRequest.getUsername()))) {
            throw new UserRegisterException();
        }
        User user = new User();
        String encodedPassword = BCrypt.hashpw(userRegisterRequest.getPassword(), BCrypt.gensalt());
        user.setName(userRegisterRequest.getName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setUsername(userRegisterRequest.getUsername());
        user.setCreatedDate(LocalDateTime.now());
        user.setLastModifiedDate(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }
}
