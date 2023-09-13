package com.example.splurgesavvy.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.budget.CreateBudgetActivity;
import com.example.splurgesavvy.activities.budget.DetailBudgetActivity;
import com.example.splurgesavvy.activities.category.DetailCategoryActivity;
import com.example.splurgesavvy.activities.manual.ManualExpenseActivity;
import com.example.splurgesavvy.activities.parcelable.ParcelableBudget;
import com.example.splurgesavvy.activities.parcelable.ParcelableCategory;
import com.example.splurgesavvy.activities.profile.Profile_Account_Data;
import com.example.splurgesavvy.adapters.BudgetAdapter;
import com.example.splurgesavvy.viewModel.BudgetViewModel;
import com.example.splurgesavvy.viewModel.UserViewModel;

public class HomeBudgetActivity extends AppCompatActivity
{
    //Initialization
    UserViewModel userViewModel;
    BudgetViewModel budgetViewModel;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Declare variables
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_budget);

        //get user_id intent
        userId = getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        //Recycler view to show the differnt expenses
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BudgetAdapter adapter = new BudgetAdapter();
        recyclerView.setAdapter(adapter);

        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        budgetViewModel.getAllBudgetsByUserId(userId).observe(this, budgets -> {
            adapter.setBudgets(budgets);
        });


        adapter.setOnItemClickListener(budget -> {
            Intent intent = new Intent(HomeBudgetActivity.this, DetailBudgetActivity.class);
            Toast.makeText(this, "User ID: " + userId, Toast.LENGTH_SHORT).show();

            // Convert Category to ParcelableCategory and pass it
            ParcelableBudget parcelableBudget = new ParcelableBudget(
                    budget.getUserId(),
                    budget.getName(),
                    budget.getValue()
            );
            intent.putExtra("parcelableBudget", parcelableBudget);
            startActivity(intent);
        });

        initializeBottomNavigation();

        RelativeLayout createBudgetButton = findViewById(R.id.button_create_budget);
        createBudgetButton.setOnClickListener(view -> navigateTo(CreateBudgetActivity.class));
    }

    //Method for Initializing the navigation bar
    private void initializeBottomNavigation() {
        ImageView homeIcon = findViewById(R.id.home_icon);
        homeIcon.setOnClickListener(view -> navigateTo(HomePageActivity.class));

        ImageView transactionIcon = findViewById(R.id.transaction_icon);
        transactionIcon.setOnClickListener(view -> navigateTo(HomeTransactionActivity.class));

        ImageView createExpenseIcon = findViewById(R.id.create_expense_icon);
        createExpenseIcon.setOnClickListener(view -> navigateTo(ManualExpenseActivity.class));

        ImageView profileIcon = findViewById(R.id.profile_icon);
        profileIcon.setOnClickListener(view -> navigateTo(HomeProfileActivity.class));

        ImageView budgetIcon = findViewById(R.id.budget_icon);
        budgetIcon.setOnClickListener(view -> Toast.makeText(this, "Budgets refreshed!", Toast.LENGTH_SHORT).show());
    }


    //passing intent
    private void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
}
