package com.sneha.SmartExpenseTracker.service;

import java.util.Map;

import java.util.List;

import java.util.List;

import com.sneha.SmartExpenseTracker.entity.User;

import com.sneha.SmartExpenseTracker.dto.TransactionDTO;

import com.sneha.SmartExpenseTracker.dto.AIInsightDTO;

import com.sneha.SmartExpenseTracker.dto.MonthlyReportDTO;

public interface DashboardService {

    Double getTotalIncome(User user);

    Double getTotalExpense(User user);

    Double getCurrentBalance(User user);

    Double getMonthlyIncome(User user);

    Double getMonthlyExpense(User user);
    
    Double getCurrentMonthBudget(User user);

    Double getRemainingBudget(User user);

    Double getBudgetUsagePercentage(User user);

    String getBudgetStatus(User user);
    
    MonthlyReportDTO getMonthlyReport(User user);
   
    // Chart Data
    Map<String, Double> getLastSixMonthsIncome(User user);

    Map<String, Double> getLastSixMonthsExpense(User user);

    Map<String, Double> getExpenseCategoryData(User user);
    
    List<TransactionDTO> getRecentTransactions(User user);
    
    List<AIInsightDTO> getAIInsights(User user);

}