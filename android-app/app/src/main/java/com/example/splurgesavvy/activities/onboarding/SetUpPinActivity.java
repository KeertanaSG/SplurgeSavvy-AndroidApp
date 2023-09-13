package com.example.splurgesavvy.activities.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.entities.User;
import com.example.splurgesavvy.repository.UserRepository;

public class SetUpPinActivity extends AppCompatActivity {

    private EditText[] pinEditTexts = new EditText[4];
    private int currentPinIndex = 0;

    private UserRepository userRepository;

    private String enteredPin = "";
    private User user; // To hold the user data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_pin);

        // Initialize the PIN EditTexts
        pinEditTexts[0] = findViewById(R.id.editText1);
        pinEditTexts[1] = findViewById(R.id.editText2);
        pinEditTexts[2] = findViewById(R.id.editText3);
        pinEditTexts[3] = findViewById(R.id.editText4);

        // Get the userId from the intent
        long userId = getIntent().getLongExtra("userId", -1);

        // Retrieve the user data from the database using the userId
        userRepository = new UserRepository(this);

        // Set click listeners for the numeric pad buttons
        setNumericPadClickListener();

        // Set click listener for the backspace button
        ImageButton btnBackspace = findViewById(R.id.btn_backspace);
        btnBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPinIndex > 0) {
                    currentPinIndex--;
                    pinEditTexts[currentPinIndex].setText("");
                }
            }
        });
    }

    private void setNumericPadClickListener() {
        int[] buttonIds = {
                R.id.btn_1, R.id.btn_2, R.id.btn_3,
                R.id.btn_4, R.id.btn_5, R.id.btn_6,
                R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_0
        };

        for (int buttonId : buttonIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentPinIndex < 4) {
                        String digit = button.getText().toString();
                        pinEditTexts[currentPinIndex].setText(digit);
                        enteredPin += digit;
                        currentPinIndex++;
                        if (currentPinIndex == 4) {
                            // Validate and save the PIN
                            String pin = getEnteredPin();
                            if (isValidPin(pin)) {
                                // Save the PIN and proceed to the next screen )
                                Toast.makeText(SetUpPinActivity.this, "PIN set successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SetUpPinActivity.this, SetUpBudgetActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Display an error message or handle incorrect PIN input
                                Toast.makeText(SetUpPinActivity.this, "Invalid PIN. Please try again.", Toast.LENGTH_SHORT).show();
                                clearPinInput();
                            }
                        }
                    }
                }
            });
        }
    }

    private String getEnteredPin() {
        StringBuilder pinBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            pinBuilder.append(pinEditTexts[i].getText().toString());
        }
        return pinBuilder.toString();
    }

    private boolean isValidPin(String pin) {
        return pin.length() == 4;
    }

    private void clearPinInput() {
        currentPinIndex = 0;
        enteredPin = "";
        for (EditText pinEditText : pinEditTexts) {
            pinEditText.setText("");
        }
    }

}
