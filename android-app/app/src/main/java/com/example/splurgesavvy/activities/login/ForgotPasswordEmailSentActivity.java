package com.example.splurgesavvy.activities.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splurgesavvy.R;

public class ForgotPasswordEmailSentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_screen1);

        // Handle click on the back button icon
        findViewById(R.id.backButtonIcon).setOnClickListener(v -> onBackPressed());
    }
}
