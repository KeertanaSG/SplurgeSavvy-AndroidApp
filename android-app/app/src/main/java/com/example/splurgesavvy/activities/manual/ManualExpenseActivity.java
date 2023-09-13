package com.example.splurgesavvy.activities.manual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.home.HomeTransactionActivity;
import com.example.splurgesavvy.activities.settings.SettingsAbout;
import com.example.splurgesavvy.activities.settings.Settings_Activity;
import com.example.splurgesavvy.database.AppDatabase;
import com.example.splurgesavvy.entities.Category;
import com.example.splurgesavvy.entities.Expense;
import com.example.splurgesavvy.entities.User;
import com.example.splurgesavvy.viewModel.CategoryViewModel;
import com.example.splurgesavvy.viewModel.ExpenseViewModel;
import com.example.splurgesavvy.viewModel.UserViewModel;

public class ManualExpenseActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText descriptionEditText;
    EditText valueEditText;
    private Spinner categorySpinner;
    ExpenseViewModel expenseViewModel;
    private UserViewModel userViewModel;
    private String username;
    long userId ;

    CategoryViewModel expenseCategoryViewModel;

    //get today's date
    Date todayDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String formattedToday = formatter.format(todayDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_expense);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        expenseCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        nameEditText = findViewById(R.id.nameEditTextName);
        descriptionEditText = findViewById(R.id.nameEditTextdes);
        valueEditText = findViewById(R.id.valueEditText);
        categorySpinner = findViewById(R.id.dropdownButton_spinner);

        // Initialize the back button view from the layout
        ImageView backButtonIcon = findViewById(R.id.backButtonIcon);

        // Initialize the ViewModels
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Get userId from intent
        userId = getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Set an adapter for the Spinner
        expenseCategoryViewModel.getAllCategoriesByUserId(userId).observe(this, categories -> {
            List<String> categoryNames = new ArrayList<>();
            for (Category category : categories) {
                categoryNames.add(category.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(adapter);
        });

        findViewById(R.id.button_continue).setOnClickListener(v -> {
            if (isValidExpenseInput()) {
                String expenseName = nameEditText.getText().toString().trim();
                String expenseDescription = descriptionEditText.getText().toString().trim();
                double expenseValue = Double.parseDouble(valueEditText.getText().toString().trim());
                String expenseCategoryName = categorySpinner.getSelectedItem().toString();

                if (userId == -1) {
                    Toast.makeText(ManualExpenseActivity.this, "Invalid User ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get categoryId based on category name
                expenseCategoryViewModel.getCategoryIdByName(expenseCategoryName).observe(this, categoryId -> {
                    if (categoryId != null && categoryId != -1) {
                        Expense expense = new Expense(userId, categoryId, expenseName, expenseValue, expenseDescription, expenseCategoryName, todayDate);
                        expenseViewModel.insert(expense);
                        Toast.makeText(ManualExpenseActivity.this, "Expense added successfully.", Toast.LENGTH_SHORT).show();
                        goBack();
                    } else {
                        Toast.makeText(ManualExpenseActivity.this, "Invalid Category", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Toast.makeText(ManualExpenseActivity.this, "Please provide valid expense details.", Toast.LENGTH_SHORT).show();
            }
        });

        // Set a click listener to the back button
        backButtonIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
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

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        Intent backIntent = new Intent(ManualExpenseActivity.this, HomeTransactionActivity.class);
        backIntent.putExtra("userId", userId);
        startActivity(backIntent);
        finish();
    }
}

