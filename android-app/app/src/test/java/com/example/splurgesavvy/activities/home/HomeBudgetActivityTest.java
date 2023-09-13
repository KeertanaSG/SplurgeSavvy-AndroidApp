package com.example.splurgesavvy.activities.home;

import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.budget.CreateBudgetActivity;
import com.example.splurgesavvy.activities.manual.ManualExpenseActivity;
import com.example.splurgesavvy.adapters.BudgetAdapter;
import com.example.splurgesavvy.entities.Budget;
import com.example.splurgesavvy.viewModel.BudgetViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class HomeBudgetActivityTest {

    private HomeBudgetActivity activity;

    @Mock
    private BudgetViewModel budgetViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        Intent intent = new Intent();
        intent.putExtra("userId", 1L);

        activity = Robolectric.buildActivity(HomeBudgetActivity.class, intent)
                .create()
                .resume()
                .get();
        activity.budgetViewModel = budgetViewModel;
    }

    @Test
    public void testRecyclerViewIsPresent() {
        RecyclerView recyclerView = activity.findViewById(R.id.recyclerView);
        assertEquals(true, recyclerView != null);
    }

    @Test
    public void testBudgetsAreLoaded() {
        MutableLiveData<List<Budget>> liveData = new MutableLiveData<>();
        List<Budget> testBudgets = Arrays.asList(
                new Budget(1L, "Groceries", 100.0),
                new Budget(1L, "Entertainment", 50.0)
        );
        liveData.setValue(testBudgets);

        when(budgetViewModel.getAllBudgetsByUserId(1L)).thenReturn(liveData);

        RecyclerView recyclerView = activity.findViewById(R.id.recyclerView);
        BudgetAdapter originalAdapter = new BudgetAdapter();
        BudgetAdapter spyAdapter = spy(originalAdapter);
        recyclerView.setAdapter(spyAdapter);

        liveData.observeForever(budgets -> {});

        verify(spyAdapter).setBudgets(testBudgets);

        assertEquals(2, spyAdapter.getItemCount());
    }

    @Test
    public void testInvalidUserIdToastMessage() {
        String expected = "Invalid user ID";

        activity = Robolectric.buildActivity(HomeBudgetActivity.class)
                .create()
                .resume()
                .get();

        Toast latestToast = ShadowToast.getLatestToast();
        String toastMessage = ShadowToast.getTextOfLatestToast();

        assertEquals(Toast.LENGTH_LONG, latestToast.getDuration());
        assertEquals(expected, toastMessage);
    }

    @Test
    public void testCreateBudgetButtonNavigation() {
        activity.findViewById(R.id.button_create_budget).performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent nextStartedActivity = shadowActivity.getNextStartedActivity();

        assertEquals(CreateBudgetActivity.class.getName(), nextStartedActivity.getComponent().getClassName());
    }

    @Test
    public void testInitializeBottomNavigationWorksCorrectly() {
        activity.findViewById(R.id.home_icon).performClick();
        verifyActivityStarted(HomePageActivity.class);

        activity.findViewById(R.id.transaction_icon).performClick();
        verifyActivityStarted(HomeTransactionActivity.class);

        activity.findViewById(R.id.create_expense_icon).performClick();
        verifyActivityStarted(ManualExpenseActivity.class);

        activity.findViewById(R.id.profile_icon).performClick();
        verifyActivityStarted(HomeProfileActivity.class);
    }

    private void verifyActivityStarted(Class<?> targetActivity) {
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent nextStartedActivity = shadowActivity.getNextStartedActivity();
        assertEquals(targetActivity.getName(), nextStartedActivity.getComponent().getClassName());
    }
}
