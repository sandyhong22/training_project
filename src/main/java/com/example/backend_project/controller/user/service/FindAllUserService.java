package com.example.backend_project.controller.user.service;


import com.example.backend_project.entity.User;
import com.example.backend_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindAllUserService {
    private final UserRepository userRepository;
    
    public List<User> getAllAccount() {
        return userRepository.findAll();
    }
    
}
