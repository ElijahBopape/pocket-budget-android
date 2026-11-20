package com.matome.pocketbudget

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

/**
 * AddExpenseActivity
 *
 * The screen where I type in a new expense. When I press Save it checks the input,
 * saves the expense and closes, which takes me back to MainActivity.
 */
class AddExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        // Shows the back arrow in the top bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val editDescription = findViewById<EditText>(R.id.editDescription)
        val editAmount = findViewById<EditText>(R.id.editAmount)
        val spinnerCategory = findViewById<Spinner>(R.id.spinnerCategory)

        // The categories come from res/values/strings.xml so they're easy to change.
        val categories = resources.getStringArray(R.array.categories)
        spinnerCategory.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)

        findViewById<Button>(R.id.buttonSave).setOnClickListener {
            val description = editDescription.text.toString().trim()
            val amount = editAmount.text.toString().toDoubleOrNull()

            // I show the error right on the field that's wrong.
            if (description.isEmpty()) {
                editDescription.error = getString(R.string.error_description)
                return@setOnClickListener
            }
            if (amount == null || amount <= 0) {
                editAmount.error = getString(R.string.invalid_amount)
                return@setOnClickListener
            }

            val now = System.currentTimeMillis()
            val expense = Expense(now, description, amount, spinnerCategory.selectedItem.toString(), now)
            ExpenseStorage(this).addExpense(expense)

            finish() // close this screen and go back
        }
    }

    // Makes the back arrow in the top bar work.
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
