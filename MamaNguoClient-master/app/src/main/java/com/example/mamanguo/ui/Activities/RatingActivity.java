package com.example.mamanguo.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.mamanguo.R;
import com.example.mamanguo.Retrofit.MamaNguoApi;
import com.example.mamanguo.Retrofit.Models.Rating;
import com.example.mamanguo.Retrofit.RetrofitClient;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mamanguo.helpers.Constants.USER_DATA;
import static com.example.mamanguo.helpers.Constants.USER_ID;

public class RatingActivity extends AppCompatActivity {

    private EditText mamanguoNameText;
    private String mamanguoName;
    private RatingBar ratingBar;
    private int userId;
    private int mamanguoId;
    private String commentText;
    private Button buttonRate;


    private static final String TAG = RatingActivity.class.getSimpleName();
    private MamaNguoApi retrofitInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Bundle extras = getIntent().getExtras();
        retrofitInstance = RetrofitClient.getRetrofitInstance();

        SharedPreferences sharedPreferences = getSharedPreferences(USER_DATA, MODE_PRIVATE);
        userId = sharedPreferences.getInt(USER_ID, 0);
        if(extras != null) {
            mamanguoId = extras.getInt("MAMANGUO_ID");
            mamanguoName = extras.getString("MAMANGUO_NAME");
        }

        commentText = findViewById(R.id.input_comment);
        mamanguoNameText = findViewById(R.id.input_mamanguo_name);
        ratingBar = findViewById(R.id.rating_bar);

        buttonRate.setOnClickListener(v -> addRating(
                userId, mamanguoId, ratingBar.getNumStars(), commentText.getText().toString()));

    }


    private void addRating(int userId, int mamanguoId, int ratingValue, String comment) {
        Rating rating = new Rating(userId, mamanguoId, ratingValue, comment);
        Call<Rating> call = retrofitInstance.addRating(rating);

        call.enqueue(new Callback<Rating>() {
            @Override
            public void onResponse(Call<Rating> call, Response<Rating> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(RatingActivity.this, "Something went wrong. Please try again later.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, Objects.requireNonNull(response.body()).getMessage());
                } else {
                    Intent intent = new Intent(RatingActivity.this, BottomNavActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Rating> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }


}
