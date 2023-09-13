package com.example.splurgesavvy.activities.dao;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.splurgesavvy.database.AppDatabase;
import com.example.splurgesavvy.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

    private UserDao userDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userDao = db.userDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertAndReadUser() throws InterruptedException {
        User user = new User();
        user.setUserId(1);
        user.setUsername("John Doe");
        user.setEmail("johndoe@example.com");

        userDao.insert(user);

        LiveData<User> liveData = userDao.getUserById(1);

        User fetchedUser = getValue(liveData);

        assertNotNull(fetchedUser);
        assertEquals(fetchedUser.getUserId(), user.getUserId());
        assertEquals(fetchedUser.getUsername(), user.getUsername());
        assertEquals(fetchedUser.getEmail(), user.getEmail());
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

