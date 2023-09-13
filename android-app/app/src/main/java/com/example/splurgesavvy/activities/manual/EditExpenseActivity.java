package com.example.splurgesavvy.activities.manual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.budget.EditBudgetActivity;
import com.example.splurgesavvy.activities.home.HomeTransactionActivity;
import com.example.splurgesavvy.activities.parcelable.ParcelableExpense;
import com.example.splurgesavvy.entities.Budget;
import com.example.splurgesavvy.entities.Expense;
import com.example.splurgesavvy.viewModel.CategoryViewModel;
import com.example.splurgesavvy.viewModel.ExpenseViewModel;
import com.example.splurgesavvy.viewModel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class EditExpenseActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText descriptionEditText;
    EditText valueEditText;
    private Spinner categorySpinner;
    ExpenseViewModel expenseViewModel;
    private UserViewModel userViewModel;
    private long userId;
    private ParcelableExpense parcelableExpense;
    private CategoryViewModel expenseCategoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_expense_edit);

        // Initialize ViewModel
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        expenseCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        // Initialize Views
        nameEditText = findViewById(R.id.nameEditTextName);
        descriptionEditText = findViewById(R.id.nameEditTextdes);
        valueEditText = findViewById(R.id.valueEditText);
        categorySpinner = findViewById(R.id.dropdownButton_spinner);

        // Retrieve ParcelableExpense from intent
        parcelableExpense = getIntent().getParcelableExtra("parcelableExpense");
        userId = parcelableExpense.getUserId();

        // Populate fields
        if (parcelableExpense != null) {
            nameEditText.setText(parcelableExpense.getName());
            descriptionEditText.setText(parcelableExpense.getDescription());
            valueEditText.setText(String.valueOf(parcelableExpense.getAmount()));

            // Set adapter for Spinner
            expenseCategoryViewModel.getAllCategoriesByUserId(userId).observe(this, categories -> {
                List<String> categoryNames = new ArrayList<>();
                for (com.example.splurgesavvy.entities.Category category : categories) {
                    categoryNames.add(category.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categorySpinner.setAdapter(adapter);
                categorySpinner.setSelection(categoryNames.indexOf(parcelableExpense.getName()));
            });
        }


        // Handle button click for updating expense
        findViewById(R.id.button_save_changes).setOnClickListener(v -> {
            if (isValidExpenseInput()) {
                String newExpenseName = nameEditText.getText().toString().trim();
                String newExpenseDescription = descriptionEditText.getText().toString().trim();
                double newExpenseValue = Double.parseDouble(valueEditText.getText().toString().trim());
                String newExpenseCategory = categorySpinner.getSelectedItem().toString();

                Long expenseId = expenseViewModel.getExpenseIdByAttributes(userId, parcelableExpense.getCategoryId(), parcelableExpense.getName(), parcelableExpense.getAmount(), parcelableExpense.getDescription(), parcelableExpense.getCategory(), parcelableExpense.getDate());

                if (expenseId != null) {
                    Expense updatedExpense = new Expense(userId, parcelableExpense.getCategoryId(), newExpenseName, newExpenseValue, newExpenseDescription, newExpenseCategory, parcelableExpense.getDate());
                    updatedExpense.setExpenseId(expenseId);
                    expenseViewModel.update(updatedExpense);
                    Toast.makeText(EditExpenseActivity.this, "Expense updated successfully.", Toast.LENGTH_SHORT).show();
                    goBack();
                } else {
                    Toast.makeText(EditExpenseActivity.this, "Failed to update expense.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(EditExpenseActivity.this, "Please provide valid expense details.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected boolean isValidExpenseInput() {
        if (nameEditText.getText().toString().trim().isEmpty() ||
                descriptionEditText.getText().toString().trim().isEmpty()) {
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

    private void goBack() {
        Intent backIntent = new Intent(EditExpenseActivity.this, HomeTransactionActivity.class);
        backIntent.putExtra("userId", userId);
        startActivity(backIntent);
        finish();
    }
}

