package com.example.mamanguo.ui.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mamanguo.R;
import com.example.mamanguo.chooseServices.ServicesActivity;

public class HomeFragment extends Fragment {

    private CardView order_cardView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_home, container, false);
        order_cardView = RootView.findViewById(R.id.profile_cardView);
        mContext = getActivity();

        order_cardView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ServicesActivity.class);
            startActivity(intent);
        });
        return RootView;
    }
}