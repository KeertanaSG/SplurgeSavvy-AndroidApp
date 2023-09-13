package com.example.splurgesavvy.activities.onboarding;

import android.content.Intent;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;


import com.example.splurgesavvy.R;

@RunWith(RobolectricTestRunner.class)
public class NewBudgetActivityTest {

    private NewBudgetActivity activity;

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra("username", "testUser");

        activity = Robolectric.buildActivity(NewBudgetActivity.class, intent).setup().get();
    }

    @Test
    public void givenInvalidInput_whenContinueClicked_showsToast() {
        activity.findViewById(R.id.button_continue).performClick();
        assertNotNull("Toast was not shown", ShadowToast.getLatestToast());
        assertEquals("Please provide valid budget details.", ShadowToast.getTextOfLatestToast());
    }


    @Test
    public void givenValidInput_whenContinueClicked_navigatesToSignupSuccessActivity() {
        ((EditText) activity.findViewById(R.id.nameEditText)).setText("BudgetName");
        ((EditText) activity.findViewById(R.id.valueEditText)).setText("2000");

        activity.findViewById(R.id.button_continue).performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        assertEquals(SignupSuccessActivity.class.getName(), startedIntent.getComponent().getClassName());
    }
}

