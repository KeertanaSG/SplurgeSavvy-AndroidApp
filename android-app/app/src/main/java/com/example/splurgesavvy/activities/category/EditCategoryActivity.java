package com.example.splurgesavvy.activities.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.home.HomeTransactionActivity;
import com.example.splurgesavvy.activities.parcelable.ParcelableCategory;
import com.example.splurgesavvy.activities.profile.Profile_Account_Data;
import com.example.splurgesavvy.entities.Category;
import com.example.splurgesavvy.viewModel.CategoryViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditCategoryActivity extends AppCompatActivity {
    //Initialization
    private ParcelableCategory parcelableCategory;
    private EditText editName;
    private EditText editDescription;
    private long userId;
    CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Create the variables for differnt views
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit_category);

        editName = findViewById(R.id.nameEditTextName);
        editDescription = findViewById(R.id.nameEditTextdes);

        // Get data from Intent
        parcelableCategory = getIntent().getParcelableExtra("parcelableCategory");
        userId = parcelableCategory.getUserId();

        if (parcelableCategory != null) {
            // Populate fields with existing details using parcelableCategory
            editName.setText(parcelableCategory.getCategoryName());
            editDescription.setText(parcelableCategory.getCategoryDescription());
        }

        // Initialize ViewModel
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        // Handle Save Changes Button
        RelativeLayout saveChangesButton = findViewById(R.id.button_save_changes);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateCategory();
            }
        });

        ImageView backButtonIcon = findViewById(R.id.backButtonIcon);
        backButtonIcon.setOnClickListener(v -> goBack());
    }

    //Method to update the category
    private void updateCategory() {
        if (parcelableCategory != null) {

            final String updatedName = editName.getText().toString();
            final String updatedDescription = editDescription.getText().toString();

            if (updatedName.isEmpty() || updatedDescription.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // Executor service with a fixed thread pool to execute database operations
            ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

            databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    Long categoryId = categoryViewModel.getCategoryIdByAttributes(
                            parcelableCategory.getUserId(),
                            parcelableCategory.getCategoryName(),
                            parcelableCategory.getCategoryDescription()
                    );

                    if (categoryId != null) {
                        Category updatedCategory = new Category();
                        updatedCategory.setCategoryId(categoryId);
                        updatedCategory.setUserId(parcelableCategory.getUserId());
                        updatedCategory.setName(updatedName);
                        updatedCategory.setDescription(updatedDescription);

                        // Update the Category in the database
                        categoryViewModel.update(updatedCategory);

                        runOnUiThread(() -> {

                            Toast.makeText(EditCategoryActivity.this, "Category successfully updated", Toast.LENGTH_SHORT).show();
                            goBack();

                        });
                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(EditCategoryActivity.this, "Error fetching categoryId", Toast.LENGTH_SHORT).show();
                        });
                    }
                }
            });
        } else {
            Toast.makeText(this, "No category to update", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        goBack();
    }

    //pass user_id as intent
    private void goBack() {
        Intent backIntent = new Intent(EditCategoryActivity.this, Profile_Account_Data.class);
        backIntent.putExtra("user_id", userId);
        startActivity(backIntent);
        finish();
    }

}
