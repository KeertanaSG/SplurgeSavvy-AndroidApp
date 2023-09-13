package com.example.splurgesavvy.activities.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.splurgesavvy.entities.Budget;

public class ParcelableBudget implements Parcelable {
    private long userId;
    private String name;
    private double value;

    public ParcelableBudget(long userId, String name, double value) {
        this.userId = userId;
        this.name = name;
        this.value = value;
    }

    public ParcelableBudget()
    {

    }

    protected ParcelableBudget(Parcel in) {
        userId = in.readLong();
        name = in.readString();
        value = in.readDouble();
    }

    public static final Creator<ParcelableBudget> CREATOR = new Creator<ParcelableBudget>() {
        @Override
        public ParcelableBudget createFromParcel(Parcel in) {
            return new ParcelableBudget(in);
        }

        @Override
        public ParcelableBudget[] newArray(int size) {
            return new ParcelableBudget[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(userId);
        parcel.writeString(name);
        parcel.writeDouble(value);
    }

    // Getter and Setter methods

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Budget toBudget() {
        return new Budget(getUserId(), getName(), getValue());
    }
}

