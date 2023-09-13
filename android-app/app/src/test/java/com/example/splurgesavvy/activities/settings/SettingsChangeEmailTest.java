package com.example.splurgesavvy.activities.settings;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

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
public class SettingsChangeEmailTest {

    private SettingsChangeEmail activity;

    @Before
    public void setUp() {
        long userId = 1L;
        Intent intent = new Intent();
        intent.putExtra("userId", userId);
        ActivityController<SettingsChangeEmail> activityController = Robolectric.buildActivity(SettingsChangeEmail.class, intent);
        activity = activityController.create().visible().get();
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveCorrectOldEmailEditText() {
        EditText oldEmailEditText = activity.findViewById(R.id.oldEmailEditText);
        assertNotNull(oldEmailEditText);
    }

    @Test
    public void shouldHaveCorrectNewEmailEditText() {
        EditText newEmailEditText = activity.findViewById(R.id.newEmailEditText);
        assertNotNull(newEmailEditText);
    }

    @Test
    public void shouldHaveCorrectConfirmEmailEditText() {
        EditText confirmEmailEditText = activity.findViewById(R.id.confirmEmailEditText);
        assertNotNull(confirmEmailEditText);
    }


    @Test
    public void shouldHaveCorrectInitialText() {
        EditText oldEmailEditText = activity.findViewById(R.id.oldEmailEditText);
        assertEquals("", oldEmailEditText.getText().toString());

        EditText newEmailEditText = activity.findViewById(R.id.newEmailEditText);
        assertEquals("", newEmailEditText.getText().toString());

        EditText confirmEmailEditText = activity.findViewById(R.id.confirmEmailEditText);
        assertEquals("", confirmEmailEditText.getText().toString());
    }
}
