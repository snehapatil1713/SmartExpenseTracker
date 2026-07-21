package com.sneha.SmartExpenseTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sneha.SmartExpenseTracker.entity.User;
import com.sneha.SmartExpenseTracker.service.DashboardService;
import com.sneha.SmartExpenseTracker.service.UserService;

@Controller
public class AIInsightController {

    @Autowired
    private UserService userService;

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/ai-insights")
    public String aiInsights(Authentication authentication, Model model) {

        User user = userService.getUserByEmail(authentication.getName());

        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "AI Insights");

        model.addAttribute(
                "insights",
                dashboardService.getAIInsights(user)
        );

        return "ai/ai-insights";
    }
}