package com.example.splurgesavvy.adapters;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.splurgesavvy.activities.onboarding.OnboardingScreen1Fragment;
import com.example.splurgesavvy.activities.onboarding.OnboardingScreen2Fragment;
import com.example.splurgesavvy.activities.onboarding.OnboardingScreen3Fragment;
import com.example.splurgesavvy.activities.onboarding.OnboardingLandingFragment;

public class OnboardingPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES_WITH_PROGRESS_BAR = 3; // Number of screens with dots progress bar

    public OnboardingPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Returning the appropriate fragment based on the position
        switch (position) {
            case 0:
                return new OnboardingLandingFragment();
            case 1:
                return new OnboardingScreen1Fragment();
            case 2:
                return new OnboardingScreen2Fragment();
            case 3:
                return new OnboardingScreen3Fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Return the total number of onboarding screens
        return NUM_PAGES_WITH_PROGRESS_BAR + 1; // Added 1 for the onboarding landing screen
    }
}


