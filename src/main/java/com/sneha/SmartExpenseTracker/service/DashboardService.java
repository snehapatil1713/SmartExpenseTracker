package com.sneha.SmartExpenseTracker.service;

import java.util.Map;

import com.sneha.SmartExpenseTracker.entity.User;

import java.util.List;

import com.sneha.SmartExpenseTracker.dto.TransactionDTO;

public interface DashboardService {

    Double getTotalIncome(User user);

    Double getTotalExpense(User user);

    Double getCurrentBalance(User user);

    Double getMonthlyIncome(User user);

    Double getMonthlyExpense(User user);
   
    // Chart Data
    Map<String, Double> getLastSixMonthsIncome(User user);

    Map<String, Double> getLastSixMonthsExpense(User user);

    Map<String, Double> getExpenseCategoryData(User user);
    
    List<TransactionDTO> getRecentTransactions(User user);

}