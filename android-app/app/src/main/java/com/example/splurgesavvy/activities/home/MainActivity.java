package com.example.splurgesavvy.activities.home;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.splurgesavvy.database.AppDatabase;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.onboarding.OnboardingActivity;

public class MainActivity extends AppCompatActivity {

    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database
        appDatabase = AppDatabase.getDatabase(this);

        // Start the OnboardingActivity after setContentView()
        startOnboardingActivity(this);
    }

    private void startOnboardingActivity(Context context) {
        Intent intent = new Intent(context, OnboardingActivity.class);
        startActivity(intent);
        finish();
    }
}
