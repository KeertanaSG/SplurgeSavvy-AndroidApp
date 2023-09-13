package com.example.splurgesavvy.repository;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.splurgesavvy.activities.dao.BudgetDao;
import com.example.splurgesavvy.activities.dao.ExpenseDao;
import com.example.splurgesavvy.entities.Budget;
import com.example.splurgesavvy.entities.Category;
import com.example.splurgesavvy.entities.Expense;
import com.example.splurgesavvy.entities.User;
import com.example.splurgesavvy.activities.dao.UserDao;
import com.example.splurgesavvy.activities.dao.CategoryDao;
import com.example.splurgesavvy.database.AppDatabase;

import java.util.Date;
import java.util.List;
public class UserRepository {

    private final UserDao userDao;
    private final BudgetDao budgetDao;
    private final CategoryDao categoryDao;
    private final ExpenseDao expenseDao;


    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);

        userDao = db.userDao();
        budgetDao = db.budgetDao();
        categoryDao = db.categoryDao();
        expenseDao = db.expenseDao();

    }

    // User operations
    public void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.insert(user));
    }

    public void update(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.update(user));
    }

    public void delete(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.delete(user));
    }

    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }

    public LiveData<User> loginUser(String username, String password) {
        return userDao.getUserByUsernameAndPassword(username, password);
    }

    public long getUserIdByUsername(String username) {
        return userDao.getUserIdByUsername(username);
    }

    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public LiveData<String> getUsername(long userId) {
        return userDao.getUsernameById(userId);
    }

    public int updateEmailForUserId(String newEmail, long userId) {
        return userDao.updateEmailForUserId(newEmail, userId);
    }

    public int updateUsernameForUserId(String newUsername, long userId) {
        return userDao.updateUsernameForUserId(newUsername, userId);
    }

    public String getPasswordForUserId(long userId) {
        // Fetch and return the password for a specific user ID.
        return userDao.getPasswordByUserId(userId);
    }

    public int updatePasswordForUserId(String newPassword, long userId) {
        // Update the password for a specific user ID and return the rows affected.
        return userDao.updatePasswordByUserId(newPassword, userId);
    }



    // Budget operations
    public void insert(Budget budget) {
        AppDatabase.databaseWriteExecutor.execute(() -> budgetDao.insert(budget));
    }

    public void update(Budget budget) {
        AppDatabase.databaseWriteExecutor.execute(() -> budgetDao.update(budget));
    }

    public void delete(Budget budget) {
        AppDatabase.databaseWriteExecutor.execute(() -> budgetDao.delete(budget));
    }

    public LiveData<List<Budget>> getAllBudgetsByUserId(long userId) {
        return budgetDao.getBudgetsByUserId(userId);
    }

    public LiveData<Budget> getInitialBudgetByUserId(long userId) {
        return budgetDao.getInitialBudgetByUserId(userId);
    }

    public Long getBudgetIdByAttributes(long userId, String name, double value) {
        return budgetDao.getBudgetIdByAttributes(userId, name, value);
    }

    public LiveData<Budget> getBudgetByCategoryAndUser(long userId, String category) {
        return budgetDao.getBudgetByCategoryAndUser(userId, category);
    }



    // Category operations
    public void insert(Category category) {
        AppDatabase.databaseWriteExecutor.execute(() -> categoryDao.insert(category));
    }

    public void update(Category category) {
        AppDatabase.databaseWriteExecutor.execute(() -> categoryDao.update(category));
    }

    public void delete(Category category) {
        AppDatabase.databaseWriteExecutor.execute(() -> categoryDao.delete(category));
    }

    public LiveData<List<Category>> getAllCategoriesByUserId(long userId) {
        return categoryDao.getCategoriesByUserId(userId);
    }

    public LiveData<Long> getCategoryIdByName(String categoryName) {
        return categoryDao.getCategoryIdByName(categoryName);
    }

    public Long getCategoryIdByAttributes(long userId, String name, String description) {
        return categoryDao.getCategoryIdByAttributes(userId, name, description);
    }


    // Expense operations
    public void insert(Expense expense) {
        AppDatabase.databaseWriteExecutor.execute(() -> expenseDao.insert(expense));
    }

    public void update(Expense expense) {
        AppDatabase.databaseWriteExecutor.execute(() ->
        {
            expenseDao.update(expense);
        });
    }

    public void delete(Expense expense) {
        AppDatabase.databaseWriteExecutor.execute(() ->
        {
            expenseDao.delete(expense);
        });
    }

    public LiveData<List<Expense>> getAllExpensesByUserId(long userId) {
        return expenseDao.getExpensesByUserId(userId);
    }

    public LiveData<Expense> getExpense(long expenseId) {
        return expenseDao.getExpenseById(expenseId);
    }

    public LiveData<Double> getExpenseAmountForCategory(long userId, String categoryName) {
        return expenseDao.getExpenseAmountForCategory(userId, categoryName);
    }

    public Long getExpenseIdByAttributes(long userId, long categoryId, String name, double amount, String description, String category, Date date) {
        return expenseDao.getExpenseIdByAttributes(userId, categoryId, name, amount, description,category,date);
    }

    public LiveData<List<Expense>> getExpensesByUserIdAndDate(long userId, Date startDate, Date endDate) {
        return expenseDao.getExpensesByUserIdAndDate(userId, startDate, endDate);
    }



}