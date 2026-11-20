# Pocket Budget (Android – Kotlin + Java)

A simple Android budgeting app I built for practice. You set a monthly budget, log what you spend, and the app shows how much you've used, what's left and where the money went by category.

I wrote the screens in **Kotlin** and the model and calculation logic in **Java**, to practise using both languages in one Android project.

## Features
- Set a monthly budget
- Add expenses with a description, amount and category
- Progress bar showing how much of the budget is used
- "Remaining" turns red when you go over budget
- Totals per category
- Long-press an expense to delete it
- Data is saved on the phone, so it's still there after closing the app

## Tech
- Kotlin (Activities, adapter, storage)
- Java (`Expense` model and `BudgetCalculator`)
- RecyclerView for the expense list
- SharedPreferences + JSON for saving data
- JUnit tests for the calculator

## How to run
1. Open the project folder in **Android Studio** (Koala or newer).
2. Let Gradle sync. It downloads the Android libraries.
3. Press **Run** to start it on an emulator or a phone (Android 7.0+).

To run the unit tests, right-click `app/src/test/.../BudgetCalculatorTest.java` and choose **Run**.

## Project structure
```
app/src/main/java/com/matome/pocketbudget/
  MainActivity.kt          home screen with summary and list
  AddExpenseActivity.kt    form to add an expense
  ExpenseAdapter.kt        turns expenses into list rows
  ExpenseStorage.kt        saves and loads data
  Expense.java             the expense model
  BudgetCalculator.java    totals, remaining, percentage, per-category
app/src/main/res/          layouts, strings, colours, theme
app/src/test/              unit tests
```

## Known limitations
- The budget doesn't reset automatically at the start of each month.
- No editing an expense, only deleting.
- SharedPreferences is fine for a small list; a bigger app should use a Room database.
- Only one currency (Rand).
