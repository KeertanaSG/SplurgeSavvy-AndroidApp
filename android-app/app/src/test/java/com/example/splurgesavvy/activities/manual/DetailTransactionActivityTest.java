package com.example.splurgesavvy.activities.manual;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Intent;

import com.example.splurgesavvy.activities.parcelable.ParcelableExpense;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class DetailTransactionActivityTest {

    private DetailTransactionActivity activity;
    private ParcelableExpense mockParcelableExpense;

    @Before
    public void setUp() {
        mockParcelableExpense = mock(ParcelableExpense.class);
        Intent intent = new Intent(RuntimeEnvironment.application, DetailTransactionActivity.class);
        intent.putExtra("parcelableExpense", mockParcelableExpense);
        activity = Robolectric.buildActivity(DetailTransactionActivity.class, intent).create().resume().get();
    }

    @Test
    public void onCreate_withParcelableExpense_populatesViews() {
        when(mockParcelableExpense.getName()).thenReturn("Sample Name");
        when(mockParcelableExpense.getAmount()).thenReturn(100.0);
        when(mockParcelableExpense.getDate()).thenReturn(new Date());
        when(mockParcelableExpense.getCategory()).thenReturn("Sample Category");
        when(mockParcelableExpense.getDescription()).thenReturn("Sample Description");

        Date mockDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String expectedFormattedDate = sdf.format(mockDate);

        activity.populateViews(mockParcelableExpense);

        assertEquals("Sample Name", activity.nameTextView.getText().toString());
        assertEquals("100.0", activity.amountTextView.getText().toString());
        assertEquals("Sample Name", activity.nameTextView.getText().toString());
        assertEquals("100.0", activity.amountTextView.getText().toString());
        assertEquals(expectedFormattedDate, activity.dateTextView.getText().toString());
        assertEquals("Sample Category", activity.categoryTextView.getText().toString());
        assertEquals("Sample Description", activity.descriptionTextView.getText().toString());
    }

}

