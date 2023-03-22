package com.example.backend_project.controller.user.service;


import com.example.backend_project.controller.user.dto.request.UserRegisterRequest;
import com.example.backend_project.entity.User;
import com.example.backend_project.enums.UserRole;
import com.example.backend_project.expection.AuthenticationException;
import com.example.backend_project.expection.UserRegisterException;
import com.example.backend_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterService {
    
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final MessageSource messageSource;
    
    public String register(Locale locale, UserRegisterRequest userRegisterRequest) {
        log.info("local{}",locale);
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userRegisterRequest.getUsername()))) {
            String errorMessage = messageSource.getMessage("ER00001", null, "", locale);
            throw new AuthenticationException(errorMessage);
        }
        User user = new User();
        user.setName(userRegisterRequest.getName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        user.setRole(UserRole.USER);
        user.setUsername(userRegisterRequest.getUsername());
        user.setCreatedDate(LocalDateTime.now());
        user.setLastModifiedDate(LocalDateTime.now());
        userRepository.save(user);
        
        return messageSource.getMessage("ER00005", null, "",locale);
    }
}
