package com.example.splurgesavvy.activities.category;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.home.HomeTransactionActivity;
import com.example.splurgesavvy.activities.parcelable.ParcelableCategory;
import com.example.splurgesavvy.activities.parcelable.ParcelableExpense;
import com.example.splurgesavvy.activities.profile.Profile_Account_Data;
import com.example.splurgesavvy.entities.Category;
import com.example.splurgesavvy.entities.Expense;
import com.example.splurgesavvy.viewModel.CategoryViewModel;
import com.example.splurgesavvy.viewModel.ExpenseViewModel;
import com.example.splurgesavvy.viewModel.UserViewModel;
import com.example.splurgesavvy.activities.category.EditCategoryActivity;

public class DetailCategoryActivity extends AppCompatActivity {

    //Initialization
    TextView nameTextView;
    TextView descriptionTextView;

    private ParcelableCategory parcelableCategory;

    private UserViewModel userViewModel;
    CategoryViewModel categoryViewModel;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Created variables for the views
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_detail_category);

        nameTextView = findViewById(R.id.categoryTitle);
        descriptionTextView = findViewById(R.id.amet_minim_);

        // Retrieve the ParcelableExpense from the Intent
        parcelableCategory = getIntent().getParcelableExtra("parcelableCategory");
        userId = parcelableCategory.getUserId();


        Toast.makeText(this, "Retrieved User ID: " + userId, Toast.LENGTH_SHORT).show();

        if (parcelableCategory != null) {
            populateViews(parcelableCategory);
        }

        ImageView deleteIcon = findViewById(R.id.trash);
        deleteIcon.setOnClickListener(view -> showDeleteConfirmationDialog());

        RelativeLayout editIcon = findViewById(R.id.button_continue);
        editIcon.setOnClickListener(v -> showEditCategoryLayout());

        ImageView backButtonIcon = findViewById(R.id.backButtonIcon);
        backButtonIcon.setOnClickListener(v -> goBack());
    }

    //Populate the views with category parceable
    private void populateViews(ParcelableCategory parcelableCategory) {
        nameTextView.setText(parcelableCategory.getCategoryName());
        descriptionTextView.setText(parcelableCategory.getCategoryDescription());
    }

    //Give a popup to get delete confirmation
    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Category")
                .setMessage("Are you sure you want to delete this category?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCategory();
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    // method to delete a category
    private void deleteCategory() {
        if (parcelableCategory != null) {
            CategoryViewModel categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

            Long categoryId = categoryViewModel.getCategoryIdByAttributes(
                    parcelableCategory.getUserId(),
                    parcelableCategory.getCategoryName(),
                    parcelableCategory.getCategoryDescription()
            );

            if(categoryId != null) {
                Category categoryToDelete = new Category();
                categoryToDelete.setCategoryId(categoryId);
                categoryViewModel.delete(categoryToDelete);
                Toast.makeText(this, "Category deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Category not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Go to next layout and pass intent as category
    private void showEditCategoryLayout() {
        Intent editIntent = new Intent(DetailCategoryActivity.this, EditCategoryActivity.class);
        editIntent.putExtra("parcelableCategory", parcelableCategory);
        startActivity(editIntent);
        finish();
    }


    @Override
    public void onBackPressed() {
        goBack();
    }

    //pass intent fo user_id using this method
    private void goBack() {
        Intent backIntent = new Intent(DetailCategoryActivity.this, Profile_Account_Data.class);
        backIntent.putExtra("user_id", userId);
        startActivity(backIntent);
        finish();
    }
}

