package com.matome.pocketbudget

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

/**
 * ExpenseStorage
 *
 * I save the expenses and the monthly budget in SharedPreferences, which is
 * a small key-value store built into Android. The expense list is saved as a JSON string.
 * For a bigger app I'd use a Room database, but this is enough for practice.
 */
class ExpenseStorage(context: Context) {

    private val prefs = context.getSharedPreferences("pocket_budget", Context.MODE_PRIVATE)

    fun loadExpenses(): MutableList<Expense> {
        val json = prefs.getString(KEY_EXPENSES, "[]") ?: "[]"
        val array = JSONArray(json)
        val expenses = mutableListOf<Expense>()

        // I turn each JSON object back into an Expense.
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            expenses.add(
                Expense(
                    obj.getLong("id"),
                    obj.getString("description"),
                    obj.getDouble("amount"),
                    obj.getString("category"),
                    obj.getLong("date")
                )
            )
        }

        // Newest expenses first.
        expenses.sortByDescending { it.dateMillis }
        return expenses
    }

    fun saveExpenses(expenses: List<Expense>) {
        val array = JSONArray()
        for (expense in expenses) {
            val obj = JSONObject()
            obj.put("id", expense.id)
            obj.put("description", expense.description)
            obj.put("amount", expense.amount)
            obj.put("category", expense.category)
            obj.put("date", expense.dateMillis)
            array.put(obj)
        }
        // apply() saves in the background so the screen doesn't freeze.
        prefs.edit().putString(KEY_EXPENSES, array.toString()).apply()
    }

    fun addExpense(expense: Expense) {
        val expenses = loadExpenses()
        expenses.add(expense)
        saveExpenses(expenses)
    }

    fun deleteExpense(id: Long) {
        val expenses = loadExpenses()
        expenses.removeAll { it.id == id }
        saveExpenses(expenses)
    }

    // SharedPreferences can't store a Double directly, so I store the budget as a Float.
    fun loadBudget(): Double = prefs.getFloat(KEY_BUDGET, 0f).toDouble()

    fun saveBudget(budget: Double) {
        prefs.edit().putFloat(KEY_BUDGET, budget.toFloat()).apply()
    }

    // A companion object is Kotlin's way of having "static" values.
    companion object {
        private const val KEY_EXPENSES = "expenses"
        private const val KEY_BUDGET = "budget"
    }
}
