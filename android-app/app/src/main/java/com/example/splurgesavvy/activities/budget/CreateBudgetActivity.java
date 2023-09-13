package com.example.splurgesavvy.activities.budget;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.home.HomeTransactionActivity;
import com.example.splurgesavvy.entities.Budget;
import com.example.splurgesavvy.entities.Category;
import com.example.splurgesavvy.viewModel.BudgetViewModel;
import com.example.splurgesavvy.viewModel.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class CreateBudgetActivity extends AppCompatActivity {

    public EditText budgetValueEditText;
    public Spinner categorySpinner;
    public BudgetViewModel budgetViewModel;
    public CategoryViewModel categoryViewModel;
    private long userId = -1;  // Initialize with -1 as invalid user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_create_budget);

        //Initialization
        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        budgetValueEditText = findViewById(R.id.valueEditText_create_budget);
        categorySpinner = findViewById(R.id.dropdownButton_spinner);

        // Get userId from intent
        userId = getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Set an adapter for the Spinner
        categoryViewModel.getAllCategoriesByUserId(userId).observe(this, categories -> {
            List<String> categoryNames = new ArrayList<>();
            for (Category category : categories) {
                categoryNames.add(category.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(adapter);
        });

        findViewById(R.id.button_continue).setOnClickListener(v -> {
            if (isValidBudgetInput()) {
                String category = categorySpinner.getSelectedItem().toString();
                double budgetAmount = Double.parseDouble(budgetValueEditText.getText().toString().trim());

                budgetViewModel.getBudgetByCategoryAndUser(userId, category).observe(this, budget -> {
                    if (budget != null) {
                        // A budget exists for the category and user
                        Toast.makeText(CreateBudgetActivity.this, "A budget already exists for this category.", Toast.LENGTH_SHORT).show();
                    } else {
                        // If No budget exists for the category and user
                        // Proceed with inserting the new budget
                        Budget newBudget = new Budget(userId, category, budgetAmount);
                        budgetViewModel.insert(newBudget);
                        Toast.makeText(CreateBudgetActivity.this, "Budget created successfully.", Toast.LENGTH_SHORT).show();
                        goBack();
                    }
                });
            } else {
                Toast.makeText(CreateBudgetActivity.this, "Please provide valid budget details.", Toast.LENGTH_SHORT).show();
            }
        });

        // Set a click listener to the back button
        findViewById(R.id.backButtonIcon).setOnClickListener(v -> goBack());
    }

    //check if its valid budget input
    public boolean isValidBudgetInput() {
        return categorySpinner.getSelectedItem() != null &&
                !budgetValueEditText.getText().toString().trim().isEmpty();
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    //Pass intent to next screen
    private void goBack() {
        Intent backIntent = new Intent(CreateBudgetActivity.this, HomeTransactionActivity.class);
        backIntent.putExtra("userId", userId);
        startActivity(backIntent);
        finish();
    }
}


