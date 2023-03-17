package com.example.backend_project.controller.user.service;

import com.example.backend_project.controller.user.dto.request.ResetPasswordRequest;
import com.example.backend_project.entity.User;
import com.example.backend_project.expection.LoginException;
import com.example.backend_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ResetPasswordService {
    
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    public String resetPassword(ResetPasswordRequest resetPasswordRequest) {
        String username = resetPasswordRequest.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(LoginException::new);
        
        if (passwordEncoder.matches(resetPasswordRequest.getOldPassword(), user.getPassword())) {
            String newPassword = resetPasswordRequest.getNewPassword();
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedNewPassword);
            userRepository.save(user);
            return "Password changed successfully";
        } else {
            return "Password change failed";
        }
    }
}
