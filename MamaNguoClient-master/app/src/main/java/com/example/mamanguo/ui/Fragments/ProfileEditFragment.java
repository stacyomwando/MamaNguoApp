package com.example.mamanguo.ui.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mamanguo.R;

import static com.example.mamanguo.helpers.Constants.EMAIL;
import static com.example.mamanguo.helpers.Constants.FIRST_NAME;
import static com.example.mamanguo.helpers.Constants.LAST_NAME;
import static com.example.mamanguo.helpers.Constants.PHONE_NUMBER;
import static com.example.mamanguo.helpers.Constants.USER_DATA;


public class ProfileEditFragment extends Fragment {

    Context mContext;
    private EditText firstName_input;
    private EditText lastName_input;
    private EditText email_input;
    private EditText phoneNumber_input;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_profile_options, container, false);
        firstName_input = RootView.findViewById(R.id.input_firstName);
        lastName_input = RootView.findViewById(R.id.input_lastName);
        email_input = RootView.findViewById(R.id.input_email);
        phoneNumber_input = RootView.findViewById(R.id.input_phone_number);
        mContext = getActivity();
        getUserData();
        return RootView;
    }


    private void getUserData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        firstName = sharedPreferences.getString(FIRST_NAME, "");
        lastName = sharedPreferences.getString(LAST_NAME, "");
        email = sharedPreferences.getString(EMAIL, "");
        phoneNumber = sharedPreferences.getString(PHONE_NUMBER, "");
        updateViews();
    }

    private void updateViews() {
        /*firstName_input.setText(firstName);
        lastName_input.setText(lastName);
        email_input.setText(email);
        phoneNumber_input.setText(phoneNumber);*/
        Toast.makeText(mContext, firstName, Toast.LENGTH_SHORT).show();
    }

}
