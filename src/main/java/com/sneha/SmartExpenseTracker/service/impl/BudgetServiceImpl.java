package com.sneha.SmartExpenseTracker.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sneha.SmartExpenseTracker.entity.Budget;
import com.sneha.SmartExpenseTracker.entity.User;
import com.sneha.SmartExpenseTracker.repository.BudgetRepository;
import com.sneha.SmartExpenseTracker.service.BudgetService;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    public Budget saveBudget(User user, Budget budget) {

        budget.setUser(user);

        return budgetRepository.save(budget);
    }

    @Override
    public List<Budget> getAllBudgets(User user) {

        return budgetRepository.findByUserOrderByYearDescMonthDesc(user);
    }

    @Override
    public Budget getBudgetById(Long id, User user) {

        return budgetRepository
                .findByIdAndUser(id, user)
                .orElse(null);
    }

    @Override
    public Budget getCurrentMonthBudget(User user) {

        LocalDate today = LocalDate.now();

        return budgetRepository
                .findByUserAndMonthAndYear(
                        user,
                        today.getMonthValue(),
                        today.getYear())
                .orElse(null);
    }

    @Override
    public void deleteBudget(Long id, User user) {

        Budget budget = getBudgetById(id, user);

        if (budget != null) {

            budgetRepository.delete(budget);

        }
    }
}