package com.example.authservice.controller;

import com.example.authservice.dtos.LoginRequestDto;
import com.example.authservice.dtos.SignUpRequestDto;
import com.example.authservice.dtos.SignUpResponseDto;
import com.example.authservice.models.User;
import com.example.authservice.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public String Login(@RequestBody LoginRequestDto loginDto) throws AuthenticationException {
        return authService.login(loginDto.getEmail(), loginDto.getPassword());
    }

    @PostMapping("/register")
    public SignUpResponseDto register(@RequestBody SignUpRequestDto signupDto) throws AuthenticationException {
        User user = authService.register(signupDto.getName(),signupDto.getEmail(),signupDto.getPassword());
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        signUpResponseDto.setEmail(user.getEmail());
        signUpResponseDto.setName(user.getName());
        signUpResponseDto.setId(user.getId());
        return signUpResponseDto;
    }

}
