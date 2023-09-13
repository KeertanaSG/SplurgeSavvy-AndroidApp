package com.example.splurgesavvy.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.splurgesavvy.entities.Category;
import com.example.splurgesavvy.repository.UserRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public CategoryViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<Long> getCategoryIdByName(String categoryName) {
        return userRepository.getCategoryIdByName(categoryName);
    }

    public LiveData<List<Category>> getAllCategoriesByUserId(long userId) {
        return userRepository.getAllCategoriesByUserId(userId);
    }

    public Long getCategoryIdByAttributes(long userId, String name, String description) {
        return userRepository.getCategoryIdByAttributes(userId, name, description);
    }

    public void insert(Category category) {
        userRepository.insert(category);
    }

    public void update(Category category) {
        userRepository.update(category);
    }

    public void delete(Category category) {
        userRepository.delete(category);
    }
}

