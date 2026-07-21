package com.sneha.SmartExpenseTracker.service;

import java.util.List;

import com.sneha.SmartExpenseTracker.entity.Expense;
import com.sneha.SmartExpenseTracker.entity.User;

public interface ExpenseService {

    Expense saveExpense(Expense expense);

    Expense updateExpense(Expense expense);

    void deleteExpense(Long id);

    Expense getExpenseById(Long id);

    List<Expense> getAllExpenses();

    List<Expense> getExpensesByUser(User user);

    List<Expense> searchExpense(User user, String keyword);

}