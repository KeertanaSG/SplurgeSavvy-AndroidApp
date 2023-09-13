package com.example.splurgesavvy.activities.dao;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.splurgesavvy.database.AppDatabase;
import com.example.splurgesavvy.entities.Expense;

@RunWith(AndroidJUnit4.class)
public class ExpenseDaoTest {

    private ExpenseDao expenseDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        expenseDao = db.expenseDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertAndReadExpense() throws InterruptedException {
        Expense expense = new Expense();
        expense.setUserId(1);
        expense.setCategoryId(2);
        expense.setName("Lunch");
        expense.setAmount(15.0);
        expense.setDate(new Date());
        expense.setDescription("Lunch at the restaurant");
        expense.setCategory("Food");

        long insertedId = expenseDao.insert(expense);

        LiveData<Expense> liveData = expenseDao.getExpenseById(insertedId);

        Expense fetchedExpense = getValue(liveData);

        assertNotNull(fetchedExpense);
        assertEquals(fetchedExpense.getUserId(), expense.getUserId());
        assertEquals(fetchedExpense.getCategoryId(), expense.getCategoryId());
        assertEquals(fetchedExpense.getName(), expense.getName());
        assertEquals(fetchedExpense.getAmount(), expense.getAmount(), 0.1);
        assertEquals(fetchedExpense.getDescription(), expense.getDescription());
        assertEquals(fetchedExpense.getCategory(), expense.getCategory());
    }

    // Utility method to get the value from a LiveData object
    public static <T> T getValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        return (T) data[0];
    }
}

