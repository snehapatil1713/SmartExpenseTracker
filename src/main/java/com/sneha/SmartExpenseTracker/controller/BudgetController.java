package com.sneha.SmartExpenseTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sneha.SmartExpenseTracker.entity.Budget;
import com.sneha.SmartExpenseTracker.entity.User;
import com.sneha.SmartExpenseTracker.service.BudgetService;
import com.sneha.SmartExpenseTracker.service.UserService;

@Controller
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private UserService userService;

    private User getLoggedInUser(Authentication authentication) {
        return userService.getUserByEmail(authentication.getName());
    }

    // =========================
    // Budget List
    // =========================
    @GetMapping
    public String budgetList(Authentication authentication, Model model) {

        User user = getLoggedInUser(authentication);

        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Budget Management");
        model.addAttribute("budgetList", budgetService.getAllBudgets(user));

        return "budget/budget-list";
    }

    // =========================
    // Add Budget Page
    // =========================
    @GetMapping("/add")
    public String addBudgetPage(Authentication authentication, Model model) {

        User user = getLoggedInUser(authentication);

        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Add Budget");
        model.addAttribute("budget", new Budget());

        return "budget/add-budget";
    }

    // =========================
    // Save Budget
    // =========================
    @PostMapping("/save")
    public String saveBudget(@ModelAttribute Budget budget,
                             Authentication authentication) {

        User user = getLoggedInUser(authentication);

        budgetService.saveBudget(user, budget);

        return "redirect:/budget?success=added";
    }

    // =========================
    // View Budget
    // =========================
    @GetMapping("/view/{id}")
    public String viewBudget(@PathVariable Long id,
                             Authentication authentication,
                             Model model) {

        User user = getLoggedInUser(authentication);

        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "View Budget");
        model.addAttribute("budget",
                budgetService.getBudgetById(id, user));

        return "budget/view-budget";
    }

    // =========================
    // Edit Budget
    // =========================
    @GetMapping("/edit/{id}")
    public String editBudget(@PathVariable Long id,
                             Authentication authentication,
                             Model model) {

        User user = getLoggedInUser(authentication);

        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Edit Budget");
        model.addAttribute("budget",
                budgetService.getBudgetById(id, user));

        return "budget/edit-budget";
    }

    // =========================
    // Update Budget
    // =========================
    @PostMapping("/update")
    public String updateBudget(@ModelAttribute Budget budget,
                               Authentication authentication) {

        User user = getLoggedInUser(authentication);

        budgetService.saveBudget(user, budget);

        return "redirect:/budget?success=updated";
    }

    // =========================
    // Delete Budget
    // =========================
    @GetMapping("/delete/{id}")
    public String deleteBudget(@PathVariable Long id,
                               Authentication authentication) {

        User user = getLoggedInUser(authentication);

        budgetService.deleteBudget(id, user);

        return "redirect:/budget?success=deleted";
    }
}