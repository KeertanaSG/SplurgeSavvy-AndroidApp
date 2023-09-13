package com.example.splurgesavvy.activities.budget;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.category.DetailCategoryActivity;
import com.example.splurgesavvy.activities.category.EditCategoryActivity;
import com.example.splurgesavvy.activities.home.HomeTransactionActivity;
import com.example.splurgesavvy.activities.parcelable.ParcelableBudget;
import com.example.splurgesavvy.entities.Budget;
import com.example.splurgesavvy.viewModel.BudgetViewModel;
import com.example.splurgesavvy.viewModel.ExpenseViewModel;
import com.example.splurgesavvy.viewModel.UserViewModel;

public class DetailBudgetActivity extends AppCompatActivity {

    private TextView titleTextView, categoryTextView, valueDetailBudgetTextView,valueDetailSpentTextView;

    ParcelableBudget parcelableBudget;

    private UserViewModel userViewModel;
    long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_detail_budget); // Adjusted layout name

        titleTextView = findViewById(R.id.title);
        categoryTextView = findViewById(R.id.shopping);
        valueDetailBudgetTextView = findViewById(R.id.value_detail_budget);
        valueDetailSpentTextView = findViewById(R.id.value_spent);

        // Retrieve the ParcelableBudget from the Intent
        parcelableBudget = getIntent().getParcelableExtra("parcelableBudget");
        userId = parcelableBudget.getUserId();

        Toast.makeText(this, "Retrieved User ID: " + userId, Toast.LENGTH_SHORT).show();

        if (parcelableBudget != null) {
            populateViews(parcelableBudget);
        }

        ImageView deleteIcon = findViewById(R.id.trash);
        deleteIcon.setOnClickListener(view -> showDeleteConfirmationDialog());

        RelativeLayout editIcon = findViewById(R.id.button_edit);
        editIcon.setOnClickListener(v -> showEditBudgetLayout());

        ImageView backButtonIcon = findViewById(R.id.backButtonIcon);
        backButtonIcon.setOnClickListener(v -> goBack());
    }

    //Populate the Expense and Budget Values
    private void populateViews(ParcelableBudget parcelableBudget) {
        categoryTextView.setText(parcelableBudget.getName());

        BudgetViewModel budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        ExpenseViewModel expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        if (parcelableBudget != null) {
            observeExpenseAmountAndUpdateUI(parcelableBudget, expenseViewModel);
        }
    }

    //Observe the live data and update the UI accordingly
    private void observeExpenseAmountAndUpdateUI(ParcelableBudget parcelableBudget, ExpenseViewModel expenseViewModel) {
        expenseViewModel.getExpenseAmountForCategory(parcelableBudget.getUserId(), parcelableBudget.getName())
                .observe(this, expenseAmount -> {
                    if (expenseAmount != null) {
                        // Constructing the "amount_remaining" text
                        String amountRemainingText = String.format("%.2f of %.2f",
                                expenseAmount,
                                parcelableBudget.getValue());
                        valueDetailSpentTextView.setText(amountRemainingText);
                        double difference = parcelableBudget.getValue() - expenseAmount;
                        valueDetailBudgetTextView.setText(String.format("£%.2f", difference));

                        if (difference < 0) {
                            valueDetailBudgetTextView.setTextColor(Color.RED);
                        } else {
                            valueDetailBudgetTextView.setTextColor(Color.BLACK);
                        }
                    } else {
                        // Handle scenario when expenseAmount is not available
                        valueDetailBudgetTextView.setText("N/A");
                        valueDetailBudgetTextView.setTextColor(Color.GRAY);
                    }
                });
    }

    void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Budget")
                .setMessage("Are you sure you want to delete this budget?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteBudget();
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    void deleteBudget() {
        if (parcelableBudget != null) {
            BudgetViewModel budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);

            Long budgetId = budgetViewModel.getBudgetIdByAttributes(
                    parcelableBudget.getUserId(),
                    parcelableBudget.getName(),
                    parcelableBudget.getValue()
            );

            if(budgetId != null) {
                Budget budgetToDelete = new Budget();
                budgetToDelete.setBudgetId(budgetId);
                budgetViewModel.delete(budgetToDelete);
                Toast.makeText(this, "Budget deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Budget not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showEditBudgetLayout() {
        Intent editIntent = new Intent(DetailBudgetActivity.this, EditBudgetActivity.class);
        editIntent.putExtra("parcelableBudget", parcelableBudget);
        startActivity(editIntent);
        finish();
    }


    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        Intent backIntent = new Intent(DetailBudgetActivity.this, HomeTransactionActivity.class);
        backIntent.putExtra("userId", userId);
        startActivity(backIntent);
        finish();
    }
}
