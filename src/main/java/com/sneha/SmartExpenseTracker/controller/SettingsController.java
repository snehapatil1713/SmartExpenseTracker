package com.sneha.SmartExpenseTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sneha.SmartExpenseTracker.entity.User;
import com.sneha.SmartExpenseTracker.service.UserService;

@Controller
public class SettingsController {

    @Autowired
    private UserService userService;

    // ===========================
    // Settings Page
    // ===========================
    @GetMapping("/settings")
    public String settings(Authentication authentication, Model model) {

        User user = userService.getUserByEmail(authentication.getName());

        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Settings");

        return "settings";
    }

    // ===========================
    // Change Password
    // ===========================
    @PostMapping("/settings/change-password")
    public String changePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Authentication authentication,
            Model model) {

        User user = userService.getUserByEmail(authentication.getName());

        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Settings");

        // Validate new password confirmation
        if (!newPassword.equals(confirmPassword)) {

            model.addAttribute("error",
                    "New password and Confirm password do not match.");

            return "settings";
        }

        boolean success = userService.changePassword(
                authentication.getName(),
                currentPassword,
                newPassword);

        if (success) {
            model.addAttribute("success",
                    "Password updated successfully.");
        } else {
            model.addAttribute("error",
                    "Current password is incorrect.");
        }

        return "settings";
    }

}