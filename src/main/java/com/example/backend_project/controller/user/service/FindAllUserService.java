package com.example.backend_project.controller.user.service;


import com.example.backend_project.entity.User;
import com.example.backend_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllUserService {
    private final UserRepository userRepository;
    
    public List<User> getAllAccount() {
        return userRepository.findAll();
    }
    
}
