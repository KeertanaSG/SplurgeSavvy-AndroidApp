package com.example.splurgesavvy.viewModel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.splurgesavvy.entities.User;
import com.example.splurgesavvy.repository.UserRepository;

import java.util.List;
public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private final LiveData<List<User>> allUsers;

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
        allUsers = userRepository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insert(User user) {
        userRepository.insert(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public long getUserIdByUsername(String username) {
        return userRepository.getUserIdByUsername(username);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public LiveData<String> getUsername(long userId) {
        return userRepository.getUsername(userId);
    }

    public int updateEmailForUserId(String newEmail, long userId) {
        return userRepository.updateEmailForUserId(newEmail, userId);
    }

    public int updateUsernameForUserId(String newUsername, long userId) {
        return userRepository.updateUsernameForUserId(newUsername, userId);
    }

    public boolean isPasswordCorrectForUserId(String password, long userId) {
        String storedPassword = userRepository.getPasswordForUserId(userId);
        return password.equals(storedPassword);
    }

    public int updatePasswordForUserId(String newPassword, long userId) {
        return userRepository.updatePasswordForUserId(newPassword, userId);
    }
}

