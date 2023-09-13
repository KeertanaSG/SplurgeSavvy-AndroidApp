package com.example.splurgesavvy.activities.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.home.HomeBudgetActivity;
import com.example.splurgesavvy.viewModel.UserViewModel;

public class SettingsAbout extends AppCompatActivity {

    private UserViewModel userViewModel;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_about);

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

        // Set a click listener to the back button
        backButtonIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        Intent backIntent = new Intent(SettingsAbout.this, Settings_Activity.class);
        backIntent.putExtra("userId", userId);
        startActivity(backIntent);
        finish();
    }
}
