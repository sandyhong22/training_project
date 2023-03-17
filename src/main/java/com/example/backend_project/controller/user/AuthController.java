package com.example.backend_project.controller.user;

import com.example.backend_project.controller.user.dto.reponse.LoginResponse;
import com.example.backend_project.controller.user.dto.request.LoginRequest;
import com.example.backend_project.controller.user.dto.request.UserRegisterRequest;
import com.example.backend_project.controller.user.service.LoginService;
import com.example.backend_project.controller.user.service.RegisterService;
import com.example.backend_project.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final LoginService loginService;
    private final RegisterService registerService;
    
    @PostMapping("/login")
    public ResponseDto<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseDto.success(loginService.login(loginRequest));
    }
    
    @PostMapping("/register")
    public ResponseDto<String> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseDto.success(registerService.register(userRegisterRequest));
    }
}
