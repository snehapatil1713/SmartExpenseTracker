package com.sneha.SmartExpenseTracker.service;

import java.util.List;

import com.sneha.SmartExpenseTracker.entity.Income;
import com.sneha.SmartExpenseTracker.entity.User;

public interface IncomeService {

    // Save Income
    Income saveIncome(Income income);

    // Update Income
    Income updateIncome(Income income);

    // Delete Income
    void deleteIncome(Long id);

    // Get Income by ID
    Income getIncomeById(Long id);

    // Get All Income
    List<Income> getAllIncome();

    // Get Logged-in User Income
    List<Income> getIncomeByUser(User user);

    // Search Income
    List<Income> searchIncome(User user, String keyword);

}