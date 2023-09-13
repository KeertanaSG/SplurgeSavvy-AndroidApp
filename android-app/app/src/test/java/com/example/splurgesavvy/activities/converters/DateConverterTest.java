package com.example.splurgesavvy.activities.converters;

import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.example.splurgesavvy.converters.DateConverter;

public class DateConverterTest {

    @Test
    public void testToDate_withNullTimestamp() {
        DateConverter DateConverter = null;
        assertNull(DateConverter.toDate(null));
    }

    @Test
    public void testToTimestamp_withNullDate() {
        assertNull(DateConverter.toTimestamp(null));
    }

    @Test
    public void testToDate_withValidTimestamp() {
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);

        assertEquals(date, DateConverter.toDate(currentTimeMillis));
    }

    @Test
    public void testToTimestamp_withValidDate() {
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);

        assertEquals(Long.valueOf(currentTimeMillis), DateConverter.toTimestamp(date));
    }

    @Test
    public void testConversionConsistency() {
        long currentTimeMillis = System.currentTimeMillis();
        Date originalDate = new Date(currentTimeMillis);
        Long timestamp = DateConverter.toTimestamp(originalDate);
        Date convertedDate = DateConverter.toDate(timestamp);

        assertEquals(originalDate, convertedDate);
    }
}
