package com.example.splurgesavvy.activities.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.splurgesavvy.entities.Expense;
import com.example.splurgesavvy.entities.User;

import java.util.Date;
import java.util.List;

@Dao
public interface ExpenseDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Expense expense);

    @Update
    int update(Expense expense);

    @Delete
    int delete(Expense expense);

    @Query("SELECT * FROM Expense WHERE userId = :userId")
    LiveData<List<Expense>> getExpensesByUserId(long userId);

    @Query("SELECT * FROM Expense WHERE categoryId = :categoryId")
    LiveData<List<Expense>> getExpensesByCategoryId(long categoryId);

    @Query("SELECT * FROM Expense WHERE expenseId = :expenseId")
    LiveData<Expense> getExpenseById(long expenseId);

    @Query("SELECT COUNT(*) FROM Expense")
    int countExpenses();

    @Query("SELECT * FROM expense")
    LiveData<List<Expense>> getAllExpense();

    @Query("SELECT SUM(amount) FROM expense WHERE userId = :userId AND category = :categoryName")
    LiveData<Double> getExpenseAmountForCategory(long userId, String categoryName);

    @Query("SELECT expenseId FROM expense WHERE userId = :userId AND categoryId = :categoryId AND name = :name AND description = :description AND date = :date AND amount = :amount AND category = :category AND date = :date")
    Long getExpenseIdByAttributes(long userId, long categoryId, String name, double amount, String description, String category, Date date);

    @Query("SELECT * FROM expense WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    LiveData<List<Expense>> getExpensesByUserIdAndDate(long userId, Date startDate, Date endDate);


}
