package com.matome.pocketbudget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * ExpenseAdapter
 *
 * A RecyclerView needs an adapter to turn each Expense into a row on the screen.
 * I pass in a function (onLongClick) so MainActivity decides what happens when a row is held down.
 */
class ExpenseAdapter(
    private var expenses: List<Expense>,
    private val onLongClick: (Expense) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    // The ViewHolder keeps references to the views in one row, so I don't search for them every time.
    class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val description: TextView = view.findViewById(R.id.textDescription)
        val details: TextView = view.findViewById(R.id.textDetails)
        val amount: TextView = view.findViewById(R.id.textAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.description.text = expense.description
        holder.details.text = "${expense.category} • ${dateFormat.format(Date(expense.dateMillis))}"
        holder.amount.text = formatRand(expense.amount)

        holder.itemView.setOnLongClickListener {
            onLongClick(expense)
            true // true means "I handled this click"
        }
    }

    override fun getItemCount(): Int = expenses.size

    // I call this after adding or deleting so the list on screen updates.
    fun updateExpenses(newExpenses: List<Expense>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }
}

// A small helper I use on every screen to show money as "R1 234.50".
fun formatRand(amount: Double): String = "R" + String.format(Locale.US, "%,.2f", amount).replace(",", " ")
