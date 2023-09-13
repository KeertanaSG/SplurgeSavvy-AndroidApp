package com.example.splurgesavvy.activities.profile;

import android.content.Intent;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import com.example.splurgesavvy.R;

@RunWith(RobolectricTestRunner.class)
public class Profile_Export_DataTest {

    private Profile_Export_Data activity;

    @Mock
    private ViewModelProvider mockViewModelProvider;
    @Mock
    private ViewModelStoreOwner mockViewModelStoreOwner;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        long userId = 1L; // Mock user ID
        Intent intent = new Intent();
        intent.putExtra("userId", userId);
        activity = Robolectric.buildActivity(Profile_Export_Data.class, intent).create().get();
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveConfirmButton() throws Exception {
        RelativeLayout confirmButton = activity.findViewById(R.id.button_confirm);
        assertNotNull(confirmButton);
    }

    @Test
    public void viewModelProvidersShouldNotBeNull() {
        assertNotNull(activity.budgetViewModel);
        assertNotNull(activity.categoryViewModel);
        assertNotNull(activity.expenseViewModel);
    }

    @Test
    public void shouldReceiveUserIdFromIntent() {
        long userId = activity.getIntent().getLongExtra("userId", -1);
        assertEquals(1L, userId);
    }

}

