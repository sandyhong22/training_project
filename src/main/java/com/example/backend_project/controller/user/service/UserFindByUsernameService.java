package com.example.backend_project.controller.user.service;


import com.example.backend_project.controller.user.dto.reponse.UserResponse;
import com.example.backend_project.entity.User;
import com.example.backend_project.expection.UserNotFoundException;
import com.example.backend_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserFindByUsernameService {
    private final UserRepository userRepository;
    
    public UserResponse findByUsername(String username) {
        if (Boolean.FALSE.equals(userRepository.existsByUsername(username))) {
            throw new UserNotFoundException();
        }
        User user = userRepository.findByUsername(username);
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setName(user.getName());
        
        return userResponse;
    }
}
