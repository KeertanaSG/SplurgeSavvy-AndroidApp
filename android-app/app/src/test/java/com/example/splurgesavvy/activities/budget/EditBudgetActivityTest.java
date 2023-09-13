package com.example.splurgesavvy.activities.budget;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.home.HomeTransactionActivity;
import com.example.splurgesavvy.activities.parcelable.ParcelableBudget;
import com.example.splurgesavvy.entities.Category;
import com.example.splurgesavvy.viewModel.BudgetViewModel;
import com.example.splurgesavvy.viewModel.CategoryViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class EditBudgetActivityTest {

    private EditBudgetActivity activity;

    private HomeTransactionActivity homeTransactionActivity;

    @Mock
    private BudgetViewModel budgetViewModel;

    @Mock
    private CategoryViewModel categoryViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ParcelableBudget parcelableBudget = new ParcelableBudget();
        parcelableBudget.setUserId(1L);
        parcelableBudget.setName("Groceries");
        parcelableBudget.setValue(500.0);

        Intent intent = new Intent();
        intent.putExtra("parcelableBudget", parcelableBudget);

        activity = Robolectric.buildActivity(EditBudgetActivity.class, intent)
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldNotNull() {
        assertNotNull(activity);
    }

    @Test
    public void shouldInitializeEditTextWithParcelableBudgetValue() {
        EditText budgetValueEditText = activity.findViewById(R.id.valueEditText_create_budget);
        assertEquals("500.0", budgetValueEditText.getText().toString());
    }

    @Test
    public void shouldInitializeCategorySpinner() {
        Spinner categorySpinner = activity.findViewById(R.id.dropdownButton_spinner);
        assertNotNull(categorySpinner);
    }


    @Test
    public void shouldToastFailedToUpdateBudget() {
        when(budgetViewModel.getBudgetIdByAttributes(1L, "Groceries", 500.0)).thenReturn(null);

        activity.findViewById(R.id.button_continue).performClick();

        assertEquals("Failed to update budget.", ShadowToast.getTextOfLatestToast());
    }

}

