package com.example.authservice.controller;

import com.example.authservice.dtos.LoginRequestDto;
import com.example.authservice.dtos.SignUpRequestDto;
import com.example.authservice.dtos.SignUpResponseDto;
import com.example.authservice.models.Token;
import com.example.authservice.models.User;
import com.example.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    @Value("${jwt.expiration}")
    private String expiryTime;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<Token> Login(@RequestBody LoginRequestDto loginDto) throws AuthenticationException {
        String token = authService.login(loginDto.getEmail(), loginDto.getPassword());
        Token tok = new Token();
        tok.setToken(token);
        tok.setExpiryTime(expiryTime);
        return ResponseEntity.ok(tok);
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
