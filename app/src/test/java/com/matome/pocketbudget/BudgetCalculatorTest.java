package com.matome.pocketbudget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * My unit tests for BudgetCalculator.
 * Run them in Android Studio by right-clicking this file and choosing "Run".
 */
public class BudgetCalculatorTest {

    private final List<Expense> expenses = Arrays.asList(
            new Expense(1, "Groceries", 300, "Food", 0),
            new Expense(2, "Taxi", 50, "Transport", 0),
            new Expense(3, "Lunch", 150, "Food", 0)
    );

    @Test
    public void totalSpent_addsAllAmounts() {
        assertEquals(500.0, BudgetCalculator.totalSpent(expenses), 0.001);
    }

    @Test
    public void remaining_subtractsSpentFromBudget() {
        assertEquals(500.0, BudgetCalculator.remaining(1000, expenses), 0.001);
    }

    @Test
    public void percentUsed_isCappedAt100() {
        assertEquals(50, BudgetCalculator.percentUsed(1000, expenses));
        assertEquals(100, BudgetCalculator.percentUsed(200, expenses));
    }

    @Test
    public void percentUsed_isZeroWhenNoBudget() {
        assertEquals(0, BudgetCalculator.percentUsed(0, expenses));
    }

    @Test
    public void isOverBudget_works() {
        assertTrue(BudgetCalculator.isOverBudget(400, expenses));
        assertFalse(BudgetCalculator.isOverBudget(1000, expenses));
    }

    @Test
    public void totalsByCategory_groupsAmounts() {
        Map<String, Double> totals = BudgetCalculator.totalsByCategory(expenses);
        assertEquals(450.0, totals.get("Food"), 0.001);
        assertEquals(50.0, totals.get("Transport"), 0.001);
    }

    @Test
    public void emptyList_givesZero() {
        assertEquals(0.0, BudgetCalculator.totalSpent(new ArrayList<>()), 0.001);
    }
}
