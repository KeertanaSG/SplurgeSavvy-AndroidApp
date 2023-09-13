package com.example.splurgesavvy.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.splurgesavvy.entities.Expense;
import com.example.splurgesavvy.repository.UserRepository;

import java.util.Date;
import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public ExpenseViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<List<Expense>> getAllExpensesByUserId(long userId) {
        return userRepository.getAllExpensesByUserId(userId);
    }

    public LiveData<Expense> getExpense(long expenseId) {
        return userRepository.getExpense(expenseId);
    }

    public LiveData<Double> getExpenseAmountForCategory(long userId, String categoryName) {
        return userRepository.getExpenseAmountForCategory(userId, categoryName);
    }

    public void insert(Expense expense) {
        userRepository.insert(expense);
    }

    public void update(Expense expense) {
        userRepository.update(expense);
    }

    public void delete(Expense expense) {
        userRepository.delete(expense);
    }

    public Long getExpenseIdByAttributes(long userId, long categoryId, String name, double amount, String description, String category, Date date) {
        return userRepository.getExpenseIdByAttributes(userId, categoryId, name, amount, description,category,date);
    }

    public LiveData<List<Expense>> getExpensesByUserIdAndDate(long userId, Date startDate, Date endDate) {
        return userRepository.getExpensesByUserIdAndDate(userId, startDate, endDate);
    }

}

