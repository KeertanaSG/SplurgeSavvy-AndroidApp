package com.example.splurgesavvy.activities.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.database.AppDatabase;
import com.example.splurgesavvy.entities.Budget;
import com.example.splurgesavvy.entities.Category;
import com.example.splurgesavvy.entities.User;
import com.example.splurgesavvy.viewModel.BudgetViewModel;
import com.example.splurgesavvy.viewModel.CategoryViewModel;
import com.example.splurgesavvy.viewModel.UserViewModel;

public class NewBudgetActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText valueEditText;
    private BudgetViewModel budgetViewModel;
    private UserViewModel userViewModel;

    private CategoryViewModel categoryViewModel;

    private String username;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_budget_amount);

        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);


        nameEditText = findViewById(R.id.nameEditText);
        valueEditText = findViewById(R.id.valueEditText);

        // Retrieve username from the Intent
        username = getIntent().getStringExtra("username");

        // Fetch the userId using the username
        AppDatabase.databaseWriteExecutor.execute(() -> {
            User user = userViewModel.getUserByUsername(username);
            if (user != null) {
                userId = user.getUserId();
            }
        });

        findViewById(R.id.button_continue).setOnClickListener(v -> {
            if (isValidBudgetInput()) {
                String budgetName = nameEditText.getText().toString().trim();
                double budgetValue = Double.parseDouble(valueEditText.getText().toString().trim());

                if (userId == -1) {
                    Toast.makeText(NewBudgetActivity.this, "Invalid User ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                Budget budget = new Budget(userId, budgetName, budgetValue);

                budgetViewModel.insert(budget);

                // Initialize default categories for this user
                String[] defaultCategories = {"Food", "Transport", "Entertainment", "Gourmet", "Clothing", "Electronics", "Hobbies", "Travel", "Beauty And Cosmetics", "Home Decor"};
                for (String category : defaultCategories) {
                    Category newCategory = new Category(userId, category, "Default description for " + category);
                    categoryViewModel.insert(newCategory); // Using CategoryViewModel to insert.
                }

                Intent intent = new Intent(NewBudgetActivity.this, SignupSuccessActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(NewBudgetActivity.this, "Please provide valid budget details.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidBudgetInput() {
        if (nameEditText.getText().toString().trim().isEmpty()) {
            return false;
        }
        try {
            double value = Double.parseDouble(valueEditText.getText().toString().trim());
            if (value < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}





