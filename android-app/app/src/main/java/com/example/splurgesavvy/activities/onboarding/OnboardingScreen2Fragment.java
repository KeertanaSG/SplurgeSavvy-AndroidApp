package com.example.splurgesavvy.activities.onboarding;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.Intent;
import android.widget.RelativeLayout;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.activities.login.LoginPasswordActivity;

public class OnboardingScreen2Fragment extends Fragment {

    public OnboardingScreen2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.onboarding_screen2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RelativeLayout signUpButton = view.findViewById(R.id.button_signup);
        RelativeLayout loginButton = view.findViewById(R.id.button_login);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on the "Sign Up" button in OnboardingScreen1Fragment
                navigateToSignUpActivity();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on the "Login" button
                navigateToLoginActivity();
            }
        });
    }

    private void navigateToSignUpActivity() {
        // Start SignUpActivity when "Sign Up" button is clicked
        Intent intent = new Intent(requireContext(), SignUpActivity.class);
        startActivity(intent);
    }

    private void navigateToLoginActivity() {
        // Start LoginActivity when "Login" button is clicked
        Intent intent = new Intent(requireContext(), LoginPasswordActivity.class);
        startActivity(intent);
    }


}
