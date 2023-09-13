package com.example.splurgesavvy.activities.settings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.home.HomeProfileActivity;
import com.example.splurgesavvy.viewModel.UserViewModel;

public class Settings_Activity extends AppCompatActivity {

    UserViewModel userViewModel;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_settings);

        // Initialize the ViewModels
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Get userId from intent
        userId = getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        RelativeLayout backButton = findViewById(R.id.magicons_gl);
        RelativeLayout profileFrame = findViewById(R.id.frame_1);
        RelativeLayout changeEmailFrame = findViewById(R.id.frame_2);
        RelativeLayout changePasswordFrame = findViewById(R.id.frame_3);
        RelativeLayout aboutFrame = findViewById(R.id.frame_4);

        backButton.setOnClickListener(v -> onBackPressed());

        profileFrame.setOnClickListener(v -> openProfileSettings());
        changeEmailFrame.setOnClickListener(v -> openChangeEmailSettings());
        changePasswordFrame.setOnClickListener(v -> openChangePasswordSettings());
        aboutFrame.setOnClickListener(v -> openAboutPage());

    }

    private void openProfileSettings() {
        Intent intent = new Intent(this, SettingsProfile.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    private void openChangeEmailSettings() {
        Intent intent = new Intent(this, SettingsChangeEmail.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    private void openChangePasswordSettings() {
        Intent intent = new Intent(this, SettingsChangePassword.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    private void openAboutPage() {
        Intent intent = new Intent(this, SettingsAbout.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        Intent backIntent = new Intent(Settings_Activity.this, HomeProfileActivity.class);
        backIntent.putExtra("userId", userId);
        startActivity(backIntent);
        finish();
    }
}
