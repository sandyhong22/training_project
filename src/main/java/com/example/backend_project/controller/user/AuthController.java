package com.example.backend_project.controller.user;

import com.example.backend_project.controller.user.dto.reponse.LoginResponse;
import com.example.backend_project.controller.user.dto.request.LoginRequest;
import com.example.backend_project.controller.user.dto.request.UserRegisterRequest;
import com.example.backend_project.controller.user.service.LoginService;
import com.example.backend_project.controller.user.service.RegisterService;
import com.example.backend_project.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "認證相關Api")
@RequestMapping("/auth")
public class AuthController {
    private final LoginService loginService;
    private final RegisterService registerService;
    
    @Operation(summary = "登入")
    @PostMapping("/login")
    public ResponseDto<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseDto.success(loginService.login(loginRequest));
    }
    
    @Operation(summary = "註冊")
    @PostMapping("/register")
    public ResponseDto<String> register(HttpServletRequest request,@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseDto.success(registerService.register(request,userRegisterRequest));
    }
}
