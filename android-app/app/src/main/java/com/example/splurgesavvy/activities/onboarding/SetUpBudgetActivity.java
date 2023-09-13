package com.example.splurgesavvy.activities.onboarding;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.splurgesavvy.R;

public class SetUpBudgetActivity extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_budget);

        // Get the passed username from the Intent
        username = getIntent().getStringExtra("username");

        findViewById(R.id.button_continue_b).setOnClickListener(v -> onContinueButtonClick());
    }

    private void onContinueButtonClick() {
        Intent intent = new Intent(SetUpBudgetActivity.this, NewBudgetActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
}


