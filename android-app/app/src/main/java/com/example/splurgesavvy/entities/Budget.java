package com.example.splurgesavvy.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "budgets")
public class Budget {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long budgetId;
    private long userId;
    private String name;
    private double value;

    public Budget()
    {

    }

    public Budget(long userId,String name, double value) {
        this.name = name;
        this.value = value;
        this.userId = userId;
    }

    public long getBudgetId() {return budgetId;}

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    // Setters
    public void setBudgetId(long budgetId) {
        this.budgetId = budgetId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setValue(double value) {
        this.value= value;
    }

    public void setName(String name)
    {
        this.name= name;
    }

}

