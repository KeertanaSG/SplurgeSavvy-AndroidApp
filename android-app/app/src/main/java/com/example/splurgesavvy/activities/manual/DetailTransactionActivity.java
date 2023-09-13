package com.example.splurgesavvy.activities.manual;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.budget.DetailBudgetActivity;
import com.example.splurgesavvy.activities.budget.EditBudgetActivity;
import com.example.splurgesavvy.activities.home.HomeTransactionActivity;
import com.example.splurgesavvy.activities.parcelable.ParcelableExpense;
import com.example.splurgesavvy.entities.Expense;
import com.example.splurgesavvy.viewModel.ExpenseViewModel;
import com.example.splurgesavvy.viewModel.UserViewModel;

import java.text.SimpleDateFormat;

public class DetailTransactionActivity extends AppCompatActivity {

    TextView nameTextView;
    TextView amountTextView;
    TextView dateTextView;
    TextView categoryTextView;
    TextView descriptionTextView;
    private ParcelableExpense parcelableExpense;

    private UserViewModel userViewModel;
    private ExpenseViewModel expenseViewModel;
    private long userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_transaction);

        nameTextView = findViewById(R.id.expense_Name);
        amountTextView = findViewById(R.id.expenseAmount);
        dateTextView = findViewById(R.id.date);
        categoryTextView = findViewById(R.id.expenseCategory);
        descriptionTextView = findViewById(R.id.amet_minim_);

        // Retrieve the ParcelableExpense from the Intent
        parcelableExpense = getIntent().getParcelableExtra("parcelableExpense");
        userId = parcelableExpense.getUserId();

        Toast.makeText(this, "Retrieved User ID: " + userId, Toast.LENGTH_SHORT).show();
        
        if (parcelableExpense != null) {
            populateViews(parcelableExpense);
        }
        Toast.makeText(this, "Category ID: " + parcelableExpense.getCategoryId(), Toast.LENGTH_SHORT).show();

        ImageView deleteIcon = findViewById(R.id.trash);
        deleteIcon.setOnClickListener(view -> showDeleteConfirmationDialog());

        RelativeLayout editIcon = findViewById(R.id.button_continue);
        editIcon.setOnClickListener(v -> showEditExpenseLayout());


        // Initialize the back button view from the layout
        ImageView backButtonIcon = findViewById(R.id.backButtonIcon);

        // Initialize the ViewModels
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Initialize ViewModel
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        // Set a click listener to the back button
        backButtonIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    void populateViews(ParcelableExpense parcelableExpense) {
        nameTextView.setText(parcelableExpense.getName());
        amountTextView.setText(String.valueOf(parcelableExpense.getAmount()));

        // Format the Date before setting it to the TextView
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Format as day-month-year
        String formattedDate = sdf.format(parcelableExpense.getDate());
        dateTextView.setText(formattedDate);

        categoryTextView.setText(parcelableExpense.getCategory());
        descriptionTextView.setText(parcelableExpense.getDescription());
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Expense")
                .setMessage("Are you sure you want to delete this expense?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteExpense();
                        finish();  // Close the current activity after deletion
                    }
                })
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteExpense() {
        if (parcelableExpense != null) {
            ExpenseViewModel expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

            Long expenseId = expenseViewModel.getExpenseIdByAttributes(
                    parcelableExpense.getUserId(),
                    parcelableExpense.getCategoryId(),
                    parcelableExpense.getName(),
                    parcelableExpense.getAmount(),
                    parcelableExpense.getDescription(),
                    parcelableExpense.getCategory(),
                    parcelableExpense.getDate()
        );
            if(expenseId != null) {
                Expense expenseToDelete = new Expense();
                expenseToDelete.setExpenseId(expenseId);
                expenseViewModel.delete(expenseToDelete);
                Toast.makeText(this, "Expense deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Expense not found", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void showEditExpenseLayout() {
        Intent editIntent = new Intent(DetailTransactionActivity.this, EditExpenseActivity.class);
        editIntent.putExtra("parcelableExpense", parcelableExpense);
        startActivity(editIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        Intent backIntent = new Intent(DetailTransactionActivity.this, HomeTransactionActivity.class);
        backIntent.putExtra("user_id", userId);
        startActivity(backIntent);
        finish();
        Toast.makeText(this, "User ID: " + userId, Toast.LENGTH_SHORT).show();
    }
}
