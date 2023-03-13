package com.example.backend_project.controller.user;

import com.example.backend_project.controller.user.dto.reponse.LoginResponse;
import com.example.backend_project.controller.user.dto.request.LoginRequest;
import com.example.backend_project.controller.user.service.*;
import com.example.backend_project.controller.user.dto.reponse.UserRegisterResponse;
import com.example.backend_project.controller.user.dto.request.UserRegisterRequest;
import com.example.backend_project.dto.ResponseDto;
import com.example.backend_project.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    FindAllUserService accountService;

    @Autowired
    RegisterService registerService;

    @Autowired
    UserFindByIdService userFindByIdService;

    @Autowired
    UserFindByUsernameService userFindByUsername;

    @Autowired
    LoginService loginService;

    @GetMapping("/account")
    public ResponseDto<List<User>> getAllAccount(HttpServletRequest request) throws AuthException {
        return ResponseDto.success(accountService.getAllAccount(request));
    }


    @PostMapping("/register")
    public ResponseDto<List<User>> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseDto.success(registerService.register(userRegisterRequest));
    }


    @GetMapping("/account/username/{username}")
    public ResponseDto<UserRegisterResponse> findByUsername(HttpServletRequest request,@PathVariable("username") String username) throws AuthException {
        log.info("username{}", username);
        return ResponseDto.success(userFindByUsername.findByUsername(request,username));
    }

    @GetMapping("/account/userId/{userId}")
    public ResponseDto<Optional<User>> findById(HttpServletRequest request,@PathVariable("userId") Long userId) throws AuthException {
        return ResponseDto.success(userFindByIdService.findById(request,userId));
    }

    @PostMapping("/login")
    public ResponseDto<LoginResponse> login(@RequestBody LoginRequest loginRequest)  {
        return ResponseDto.success(loginService.login(loginRequest));
    }
}
