package com.example.mamanguo.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mamanguo.R;
import com.example.mamanguo.Retrofit.MamaNguoApi;
import com.example.mamanguo.Retrofit.RetrofitClient;
import com.example.mamanguo.Retrofit.Models.User;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mamanguo.helpers.Constants.EMAIL;
import static com.example.mamanguo.helpers.Constants.FIRST_NAME;
import static com.example.mamanguo.helpers.Constants.LAST_NAME;
import static com.example.mamanguo.helpers.Constants.PHONE_NUMBER;
import static com.example.mamanguo.helpers.Constants.USER_DATA;
import static com.example.mamanguo.helpers.Constants.USER_ID;

public class ProfileEditActivity extends AppCompatActivity {

    Context mContext;
    private EditText firstName_input;
    private EditText lastName_input;
    private EditText email_input;
    private EditText phoneNumber_input;
    private Button btnUpdate;

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private static String TAG = ProfileEditActivity.class.getSimpleName();
    private MamaNguoApi retrofitInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        retrofitInstance = RetrofitClient.getRetrofitInstance();
        firstName_input = findViewById(R.id.input_firstName);
        lastName_input = findViewById(R.id.input_lastName);
        email_input = findViewById(R.id.input_email);
        phoneNumber_input = findViewById(R.id.input_phone_number);
        btnUpdate = findViewById(R.id.btn_update);

        btnUpdate.setOnClickListener(v -> updateProfile(userId,firstName,lastName,phoneNumber,email));
        getUserData();
    }

    private void getUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        userId = sharedPreferences.getInt(USER_ID, 0);
        firstName = sharedPreferences.getString(FIRST_NAME, "");
        lastName = sharedPreferences.getString(LAST_NAME, "");
        email = sharedPreferences.getString(EMAIL, "");
        phoneNumber = sharedPreferences.getString(PHONE_NUMBER, "");
        updateViews();
    }

    private void updateViews() {
        firstName_input.setText(firstName);
        lastName_input.setText(lastName);
        email_input.setText(email);
        phoneNumber_input.setText(phoneNumber);
    }

    private void updateProfile(int userId,String firstName, String lastName, String phoneNumber, String email) {
        User user = new User(userId,firstName,lastName, phoneNumber, email);
        Call<User> call = retrofitInstance.updateProfile(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ProfileEditActivity.this, "Something went wrong. Please try again later.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, Objects.requireNonNull(response.body()).getMessage());
                    return;
                }

                if(Objects.requireNonNull(response.body()).getStatus()) {
                    //Store user data in shared preferences
                    Toast.makeText(ProfileEditActivity.this, "Ypetails", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}

