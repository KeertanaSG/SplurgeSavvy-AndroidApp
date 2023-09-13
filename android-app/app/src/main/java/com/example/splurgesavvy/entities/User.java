package com.example.splurgesavvy.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

 @Entity(tableName = "users")
 public class User {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long userId;
    private String username;
    private String email;
    private String password;

    // Constructor with three String parameters for username, email, and password
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User()
    {

    }

    // Getter methods for username, email, and password
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Getter and Setter methods for userId
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }

     public void setUsername(String username) {
         this.username = username;
     }

     public void setEmail(String email) {
         this.email = email;
     }

     public void setPassword(String password) {
         this.password = password;
     }

 }

