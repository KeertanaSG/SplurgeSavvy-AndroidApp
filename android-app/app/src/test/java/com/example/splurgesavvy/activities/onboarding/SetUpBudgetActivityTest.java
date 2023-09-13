package com.example.splurgesavvy.activities.onboarding;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class SetUpBudgetActivityTest {

    private SetUpBudgetActivity activity;

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra("username", "testUser");

        activity = Robolectric.buildActivity(SetUpBudgetActivity.class, intent).setup().get();
    }

    @Test
    public void intentExtraIsSetCorrectly() {
        assertEquals("testUser", activity.getIntent().getStringExtra("username"));
    }
}
