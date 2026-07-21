package com.sneha.SmartExpenseTracker.service.impl;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sneha.SmartExpenseTracker.entity.Expense;
import com.sneha.SmartExpenseTracker.entity.Income;
import com.sneha.SmartExpenseTracker.entity.User;
import com.sneha.SmartExpenseTracker.repository.ExpenseRepository;
import com.sneha.SmartExpenseTracker.repository.IncomeRepository;
import com.sneha.SmartExpenseTracker.service.DashboardService;
import com.sneha.SmartExpenseTracker.dto.TransactionDTO;
import com.sneha.SmartExpenseTracker.dto.AIInsightDTO;
import com.sneha.SmartExpenseTracker.repository.BudgetRepository;
import com.sneha.SmartExpenseTracker.entity.Budget;
import com.sneha.SmartExpenseTracker.dto.MonthlyReportDTO;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;
    
    @Autowired
    private BudgetRepository budgetRepository;

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
    
    @Override
    public List<AIInsightDTO> getAIInsights(User user) {

        List<AIInsightDTO> insights = new ArrayList<>();

        double monthlyIncome = getMonthlyIncome(user);
        double monthlyExpense = getMonthlyExpense(user);
        double savings = monthlyIncome - monthlyExpense;

        double budget = getCurrentMonthBudget(user);
        double remainingBudget = getRemainingBudget(user);
        double budgetUsage = getBudgetUsagePercentage(user);
        String budgetStatus = getBudgetStatus(user);

        // Savings Insight
        if (savings >= 0) {

            insights.add(new AIInsightDTO(
                    "💰 Savings",
                    "Great! You saved ₹" + String.format("%.2f", savings) + " this month.",
                    "success"));

        } else {

            insights.add(new AIInsightDTO(
                    "⚠️ Overspending",
                    "Your expenses exceeded your income by ₹"
                            + String.format("%.2f", Math.abs(savings))
                            + " this month.",
                    "danger"));
        }

        // Highest Spending Category
        Map<String, Double> categoryMap = getExpenseCategoryData(user);

        if (!categoryMap.isEmpty()) {

            String highestCategory =
                    Collections.max(categoryMap.entrySet(),
                            Map.Entry.comparingByValue()).getKey();

            Double amount = categoryMap.get(highestCategory);

            insights.add(new AIInsightDTO(
                    "📊 Highest Spending",
                    "Your highest spending category is "
                            + highestCategory
                            + " (₹"
                            + String.format("%.2f", amount)
                            + ").",
                    "warning"));
        }

        // Savings Rate
        if (monthlyIncome > 0) {

            double rate = (savings / monthlyIncome) * 100;

            insights.add(new AIInsightDTO(
                    "📈 Savings Rate",
                    "You saved "
                            + String.format("%.1f", rate)
                            + "% of your income this month.",
                    "info"));
        }

        // Budget Insight
        if (budget > 0) {

            insights.add(new AIInsightDTO(
                    "💳 Budget Status",
                    "You have used "
                            + String.format("%.1f", budgetUsage)
                            + "% of your monthly budget. Remaining Budget: ₹"
                            + String.format("%.2f", remainingBudget)
                            + ".",
                    budgetUsage <= 80 ? "success"
                            : budgetUsage <= 100 ? "warning"
                            : "danger"));

        } else {

            insights.add(new AIInsightDTO(
                    "💳 Budget Status",
                    "No budget has been set for this month.",
                    "info"));
        }

        // Recommendation
        String recommendation;

        if (budget <= 0) {

            recommendation = "Set a monthly budget to receive better financial insights.";

        } else if (budgetUsage < 50) {

            recommendation = "Excellent! You have used only "
                    + Math.round(budgetUsage)
                    + "% of your budget. Keep up the disciplined spending.";

        } else if (budgetUsage < 80) {

            recommendation = "You have used "
                    + Math.round(budgetUsage)
                    + "% of your budget. Your spending is on track.";

        } else if (budgetUsage <= 100) {

            recommendation = "Warning! You have already used "
                    + Math.round(budgetUsage)
                    + "% of your budget. Only ₹"
                    + String.format("%.2f", remainingBudget)
                    + " is remaining.";

        } else {

            recommendation = "Budget exceeded! You have overspent by ₹"
                    + String.format("%.2f", Math.abs(remainingBudget))
                    + ". Consider reducing unnecessary expenses.";

        }

        insights.add(new AIInsightDTO(
                "🎯 Recommendation",
                recommendation,
                budgetUsage < 80 ? "success"
                        : budgetUsage <= 100 ? "warning"
                        : "danger"));

        return insights;
    }
    
    @Override
    public Double getCurrentMonthBudget(User user) {

        LocalDate today = LocalDate.now();

        Budget budget = budgetRepository
                .findByUserAndMonthAndYear(
                        user,
                        today.getMonthValue(),
                        today.getYear())
                .orElse(null);

        return budget != null ? budget.getAmount() : 0.0;
    }
    
    @Override
    public Double getRemainingBudget(User user) {

        double budget = getCurrentMonthBudget(user);
        double expense = getMonthlyExpense(user);

        return budget - expense;
    }
    
    @Override
    public Double getBudgetUsagePercentage(User user) {

        double budget = getCurrentMonthBudget(user);

        if (budget <= 0) {
            return 0.0;
        }

        return (getMonthlyExpense(user) / budget) * 100;
    }
    
    @Override
    public String getBudgetStatus(User user) {

        double usage = getBudgetUsagePercentage(user);

        if (usage < 80) {
            return "On Track";
        } else if (usage <= 100) {
            return "Near Limit";
        } else {
            return "Budget Exceeded";
        }
    }
    
    @Override
    public MonthlyReportDTO getMonthlyReport(User user) {

        MonthlyReportDTO report = new MonthlyReportDTO();

        double income = getMonthlyIncome(user);
        double expense = getMonthlyExpense(user);
        double savings = income - expense;

        double budget = getCurrentMonthBudget(user);
        double remainingBudget = getRemainingBudget(user);
        double budgetUsage = getBudgetUsagePercentage(user);

        report.setTotalIncome(income);
        report.setTotalExpense(expense);
        report.setSavings(savings);

        report.setBudget(budget);
        report.setRemainingBudget(remainingBudget);
        report.setBudgetUsage(budgetUsage);

        // Highest Spending Category
        Map<String, Double> categoryMap = getExpenseCategoryData(user);

        if (!categoryMap.isEmpty()) {

            String highestCategory = Collections.max(
                    categoryMap.entrySet(),
                    Map.Entry.comparingByValue())
                    .getKey();

            report.setHighestCategory(highestCategory);

        } else {

            report.setHighestCategory("No Expenses");

        }

        // Savings Rate
        if (income > 0) {
            report.setSavingsRate((savings / income) * 100);
        } else {
            report.setSavingsRate(0.0);
        }

        return report;
    }

}