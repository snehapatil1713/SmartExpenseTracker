package com.sneha.SmartExpenseTracker.service;

import java.util.List;

import com.sneha.SmartExpenseTracker.entity.Budget;
import com.sneha.SmartExpenseTracker.entity.User;

public interface BudgetService {

    Budget saveBudget(User user, Budget budget);

    List<Budget> getAllBudgets(User user);

    Budget getBudgetById(Long id, User user);

    Budget getCurrentMonthBudget(User user);

    void deleteBudget(Long id, User user);
}