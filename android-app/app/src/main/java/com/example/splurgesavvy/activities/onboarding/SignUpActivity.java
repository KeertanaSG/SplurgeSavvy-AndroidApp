package com.example.splurgesavvy.activities.onboarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.login.LoginPasswordActivity;
import com.example.splurgesavvy.database.AppDatabase;
import com.example.splurgesavvy.entities.User;
import com.example.splurgesavvy.viewModel.UserViewModel;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Find views by their IDs
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        RelativeLayout signUpButton = findViewById(R.id.button_signup);

        findViewById(R.id.backButtonIcon).setOnClickListener(v -> onBackPressed());

        findViewById(R.id.login_text).setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginPasswordActivity.class);
            startActivity(intent);
        });

        signUpButton.setOnClickListener(v -> handleSignUp());
    }

    private void handleSignUp() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (!validateInputs(username, email, password)) {
            return;
        }

        AppDatabase.databaseWriteExecutor.execute(() -> {
            // Check if username or email already exists
            if (userViewModel.getUserByUsername(username) != null) {
                displayToast("Username already taken.");
                return;
            }
            if (userViewModel.getUserByEmail(email) != null) {
                displayToast("Email already registered.");
                return;
            }

            User user = new User(username, email, password);
            userViewModel.insert(user);



            runOnUiThread(() -> {
                Intent intent = new Intent(SignUpActivity.this, SetUpBudgetActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            });
        });
    }

    protected boolean validateInputs(String username, String email, String password) {
        if (username.isEmpty()) {
            displayToast("Username is required.");
            return false;
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            displayToast("Enter a valid email address.");
            return false;
        }

        if (password.isEmpty() || password.length() < 6) {
            displayToast("Password should be at least 6 characters long.");
            return false;
        }

        return true;
    }

    private void displayToast(String message) {
        runOnUiThread(() -> Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show());
    }

}