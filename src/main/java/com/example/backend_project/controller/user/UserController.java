package com.example.backend_project.controller.user;

import com.example.backend_project.controller.user.dto.reponse.LoginResponse;
import com.example.backend_project.controller.user.dto.reponse.UserResponse;
import com.example.backend_project.controller.user.dto.request.LoginRequest;
import com.example.backend_project.controller.user.dto.request.UserRegisterRequest;
import com.example.backend_project.controller.user.service.FindAllUserService;
import com.example.backend_project.controller.user.service.LoginService;
import com.example.backend_project.controller.user.service.RegisterService;
import com.example.backend_project.controller.user.service.UserFindByUsernameService;
import com.example.backend_project.dto.ResponseDto;
import com.example.backend_project.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final FindAllUserService accountService;
    
    private final RegisterService registerService;
    
    private final UserFindByUsernameService userFindByUsername;
    
    private final LoginService loginService;
    
    @GetMapping("/account")
    public ResponseDto<List<User>> getAllAccount() {
        return ResponseDto.success(accountService.getAllAccount());
    }
    
    
    @PostMapping("/register")
    public ResponseDto<User> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseDto.success(registerService.register(userRegisterRequest));
    }
    
    
    @GetMapping("/account/username/{username}")
    public ResponseDto<UserResponse> findByUsername(@PathVariable("username") String username) {
        return ResponseDto.success(userFindByUsername.findByUsername(username));
    }
    
    @PostMapping("/login")
    public ResponseDto<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseDto.success(loginService.login(loginRequest));
    }
}
