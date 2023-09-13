package com.example.splurgesavvy.activities.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.home.HomePageActivity;

public class SignupSuccessActivity extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_success);

        // Get the username from the Intent
        username = getIntent().getStringExtra("username");

        // Get the root view of the layout
        View rootView = findViewById(android.R.id.content);

        // Set click listener to navigate to HomePageActivity
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupSuccessActivity.this, HomePageActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });
    }
}


