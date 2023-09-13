package com.example.splurgesavvy.activities.profile;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.category.AddCategoryActivity;
import com.example.splurgesavvy.activities.category.DetailCategoryActivity;
import com.example.splurgesavvy.activities.parcelable.ParcelableCategory;
import com.example.splurgesavvy.activities.settings.SettingsChangeEmail;
import com.example.splurgesavvy.activities.settings.Settings_Activity;
import com.example.splurgesavvy.adapters.CategoryAdapter;
import com.example.splurgesavvy.viewModel.CategoryViewModel;

public class Profile_Account_Data extends AppCompatActivity {

    private TextView title, budget, budgetAmount, categoriesList;
    private ImageView backButtonIcon;
    private RelativeLayout buttonCategory;
    private RecyclerView recyclerView;

    private long userId;

    private CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_account_data);

        // Initialize UI Elements
        title = findViewById(R.id.title);
        budget = findViewById(R.id.budget);
        budgetAmount = findViewById(R.id.budget_amount);
        categoriesList = findViewById(R.id.categories_list);
        backButtonIcon = findViewById(R.id.backButtonIcon);
        buttonCategory = findViewById(R.id.button_category);
        recyclerView = findViewById(R.id.recyclerView);

        userId = getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CategoryAdapter adapter = new CategoryAdapter();
        recyclerView.setAdapter(adapter);

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategoriesByUserId(userId).observe(this, categories -> {
            adapter.setCategories(categories);
        });

        adapter.setOnItemClickListener(category -> {
            Intent intent = new Intent(Profile_Account_Data.this, DetailCategoryActivity.class);
            Toast.makeText(this, "User ID: " + userId, Toast.LENGTH_SHORT).show();

            // Convert Category to ParcelableCategory and pass it
            ParcelableCategory parcelableCategory = new ParcelableCategory(
                    category.getUserId(),
                    category.getName(),
                    category.getDescription()
            );
            intent.putExtra("parcelableCategory", parcelableCategory);
            startActivity(intent);
        });

        findViewById(R.id.button_category).setOnClickListener(v -> {
            Intent addCategoryIntent = new Intent(Profile_Account_Data.this, AddCategoryActivity.class);
            addCategoryIntent.putExtra("userId", userId);
            startActivity(addCategoryIntent);
        });

        backButtonIcon.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        Intent backIntent = new Intent(Profile_Account_Data.this, Settings_Activity.class);
        backIntent.putExtra("userId", userId);
        startActivity(backIntent);
        finish();
    }
}
