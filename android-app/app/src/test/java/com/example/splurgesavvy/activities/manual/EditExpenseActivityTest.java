package com.example.splurgesavvy.activities.manual;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.lifecycle.MutableLiveData;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.home.HomeTransactionActivity;
import com.example.splurgesavvy.activities.parcelable.ParcelableExpense;
import com.example.splurgesavvy.entities.Category;
import com.example.splurgesavvy.viewModel.CategoryViewModel;
import com.example.splurgesavvy.viewModel.ExpenseViewModel;
import com.example.splurgesavvy.viewModel.UserViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class EditExpenseActivityTest {

    @Mock
    private ExpenseViewModel expenseViewModel;
    @Mock
    private UserViewModel userViewModel;
    @Mock
    private CategoryViewModel categoryViewModel;

    private ActivityController<EditExpenseActivity> activityController;
    private long userId = 1L; // Test userId

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ParcelableExpense parcelableExpense = new ParcelableExpense(userId, 101, "Clothing", "Expenses", 123, "This is an expense", new Date());

        Intent intent = new Intent().putExtra("parcelableExpense", parcelableExpense);
        activityController = Robolectric.buildActivity(EditExpenseActivity.class, intent);
        activityController.create().start().resume();
    }

    @Test
    public void testFieldsPopulatedCorrectly() {
        EditText nameEditText = activityController.get().findViewById(R.id.nameEditTextName);
        EditText descriptionEditText = activityController.get().findViewById(R.id.nameEditTextdes);
        EditText valueEditText = activityController.get().findViewById(R.id.valueEditText);

        // Assumption: the ParcelableExpense had these values
        assertEquals("Expenses", nameEditText.getText().toString());
        assertEquals("This is an expense", descriptionEditText.getText().toString());
        assertEquals("123", valueEditText.getText().toString());
    }


    @Test
    public void testCategorySpinner() {
        Spinner categorySpinner = activityController.get().findViewById(R.id.dropdownButton_spinner);

        List<Category> categories = Arrays.asList(
                new Category(userId, "Clothes","This is for clothes"),
                new Category(userId, "Non-Utilities", "This is for non-utilities")
        );

        MutableLiveData<List<Category>> liveData = new MutableLiveData<>();
        liveData.setValue(categories);
        when(categoryViewModel.getAllCategoriesByUserId(userId)).thenReturn(liveData);

        // Now assuming the Spinner will be updated, you can check its state
        assertEquals("Groceries", categorySpinner.getSelectedItem().toString());
    }

    @Test
    public void testSaveButtonDisabledForInvalidInput() {
        EditText nameEditText = activityController.get().findViewById(R.id.nameEditTextName);
        Button saveButton = activityController.get().findViewById(R.id.button_save_changes);

        nameEditText.setText(""); // Invalid input
        Assert.assertFalse(saveButton.isEnabled());
    }


}

