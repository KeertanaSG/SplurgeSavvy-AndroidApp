package com.example.splurgesavvy.activities.login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

import android.content.Intent;
import android.os.Build;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.home.HomePageActivityTest;
import com.example.splurgesavvy.activities.onboarding.SignUpActivity;
import com.example.splurgesavvy.entities.User;
import com.example.splurgesavvy.repository.UserRepository;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class LoginPasswordActivityTest {

    private LoginPasswordActivity activity;
    private UserRepository mockUserRepository;
    private MutableLiveData<User> loginUserLiveData;

    @Before
    public void setUp() {
        loginUserLiveData = new MutableLiveData<>();
        mockUserRepository = mock(UserRepository.class);

        when(mockUserRepository.loginUser(anyString(), anyString())).thenReturn(loginUserLiveData);

        // Set up the Robolectric activity
        activity = Robolectric.buildActivity(LoginPasswordActivity.class).create().get();

        // Inject the mockUserRepository into our activity
        activity.userRepository = mockUserRepository;
    }

    @Test
    public void activityShouldNotBeNull() {
        assertNotNull(activity);
    }

    @Test
    public void onClickSignUp_shouldNavigateToSignUpActivity() {
        activity.findViewById(R.id.sign_up).performClick();

        Intent expectedIntent = new Intent(activity, SignUpActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void onClickForgotPassword_shouldNavigateToForgotPasswordActivity() {
        activity.findViewById(R.id.forgot_pass).performClick();

        Intent expectedIntent = new Intent(activity, ForgotPasswordActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void onClickUsePin_shouldNavigateToLoginPincodeActivity() {
        activity.findViewById(R.id.use_pin).performClick();

        Intent expectedIntent = new Intent(activity, LoginPincodeActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void givenValidCredentials_onLogin_shouldNavigateToHomePageActivity() {
        // Simulate user entering valid credentials
        ((EditText) activity.findViewById(R.id.usernameEditText)).setText("validUsername");
        ((EditText) activity.findViewById(R.id.passwordEditText)).setText("validPassword");

        activity.findViewById(R.id.button_signup).performClick();

        // Simulate the repository returning a valid user
        loginUserLiveData.postValue(new User());

        Intent expectedIntent = new Intent(activity, HomePageActivityTest.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void givenInvalidCredentials_onLogin_shouldShowToast() {
        // Simulate user entering invalid credentials
        ((EditText) activity.findViewById(R.id.usernameEditText)).setText("invalidUsername");
        ((EditText) activity.findViewById(R.id.passwordEditText)).setText("invalidPassword");

        activity.findViewById(R.id.button_signup).performClick();

        // Simulate the repository returning null for the user
        loginUserLiveData.postValue(null);

        ShadowToast shadowToast = shadowOf(Toast.makeText(activity, "", Toast.LENGTH_SHORT));
        assertEquals("Invalid credentials. Please try again.", shadowToast.getTextOfLatestToast());
    }

}

