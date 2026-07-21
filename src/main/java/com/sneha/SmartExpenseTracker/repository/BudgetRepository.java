package com.sneha.SmartExpenseTracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sneha.SmartExpenseTracker.entity.Budget;
import com.sneha.SmartExpenseTracker.entity.User;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByUserAndMonthAndYear(User user, Integer month, Integer year);

    List<Budget> findByUserOrderByYearDescMonthDesc(User user);

    Optional<Budget> findByIdAndUser(Long id, User user);

}