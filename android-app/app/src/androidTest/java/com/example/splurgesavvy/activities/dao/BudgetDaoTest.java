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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.splurgesavvy.database.AppDatabase;
import com.example.splurgesavvy.entities.Budget;

@RunWith(AndroidJUnit4.class)
public class BudgetDaoTest {

    private BudgetDao budgetDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        budgetDao = db.budgetDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertAndReadBudget() throws InterruptedException {
        Budget budget = new Budget();
        budget.setUserId(1);
        budget.setName("Grocery");
        budget.setValue(200.0);

        long insertedId = budgetDao.insert(budget);

        LiveData<Budget> liveData = budgetDao.getBudgetById(insertedId);

        Budget fetchedBudget = getValue(liveData);

        assertNotNull(fetchedBudget);
        assertEquals(fetchedBudget.getUserId(), budget.getUserId());
        assertEquals(fetchedBudget.getName(), budget.getName());
        assertEquals(fetchedBudget.getValue(), budget.getValue(), 0.0);
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
