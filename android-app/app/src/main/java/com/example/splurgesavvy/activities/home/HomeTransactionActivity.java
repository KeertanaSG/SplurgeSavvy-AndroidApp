package com.example.splurgesavvy.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.manual.DetailTransactionActivity;
import com.example.splurgesavvy.activities.manual.ManualExpenseActivity;
import com.example.splurgesavvy.activities.parcelable.ParcelableExpense;
import com.example.splurgesavvy.adapters.ExpenseAdapter;
import com.example.splurgesavvy.entities.Expense;
import com.example.splurgesavvy.viewModel.ExpenseViewModel;

public class HomeTransactionActivity extends AppCompatActivity {
    private ExpenseViewModel expenseViewModel;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_expense);

        userId = getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ExpenseAdapter adapter = new ExpenseAdapter();
        recyclerView.setAdapter(adapter);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseViewModel.getAllExpensesByUserId(userId).observe(this, expenses -> {
            adapter.setExpenses(expenses);
            adapter.notifyDataSetChanged();
        });

        adapter.setOnItemClickListener(expense -> {
            Intent intent = new Intent(HomeTransactionActivity.this, DetailTransactionActivity.class);
            Toast.makeText(this, "User ID: " + userId, Toast.LENGTH_SHORT).show();


            // Convert Expense to ParcelableExpense and pass it
            ParcelableExpense parcelableExpense = new ParcelableExpense(
                    expense.getUserId(),
                    expense.getCategoryId(),
                    expense.getCategory(),
                    expense.getName(),
                    expense.getAmount(),
                    expense.getDescription(),
                    expense.getDate()
            );
            intent.putExtra("parcelableExpense", parcelableExpense);
            startActivity(intent);
        });

        // Search Icon
        ImageView searchIcon = findViewById(R.id.search);
        searchIcon.setOnClickListener(view -> {
            Toast.makeText(this, "Search clicked!", Toast.LENGTH_SHORT).show();
        });

        // Filter Icon
        ImageView filterIcon = findViewById(R.id.filter);
        filterIcon.setOnClickListener(view -> {
            Toast.makeText(this, "Filter clicked!", Toast.LENGTH_SHORT).show();
        });

        // Bottom Navigation Icons

        // Home Icon
        ImageView homeIcon = findViewById(R.id.home_icon);
        homeIcon.setOnClickListener(view -> {
            // Navigate to Home page
            Intent intent = new Intent(this, HomePageActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
            Toast.makeText(this, "User ID: " + userId, Toast.LENGTH_SHORT).show();
        });

        // Transaction Icon
        ImageView transactionIcon = findViewById(R.id.transaction_icon);
        transactionIcon.setOnClickListener(view -> {
            // Show userId in a Toast
            Toast.makeText(this, "User ID: " + userId, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Transactions refreshed!", Toast.LENGTH_SHORT).show();
        });

        // Create Expense Icon
        ImageView createExpenseIcon = findViewById(R.id.create_expense_icon);
        createExpenseIcon.setOnClickListener(view -> {
            // Navigate to Create Expense page
            Intent intent = new Intent(this, ManualExpenseActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
            Toast.makeText(this, "User ID: " + userId, Toast.LENGTH_SHORT).show();
        });

        // Profile Icon
        ImageView profileIcon = findViewById(R.id.profile_icon);
        profileIcon.setOnClickListener(view -> {
            // Navigate to Profile page
            Intent intent = new Intent(this, HomeProfileActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
            Toast.makeText(this, "User ID: " + userId, Toast.LENGTH_SHORT).show();
        });

        // Budget Icon
        ImageView budgetIcon = findViewById(R.id.budget_icon);
        budgetIcon.setOnClickListener(view -> {
            // Navigate to Budget page
            Intent intent = new Intent(this, HomeBudgetActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
            Toast.makeText(this, "User ID: " + userId, Toast.LENGTH_SHORT).show();
        });

    }
}
