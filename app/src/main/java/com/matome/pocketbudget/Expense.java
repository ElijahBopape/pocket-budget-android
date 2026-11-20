package com.matome.pocketbudget;

/**
 * Expense
 *
 * I wrote my model class in Java on purpose, to show that Java and Kotlin
 * can live in the same Android project. My Kotlin code uses this class directly.
 *
 * One Expense is one thing I spent money on, e.g. "Taxi to campus, R25, Transport".
 */
public class Expense {

    private final long id;          // I use the time it was created as a simple unique ID
    private final String description;
    private final double amount;
    private final String category;
    private final long dateMillis;  // the date stored as milliseconds, which is easy to save and sort

    public Expense(long id, String description, double amount, String category, long dateMillis) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.dateMillis = dateMillis;
    }

    public long getId() { return id; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public long getDateMillis() { return dateMillis; }
}
