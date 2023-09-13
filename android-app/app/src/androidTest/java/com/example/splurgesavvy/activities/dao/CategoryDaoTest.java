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

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.splurgesavvy.database.AppDatabase;
import com.example.splurgesavvy.entities.Category;

@RunWith(AndroidJUnit4.class)
public class CategoryDaoTest {

    private CategoryDao categoryDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        categoryDao = db.categoryDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertAndReadCategory() throws InterruptedException {
        Category category = new Category();
        category.setUserId(1);
        category.setName("Grocery");
        category.setDescription("Daily groceries");

        long insertedId = categoryDao.insert(category);

        LiveData<Category> liveData = categoryDao.getCategoryById(insertedId);

        Category fetchedCategory = getValue(liveData);

        assertNotNull(fetchedCategory);
        assertEquals(fetchedCategory.getUserId(), category.getUserId());
        assertEquals(fetchedCategory.getName(), category.getName());
        assertEquals(fetchedCategory.getDescription(), category.getDescription());
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

