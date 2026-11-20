# Pocket Budget (Android, Kotlin + Java)

Hi, welcome! 👋

Pocket Budget is a simple Android app I built to help keep track of spending. You set a budget for the month, add what you spend as you go, and the app shows how much you've used, how much is left and which categories are eating your money.

I wrote the screens in **Kotlin** and the model and calculation logic in **Java**. I did that on purpose, because I wanted to see how the two languages work together in one Android project. (Turns out they get along really well.)

## What you'll need

- **Android Studio**, version Koala (2024.1) or newer. It's free from [developer.android.com/studio](https://developer.android.com/studio).
- **JDK 17**. You don't need to install this separately, because it comes bundled with Android Studio.
- **Somewhere to run the app**: either an emulator (Android Studio can set one up for you) or a real phone running **Android 7.0 or newer**.
- **An internet connection the first time** you open the project, so Gradle can download the Android libraries.

For the curious, the project uses Android Gradle Plugin 8.5.2, Kotlin 1.9.24, Gradle 8.7 and compiles against API 34. Android Studio sorts all of that out for you.

## Running it

1. Open Android Studio and choose **File → Open**, then pick this project folder.
2. Wait for Gradle to finish syncing. The first time can take a few minutes, so maybe grab a coffee ☕
3. Choose an emulator or plug in your phone (with USB debugging turned on).
4. Press the green **Run** button.

### Prefer the command line?

You can also build it with the Gradle wrapper:

```bash
# Windows
gradlew.bat assembleDebug

# macOS / Linux
./gradlew assembleDebug
```

You'll still need the Android SDK installed. The easiest way to get it is to install Android Studio anyway.

## Running the tests

There are JUnit tests for the budget calculations. In Android Studio, right-click `app/src/test/java/com/matome/pocketbudget/BudgetCalculatorTest.java` and choose **Run**.

Or from the terminal:

```bash
./gradlew test        # use gradlew.bat test on Windows
```

## What it can do

- Set a monthly budget
- Add an expense with a description, amount and category
- See a progress bar of how much of the budget you've used
- "Remaining" turns red when you go over budget (a gentle telling-off)
- See totals for each category
- Long-press an expense to delete it
- Everything is saved on the phone, so it's still there after you close the app

## How it's put together

```
app/src/main/java/com/matome/pocketbudget/
  MainActivity.kt          home screen with the summary and expense list
  AddExpenseActivity.kt    the form for adding an expense
  ExpenseAdapter.kt        turns expenses into rows in the list
  ExpenseStorage.kt        saves and loads the data
  Expense.java             the expense model
  BudgetCalculator.java    totals, remaining, percentage and per-category sums
app/src/main/res/          layouts, strings, colours and the theme
app/src/test/              unit tests
```

Under the hood it uses a `RecyclerView` for the list and `SharedPreferences` with JSON for saving.

## Things I know aren't perfect

- The budget doesn't reset by itself at the start of a new month.
- You can delete an expense but you can't edit one.
- `SharedPreferences` is fine for a small list, but a bigger app should really use a Room database.
- It only does one currency (Rand).

Thanks for checking it out! If you have ideas or spot something I could do better, please tell me.
