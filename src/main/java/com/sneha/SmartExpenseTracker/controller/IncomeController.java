package com.sneha.SmartExpenseTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sneha.SmartExpenseTracker.entity.Income;
import com.sneha.SmartExpenseTracker.entity.User;
import com.sneha.SmartExpenseTracker.service.IncomeService;
import com.sneha.SmartExpenseTracker.service.UserService;

@Controller
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private UserService userService;

    // ==============================
    // Income List + Search
    // ==============================
    @GetMapping
    public String incomeList(
            @RequestParam(value = "keyword", required = false) String keyword,
            Authentication authentication,
            Model model) {

        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("incomeList",
                    incomeService.searchIncome(user, keyword));
        } else {
            model.addAttribute("incomeList",
                    incomeService.getIncomeByUser(user));
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("pageTitle", "Income Management");

        return "income/income-list";
    }

    // ==============================
    // Add Income Page
    // ==============================
    @GetMapping("/add")
    public String addIncomePage(Model model) {

        model.addAttribute("income", new Income());
        model.addAttribute("pageTitle", "Add Income");
        model.addAttribute("buttonText", "Save Income");

        return "income/add-income";
    }

    // ==============================
    // Save Income
    // ==============================
    @PostMapping("/save")
    public String saveIncome(Income income,
                             Authentication authentication) {

        String email = authentication.getName();

        User user = userService.getUserByEmail(email);

        income.setUser(user);

        incomeService.saveIncome(income);

        if (income.getId() == null) {
            return "redirect:/income?success=added";
        } else {
            return "redirect:/income?success=updated";
        }
    }

    // ==============================
    // Edit Income
    // ==============================
    @GetMapping("/edit/{id}")
    public String editIncome(@PathVariable Long id,
                             Model model) {

        Income income = incomeService.getIncomeById(id);

        model.addAttribute("income", income);
        model.addAttribute("pageTitle", "Edit Income");
        model.addAttribute("buttonText", "Update Income");

        return "income/add-income";
    }

    // ==============================
    // View Income
    // ==============================
    @GetMapping("/view/{id}")
    public String viewIncome(@PathVariable Long id,
                             Model model) {

        Income income = incomeService.getIncomeById(id);

        model.addAttribute("income", income);
        model.addAttribute("pageTitle", "Income Details");

        return "income/view-income";
    }

    // ==============================
    // Delete Income
    // ==============================
    @GetMapping("/delete/{id}")
    public String deleteIncome(@PathVariable Long id) {

        incomeService.deleteIncome(id);

        return "redirect:/income?success=deleted";
    }

}