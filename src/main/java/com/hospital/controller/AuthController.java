package com.hospital.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.model.User;
import com.hospital.repository.UserRepository;
import com.hospital.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registered = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(registered);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username") != null ? credentials.get("username").trim() : "";
        String password = credentials.get("password") != null ? credentials.get("password").trim() : "";
        String requestedRole = credentials.get("role") != null ? credentials.get("role").trim() : null;

        Optional<User> userOpt = userRepository.findAll().stream()
            .filter(user -> username.equals(user.getUsername()))
            .findFirst();

        if (userOpt.isPresent()) {
            User u = userOpt.get();

            // 1. Validate requested role matches the account's assigned role
            if (requestedRole != null && !requestedRole.equalsIgnoreCase(u.getRole())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Selected role does not match this account."));
            }

            // 2. Hybrid password check (BCrypt hash vs. Legacy Plaintext)
            String storedPassword = u.getPassword();
            boolean passwordMatches = false;

            if (storedPassword != null && (storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$") || storedPassword.startsWith("$2y$"))) {
                passwordMatches = passwordEncoder.matches(password, storedPassword);
            } else {
                passwordMatches = password.equals(storedPassword);
            }

            if (passwordMatches) {
                Map<String, Object> resp = new HashMap<>();
                resp.put("username", u.getUsername());
                resp.put("role", u.getRole().toUpperCase());
                resp.put("fullName", u.getFullName() != null ? u.getFullName() : u.getUsername());
                return ResponseEntity.ok(resp);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid ID or Password"));
    }
}