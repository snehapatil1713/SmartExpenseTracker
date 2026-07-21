package com.sneha.SmartExpenseTracker.dto;

import java.time.LocalDate;

public class TransactionDTO {

    private String type;
    private String title;
    private String category;
    private Double amount;
    private LocalDate date;

    public TransactionDTO() {
    }

    public TransactionDTO(String type, String title, String category,
                          Double amount, LocalDate date) {
        this.type = type;
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}