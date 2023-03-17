package com.example.backend_project.controller.user.service;

import com.example.backend_project.controller.user.dto.reponse.LoginResponse;
import com.example.backend_project.controller.user.dto.request.LoginRequest;
import com.example.backend_project.entity.User;
import com.example.backend_project.expection.AuthenticationException;
import com.example.backend_project.expection.LoginException;
import com.example.backend_project.expection.PasswordException;
import com.example.backend_project.repository.UserMapper;
import com.example.backend_project.repository.UserRepository;
import com.example.backend_project.config.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            ));
            
        } catch (AuthenticationException | DisabledException | LockedException e) {
            throw new AuthenticationException(e.getMessage());
        }
        Boolean userIsExist = userRepository.existsByUsername(loginRequest.getUsername());
        
        if (Boolean.FALSE.equals(userIsExist)) {
            throw new LoginException();
        }
        User user = userMapper.findByUsername(loginRequest.getUsername());
        String jwtToken;
        boolean checkPassword = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (checkPassword) {
          jwtToken = jwtService.generateToken(user);
        } else {
            log.info("password not match");
            throw new PasswordException();

        }
        
        return LoginResponse.builder().token(jwtToken).build();
        
    }
}
