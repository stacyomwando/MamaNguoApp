package com.example.mamanguo.ui.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mamanguo.R;
import com.example.mamanguo.ui.Activities.LoginActivity;
import com.example.mamanguo.ui.Activities.ProfileEditActivity;

import static com.example.mamanguo.helpers.Constants.USER_DATA;
import static com.example.mamanguo.helpers.Constants.USER_ID;


public class ProfileOptionsFragment extends Fragment {
    private static final String TAG = "ProfileOptionsFragment";
    private CardView profileCardView;
    private CardView logoutCardView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_profile_options, container, false);
        profileCardView = RootView.findViewById(R.id.profile_cardView);
        logoutCardView = RootView.findViewById(R.id.logout_cardView);
        mContext = getActivity();

        profileCardView.setOnClickListener(v -> createIntent());
        logoutCardView.setOnClickListener(v -> logout());
        return RootView;
    }

    private void logout() {
        SharedPreferences settings = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        settings.edit().remove(USER_ID).apply();

        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
    }


    private void createIntent() {
        Intent intent = new Intent(mContext, ProfileEditActivity.class);
        startActivity(intent);
    }


}
