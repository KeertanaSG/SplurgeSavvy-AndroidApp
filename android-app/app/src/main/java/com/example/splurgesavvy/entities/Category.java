package com.example.splurgesavvy.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class Category
{
    @PrimaryKey(autoGenerate = true)
    private long categoryId;
    private long userId;
    private String name;

    private String description;

    public Category()
    {

    }

    public Category(long userId, String name, String description) {
        this.userId = userId;
        this.name = name;
        this.description = description;
    }

    public Category(Long categoryId, long userId, String name, String description) {
        this.categoryId = categoryId;
        this.userId = userId;
        this.name = name;
        this.description = description;
    }


    // Getters
    public long getCategoryId() {
        return categoryId;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() { return description;}

    // Setters
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) { this.description = description;}
}
