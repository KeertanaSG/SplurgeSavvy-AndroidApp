package com.example.splurgesavvy.activities.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.splurgesavvy.entities.Expense;
import com.example.splurgesavvy.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);
    @Delete
    void delete(User user);

    @Query("DELETE FROM users")
    void deleteAllUsers();

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM users WHERE userId = :userId")
    LiveData<User> getUserById(long userId);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    LiveData<User> loginUser(String username, String password);

    @Query("SELECT COUNT(*) FROM users")
    int countUsers();

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    LiveData<User> getUserByUsernameAndPassword(String username, String password);

    @Query("SELECT userId FROM users WHERE username = :username LIMIT 1")
    long getUserIdByUsername(String username);

    @Query("SELECT * FROM users WHERE username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM users WHERE email = :email")
    User getUserByEmail(String email);

    @Query("SELECT username FROM users WHERE userid = :userId")
    LiveData<String> getUsernameById(long userId);

    @Query("UPDATE users SET email = :newEmail WHERE userId = :userId")
    int updateEmailForUserId(String newEmail, long userId);

    @Query("UPDATE users SET username = :newUsername WHERE userId = :userId")
    int updateUsernameForUserId(String newUsername, long userId);

    @Query("SELECT password FROM users WHERE userId = :userId")
    String getPasswordByUserId(long userId);

    @Query("UPDATE users SET password = :newPassword WHERE userId = :userId")
    int updatePasswordByUserId(String newPassword, long userId);


}


