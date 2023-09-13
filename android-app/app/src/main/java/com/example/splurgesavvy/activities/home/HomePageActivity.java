package com.example.splurgesavvy.activities.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.manual.ManualExpenseActivity;
import com.example.splurgesavvy.database.AppDatabase;
import com.example.splurgesavvy.entities.Expense;
import com.example.splurgesavvy.viewModel.BudgetViewModel;
import com.example.splurgesavvy.viewModel.ExpenseViewModel;
import com.example.splurgesavvy.viewModel.UserViewModel;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomePageActivity extends AppCompatActivity {

    //Initialization
    private TextView budgetTextView;
    private TextView expenseTextView;

    private TextView chartDescription;
    private UserViewModel userViewModel;
    private BudgetViewModel budgetViewModel;
    private ExpenseViewModel expenseViewModel;
    private long currentUserId;

    private LineChart lineChart;

    private final String PREFS_NAME = "app_preferences";
    private final String KEY_USER_ID = "currentUserId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        //Declaring variables
        budgetTextView = findViewById(R.id.balance_amount);
        expenseTextView = findViewById(R.id.expense_amount);
        chartDescription = findViewById(R.id.chart_description);

        // Initialize the ViewModels
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        // Check if the intent contains userId. If not, fallback to username
        if (getIntent().hasExtra("userId")) {
            currentUserId = getIntent().getLongExtra("userId", -1);
            updateUserData(currentUserId);
        } else {
            // Get username from intent
            String username = getIntent().getStringExtra("username");
            fetchUserId(username);
        }

        // Set the text for the TextView
        chartDescription.setText("The line chart above illustrates your daily expenses for the past 10 days. " +
                "Each point on the line represents the total amount spent on a specific day. Days without any expenses are marked as zero on the y-axis. " +
                "This visualization aims to provide a quick insight into your spending habits, helping you understand your financial behavior better.");
        chartDescription.setTextSize(10);

        initializeBottomNavigation();
    }

    void updateUserData(long userId) {
        if (userId != -1) {
            budgetViewModel.getInitialBudgetByUserId(userId).observe(this, budget -> {
                if (budget != null) {
                    budgetTextView.setText(String.format(Locale.getDefault(), "%.2f", budget.getValue()));
                }
            });

            expenseViewModel.getAllExpensesByUserId(userId).observe(this, expenses -> {
                double totalExpenses = 0.0;
                for (Expense expense : expenses) {
                    totalExpenses += expense.getAmount();
                }
                expenseTextView.setText(String.format(Locale.getDefault(), "%.2f", totalExpenses));
            });

            // Initialize LineChart
            LineChart lineChart = findViewById(R.id.spend_chart);

            // Set chart description
            Description description = new Description();
            description.setText("Sum Of Expenses for the day");
            description.setTextSize(12f);
            lineChart.setDescription(description);

            // Custom formatter for x-axis to display dates
            ValueFormatter xAxisFormatter = new ValueFormatter() {
                private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd", Locale.getDefault());

                @Override
                public String getFormattedValue(float value) {
                    return sdf.format(new Date((long) value));
                }
            };

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setValueFormatter(xAxisFormatter);

            // Get the current date and time
            Calendar calendar = Calendar.getInstance();
            Date endDate = calendar.getTime();  // Sets end date as today

            // Go back 10 days from the current date
            calendar.add(Calendar.DATE, -10);
            Date startDate = calendar.getTime();  // Sets start date as 10 days ago

            // Observe the data from ViewModel and update UI based on userId
            expenseViewModel.getExpensesByUserIdAndDate(userId, startDate, endDate).observe(this, expenses -> {
                Map<Long, Float> dailySums = new HashMap<>();
                SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

                for (Expense expense : expenses) {
                    String dayStr = dayFormat.format(expense.getDate());
                    Long day = Long.valueOf(dayStr);

                    float existingAmount = 0f;
                    if (dailySums.containsKey(day)) {
                        existingAmount = dailySums.get(day);
                    }

                    dailySums.put(day, existingAmount + (float) expense.getAmount());
                }

                List<Entry> entries = new ArrayList<>();

                // Initialize calendar to start date and loop through each day to endDate
                Calendar currentDay = Calendar.getInstance();
                currentDay.setTime(startDate);
                while (!currentDay.getTime().after(endDate)) {
                    String dayStr = dayFormat.format(currentDay.getTime());
                    Long day = Long.valueOf(dayStr);

                    // Manually check for value
                    float amount;
                    if (dailySums.containsKey(day)) {
                        amount = dailySums.get(day);
                    } else {
                        amount = 0f;
                    }

                    entries.add(new Entry(currentDay.getTime().getTime(), amount));

                    // Move to the next day
                    currentDay.add(Calendar.DATE, 1);
                }

                LineDataSet dataSet = new LineDataSet(entries, "Last 10 Days Expenses");


                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();  // Refresh the chart

                // Auto scale the axes
                lineChart.getXAxis().setAxisMinimum(entries.get(0).getX());
                lineChart.getXAxis().setAxisMaximum(entries.get(entries.size() - 1).getX());
            });

        }
    }

    private void fetchUserId(String username) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            currentUserId = userViewModel.getUserIdByUsername(username);

            // Store userId in shared preferences
            storeUserIdInPreferences(currentUserId);

            runOnUiThread(() -> {
                updateUserData(currentUserId);
            });
        });
    }

    //Initialize the navigation
    private void initializeBottomNavigation() {
        ImageView homeIcon = findViewById(R.id.home_icon);
        homeIcon.setOnClickListener(view -> Toast.makeText(this, "Transactions refreshed!", Toast.LENGTH_SHORT).show());

        ImageView transactionIcon = findViewById(R.id.transaction_icon);
        transactionIcon.setOnClickListener(view -> navigateTo(HomeTransactionActivity.class));

        ImageView createExpenseIcon = findViewById(R.id.create_expense_icon);
        createExpenseIcon.setOnClickListener(view -> navigateTo(ManualExpenseActivity.class));

        ImageView profileIcon = findViewById(R.id.profile_icon);
        profileIcon.setOnClickListener(view -> navigateTo(HomeProfileActivity.class));

        ImageView budgetIcon = findViewById(R.id.budget_icon);
        budgetIcon.setOnClickListener(view -> navigateTo(HomeBudgetActivity.class));
    }

    private void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        intent.putExtra("userId", currentUserId);
        startActivity(intent);
        Toast.makeText(this, "User ID: " + currentUserId, Toast.LENGTH_SHORT).show();
    }

    // Utility methods for Shared Preferences
    void storeUserIdInPreferences(long userId) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(KEY_USER_ID, userId);
        editor.apply();
    }

    long getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return sharedPreferences.getLong(KEY_USER_ID, -1);  // Returns -1 if not found
    }
}
