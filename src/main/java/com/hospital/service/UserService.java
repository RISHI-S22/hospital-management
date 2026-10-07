package com.hospital.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hospital.model.User;
import com.hospital.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        String role = user.getRole().toUpperCase();
        String id = user.getUsername().toUpperCase();

        // Enforce required ID prefixes
        if (role.equals("DOCTOR") && !id.contains("DOC")) {
            throw new IllegalArgumentException("Doctor ID must contain 'doc' (e.g. DOC-101)");
        } else if (role.equals("NURSE") && !id.contains("NUR")) {
            throw new IllegalArgumentException("Nurse ID must contain 'nur' (e.g. NUR-201)");
        } else if (role.equals("PHARMACIST") && !id.contains("PHARM")) {
            throw new IllegalArgumentException("Pharmacist ID must contain 'pharm' (e.g. PHARM-301)");
        } else if (role.equals("ADMIN") && !id.contains("ADM")) {
            throw new IllegalArgumentException("Admin ID must contain 'adm' (e.g. ADM-001)");
        }

        user.setUsername(id);
        user.setRole(role);
        return userRepository.save(user);
    }

   public Optional<User> authenticate(String username, String password) {
    return userRepository.findByUsernameIgnoreCaseAndPassword(username.trim(), password.trim());
}
}