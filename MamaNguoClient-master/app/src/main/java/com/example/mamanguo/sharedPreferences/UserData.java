package com.example.mamanguo.sharedPreferences;

import android.content.SharedPreferences;

import static com.example.mamanguo.helpers.Constants.EMAIL;
import static com.example.mamanguo.helpers.Constants.FIRST_NAME;
import static com.example.mamanguo.helpers.Constants.LAST_NAME;
import static com.example.mamanguo.helpers.Constants.PHONE_NUMBER;
import static com.example.mamanguo.helpers.Constants.USER_ID;

public class UserData {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public UserData(int userId, String firstName, String lastName, String email, String phoneNumber) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void saveUserData(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID, this.userId);
        editor.putString(FIRST_NAME, this.firstName);
        editor.putString(LAST_NAME, this.lastName);
        editor.putString(EMAIL, this.email);
        editor.putString(PHONE_NUMBER, this.phoneNumber);
        editor.apply();
    }

}
