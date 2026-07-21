package com.sneha.SmartExpenseTracker.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sneha.SmartExpenseTracker.entity.User;
import com.sneha.SmartExpenseTracker.repository.UserRepository;
import com.sneha.SmartExpenseTracker.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ===========================
    // Register User
    // ===========================

    @Override
    public User registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    // ===========================
    // Get User
    // ===========================

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // ===========================
    // Update Profile
    // ===========================

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    // ===========================
    // Change Password
    // ===========================

    @Override
    public boolean changePassword(String email,
                                  String currentPassword,
                                  String newPassword) {

        User user = getUserByEmail(email);

        if (user == null) {
            return false;
        }

        // Verify current password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return false;
        }

        // Save new encrypted password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return true;
    }

}