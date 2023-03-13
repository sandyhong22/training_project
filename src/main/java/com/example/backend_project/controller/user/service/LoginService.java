package com.example.backend_project.controller.user.service;

import com.example.backend_project.controller.user.dto.reponse.LoginResponse;
import com.example.backend_project.controller.user.dto.request.LoginRequest;
import com.example.backend_project.entity.User;
import com.example.backend_project.expection.LoginException;
import com.example.backend_project.expection.PasswordException;
import com.example.backend_project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTService jwtService;

    public LoginResponse login(LoginRequest loginRequest) {
        Boolean userIsExist = userRepository.existsByUsername(loginRequest.getUsername());

        if (Boolean.FALSE.equals(userIsExist)) {
            throw new LoginException();
        }
        User user = userRepository.findByUsername(loginRequest.getUsername());
        boolean checkPassword = BCrypt.checkpw(loginRequest.getPassword(), user.getPassword());
        LoginResponse loginResponse = new LoginResponse();
        if (checkPassword) {
            String jwtToken = jwtService.generateToken(user);
            loginResponse.setToken(jwtToken);
        } else {
            log.info("password not match");
            throw new PasswordException();

        }
        return loginResponse;

    }

}
