package com.example.splurgesavvy.activities.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.splurgesavvy.entities.Category;

public class ParcelableCategory implements Parcelable {
    private long userId;
    private String categoryName;
    private String categoryDescription;

    public ParcelableCategory(long userId, String categoryName, String categoryDescription) {
        this.userId = userId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    protected ParcelableCategory(Parcel in) {
        userId = in.readLong();
        categoryName = in.readString();
        categoryDescription = in.readString();
    }

    public static final Creator<ParcelableCategory> CREATOR = new Creator<ParcelableCategory>() {
        @Override
        public ParcelableCategory createFromParcel(Parcel in) {
            return new ParcelableCategory(in);
        }

        @Override
        public ParcelableCategory[] newArray(int size) {
            return new ParcelableCategory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(userId);
        parcel.writeString(categoryName);
        parcel.writeString(categoryDescription);
    }

    // Getter and Setter methods

    public long getUserId() {
        return userId;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public Category toCategory() {
        return new Category(getUserId(), getCategoryName(), getCategoryDescription());
    }
}
