package com.example.splurgesavvy.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.login.LoginPasswordActivity;
import com.example.splurgesavvy.activities.manual.ManualExpenseActivity;
import com.example.splurgesavvy.activities.profile.Profile_Account_Data;
import com.example.splurgesavvy.activities.profile.Profile_Export_Data;
import com.example.splurgesavvy.activities.settings.Settings_Activity;
import com.example.splurgesavvy.viewModel.UserViewModel;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class HomeProfileActivity extends AppCompatActivity
{

    private UserViewModel userViewModel;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        // Initialize the ViewModels
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Account Options
        RelativeLayout rectangle1 = findViewById(R.id.rectangle_1);
        RelativeLayout rectangle2 = findViewById(R.id.rectangle_2);
        RelativeLayout rectangle3 = findViewById(R.id.rectangle_3);
        RelativeLayout rectangle4 = findViewById(R.id.rectangle_4);

        rectangle1.setOnClickListener(view -> {
            navigateTo(Profile_Account_Data.class);
        });

        rectangle2.setOnClickListener(view -> {
            navigateTo(Settings_Activity.class);
        });

        rectangle3.setOnClickListener(view -> {
            navigateTo(Profile_Export_Data.class);
        });

        rectangle4.setOnClickListener(view -> {
            // Creating an AlertDialog to confirm logout
            new AlertDialog.Builder(HomeProfileActivity.this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Navigate back to login activity
                            Intent intent = new Intent(HomeProfileActivity.this, LoginPasswordActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });


        // Get userId from intent
        userId = getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Observe username changes
        userViewModel.getUsername(userId).observe(this, username -> {
            if (username != null) {
                TextView usernameTextView = findViewById(R.id.anonymous);
                usernameTextView.setText(username);
            } else {
                Toast.makeText(this, "User not found", Toast.LENGTH_LONG).show();
            }
        });

        initializeBottomNavigation();
    }


    private void initializeBottomNavigation() {
        ImageView homeIcon = findViewById(R.id.home_icon);
        homeIcon.setOnClickListener(view -> navigateTo(HomePageActivity.class));

        ImageView transactionIcon = findViewById(R.id.transaction_icon);
        transactionIcon.setOnClickListener(view -> navigateTo(HomeTransactionActivity.class));

        ImageView createExpenseIcon = findViewById(R.id.create_expense_icon);
        createExpenseIcon.setOnClickListener(view -> navigateTo(ManualExpenseActivity.class));

        ImageView profileIcon = findViewById(R.id.profile_icon);
        profileIcon.setOnClickListener(view -> Toast.makeText(this, "Profile refreshed!", Toast.LENGTH_SHORT).show());

        ImageView budgetIcon = findViewById(R.id.budget_icon);
        budgetIcon.setOnClickListener(view -> navigateTo(HomeBudgetActivity.class));
    }


    private void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
}
