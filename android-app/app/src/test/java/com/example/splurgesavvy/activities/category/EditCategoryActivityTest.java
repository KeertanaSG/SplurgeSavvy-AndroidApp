package com.example.splurgesavvy.activities.category;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.parcelable.ParcelableCategory;
import com.example.splurgesavvy.entities.Category;
import com.example.splurgesavvy.viewModel.CategoryViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

@RunWith(RobolectricTestRunner.class)
public class EditCategoryActivityTest {

    private EditCategoryActivity activity;
    private CategoryViewModel categoryViewModel;

    @Before
    public void setUp() {
        ParcelableCategory parcelableCategory = new ParcelableCategory(101, "Clothes","This is for clothes");
        Intent intent = new Intent();
        intent.putExtra("parcelableCategory", parcelableCategory);

        categoryViewModel = mock(CategoryViewModel.class);

        ActivityController<EditCategoryActivity> controller = Robolectric.buildActivity(EditCategoryActivity.class, intent)
                .create()
                .start()
                .resume()
                .visible();

        activity = controller.get();
        activity.categoryViewModel = categoryViewModel;
    }

    @Test
    public void shouldPopulateFields() {
        EditText editName = activity.findViewById(R.id.nameEditTextName);
        EditText editDescription = activity.findViewById(R.id.nameEditTextdes);

        assertEquals("Clothes", editName.getText().toString());
        assertEquals("This is for clothes", editDescription.getText().toString());
    }


    @Test
    public void clickingBackShouldFinishActivity() {
        ImageView backButtonIcon = activity.findViewById(R.id.backButtonIcon);
        backButtonIcon.performClick();
        assertEquals(true, activity.isFinishing());
    }
}
