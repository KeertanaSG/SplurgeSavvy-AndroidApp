package com.example.splurgesavvy.activities.home;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import androidx.lifecycle.MutableLiveData;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.dao.BudgetDao;
import com.example.splurgesavvy.entities.Budget;
import com.example.splurgesavvy.entities.Expense;
import com.example.splurgesavvy.repository.UserRepository;
import com.example.splurgesavvy.viewModel.BudgetViewModel;
import com.example.splurgesavvy.viewModel.ExpenseViewModel;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class HomePageActivityTest {

    private HomePageActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(HomePageActivity.class)
                .create()
                .resume()
                .get();
    }



    @Test
    public void testSharedPreferences() {
        // Simulate storing a UserId in SharedPreferences
        activity.storeUserIdInPreferences(1L);

        // Verify if it retrieves the same UserId
        long storedUserId = activity.getUserIdFromPreferences();
        assertEquals("UserId should be the same as stored in SharedPreferences", 1L, storedUserId);
    }

    @Test
    public void testBottomNavigation_ProfileIcon() {
        activity.findViewById(R.id.profile_icon).performClick();
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        assertTrue(startedIntent.getComponent().getClassName().contains("HomeProfileActivity"));
    }

    @Test
    public void testBottomNavigation_BudgetIcon() {
        activity.findViewById(R.id.budget_icon).performClick();
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        assertTrue(startedIntent.getComponent().getClassName().contains("HomeBudgetActivity"));
    }

    @Test
    public void testBottomNavigation_TransactionIcon() {
        activity.findViewById(R.id.transaction_icon).performClick();
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        assertTrue(startedIntent.getComponent().getClassName().contains("HomeTransactionActivity"));
    }

    @Test
    public void testBottomNavigation_CreateExpenseIcon() {
        activity.findViewById(R.id.create_expense_icon).performClick();
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        assertTrue(startedIntent.getComponent().getClassName().contains("ManualExpenseActivity"));
    }

    @Test
    public void testIntentHasUserId() {
        Intent intent = new Intent();
        intent.putExtra("userId", 1L);
        activity = Robolectric.buildActivity(HomePageActivity.class, intent)
                .create()
                .resume()
                .get();
        assertTrue(activity.getIntent().hasExtra("userId"));
    }

    @Test
    public void testDefaultText_ChartDescription() {
        TextView chartDescription = activity.findViewById(R.id.chart_description);
        assertTrue(chartDescription.getText().toString().contains("The line chart above illustrates"));
    }

    @Test
    public void testOnCreate_InitializeBottomNavigation() {
        assertNotNull(activity.findViewById(R.id.home_icon));
        assertNotNull(activity.findViewById(R.id.transaction_icon));
        assertNotNull(activity.findViewById(R.id.create_expense_icon));
        assertNotNull(activity.findViewById(R.id.profile_icon));
        assertNotNull(activity.findViewById(R.id.budget_icon));
    }


}
