package com.example.splurgesavvy.activities.dao;

import com.example.splurgesavvy.entities.Budget;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface BudgetDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Budget budget);

    @Update
    int update(Budget budget);

    @Delete
    void delete(Budget budget);

    @Query("SELECT * FROM budgets WHERE userId = :userId")
    LiveData<List<Budget>> getBudgetsByUserId(long userId);

    @Query("SELECT * FROM budgets WHERE budgetId = :budgetId")
    LiveData<Budget> getBudgetById(long budgetId);

    @Query("SELECT COUNT(*) FROM budgets")
    int countBudgets();

    @Query("SELECT * FROM budgets")
    LiveData<List<Budget>> getAllBudgets();

    @Query("SELECT * FROM budgets WHERE userId = :userId ORDER BY budgetId ASC LIMIT 1")
    LiveData<Budget> getInitialBudgetByUserId(long userId);

    @Query("SELECT budgetId FROM budgets WHERE userId = :userId AND name = :name AND value = :value")
    Long getBudgetIdByAttributes(long userId, String name, double value);

    @Query("SELECT * FROM budgets WHERE userId = :userId AND name = :name LIMIT 1")
    LiveData<Budget> getBudgetByCategoryAndUser(long userId, String name);

}
