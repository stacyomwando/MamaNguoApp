package com.example.mamanguo.ui.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mamanguo.R;
import com.example.mamanguo.Register.SignUpActivity;
import com.example.mamanguo.Retrofit.MamaNguoApi;
import com.example.mamanguo.Retrofit.RetrofitClient;
import com.example.mamanguo.Retrofit.Models.User;
import com.example.mamanguo.helpers.UIFeatures;
import com.example.mamanguo.sharedPreferences.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mamanguo.helpers.Constants.USER_DATA;
import static com.example.mamanguo.helpers.Constants.USER_ID;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText emailText;
    EditText passwordText;
    Button loginButton;
    TextView signUpLink;
    Context mContext;
    MamaNguoApi retrofitInstance;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(isLoggedIn()) {
            Intent intent = new Intent(this, BottomNavActivity.class);
            startActivity(intent);
        }

        retrofitInstance = RetrofitClient.createRetrofitInstance().create(MamaNguoApi.class);
        mContext = LoginActivity.this;
        initComponents();
        attachListeners();

    }

    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_DATA, MODE_PRIVATE);
        return sharedPreferences.contains(USER_ID);
    }


    private void initComponents() {
        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        signUpLink = findViewById(R.id.link_signup);
    }

    public void login() {
        Log.d(TAG, "Login");

        if (validate()) {
            progressDialog = UIFeatures.showProgressDialog(mContext, "Authenticating...");
            loginButton.setEnabled(false);
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            userLogin(email, password);
            UIFeatures.dismissProgressDialog(progressDialog);
        }

    }


    private void userLogin(String email, String password) {
        User user = new User(email, password);
        Call<User> call = retrofitInstance.userLogin(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User userResponse = response.body();
                if (userResponse.getStatus()) {
                    onLoginSuccess(userResponse);
                } else {
                    onLoginFailed(userResponse.getMessage(), userResponse.getTarget());
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                loginButton.setEnabled(true);
                Toast.makeText(LoginActivity.this, "Check your internet connection and try again", Toast.LENGTH_SHORT).show();
            }
        });
        UIFeatures.dismissProgressDialog(progressDialog);
    }

    private void onLoginSuccess(User user) {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_DATA, MODE_PRIVATE);
        UserData userData = new UserData(user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber());
        userData.saveUserData(sharedPreferences);

        loginButton.setEnabled(true);
        Bundle extras = new Bundle();
        extras.putString("firstName", user.getFirstName());
        extras.putString("lastName", user.getLastName());
        extras.putString("email", user.getEmail());
        extras.putString("phoneNumber", user.getPhoneNumber());
        Intent intent = new Intent(mContext, BottomNavActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    private void onLoginFailed(String message, String target) {
        Log.d(TAG, String.format("onLoginFailed: %s", target));
        loginButton.setEnabled(true);

        //Set the error message
        switch (target) {
            case "username":
                emailText.setError(message);
                break;
            case "password":
                passwordText.setError(message);
                break;
            default:
                Toast.makeText(mContext, "Invalid login credentials", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty()) {
            passwordText.setError("A password is required");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    public void attachListeners() {
        loginButton.setOnClickListener(v -> login());
        signUpLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
    }
}
