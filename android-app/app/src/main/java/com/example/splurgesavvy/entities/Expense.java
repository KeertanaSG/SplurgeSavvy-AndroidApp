package com.example.splurgesavvy.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.splurgesavvy.converters.DateConverter;

import java.util.Date;

@Entity(tableName = "expense")
@TypeConverters(DateConverter.class)
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private long expenseId;
    private long userId;
    private long categoryId;

    private String category;

    private String name;
    private double amount;
    private String description;
    private Date date;

    public Expense()
    {
    }

    public Expense(long userId, long categoryId, String name, double amount, String description, String category, Date date) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.category = category;
        this.name = name;
    }

    // Getters
    public long getExpenseId() {
        return expenseId;
    }

    public long getUserId() {
        return userId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    // Setters
    public void setExpenseId(long expenseId) {
        this.expenseId = expenseId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
