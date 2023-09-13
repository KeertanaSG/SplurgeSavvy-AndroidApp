package com.example.splurgesavvy.activities.settings;

import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.splurgesavvy.R;

@RunWith(RobolectricTestRunner.class)
public class SettingsProfileTest {

    private SettingsProfile activity;

    @Before
    public void setUp() {
        long userId = 1L; // Replace with an actual user ID if needed
        Intent intent = new Intent();
        intent.putExtra("userId", userId);
        ActivityController<SettingsProfile> activityController = Robolectric.buildActivity(SettingsProfile.class, intent);
        activity = activityController.create().visible().get();
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveCorrectOldUsernameEditText() {
        EditText oldUsernameEditText = activity.findViewById(R.id.oldUsernameEditText);
        assertNotNull(oldUsernameEditText);
    }

    @Test
    public void shouldHaveCorrectNewUsernameEditText() {
        EditText newUsernameEditText = activity.findViewById(R.id.newUsernameEditText);
        assertNotNull(newUsernameEditText);
    }

    @Test
    public void shouldHaveCorrectConfirmUsernameEditText() {
        EditText confirmUsernameEditText = activity.findViewById(R.id.confirmUsernameEditText);
        assertNotNull(confirmUsernameEditText);
    }

    @Test
    public void shouldHaveCorrectButtonConfirm() {
        RelativeLayout buttonConfirm = activity.findViewById(R.id.button_confirm);
        assertNotNull(buttonConfirm);
    }

    @Test
    public void shouldHaveCorrectInitialText() {
        EditText oldUsernameEditText = activity.findViewById(R.id.oldUsernameEditText);
        assertEquals("", oldUsernameEditText.getText().toString());

        EditText newUsernameEditText = activity.findViewById(R.id.newUsernameEditText);
        assertEquals("", newUsernameEditText.getText().toString());

        EditText confirmUsernameEditText = activity.findViewById(R.id.confirmUsernameEditText);
        assertEquals("", confirmUsernameEditText.getText().toString());
    }

    @Test
    public void shouldHaveBackButtonIcon() {
        ImageView backButtonIcon = activity.findViewById(R.id.backButtonIcon);
        assertNotNull(backButtonIcon);
    }
}
