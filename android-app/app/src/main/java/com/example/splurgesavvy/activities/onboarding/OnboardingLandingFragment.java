package com.example.splurgesavvy.activities.onboarding;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.splurgesavvy.R;

public class OnboardingLandingFragment extends Fragment {

    public OnboardingLandingFragment() {
        // Required empty public constructor
    }

    public interface OnboardingInteractionListener {
        void onSignUpClicked();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.onboarding_landing, container, false);
        View imageViewLogo = rootView.findViewById(R.id.splurgesavv);
        View viewRectangle = rootView.findViewById(R.id.rectangle_2);
        View imageViewBusiness = rootView.findViewById(R.id.business_an);

        return rootView;
    }
}



