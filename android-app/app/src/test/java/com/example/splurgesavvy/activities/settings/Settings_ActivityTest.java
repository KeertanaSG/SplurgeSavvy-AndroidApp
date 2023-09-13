package com.example.splurgesavvy.activities.settings;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.home.HomeProfileActivity;
import com.example.splurgesavvy.activities.settings.Settings_Activity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Intent;

@RunWith(RobolectricTestRunner.class)
public class Settings_ActivityTest {

    private Settings_Activity activity;
    private ActivityController<Settings_Activity> activityController;
    private long userId = 1L;

    @Before
    public void setUp() {
        Intent intent = new Intent(RuntimeEnvironment.application, Settings_Activity.class);
        intent.putExtra("userId", userId);
        activityController = Robolectric.buildActivity(Settings_Activity.class, intent);
        activity = activityController.create().visible().get();
    }

    @Test
    public void initializesUserViewModel() {
        assertNotNull(activity.userViewModel);
    }

    @Test
    public void checksInvalidUserIDAndFinishesActivity() {
        Intent intent = new Intent(RuntimeEnvironment.application, Settings_Activity.class);
        intent.putExtra("userId", -1); // Invalid userId
        Settings_Activity invalidActivity = Robolectric.buildActivity(Settings_Activity.class, intent).create().get();

        assertEquals(ShadowToast.getTextOfLatestToast(), "Invalid user ID");
        assertTrue(invalidActivity.isFinishing());
    }

    @Test
    public void navigatesToProfileSettingsOnClick() {
        activity.findViewById(R.id.frame_1).performClick();

        Intent startedIntent = Shadows.shadowOf(activity).getNextStartedActivity();
        assertEquals(SettingsProfile.class.getName(), startedIntent.getComponent().getClassName());
        assertEquals(userId, startedIntent.getLongExtra("userId", -1));
    }

    @Test
    public void navigatesToChangeEmailSettingsOnClick() {
        activity.findViewById(R.id.frame_2).performClick();

        Intent startedIntent = Shadows.shadowOf(activity).getNextStartedActivity();
        assertEquals(SettingsChangeEmail.class.getName(), startedIntent.getComponent().getClassName());
        assertEquals(userId, startedIntent.getLongExtra("userId", -1));
    }

    @Test
    public void navigatesToChangePasswordSettingsOnClick() {
        activity.findViewById(R.id.frame_3).performClick();

        Intent startedIntent = Shadows.shadowOf(activity).getNextStartedActivity();
        assertEquals(SettingsChangePassword.class.getName(), startedIntent.getComponent().getClassName());
        assertEquals(userId, startedIntent.getLongExtra("userId", -1));
    }

    @Test
    public void navigatesToAboutPageOnClick() {
        activity.findViewById(R.id.frame_4).performClick();

        Intent startedIntent = Shadows.shadowOf(activity).getNextStartedActivity();
        assertEquals(SettingsAbout.class.getName(), startedIntent.getComponent().getClassName());
        assertEquals(userId, startedIntent.getLongExtra("userId", -1));
    }

    @Test
    public void navigatesBackOnBackPressed() {
        activity.onBackPressed();

        Intent startedIntent = Shadows.shadowOf(activity).getNextStartedActivity();
        assertEquals(HomeProfileActivity.class.getName(), startedIntent.getComponent().getClassName());
        assertEquals(userId, startedIntent.getLongExtra("userId", -1));
    }
}
