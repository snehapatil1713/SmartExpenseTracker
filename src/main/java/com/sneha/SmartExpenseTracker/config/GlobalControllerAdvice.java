package com.sneha.SmartExpenseTracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sneha.SmartExpenseTracker.entity.User;
import com.sneha.SmartExpenseTracker.service.UserService;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UserService userService;

    @ModelAttribute("loggedInUser")
    public User loggedInUser(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return userService.getUserByEmail(authentication.getName());
    }
}