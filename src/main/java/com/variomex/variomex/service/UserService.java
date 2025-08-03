package com.variomex.variomex.service;

import com.variomex.variomex.Enum.Role;
import com.variomex.variomex.model.Users;
import com.variomex.variomex.repository.UserRepository;
import com.variomex.variomex.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String registerUser(String email, String rawPassword, String role) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already in use!");
        }

        Users user = new Users();
        user.setEmail(email);
        user.setRole(Role.valueOf(role));
        user.setPassword(passwordEncoder.encode(rawPassword)); // 🔑 hash password

        userRepository.save(user);
        return "User registered successfully!";
    }

    public String login(String email, String rawPassword) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail(), String.valueOf(user.getRole()));
    }
}
