package com.example.backend_project.controller.user.service;


import com.example.backend_project.controller.user.dto.reponse.UserRegisterResponse;
import com.example.backend_project.entity.User;
import com.example.backend_project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserFindByUsernameService {
    @Autowired
    UserRepository userRepository;

    public UserRegisterResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        userRegisterResponse.setUsername(user.getUsername());
        userRegisterResponse.setPassword(user.getPassword());
        return userRegisterResponse;
    }
}
