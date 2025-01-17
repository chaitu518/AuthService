package com.example.authservice.service;

import com.example.authservice.models.User;
import com.example.authservice.repos.AuthRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
public class AuthService {
    private AuthRepo authRepo;
    private PasswordEncoder passwordEncoder;
    public AuthService(AuthRepo authRepo, PasswordEncoder passwordEncoder) {
        this.authRepo = authRepo;
        this.passwordEncoder = passwordEncoder;
    }
    public String login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUser = authRepo.findByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(passwordEncoder.matches(password, user.getPassword())) {
                return "logged in";
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
