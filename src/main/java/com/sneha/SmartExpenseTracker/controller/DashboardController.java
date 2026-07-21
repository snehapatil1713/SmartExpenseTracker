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
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {

        String email = authentication.getName();

        User user = userService.getUserByEmail(email);

        // Summary Cards
        model.addAttribute("totalIncome", dashboardService.getTotalIncome(user));
        model.addAttribute("totalExpense", dashboardService.getTotalExpense(user));
        model.addAttribute("currentBalance", dashboardService.getCurrentBalance(user));
        model.addAttribute("monthlyIncome", dashboardService.getMonthlyIncome(user));
        model.addAttribute("monthlyExpense", dashboardService.getMonthlyExpense(user));
        model.addAttribute("currentBudget",
                dashboardService.getCurrentMonthBudget(user));

        model.addAttribute("remainingBudget",
                dashboardService.getRemainingBudget(user));

        model.addAttribute("budgetUsage",
                dashboardService.getBudgetUsagePercentage(user));

        model.addAttribute("budgetStatus",
                dashboardService.getBudgetStatus(user));

        // Charts
        model.addAttribute("incomeChart", dashboardService.getLastSixMonthsIncome(user));
        model.addAttribute("expenseChart", dashboardService.getLastSixMonthsExpense(user));
        model.addAttribute("categoryChart", dashboardService.getExpenseCategoryData(user));
        
        model.addAttribute(
                "recentTransactions",
                dashboardService.getRecentTransactions(user)
        );
        
     // AI Insights
        model.addAttribute(
                "aiInsights",
                dashboardService.getAIInsights(user)
        );


        return "dashboard";
    }
}