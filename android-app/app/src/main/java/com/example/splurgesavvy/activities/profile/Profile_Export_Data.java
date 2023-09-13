package com.example.splurgesavvy.activities.profile;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.entities.Budget;
import com.example.splurgesavvy.entities.Category;
import com.example.splurgesavvy.entities.Expense;
import com.example.splurgesavvy.viewModel.BudgetViewModel;
import com.example.splurgesavvy.viewModel.CategoryViewModel;
import com.example.splurgesavvy.viewModel.ExpenseViewModel;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class Profile_Export_Data extends AppCompatActivity {

    private static final String TAG = "Profile_Export_Data";  // TAG for logging

    private long userId;
    private RelativeLayout confirmButton;
    BudgetViewModel budgetViewModel;
    ExpenseViewModel expenseViewModel;
    CategoryViewModel categoryViewModel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_export_data);

        userId = getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            Log.d(TAG, "Invalid user ID");
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        confirmButton = findViewById(R.id.button_confirm);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Confirm Button Clicked");
                exportData();
            }
        });
    }

    private void exportData() {
        Log.d(TAG, "Exporting data");

        budgetViewModel.getAllBudgetsByUserId(userId).observe(this, new Observer<List<Budget>>() {
            @Override
            public void onChanged(List<Budget> budgets) {
                Log.d(TAG, "Inside observer. Budgets fetched: " + budgets);
                exportToCSV(budgets);
            }
        });

        expenseViewModel.getAllExpensesByUserId(userId).observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                Log.d(TAG, "Inside observer. Budgets fetched: " + expenses);
                exportToCSV(expenses);
            }
        });

        categoryViewModel.getAllCategoriesByUserId(userId).observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                Log.d(TAG, "Inside observer. Budgets fetched: " + categories);
                exportToCSV(categories);
            }
        });
    }

    private <T> void exportToCSV(List<T> items) {
        Log.d(TAG, "Inside exportToCSV");

        if (items == null || items.isEmpty()) {
            Log.d(TAG, "No data found to export");
            Toast.makeText(this, "No data found to export", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder csvData = new StringBuilder();

        if (!items.isEmpty()) {
            Object firstItem = items.get(0);

            if (firstItem instanceof Budget) {
                csvData.append("BudgetId,UserId,Name,Value\n");
                for (T item : items) {
                    Budget budget = (Budget) item;
                    csvData.append(budget.getBudgetId()).append(",")
                            .append(budget.getUserId()).append(",")
                            .append(budget.getName()).append(",")
                            .append(budget.getValue()).append("\n");
                }
            } else if (firstItem instanceof Expense) {
                csvData.append("ExpenseId,UserId,CategoryId,Name,Amount,Description,Date\n");
                for (T item : items) {
                    Expense expense = (Expense) item;
                    csvData.append(expense.getExpenseId()).append(",")
                            .append(expense.getUserId()).append(",")
                            .append(expense.getCategoryId()).append(",")
                            .append(expense.getName()).append(",")
                            .append(expense.getAmount()).append(",")
                            .append(expense.getDescription()).append(",")
                            .append(expense.getDate()).append("\n");
                }
            } else if (firstItem instanceof Category) {
                csvData.append("CategoryId,UserId,Name,Description\n");
                for (T item : items) {
                    Category category = (Category) item;
                    csvData.append(category.getCategoryId()).append(",")
                            .append(category.getUserId()).append(",")
                            .append(category.getName()).append(",")
                            .append(category.getDescription()).append("\n");
                }
            }

            Log.d(TAG, "CSV data generated: " + csvData.toString());

            OutputStream fos = null;
            try {
                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, "exported_data.csv");
                values.put(MediaStore.MediaColumns.MIME_TYPE, "text/csv");
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                ContentResolver resolver = getContentResolver();
                Uri fileUri = resolver.insert(MediaStore.Files.getContentUri("external"), values);

                Log.d(TAG, "File URI: " + fileUri);

                if (fileUri != null) {
                    fos = resolver.openOutputStream(fileUri);
                    Log.d(TAG, "File Output Stream: " + fos);

                    if (fos != null) {
                        fos.write(csvData.toString().getBytes());
                        fos.close();
                        Log.d(TAG, "CSV exported to Downloads directory");
                        Toast.makeText(this, "CSV exported to Downloads directory", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.e(TAG, "Error exporting CSV: File URI is null");
                    Toast.makeText(this, "Error exporting CSV", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "Error exporting CSV: ", e);
                Toast.makeText(this, "Error exporting CSV: " + e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception during close: ", e);
                }
            }
        }
    }
}
