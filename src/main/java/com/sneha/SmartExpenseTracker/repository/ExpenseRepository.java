package com.sneha.SmartExpenseTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sneha.SmartExpenseTracker.entity.Expense;
import com.sneha.SmartExpenseTracker.entity.User;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Get all expenses of the logged-in user
    List<Expense> findByUser(User user);

    // Search expenses by title, category, or description
    @Query("""
        SELECT e
        FROM Expense e
        WHERE e.user = :user
        AND (
            LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(e.category) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    List<Expense> searchExpense(@Param("user") User user,
                                @Param("keyword") String keyword);

    // Dashboard Statistics

    @Query("""
        SELECT COALESCE(SUM(e.amount), 0)
        FROM Expense e
        WHERE e.user = :user
    """)
    Double getTotalExpense(@Param("user") User user);

    @Query("""
        SELECT COALESCE(SUM(e.amount), 0)
        FROM Expense e
        WHERE e.user = :user
        AND MONTH(e.date) = :month
        AND YEAR(e.date) = :year
    """)
    Double getMonthlyExpense(@Param("user") User user,
                             @Param("month") int month,
                             @Param("year") int year);
}