package com.sneha.SmartExpenseTracker.dto;

public class MonthlyReportDTO {

    private Double totalIncome;
    private Double totalExpense;
    private Double savings;

    private Double budget;
    private Double remainingBudget;
    private Double budgetUsage;

    private String highestCategory;
    private Double savingsRate;

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public Double getSavings() {
        return savings;
    }

    public void setSavings(Double savings) {
        this.savings = savings;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Double getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(Double remainingBudget) {
        this.remainingBudget = remainingBudget;
    }

    public Double getBudgetUsage() {
        return budgetUsage;
    }

    public void setBudgetUsage(Double budgetUsage) {
        this.budgetUsage = budgetUsage;
    }

    public String getHighestCategory() {
        return highestCategory;
    }

    public void setHighestCategory(String highestCategory) {
        this.highestCategory = highestCategory;
    }

    public Double getSavingsRate() {
        return savingsRate;
    }

    public void setSavingsRate(Double savingsRate) {
        this.savingsRate = savingsRate;
    }
}