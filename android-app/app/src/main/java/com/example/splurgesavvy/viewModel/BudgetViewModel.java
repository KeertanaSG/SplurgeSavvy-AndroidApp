package com.example.splurgesavvy.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

import com.example.splurgesavvy.entities.Budget;
import com.example.splurgesavvy.repository.UserRepository;

public class BudgetViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    private LiveData<List<Budget>> allBudgets;

    public BudgetViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<List<Budget>> getAllBudgetsByUserId(long userId) {
        return userRepository.getAllBudgetsByUserId(userId);
    }

    public LiveData<Budget> getInitialBudgetByUserId(long userId) {
        return userRepository.getInitialBudgetByUserId(userId);
    }

    public Long getBudgetIdByAttributes(long userId, String name, double value) {
        return userRepository.getBudgetIdByAttributes(userId, name, value);
    }

    public LiveData<Budget> getBudgetByCategoryAndUser(long userId, String category) {
        return userRepository.getBudgetByCategoryAndUser(userId, category);
    }



    public void insert(Budget budget) {
        userRepository.insert(budget);
    }

    public void update(Budget budget) {
        userRepository.update(budget);
    }

    public void delete(Budget budget) {
        userRepository.delete(budget);
    }
}
