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
import com.example.splurgesavvy.activities.home.HomeProfileActivity;
import com.example.splurgesavvy.viewModel.UserViewModel;

public class SettingsChangeEmail extends AppCompatActivity {

    UserViewModel userViewModel;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_change_email);

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

        // Setting up change email functionality
        RelativeLayout buttonConfirm = findViewById(R.id.button_confirm);
        buttonConfirm.setOnClickListener(v -> changeEmail());

    }

    private void changeEmail() {
        EditText oldEmailEditText = findViewById(R.id.oldEmailEditText);
        EditText newEmailEditText = findViewById(R.id.newEmailEditText);
        EditText confirmEmailEditText = findViewById(R.id.confirmEmailEditText);

        String oldEmail = oldEmailEditText.getText().toString().trim();
        String newEmail = newEmailEditText.getText().toString().trim();
        String confirmEmail = confirmEmailEditText.getText().toString().trim();

        // Validate fields
        if (oldEmail.isEmpty() || newEmail.isEmpty() || confirmEmail.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newEmail.equals(confirmEmail)) {
            Toast.makeText(this, "New Email and Confirm Email must match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if old email exists in the database and belongs to the current user
        if (userViewModel.getUserByEmail(oldEmail) == null || userViewModel.getUserByEmail(oldEmail).getUserId() != userId) {
            Toast.makeText(this, "Old email does not match records", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if new email already exists in the database
        if (userViewModel.getUserByEmail(newEmail) != null) {
            Toast.makeText(this, "New email already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update email
        int rowsAffected = userViewModel.updateEmailForUserId(newEmail, userId);
        if (rowsAffected > 0) {
            Toast.makeText(this, "Email updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update email", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        Intent backIntent = new Intent(SettingsChangeEmail.this, Settings_Activity.class);
        backIntent.putExtra("userId", userId);
        startActivity(backIntent);
        finish();
    }
}
