package com.sneha.SmartExpenseTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sneha.SmartExpenseTracker.entity.User;
import com.sneha.SmartExpenseTracker.service.UserService;
import com.sneha.SmartExpenseTracker.service.DashboardService;

@Controller
public class ReportController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/reports")
    public String reports(Authentication authentication,
                          Model model) {

        User user = userService.getUserByEmail(authentication.getName());

        model.addAttribute("user", user);

        model.addAttribute("pageTitle", "Reports");

        model.addAttribute(
                "report",
                dashboardService.getMonthlyReport(user)
        );

        // Charts
        model.addAttribute(
                "incomeChart",
                dashboardService.getLastSixMonthsIncome(user)
        );

        model.addAttribute(
                "expenseChart",
                dashboardService.getLastSixMonthsExpense(user)
        );

        model.addAttribute(
                "categoryChart",
                dashboardService.getExpenseCategoryData(user)
        );

        return "reports/reports";
    }
}