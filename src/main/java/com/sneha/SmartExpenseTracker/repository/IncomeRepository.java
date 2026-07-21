package com.sneha.SmartExpenseTracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sneha.SmartExpenseTracker.entity.Income;
import com.sneha.SmartExpenseTracker.entity.User;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByUser(User user);

    @Query("""
        SELECT i
        FROM Income i
        WHERE i.user = :user
        AND (
            LOWER(i.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(i.source) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(i.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    List<Income> searchIncome(@Param("user") User user,
                              @Param("keyword") String keyword);

    // Dashboard Statistics

    @Query("""
        SELECT COALESCE(SUM(i.amount), 0)
        FROM Income i
        WHERE i.user = :user
    """)
    Double getTotalIncome(@Param("user") User user);

    @Query("""
        SELECT COALESCE(SUM(i.amount), 0)
        FROM Income i
        WHERE i.user = :user
        AND MONTH(i.date) = :month
        AND YEAR(i.date) = :year
    """)
    Double getMonthlyIncome(@Param("user") User user,
                            @Param("month") int month,
                            @Param("year") int year);
}