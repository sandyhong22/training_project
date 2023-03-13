package com.example.backend_project.controller.user.service;


import com.example.backend_project.controller.user.dto.reponse.UserRegisterResponse;
import com.example.backend_project.entity.User;
import com.example.backend_project.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class UserFindByUsernameService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTService jwtService;

    public UserRegisterResponse findByUsername(HttpServletRequest request, String username) throws AuthException {
        Claims tokenInfo = jwtService.decodeToken(request);
        User user = userRepository.findByUsername(username);
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        userRegisterResponse.setUsername(user.getUsername());
        userRegisterResponse.setPassword(user.getPassword());
        return userRegisterResponse;
    }
}
