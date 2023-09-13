package com.example.splurgesavvy.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splurgesavvy.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText passwordEditText;
    private EditText passwordConfirmEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_screen2);

        // Find views by their IDs
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordConfirmEditText = findViewById(R.id.passwordConfirmEditText);

        // Handle click on the back button icon
        findViewById(R.id.backButtonIcon).setOnClickListener(v -> onBackPressed());

        // Handle click on the "Continue" button
        findViewById(R.id.button_continue).setOnClickListener(v -> resetPassword());
    }

    private void resetPassword() {
        String newPassword = passwordEditText.getText().toString();
        String newPasswordConfirm = passwordConfirmEditText.getText().toString();

        // Check if the passwords match
        if (newPassword.equals(newPasswordConfirm)) {
            Toast.makeText(this, "Password successfully reset!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ResetPasswordActivity.this, LoginPasswordActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Show an error message that the passwords do not match
            Toast.makeText(this, "Passwords do not match. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}

