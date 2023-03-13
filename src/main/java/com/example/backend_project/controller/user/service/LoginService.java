package com.example.backend_project.controller.user.service;

import com.example.backend_project.controller.user.dto.reponse.LoginResponse;
import com.example.backend_project.controller.user.dto.request.LoginRequest;
import com.example.backend_project.entity.User;
import com.example.backend_project.expection.LoginException;
import com.example.backend_project.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class LoginService {

    @Autowired
    UserRepository userRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        Boolean userIsExist = userRepository.existsByUsername(loginRequest.getUsername());

        if (Boolean.FALSE.equals(userIsExist)) {
            throw new LoginException();
        }

        User user = userRepository.findByUsername(loginRequest.getUsername());
        LoginResponse loginResponse = new LoginResponse();
        Date expireDate =
                //設定30min過期
                new Date(System.currentTimeMillis() + 30 * 60 * 1000);
        String jwtToken = Jwts.builder()
                .setSubject(user.getEmail())
                .setSubject(user.getUsername())
                .setSubject(user.getName())
                .setExpiration(expireDate)
                //MySecret是自訂的私鑰，HS512是自選的演算法，可以任意改變
                .signWith(SignatureAlgorithm.HS512, "MySecret")
                .compact();


        loginResponse.setToken(jwtToken);
        return loginResponse;

    }

}
