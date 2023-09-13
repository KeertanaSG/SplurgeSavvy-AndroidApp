package com.example.splurgesavvy.activities.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splurgesavvy.R;

public class VerificationCodeActivity extends AppCompatActivity {

    private EditText[] codeEditTexts = new EditText[4];
    private int currentCodeIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_screen);

        // Initialize the code EditTexts
        codeEditTexts[0] = findViewById(R.id.editText1);
        codeEditTexts[1] = findViewById(R.id.editText2);
        codeEditTexts[2] = findViewById(R.id.editText3);
        codeEditTexts[3] = findViewById(R.id.editText4);

        // Set click listeners for the verify button
        RelativeLayout btnVerify = findViewById(R.id.button_verify);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode();
            }
        });
    }

    private void verifyCode() {
        String enteredCode = getEnteredCode();
        String expectedCode = "1234"; // Replace this with the actual expected verification code

        if (enteredCode.equals(expectedCode)) {
            // Code verification success
            Toast.makeText(this, "Verification successful!", Toast.LENGTH_SHORT).show();
           // Intent intent = new Intent(VerificationCodeActivity.this, SetUpPinActivity.class);
            //startActivity(intent);
            finish();
        } else {
            // Code verification failed, show an error message or handle incorrect code input
            Toast.makeText(this, "Invalid verification code. Please try again.", Toast.LENGTH_SHORT).show();
            clearCodeInput();
        }
    }

    private String getEnteredCode() {
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            codeBuilder.append(codeEditTexts[i].getText().toString());
        }
        return codeBuilder.toString();
    }

    private void clearCodeInput() {
        currentCodeIndex = 0;
        for (EditText codeEditText : codeEditTexts) {
            codeEditText.setText("");
        }
    }
}


