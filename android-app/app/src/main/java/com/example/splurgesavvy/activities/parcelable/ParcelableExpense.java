package com.example.splurgesavvy.activities.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.splurgesavvy.entities.Expense;

import java.util.Date;

public class ParcelableExpense implements Parcelable {
    private long userId;
    private long categoryId;
    private String category;
    private String name;
    private double amount;
    private String description;
    private Date date;

    public ParcelableExpense(long userId, long categoryId, String category, String name, double amount, String description, Date date) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.category = category;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    protected ParcelableExpense(Parcel in) {
        userId = in.readLong();
        categoryId = in.readLong();
        category = in.readString();
        name = in.readString();
        amount = in.readDouble();
        description = in.readString();
        long tmpDate = in.readLong();
        date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Creator<ParcelableExpense> CREATOR = new Creator<ParcelableExpense>() {
        @Override
        public ParcelableExpense createFromParcel(Parcel in) {
            return new ParcelableExpense(in);
        }

        @Override
        public ParcelableExpense[] newArray(int size) {
            return new ParcelableExpense[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(userId);
        parcel.writeLong(categoryId);
        parcel.writeString(category);
        parcel.writeString(name);
        parcel.writeDouble(amount);
        parcel.writeString(description);
        long tmpDate = date == null ? -1 : date.getTime();
        parcel.writeLong(tmpDate);
    }

    // Getter and Setter methods (if needed)

    public long getUserId() {
        return userId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
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

    public Expense toExpense() {
        return new Expense(getUserId(), getCategoryId(), getName(), getAmount(), getDescription(), getCategory(), getDate());
    }
}
