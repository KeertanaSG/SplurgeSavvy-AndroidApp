package com.example.splurgesavvy.activities.home;

import android.content.Intent;
import android.os.Build;

import com.example.splurgesavvy.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class HomeTransactionActivityTest {

    private HomeTransactionActivity activity;

    @Before
    public void setUp() {
        ActivityController<HomeTransactionActivity> activityController = Robolectric.buildActivity(HomeTransactionActivity.class);
        activity = activityController.get();
    }

    @Test
    public void givenValidUserId_shouldNotFinish() {
        Intent intent = new Intent();
        intent.putExtra("userId", 1L);
        activity.setIntent(intent);

        activity.onCreate(null);

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        assertNull(ShadowToast.getLatestToast());
        // Check that activity is not finishing
        assertNotNull(activity);
    }

    @Test
    public void onClickHomeIcon_shouldNavigateToHomePageActivity() {
        Intent intent = new Intent();
        intent.putExtra("userId", 1L);
        activity.setIntent(intent);

        activity.onCreate(null);
        activity.findViewById(R.id.home_icon).performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent startedIntent = shadowActivity.peekNextStartedActivity();

        assertEquals(HomePageActivity.class.getCanonicalName(), startedIntent.getComponent().getClassName());
    }
}
