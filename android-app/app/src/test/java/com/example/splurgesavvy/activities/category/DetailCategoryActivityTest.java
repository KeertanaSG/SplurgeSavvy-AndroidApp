package com.example.splurgesavvy.activities.category;

import android.content.Intent;
import android.widget.ImageView;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.parcelable.ParcelableCategory;
import com.example.splurgesavvy.activities.profile.Profile_Account_DataTest;
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
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class DetailCategoryActivityTest {

    private DetailCategoryActivity activity;

    @Mock
    private CategoryViewModel categoryViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ParcelableCategory parcelableCategory = new ParcelableCategory(1L, "Groceries", "Expense for food");

        Intent intent = new Intent(RuntimeEnvironment.application, DetailCategoryActivity.class);
        intent.putExtra("parcelableCategory", parcelableCategory);

        ActivityController<DetailCategoryActivity> controller = Robolectric.buildActivity(DetailCategoryActivity.class, intent);
        activity = controller.get();

        activity.categoryViewModel = categoryViewModel;

        controller.create().start().resume();
    }

    @Test
    public void shouldRetrieveParcelableAndPopulateViews() {
        assertEquals("Groceries", activity.nameTextView.getText().toString());
        assertEquals("Expense for food", activity.descriptionTextView.getText().toString());
    }

    @Test
    public void clickingDeleteIconShouldShowDialog() {
        ImageView deleteIcon = activity.findViewById(R.id.trash);
        deleteIcon.performClick();
    }

    @Test
    public void clickingEditIconShouldNavigateToEditCategoryActivity() {
        activity.findViewById(R.id.button_continue).performClick();

        Intent actualIntent = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(EditCategoryActivity.class.getName(), actualIntent.getComponent().getClassName());
    }

    @Test
    public void clickingBackIconShouldNavigateBackToProfile_Account_Data() {
        activity.findViewById(R.id.backButtonIcon).performClick();

        Intent actualIntent = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(Profile_Account_DataTest.class.getName(), actualIntent.getComponent().getClassName());
    }
}

