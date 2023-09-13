package com.example.splurgesavvy.activities.category;

import android.content.Intent;

import androidx.lifecycle.ViewModelProvider;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.category.AddCategoryActivity;
import com.example.splurgesavvy.activities.home.HomeTransactionActivity;
import com.example.splurgesavvy.entities.Category;
import com.example.splurgesavvy.viewModel.CategoryViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AddCategoryActivityTest {

    private AddCategoryActivity activity;

    @Mock
    private CategoryViewModel categoryViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        Intent intent = new Intent(RuntimeEnvironment.application, AddCategoryActivity.class);
        intent.putExtra("userId", 1L);

        ActivityController<AddCategoryActivity> controller = Robolectric.buildActivity(AddCategoryActivity.class, intent);
        activity = controller.get();

        activity.categoryViewModel = categoryViewModel;

        controller.create().start().resume();
    }


    @Test
    public void clickingContinueWithValidInputShouldNavigateBackToHomeTransactionActivity() {
        activity.categoryNameEditText.setText("Groceries");
        activity.categoryDescriptionEditText.setText("Expenses on groceries");

        activity.findViewById(R.id.button_continue).performClick();

        Intent actualIntent = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(HomeTransactionActivity.class.getName(), actualIntent.getComponent().getClassName());
    }

    @Test
    public void clickingBackButtonShouldNavigateBackToHomeTransactionActivity() {
        activity.findViewById(R.id.backButtonIcon).performClick();

        Intent actualIntent = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(HomeTransactionActivity.class.getName(), actualIntent.getComponent().getClassName());
    }
}

