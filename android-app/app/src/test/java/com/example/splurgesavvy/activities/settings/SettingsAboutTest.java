package com.example.splurgesavvy.activities.settings;
import android.content.Intent;
import android.widget.ImageView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;

import static org.junit.Assert.assertEquals;

import com.example.splurgesavvy.R;

@RunWith(RobolectricTestRunner.class)
public class SettingsAboutTest {

    private SettingsAbout activity;
    private long userId = 1L;
    private ActivityController<SettingsAbout> activityController;

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra("userId", userId);
        ActivityController<SettingsAbout> activityController = Robolectric.buildActivity(SettingsAbout.class, intent);
        activity = activityController
                .create()
                .visible()
                .get();
    }

    @Test
    public void shouldNavigateToSettingsActivityWhenBackButtonClicked() {
        ImageView backButtonIcon = activity.findViewById(R.id.backButtonIcon);
        backButtonIcon.performClick();

        Intent startedIntent = Shadows.shadowOf(activity).getNextStartedActivity();
        assertEquals(Settings_Activity.class.getName(), startedIntent.getComponent().getClassName());
        assertEquals(userId, startedIntent.getLongExtra("userId", -1));
    }

    @Test
    public void shouldHandleInvalidUserIdGracefully() {
        Intent invalidIntent = new Intent();
        invalidIntent.putExtra("userId", -1L);
        ActivityController<SettingsAbout> invalidController = Robolectric.buildActivity(SettingsAbout.class, invalidIntent)
                .create()
                .visible();

        SettingsAbout invalidActivity = invalidController.get();
    }
}

