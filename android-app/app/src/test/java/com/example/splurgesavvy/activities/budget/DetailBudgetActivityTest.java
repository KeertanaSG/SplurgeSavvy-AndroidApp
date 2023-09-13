package com.example.splurgesavvy.activities.budget;

import android.content.Intent;
import android.widget.TextView;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.parcelable.ParcelableBudget;
import com.example.splurgesavvy.viewModel.BudgetViewModel;
import com.example.splurgesavvy.viewModel.ExpenseViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowToast;

import androidx.lifecycle.MutableLiveData;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28) // Set your desired API level
public class DetailBudgetActivityTest {

    private DetailBudgetActivity activity;

    @Mock
    private BudgetViewModel budgetViewModel;

    @Mock
    private ExpenseViewModel expenseViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ParcelableBudget parcelableBudget = new ParcelableBudget();
        parcelableBudget.setUserId(1L);
        parcelableBudget.setName("Test");
        parcelableBudget.setValue(1000.0);

        Intent intent = new Intent();
        intent.putExtra("parcelableBudget", parcelableBudget);

        activity = Robolectric.buildActivity(DetailBudgetActivity.class, intent)
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldNotNull() {
        assertNotNull(activity);
    }

    @Test
    public void shouldInitializeTextViews() {
        TextView categoryTextView = activity.findViewById(R.id.shopping);
        assertNotNull(categoryTextView);
        assertEquals("Test", categoryTextView.getText().toString());
    }

    @Test
    public void shouldShowDeleteConfirmationDialog() {
        activity.showDeleteConfirmationDialog();
        assertNotNull(ShadowAlertDialog.getLatestDialog());
    }


    @Test
    public void shouldToastBudgetNotFound() {
        when(budgetViewModel.getBudgetIdByAttributes(1L, "Test", 1000.0)).thenReturn(null);

        activity.deleteBudget();

        assertEquals("Budget not found", ShadowToast.getTextOfLatestToast());
    }

}
