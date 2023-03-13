package com.example.backend_project.controller.user.service;


import com.example.backend_project.entity.User;
import com.example.backend_project.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@Service
public class UserFindByIdService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTService jwtService;

    public Optional<User> findById(HttpServletRequest request, Long id) throws AuthException {
        Claims tokenInfo = jwtService.decodeToken(request);
        return userRepository.findById(id);
    }
}
