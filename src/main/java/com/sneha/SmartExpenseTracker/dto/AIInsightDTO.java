package com.sneha.SmartExpenseTracker.dto;

public class AIInsightDTO {

    private String title;
    private String message;
    private String alertType;
    private Double budgetUsage;
    private Double remainingBudget;
    private String budgetStatus;

    public AIInsightDTO() {
    }

    public AIInsightDTO(String title, String message, String alertType) {
        this.title = title;
        this.message = message;
        this.alertType = alertType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }
    
    public Double getBudgetUsage() {
        return budgetUsage;
    }

    public void setBudgetUsage(Double budgetUsage) {
        this.budgetUsage = budgetUsage;
    }

    public Double getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(Double remainingBudget) {
        this.remainingBudget = remainingBudget;
    }

    public String getBudgetStatus() {
        return budgetStatus;
    }

    public void setBudgetStatus(String budgetStatus) {
        this.budgetStatus = budgetStatus;
    }
}