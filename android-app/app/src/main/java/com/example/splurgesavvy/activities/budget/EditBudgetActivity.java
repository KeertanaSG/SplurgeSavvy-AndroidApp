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
import com.example.splurgesavvy.activities.parcelable.ParcelableBudget;
import com.example.splurgesavvy.entities.Budget;
import com.example.splurgesavvy.entities.Category;
import com.example.splurgesavvy.viewModel.BudgetViewModel;
import com.example.splurgesavvy.viewModel.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class EditBudgetActivity extends AppCompatActivity {

    //Initialization
    private EditText budgetValueEditText;
    private Spinner categorySpinner;
    private BudgetViewModel budgetViewModel;
    private CategoryViewModel categoryViewModel;
    private ParcelableBudget parcelableBudget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initiliazations
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_edit_budget);

        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        budgetValueEditText = findViewById(R.id.valueEditText_create_budget);
        categorySpinner = findViewById(R.id.dropdownButton_spinner);

        // Get ParcelableBudget from intent
        parcelableBudget = getIntent().getParcelableExtra("parcelableBudget");

        if (parcelableBudget != null) {
            budgetValueEditText.setText(String.valueOf(parcelableBudget.getValue()));
            long userId = parcelableBudget.getUserId();

            //Update Category spinner dynamically
            categoryViewModel.getAllCategoriesByUserId(userId).observe(this, categories -> {
                List<String> categoryNames = new ArrayList<>();
                for (Category category : categories) {
                    categoryNames.add(category.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categorySpinner.setAdapter(adapter);
                categorySpinner.setSelection(categoryNames.indexOf(parcelableBudget.getName()));
            });

            //check if valid budget input
            findViewById(R.id.button_continue).setOnClickListener(v -> {
                if (isValidBudgetInput()) {
                    String newCategory = categorySpinner.getSelectedItem().toString();
                    double newBudgetAmount = Double.parseDouble(budgetValueEditText.getText().toString().trim());

                    Long budgetId = budgetViewModel.getBudgetIdByAttributes(userId, parcelableBudget.getName(), parcelableBudget.getValue());
                    if (budgetId != null) {
                        Budget updatedBudget = new Budget(userId, newCategory, newBudgetAmount);
                        updatedBudget.setBudgetId(budgetId);
                        budgetViewModel.update(updatedBudget);
                        Toast.makeText(EditBudgetActivity.this, "Budget updated successfully.", Toast.LENGTH_SHORT).show();
                        goBack(userId);
                    } else {
                        Toast.makeText(EditBudgetActivity.this, "Failed to update budget.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Invalid budget details", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    private boolean isValidBudgetInput() {
        return categorySpinner.getSelectedItem() != null &&
                !budgetValueEditText.getText().toString().trim().isEmpty();
    }

    //Passing User_id to next screen
    private void goBack(long userId) {
        Intent backIntent = new Intent(EditBudgetActivity.this, HomeTransactionActivity.class);
        backIntent.putExtra("userId", userId);
        startActivity(backIntent);
        finish();
    }
}
