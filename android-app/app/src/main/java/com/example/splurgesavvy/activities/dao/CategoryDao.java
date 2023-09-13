package com.example.splurgesavvy.activities.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.splurgesavvy.entities.Category;
import com.example.splurgesavvy.entities.User;

import java.util.List;
@Dao
public interface CategoryDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Category category);

    @Update
    int update(Category category);

    @Delete
    void delete(Category category);

    @Query("SELECT * FROM Category WHERE userId = :userId")
    LiveData<List<Category>> getCategoriesByUserId(long userId);

    @Query("SELECT * FROM Category WHERE categoryId = :categoryId")
    LiveData<Category> getCategoryById(long categoryId);

    @Query("SELECT * FROM Category")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT categoryId FROM Category WHERE Name = :categoryName")
    LiveData<Long> getCategoryIdByName(String categoryName);

    @Query("SELECT CategoryId FROM Category WHERE userId = :userId AND name = :name AND description = :description")
    Long getCategoryIdByAttributes(long userId, String name, String description);


}
