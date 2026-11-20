package com.matome.pocketbudget;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * BudgetCalculator
 *
 * I kept all the maths in this one Java class with no Android code in it.
 * That means I can unit test it on my computer without running an emulator.
 */
public class BudgetCalculator {

    // I made the constructor private because this class only has static helper methods.
    private BudgetCalculator() { }

    public static double totalSpent(List<Expense> expenses) {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }

    public static double remaining(double budget, List<Expense> expenses) {
        return budget - totalSpent(expenses);
    }

    /**
     * Returns how much of the budget has been used, from 0 to 100.
     * I cap it at 100 so the progress bar never goes past full.
     */
    public static int percentUsed(double budget, List<Expense> expenses) {
        if (budget <= 0) {
            return 0; // I avoid dividing by zero when no budget is set
        }
        int percent = (int) Math.round(totalSpent(expenses) / budget * 100);
        return Math.min(percent, 100);
    }

    public static boolean isOverBudget(double budget, List<Expense> expenses) {
        return budget > 0 && totalSpent(expenses) > budget;
    }

    /**
     * Adds up spending per category, e.g. {Food=450.0, Transport=120.0}.
     * I use a LinkedHashMap so the categories stay in the order they were first seen.
     */
    public static Map<String, Double> totalsByCategory(List<Expense> expenses) {
        Map<String, Double> totals = new LinkedHashMap<>();
        for (Expense e : expenses) {
            double current = totals.getOrDefault(e.getCategory(), 0.0);
            totals.put(e.getCategory(), current + e.getAmount());
        }
        return totals;
    }
}
