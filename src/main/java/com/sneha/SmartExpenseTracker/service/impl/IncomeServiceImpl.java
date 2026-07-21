package com.sneha.SmartExpenseTracker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sneha.SmartExpenseTracker.entity.Income;
import com.sneha.SmartExpenseTracker.entity.User;
import com.sneha.SmartExpenseTracker.repository.IncomeRepository;
import com.sneha.SmartExpenseTracker.service.IncomeService;

@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Override
    public Income saveIncome(Income income) {
        return incomeRepository.save(income);
    }

    @Override
    public Income updateIncome(Income income) {
        return incomeRepository.save(income);
    }

    @Override
    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }

    @Override
    public Income getIncomeById(Long id) {
        return incomeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Income> getAllIncome() {
        return incomeRepository.findAll();
    }

    @Override
    public List<Income> getIncomeByUser(User user) {
        return incomeRepository.findByUser(user);
    }

    @Override
    public List<Income> searchIncome(User user, String keyword) {
        return incomeRepository.searchIncome(user, keyword);
    }

}