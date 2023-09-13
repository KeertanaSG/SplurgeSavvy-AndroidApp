package com.example.splurgesavvy.activities.manual;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class ManualExpenseActivityTest {

    private ManualExpenseActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(ManualExpenseActivity.class).create().resume().get();
    }

    @Test
    public void isValidExpenseInput_withEmptyName_returnsFalse() {
        // Populate EditTexts
        activity.nameEditText.setText("");
        activity.descriptionEditText.setText("Description");
        activity.valueEditText.setText("10");

        assertFalse(activity.isValidExpenseInput());
    }

    @Test
    public void isValidExpenseInput_withEmptyDescription_returnsFalse() {
        activity.nameEditText.setText("Name");
        activity.descriptionEditText.setText("");
        activity.valueEditText.setText("10");

        assertFalse(activity.isValidExpenseInput());
    }

    @Test
    public void isValidExpenseInput_withNegativeValue_returnsFalse() {
        activity.nameEditText.setText("Name");
        activity.descriptionEditText.setText("Description");
        activity.valueEditText.setText("-10");

        assertFalse(activity.isValidExpenseInput());
    }

    @Test
    public void isValidExpenseInput_withNonNumericValue_returnsFalse() {
        activity.nameEditText.setText("Name");
        activity.descriptionEditText.setText("Description");
        activity.valueEditText.setText("Ten");

        assertFalse(activity.isValidExpenseInput());
    }

    @Test
    public void isValidExpenseInput_withValidInput_returnsTrue() {
        activity.nameEditText.setText("Name");
        activity.descriptionEditText.setText("Description");
        activity.valueEditText.setText("10");

        assertTrue(activity.isValidExpenseInput());
    }
}
