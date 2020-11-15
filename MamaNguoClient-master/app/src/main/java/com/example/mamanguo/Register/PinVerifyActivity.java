package com.example.mamanguo.Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.example.mamanguo.R;
import com.example.mamanguo.Retrofit.MamaNguoApi;
import com.example.mamanguo.Retrofit.RetrofitClient;
import com.example.mamanguo.Retrofit.Models.User;
import com.example.mamanguo.helpers.UIFeatures;
import com.example.mamanguo.sharedPreferences.UserData;
import com.example.mamanguo.ui.Activities.BottomNavActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mamanguo.helpers.Constants.USER_DATA;

public class PinVerifyActivity extends AppCompatActivity {

    private static final String TAG = PinVerifyActivity.class.getSimpleName();
    private PinEntryEditText pinEntry;
    private TextView resendLink;
    private String codeSent;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private MamaNguoApi retrofitInstance;

    //User data
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_verify);
        pinEntry = findViewById(R.id.txt_pin_entry);
        resendLink = findViewById(R.id.resend_link);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        retrofitInstance = RetrofitClient.createRetrofitInstance().create(MamaNguoApi.class);

        try {
            Bundle extras = getIntent().getExtras();
            firstName = extras.getString("firstName");
            lastName = extras.getString("lastName");
            phoneNumber = extras.getString("phoneNumber");
            email = extras.getString("email");
            password = extras.getString("password");

            sendVerificationCode(phoneNumber);
            Toast.makeText(this, firstName
                    , Toast.LENGTH_SHORT).show();
            Log.d(TAG, String.format("Phone number is %s", phoneNumber));
            /*Toast.makeText(this, "Sending verification code. Please wait...", Toast.LENGTH_SHORT).show();*/
            ProgressDialog progressDialog = UIFeatures.showProgressDialog(this, "Sending verification code...");
            UIFeatures.dismissProgressDialog(progressDialog);

        } catch (NullPointerException e) {
            Log.e(TAG, e.getMessage());

        }


        if (pinEntry != null) {
            /*pinEntry.setTypeface(ResourcesCompat.getFont(this, R.font.charmonman_regular));*/
            pinEntry.setOnPinEnteredListener(str -> {
                String pinEntered = str.toString();
                if (pinEntered.length() == 6) {
                    verifyPin(pinEntered);
                }
            });
        }

        resendLink.setOnClickListener(v -> {
            resendVerificationCode(phoneNumber, mResendToken);
        });
    }

    private void addUser() {
        User user = new User(firstName, lastName, phoneNumber, email, password);
        Call<User> call = retrofitInstance.addUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(PinVerifyActivity.this, "Something went wrong. Please try again later.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, Objects.requireNonNull(response.body()).getMessage());

                } else {
                    //Store user data in shared preferences
                    userId = Objects.requireNonNull(response.body()).getUserId();
                    onSignUpSuccess();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }


    private void saveUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_DATA, MODE_PRIVATE);
        UserData userData = new UserData(userId, firstName, lastName, email, phoneNumber);
        userData.saveUserData(sharedPreferences);
    }

    private void onSignUpSuccess() {
        saveUserData();
        setResult(RESULT_OK, null);
        Intent intent = new Intent(this, BottomNavActivity.class);
        startActivity(intent);
    }


    private void verifyPin(String pinEntered) {
        Log.d(TAG, String.format("verifyPin: %s", pinEntered));
        progressBar.setVisibility(View.VISIBLE);

        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, pinEntered);
            signInWithPhoneAuthCredential(credential);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {

                    //If the pin is verified successfully
                    progressBar.setVisibility(View.GONE);

                    try {
                        if (task.isSuccessful()) {
                            Toast.makeText(PinVerifyActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                            //Register the user
                            addUser();
                        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(PinVerifyActivity.this, "INCORRECT", Toast.LENGTH_SHORT).show();
                            pinEntry.setError(true);
                            pinEntry.postDelayed(() -> pinEntry.setText(null), 1000);
                        }

                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }

                });

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(PinVerifyActivity.this, "Verification error. Please try again.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            Log.d(TAG, "onCodeSent: Sent verification code");
            progressBar.setVisibility(View.GONE);
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
            mResendToken = forceResendingToken;
        }
    };

    //Resends verification code
    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }


}

