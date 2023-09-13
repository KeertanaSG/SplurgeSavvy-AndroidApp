package com.example.splurgesavvy.activities.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.home.HomeProfileActivity;
import com.example.splurgesavvy.activities.login.LoginPasswordActivity;
import com.example.splurgesavvy.viewModel.UserViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class HomeProfileActivityTest {

    @Rule
    public ActivityController<HomeProfileActivity> activityController;

    @Mock
    UserViewModel userViewModel;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Intent intent = new Intent();
        intent.putExtra("userId", 1L);
        activityController = Robolectric.buildActivity(HomeProfileActivity.class, intent);
    }

    @Test
    public void testUsernameIsDisplayed() {
        MutableLiveData<String> fakeUsernameLiveData = new MutableLiveData<>();
        String fakeUsername = "JohnDoe";
        fakeUsernameLiveData.setValue(fakeUsername);

        when(userViewModel.getUsername(1L)).thenReturn(fakeUsernameLiveData);

        activityController.create().start().resume();

        TextView usernameTextView = activityController.get().findViewById(R.id.anonymous);
        assertNotNull(usernameTextView);

        assertEquals("JohnDoe", usernameTextView.getText().toString());
    }

    @Test
    public void testInvalidUserIdToast() {
        Intent intent = new Intent();
        intent.putExtra("userId", -1L);
        activityController = Robolectric.buildActivity(HomeProfileActivity.class, intent);

        activityController.create().start().resume();

        assertEquals("Invalid user ID", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void testUserNotFoundToast() {
        MutableLiveData<String> fakeUsernameLiveData = new MutableLiveData<>();
        fakeUsernameLiveData.setValue(null);

        when(userViewModel.getUsername(1L)).thenReturn(fakeUsernameLiveData);

        activityController.create().start().resume();

        assertEquals("User not found", ShadowToast.getTextOfLatestToast());
    }

}

