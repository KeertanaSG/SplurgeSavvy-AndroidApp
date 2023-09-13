package com.example.splurgesavvy.activities.settings;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
public class SettingsChangePasswordTest {

    private SettingsChangePassword activity;

    @Before
    public void setUp() {
        long userId = 1L; // Replace with an actual user ID if needed
        Intent intent = new Intent();
        intent.putExtra("userId", userId);
        ActivityController<SettingsChangePassword> activityController = Robolectric.buildActivity(SettingsChangePassword.class, intent);
        activity = activityController.create().visible().get();
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveCorrectOldPasswordEditText() {
        EditText oldPasswordEditText = activity.findViewById(R.id.oldPasswordEditText);
        assertNotNull(oldPasswordEditText);
    }

    @Test
    public void shouldHaveCorrectNewPasswordEditText() {
        EditText newPasswordEditText = activity.findViewById(R.id.newPasswordEditText);
        assertNotNull(newPasswordEditText);
    }

    @Test
    public void shouldHaveCorrectConfirmPasswordEditText() {
        EditText confirmPasswordEditText = activity.findViewById(R.id.confirmPasswordEditText);
        assertNotNull(confirmPasswordEditText);
    }

    @Test
    public void shouldHaveCorrectInitialText() {
        EditText oldPasswordEditText = activity.findViewById(R.id.oldPasswordEditText);
        assertEquals("", oldPasswordEditText.getText().toString());

        EditText newPasswordEditText = activity.findViewById(R.id.newPasswordEditText);
        assertEquals("", newPasswordEditText.getText().toString());

        EditText confirmPasswordEditText = activity.findViewById(R.id.confirmPasswordEditText);
        assertEquals("", confirmPasswordEditText.getText().toString());
    }

    @Test
    public void shouldHaveBackButtonIcon() {
        ImageView backButtonIcon = activity.findViewById(R.id.backButtonIcon);
        assertNotNull(backButtonIcon);
    }
}
