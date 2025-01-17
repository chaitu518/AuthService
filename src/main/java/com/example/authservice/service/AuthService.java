package com.example.authservice.service;

import com.example.authservice.models.User;
import com.example.authservice.repos.AuthRepo;
import com.example.authservice.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
public class AuthService {
    private AuthRepo authRepo;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    public AuthService(AuthRepo authRepo, PasswordEncoder passwordEncoder,JwtUtil jwtUtil) {
        this.authRepo = authRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    public String login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUser = authRepo.findByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(passwordEncoder.matches(password, user.getPassword())) {
                String token = jwtUtil.generateToken(user.getEmail());
                return token;
            }

        }
        throw new AuthenticationException("Invalid email or password");
    }
    public User register(String name, String email, String password) throws AuthenticationException {
        Optional<User> optionalUser = authRepo.findByEmail(email);
        if(optionalUser.isPresent()) {
            throw new AuthenticationException("User already exists");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return authRepo.save(user);
    }
}
