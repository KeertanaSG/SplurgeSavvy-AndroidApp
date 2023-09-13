package com.example.splurgesavvy.activities.budget;

import android.content.Intent;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.home.HomeTransactionActivity;
import com.example.splurgesavvy.entities.Category;
import com.example.splurgesavvy.viewModel.BudgetViewModel;
import com.example.splurgesavvy.viewModel.CategoryViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class CreateBudgetActivityTest {

    private CreateBudgetActivity activity;

    @Mock
    private BudgetViewModel budgetViewModel;

    @Mock
    private CategoryViewModel categoryViewModel;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(CreateBudgetActivity.class).create().resume().get();
        activity.budgetViewModel = budgetViewModel;
        activity.categoryViewModel = categoryViewModel;
    }


    @Test
    public void isValidBudgetInput_validInput_returnsTrue() {
        Spinner categorySpinner = activity.findViewById(R.id.dropdownButton_spinner);
        categorySpinner.setSelection(1); // Select a category

        EditText budgetEditText = activity.findViewById(R.id.valueEditText_create_budget);
        budgetEditText.setText("100"); // Set a valid budget amount

        assertTrue(activity.isValidBudgetInput());
    }


    @Test
    public void isValidBudgetInput_emptyBudget_returnsFalse() {
        Spinner categorySpinner = activity.findViewById(R.id.dropdownButton_spinner);
        categorySpinner.setSelection(1); // Select a category

        EditText budgetEditText = activity.findViewById(R.id.valueEditText_create_budget);
        budgetEditText.setText(""); // Set an empty budget amount

        assertFalse(activity.isValidBudgetInput());
    }


}
