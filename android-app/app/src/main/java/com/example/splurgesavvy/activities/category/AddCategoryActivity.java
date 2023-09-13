package com.example.splurgesavvy.activities.category;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.home.HomeTransactionActivity;
import com.example.splurgesavvy.entities.Category;
import com.example.splurgesavvy.viewModel.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddCategoryActivity extends AppCompatActivity {

    //Initialization
    EditText categoryNameEditText;
    EditText categoryDescriptionEditText;
    CategoryViewModel categoryViewModel;
    private long userId;
    private List<String> categoryNamesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Setting variables for each component
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_add_category);

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        categoryNameEditText = findViewById(R.id.nameEditTextName);
        categoryDescriptionEditText = findViewById(R.id.nameEditTextdes);

        // Get userId from intent
        userId = getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        //check if valid category input
        findViewById(R.id.button_continue).setOnClickListener(v -> {
            if (isValidCategoryInput()) {
                String categoryName = categoryNameEditText.getText().toString().trim();
                String categoryDescription = categoryDescriptionEditText.getText().toString().trim();

                Category category = new Category(userId, categoryName, categoryDescription);
                categoryViewModel.insert(category);

                Toast.makeText(AddCategoryActivity.this, "Category added successfully.", Toast.LENGTH_SHORT).show();
                goBack();
            } else {
                Toast.makeText(AddCategoryActivity.this, "Please provide valid category details.", Toast.LENGTH_SHORT).show();
            }
        });

        // Set a click listener to the back button
        findViewById(R.id.backButtonIcon).setOnClickListener(v -> goBack());
    }

    private boolean isValidCategoryInput() {
        return !categoryNameEditText.getText().toString().trim().isEmpty() &&
                !categoryDescriptionEditText.getText().toString().trim().isEmpty();
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    //Pass user_id to next screen
    private void goBack() {
        Intent backIntent = new Intent(AddCategoryActivity.this, HomeTransactionActivity.class);
        backIntent.putExtra("userId", userId);
        startActivity(backIntent);
        finish();
    }
}
