package com.example.splurgesavvy.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.home.HomePageActivity;
//import com.example.splurgesavvy.activities.home.HomeTransactionActivity;
import com.example.splurgesavvy.activities.onboarding.SignUpActivity;
import com.example.splurgesavvy.entities.User;
import com.example.splurgesavvy.repository.UserRepository;

public class LoginPasswordActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private RelativeLayout loginButton;
    private TextView forgotPasswordTextView;
    private TextView usePinTextView;
    private TextView signUpTextView;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);

        // Initialize the UserRepository with the context
        userRepository = new UserRepository(this);

        // Find views by their IDs
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.button_signup);
        forgotPasswordTextView = findViewById(R.id.forgot_pass);
        usePinTextView = findViewById(R.id.use_pin);
        signUpTextView = findViewById(R.id.sign_up);

        // Handle click on the back button icon
        findViewById(R.id.backButtonIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Handle click on the "Sign Up" text to navigate to the sign-up page
        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPasswordActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // Login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditText fields
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Login button click listener
                loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get user input from EditText fields
                        String username = usernameEditText.getText().toString().trim();
                        String password = passwordEditText.getText().toString().trim();

                        // Observe the loginUser LiveData
                        userRepository.loginUser(username, password).observe(LoginPasswordActivity.this, new Observer<User>() {
                            @Override
                            public void onChanged(User user) {
                                if (user != null) {
                                    // Successful login, handle response (if needed)
                                    Toast.makeText(LoginPasswordActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                                    // Navigate to the home page (HomePageActivity) after successful login
                                    Intent intent = new Intent(LoginPasswordActivity.this, HomePageActivity.class);
                                    intent.putExtra("username", username);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Invalid credentials, show an error message.
                                    Toast.makeText(LoginPasswordActivity.this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });

        // Handle click on the "Forgot Password" text to navigate to the forgot password page
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPasswordActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        // Handle click on the "Use Pin" text to navigate to the login pin page
        usePinTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPasswordActivity.this, LoginPincodeActivity.class);
                startActivity(intent);
            }
        });
    }
}


