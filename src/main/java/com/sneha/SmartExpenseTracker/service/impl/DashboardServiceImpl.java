package com.sneha.SmartExpenseTracker.service.impl;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ArrayList;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sneha.SmartExpenseTracker.entity.Expense;
import com.sneha.SmartExpenseTracker.entity.Income;
import com.sneha.SmartExpenseTracker.entity.User;
import com.sneha.SmartExpenseTracker.repository.ExpenseRepository;
import com.sneha.SmartExpenseTracker.repository.IncomeRepository;
import com.sneha.SmartExpenseTracker.service.DashboardService;
import com.sneha.SmartExpenseTracker.dto.TransactionDTO;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public Double getTotalIncome(User user) {
        return incomeRepository.getTotalIncome(user);
    }

    @Override
    public Double getTotalExpense(User user) {
        return expenseRepository.getTotalExpense(user);
    }

    @Override
    public Double getCurrentBalance(User user) {
        return getTotalIncome(user) - getTotalExpense(user);
    }

    @Override
    public Double getMonthlyIncome(User user) {

        LocalDate today = LocalDate.now();

        return incomeRepository.getMonthlyIncome(
                user,
                today.getMonthValue(),
                today.getYear());
    }

    @Override
    public Double getMonthlyExpense(User user) {

        LocalDate today = LocalDate.now();

        return expenseRepository.getMonthlyExpense(
                user,
                today.getMonthValue(),
                today.getYear());
    }

    @Override
    public Map<String, Double> getLastSixMonthsIncome(User user) {

        Map<String, Double> data = new LinkedHashMap<>();

        LocalDate today = LocalDate.now();

        for (int i = 5; i >= 0; i--) {

            LocalDate month = today.minusMonths(i);

            Double amount = incomeRepository.getMonthlyIncome(
                    user,
                    month.getMonthValue(),
                    month.getYear());

            data.put(
                    month.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                    amount == null ? 0.0 : amount
            );
        }

        return data;
    }

    @Override
    public Map<String, Double> getLastSixMonthsExpense(User user) {

        Map<String, Double> data = new LinkedHashMap<>();

        LocalDate today = LocalDate.now();

        for (int i = 5; i >= 0; i--) {

            LocalDate month = today.minusMonths(i);

            Double amount = expenseRepository.getMonthlyExpense(
                    user,
                    month.getMonthValue(),
                    month.getYear());

            data.put(
                    month.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                    amount == null ? 0.0 : amount
            );
        }

        return data;
    }

    @Override
    public Map<String, Double> getExpenseCategoryData(User user) {

        List<Expense> expenses = expenseRepository.findByUser(user);

        Map<String, Double> categoryMap = new LinkedHashMap<>();

        for (Expense expense : expenses) {

            categoryMap.put(
                    expense.getCategory(),
                    categoryMap.getOrDefault(expense.getCategory(), 0.0)
                            + expense.getAmount()
            );
        }

        return categoryMap;
    }
    
    @Override
    public List<TransactionDTO> getRecentTransactions(User user) {

        List<TransactionDTO> transactions = new ArrayList<>();

        // Income Transactions
        for (Income income : incomeRepository.findByUser(user)) {

            transactions.add(new TransactionDTO(
                    "Income",
                    income.getTitle(),
                    income.getSource(),
                    income.getAmount().doubleValue(),
                    income.getDate()
            ));
        }

        // Expense Transactions
        for (Expense expense : expenseRepository.findByUser(user)) {

            transactions.add(new TransactionDTO(
                    "Expense",
                    expense.getTitle(),
                    expense.getCategory(),
                    expense.getAmount(),
                    expense.getDate()
            ));
        }

        // Latest First
        transactions.sort(
                Comparator.comparing(TransactionDTO::getDate).reversed()
        );

        // Return only latest 5
        if (transactions.size() > 5) {
            return transactions.subList(0, 5);
        }

        return transactions;
    }

}