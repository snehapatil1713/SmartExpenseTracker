package com.sneha.SmartExpenseTracker.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sneha.SmartExpenseTracker.entity.User;
import com.sneha.SmartExpenseTracker.service.UserService;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // ===========================
    // View Profile
    // ===========================
    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {

        User user = userService.getUserByEmail(authentication.getName());

        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "My Profile");

        return "profile";
    }

    // ===========================
    // Edit Profile
    // ===========================
    @GetMapping("/profile/edit")
    public String editProfile(Authentication authentication, Model model) {

        User user = userService.getUserByEmail(authentication.getName());

        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Edit Profile");

        return "edit-profile";
    }

    // ===========================
    // Update Profile
    // ===========================
    @PostMapping("/profile/update")
    public String updateProfile(
            @ModelAttribute("user") User updatedUser,
            @RequestParam("profilePhoto") MultipartFile profilePhoto,
            Authentication authentication) throws IOException {

        User existingUser = userService.getUserByEmail(authentication.getName());

        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
        existingUser.setGender(updatedUser.getGender());
        existingUser.setAddress(updatedUser.getAddress());

        // Upload profile image
        if (profilePhoto != null && !profilePhoto.isEmpty()) {

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID() + "_"
                    + profilePhoto.getOriginalFilename();

            Files.copy(
                    profilePhoto.getInputStream(),
                    uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);

            existingUser.setProfileImage(fileName);
        }

        userService.updateUser(existingUser);

        return "redirect:/profile";
    }
}