package com.example.splurgesavvy.activities.settings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.viewModel.UserViewModel;

public class SettingsProfile extends AppCompatActivity {

    private UserViewModel userViewModel;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_profile);

        // Initialize the ViewModels
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Get userId from intent
        userId = getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Setting up back button functionality
        ImageView backButtonIcon = findViewById(R.id.backButtonIcon);
        backButtonIcon.setOnClickListener(v -> onBackPressed());

        RelativeLayout buttonConfirm = findViewById(R.id.button_confirm);
        buttonConfirm.setOnClickListener(v -> changeUsername());

    }

    private void changeUsername() {
        EditText oldUsernameEditText = findViewById(R.id.oldUsernameEditText);
        EditText newUsernameEditText = findViewById(R.id.newUsernameEditText);
        EditText confirmUsernameEditText = findViewById(R.id.confirmUsernameEditText);

        String oldUsername = oldUsernameEditText.getText().toString().trim();
        String newUsername = newUsernameEditText.getText().toString().trim();
        String confirmUsername = confirmUsernameEditText.getText().toString().trim();

        // Validate fields
        if (oldUsername.isEmpty() || newUsername.isEmpty() || confirmUsername.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newUsername.equals(confirmUsername)) {
            Toast.makeText(this, "New Username and Confirm Username must match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if old username exists in the database and belongs to the current user
        if (userViewModel.getUserByUsername(oldUsername) == null || userViewModel.getUserByUsername(oldUsername).getUserId() != userId) {
            Toast.makeText(this, "Old username does not match records", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if new username already exists in the database
        if (userViewModel.getUserByUsername(newUsername) != null) {
            Toast.makeText(this, "New username already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update username
        int rowsAffected = userViewModel.updateUsernameForUserId(newUsername, userId);
        if (rowsAffected > 0) {
            Toast.makeText(this, "Username updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update username", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        Intent backIntent = new Intent(SettingsProfile.this, Settings_Activity.class);
        backIntent.putExtra("userId", userId);
        startActivity(backIntent);
        finish();
    }
}
