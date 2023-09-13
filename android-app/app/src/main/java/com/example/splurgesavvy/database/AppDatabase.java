package com.example.splurgesavvy.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.splurgesavvy.activities.dao.BudgetDao;
import com.example.splurgesavvy.activities.dao.CategoryDao;
import com.example.splurgesavvy.activities.dao.ExpenseDao;
import com.example.splurgesavvy.activities.dao.UserDao;
import com.example.splurgesavvy.converters.DateConverter;
import com.example.splurgesavvy.entities.Expense;
import com.example.splurgesavvy.entities.User;
import com.example.splurgesavvy.entities.Budget;
import com.example.splurgesavvy.entities.Category;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {User.class, Budget.class, Category.class, Expense.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract BudgetDao budgetDao();
    public abstract CategoryDao categoryDao();
    public abstract ExpenseDao expenseDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Singleton pattern for database instance

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.

                // Dao initialization
                UserDao userDao = INSTANCE.userDao();
                ExpenseDao expenseDao = INSTANCE.expenseDao();
                CategoryDao categoryDao = INSTANCE.categoryDao();
                BudgetDao budgetDao = INSTANCE.budgetDao();


                // Populate with default data

                // Users
                User user1 = new User("JohnDoe", "john.doe@example.com", "password123");
                userDao.insert(user1);

                // Categories
                Category category1 = new Category(user1.getUserId(), "Food", "desc");
                long categoryId = categoryDao.insert(category1);

                // Budgets
                Budget budget1 = new Budget(user1.getUserId(), "Monthly", 2000.0);
                budgetDao.insert(budget1);

                // Expenses
                Date currentDate = new Date();
                Expense expense1 = new Expense(user1.getUserId(), categoryId, "Lunch", 12.5, "Had lunch at a cafe", "Food", currentDate);
                expenseDao.insert(expense1);
            });
        }
    };
}

