package com.example.splurgesavvy.activities.onboarding;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.splurgesavvy.activities.onboarding.SignUpActivity;

public class SignUpActivityTest {

    private SignUpActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = new SignUpActivity();
    }

    @Test
    public void validateInputs_withValidInputs_returnsTrue() {
        boolean result = activity.validateInputs("testuser", "test@example.com", "password");
        assertTrue(result);
    }

    @Test
    public void validateInputs_withInvalidEmail_returnsFalse() {
        boolean result = activity.validateInputs("testuser", "invalidEmail", "password");
        assertFalse(result);
    }

}
