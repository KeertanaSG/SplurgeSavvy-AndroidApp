package com.example.splurgesavvy.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.onboarding.SignUpActivity;

public class LoginPincodeActivity extends AppCompatActivity {

    private EditText pinEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pin);

        // Find views by their IDs
        pinEditText = findViewById(R.id.pinEditText);
        RelativeLayout loginButton = findViewById(R.id.button_signup);

        // Handle click on the back button icon
        findViewById(R.id.backButtonIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Handle click on the "Forgot Password" text to navigate to the forgot password page
        findViewById(R.id.forgot_pass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPincodeActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        // Handle click on the "Use Password" text to navigate to the login password page
        findViewById(R.id.use_pin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Go back to the previous activity (LoginPasswordActivity in this case).
            }
        });

        // Handle click on the "Sign Up" text to navigate to the sign-up page
        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPincodeActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // Handle click on the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the PIN entered by the user
                String pin = pinEditText.getText().toString().trim();

                if (isValidPin(pin)) {
                    Toast.makeText(LoginPincodeActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(LoginPincodeActivity.this, "Invalid PIN. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Logic for isValidPin - WIP
    private boolean isValidPin(String pin) {
        return pin.equals("1234");
    }
}

