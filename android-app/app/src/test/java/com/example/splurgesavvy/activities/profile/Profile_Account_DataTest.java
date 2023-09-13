package com.example.splurgesavvy.activities.profile;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.splurgesavvy.R;

@RunWith(RobolectricTestRunner.class)
public class Profile_Account_DataTest {

    private Profile_Account_Data activity;

    @Before
    public void setUp() {
        long userId = 1L; // Replace with an actual user ID if needed
        Intent intent = new Intent();
        intent.putExtra("userId", userId);
        ActivityController<Profile_Account_Data> activityController = Robolectric.buildActivity(Profile_Account_Data.class, intent);
        activity = activityController.create().visible().get();
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveCorrectTitle() {
        TextView title = activity.findViewById(R.id.title);
        assertNotNull(title);
    }

    @Test
    public void shouldHaveCorrectBudget() {
        TextView budget = activity.findViewById(R.id.budget);
        assertNotNull(budget);
    }

    @Test
    public void shouldHaveCorrectBudgetAmount() {
        TextView budgetAmount = activity.findViewById(R.id.budget_amount);
        assertNotNull(budgetAmount);
    }

    @Test
    public void shouldHaveCorrectCategoriesList() {
        TextView categoriesList = activity.findViewById(R.id.categories_list);
        assertNotNull(categoriesList);
    }

    @Test
    public void shouldHaveCorrectButtonCategory() {
        RelativeLayout buttonCategory = activity.findViewById(R.id.button_category);
        assertNotNull(buttonCategory);
    }

    @Test
    public void shouldHaveBackButtonIcon() {
        ImageView backButtonIcon = activity.findViewById(R.id.backButtonIcon);
        assertNotNull(backButtonIcon);
    }

    @Test
    public void shouldHaveRecyclerView() {
        RecyclerView recyclerView = activity.findViewById(R.id.recyclerView);
        assertNotNull(recyclerView);
    }

    @Test
    public void shouldHaveCorrectInitialText() {
        TextView title = activity.findViewById(R.id.title);
        assertEquals("", title.getText().toString());

        TextView budget = activity.findViewById(R.id.budget);
        assertEquals("", budget.getText().toString());

        TextView budgetAmount = activity.findViewById(R.id.budget_amount);
        assertEquals("", budgetAmount.getText().toString());

        TextView categoriesList = activity.findViewById(R.id.categories_list);
        assertEquals("", categoriesList.getText().toString());
    }
}

