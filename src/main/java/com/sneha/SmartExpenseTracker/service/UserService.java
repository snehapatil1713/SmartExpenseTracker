package com.sneha.SmartExpenseTracker.service;

import com.sneha.SmartExpenseTracker.entity.User;

public interface UserService {

    // ===========================
    // Authentication
    // ===========================

    User registerUser(User user);

    User getUserByEmail(String email);

    // ===========================
    // Profile
    // ===========================

    User updateUser(User user);

    // ===========================
    // Settings
    // ===========================

    /**
     * Change user's password.
     *
     * @param email User email
     * @param currentPassword Current password entered by user
     * @param newPassword New password to save
     * @return true if password changed successfully, false otherwise
     */
    boolean changePassword(String email,
                           String currentPassword,
                           String newPassword);

}