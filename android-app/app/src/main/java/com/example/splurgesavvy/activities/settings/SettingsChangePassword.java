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

public class SettingsChangePassword extends AppCompatActivity {

    private UserViewModel userViewModel;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_change_password);

        // Initialize the ViewModels
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Get userId from intent
        userId = getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        ImageView backButtonIcon = findViewById(R.id.backButtonIcon);
        backButtonIcon.setOnClickListener(v -> onBackPressed());

        RelativeLayout buttonConfirm = findViewById(R.id.button_confirm);
        buttonConfirm.setOnClickListener(v -> changePassword());
    }

    private void changePassword() {
        EditText oldPasswordEditText = findViewById(R.id.oldPasswordEditText);
        EditText newPasswordEditText = findViewById(R.id.newPasswordEditText);
        EditText confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);

        String oldPassword = oldPasswordEditText.getText().toString().trim();
        String newPassword = newPasswordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Validate fields
        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "New Password and Confirm Password must match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPassword.equals(oldPassword)) {
            Toast.makeText(this, "New password cannot be the same as the old password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPassword.length() < 6) {
            Toast.makeText(this, "Password should be at least 6 digits", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if old password is correct for the current user
        if (!userViewModel.isPasswordCorrectForUserId(oldPassword, userId)) {
            Toast.makeText(this, "Old password does not match records", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update password
        int rowsAffected = userViewModel.updatePasswordForUserId(newPassword, userId);
        if (rowsAffected > 0) {
            Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        Intent backIntent = new Intent(SettingsChangePassword.this, Settings_Activity.class);
        backIntent.putExtra("userId", userId);
        startActivity(backIntent);
        finish();
    }
}

