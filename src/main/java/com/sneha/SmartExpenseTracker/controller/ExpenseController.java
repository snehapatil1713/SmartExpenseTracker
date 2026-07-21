package com.sneha.SmartExpenseTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sneha.SmartExpenseTracker.entity.Expense;
import com.sneha.SmartExpenseTracker.entity.User;
import com.sneha.SmartExpenseTracker.service.ExpenseService;
import com.sneha.SmartExpenseTracker.service.UserService;

@Controller
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    // ==============================
    // Expense List + Search
    // ==============================
    @GetMapping
    public String expenseList(
            @RequestParam(value = "keyword", required = false) String keyword,
            Authentication authentication,
            Model model) {

        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("expenseList",
                    expenseService.searchExpense(user, keyword));
        } else {
            model.addAttribute("expenseList",
                    expenseService.getExpensesByUser(user));
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("pageTitle", "Expense Management");

        return "expense/expense-list";
    }

    // ==============================
    // Add Expense Page
    // ==============================
    @GetMapping("/add")
    public String addExpensePage(Model model) {

        model.addAttribute("expense", new Expense());
        model.addAttribute("pageTitle", "Add Expense");
        model.addAttribute("buttonText", "Save Expense");

        return "expense/add-expense";
    }

    // ==============================
    // Save / Update Expense
    // ==============================
    @PostMapping("/save")
    public String saveExpense(Expense expense,
                              Authentication authentication) {

        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        boolean isNew = (expense.getId() == null);

        expense.setUser(user);
        expenseService.saveExpense(expense);

        if (isNew) {
            return "redirect:/expense?success=added";
        } else {
            return "redirect:/expense?success=updated";
        }
    }

    // ==============================
    // Edit Expense
    // ==============================
    @GetMapping("/edit/{id}")
    public String editExpense(@PathVariable Long id,
                              Model model) {

        Expense expense = expenseService.getExpenseById(id);

        model.addAttribute("expense", expense);
        model.addAttribute("pageTitle", "Edit Expense");
        model.addAttribute("buttonText", "Update Expense");

        return "expense/add-expense";
    }

    // ==============================
    // View Expense
    // ==============================
    @GetMapping("/view/{id}")
    public String viewExpense(@PathVariable Long id,
                              Model model) {

        Expense expense = expenseService.getExpenseById(id);

        model.addAttribute("expense", expense);
        model.addAttribute("pageTitle", "Expense Details");

        return "expense/view-expense";
    }

    // ==============================
    // Delete Expense
    // ==============================
    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {

        expenseService.deleteExpense(id);

        return "redirect:/expense?success=deleted";
    }

}