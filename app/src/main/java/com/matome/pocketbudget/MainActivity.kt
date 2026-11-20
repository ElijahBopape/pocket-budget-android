package com.matome.pocketbudget

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * MainActivity
 *
 * My home screen. It shows the monthly budget, how much I've spent, how much is left,
 * a progress bar, a breakdown per category and the list of expenses.
 */
class MainActivity : AppCompatActivity() {

    // "lateinit" means I promise to set these in onCreate before I use them.
    private lateinit var storage: ExpenseStorage
    private lateinit var adapter: ExpenseAdapter

    private lateinit var textBudget: TextView
    private lateinit var textSpent: TextView
    private lateinit var textRemaining: TextView
    private lateinit var textCategories: TextView
    private lateinit var textEmpty: TextView
    private lateinit var progressBudget: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storage = ExpenseStorage(this)

        textBudget = findViewById(R.id.textBudget)
        textSpent = findViewById(R.id.textSpent)
        textRemaining = findViewById(R.id.textRemaining)
        textCategories = findViewById(R.id.textCategories)
        textEmpty = findViewById(R.id.textEmpty)
        progressBudget = findViewById(R.id.progressBudget)

        // Set up the list. Holding down a row asks if I want to delete it.
        adapter = ExpenseAdapter(emptyList()) { expense -> confirmDelete(expense) }
        val recycler = findViewById<RecyclerView>(R.id.recyclerExpenses)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        findViewById<Button>(R.id.buttonAddExpense).setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        findViewById<Button>(R.id.buttonSetBudget).setOnClickListener {
            showBudgetDialog()
        }
    }

    // onResume runs every time this screen comes back into view,
    // e.g. after I save a new expense, so it's a good place to refresh.
    override fun onResume() {
        super.onResume()
        refreshScreen()
    }

    private fun refreshScreen() {
        val expenses = storage.loadExpenses()
        val budget = storage.loadBudget()

        // All the maths happens in my Java BudgetCalculator class.
        val spent = BudgetCalculator.totalSpent(expenses)
        val remaining = BudgetCalculator.remaining(budget, expenses)

        textBudget.text = if (budget > 0) formatRand(budget) else getString(R.string.no_budget)
        textSpent.text = formatRand(spent)
        textRemaining.text = formatRand(remaining)
        progressBudget.progress = BudgetCalculator.percentUsed(budget, expenses)

        // I turn the remaining amount red if I've gone over budget.
        if (BudgetCalculator.isOverBudget(budget, expenses)) {
            textRemaining.setTextColor(Color.RED)
        } else {
            textRemaining.setTextColor(Color.parseColor("#2E7D32"))
        }

        // Build a simple text breakdown, one category per line.
        val totals = BudgetCalculator.totalsByCategory(expenses)
        textCategories.text = if (totals.isEmpty()) {
            ""
        } else {
            totals.entries.joinToString("\n") { "${it.key}: ${formatRand(it.value)}" }
        }

        textEmpty.visibility = if (expenses.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
        adapter.updateExpenses(expenses)
    }

    private fun showBudgetDialog() {
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        input.hint = getString(R.string.budget_hint)

        AlertDialog.Builder(this)
            .setTitle(R.string.set_budget)
            .setView(input)
            .setPositiveButton(R.string.save) { _, _ ->
                // toDoubleOrNull() returns null instead of crashing if the text isn't a number.
                val value = input.text.toString().toDoubleOrNull()
                if (value == null || value <= 0) {
                    Toast.makeText(this, R.string.invalid_amount, Toast.LENGTH_SHORT).show()
                } else {
                    storage.saveBudget(value)
                    refreshScreen()
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun confirmDelete(expense: Expense) {
        AlertDialog.Builder(this)
            .setTitle(R.string.delete_expense)
            .setMessage(getString(R.string.delete_message, expense.description))
            .setPositiveButton(R.string.delete) { _, _ ->
                storage.deleteExpense(expense.id)
                refreshScreen()
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
}
