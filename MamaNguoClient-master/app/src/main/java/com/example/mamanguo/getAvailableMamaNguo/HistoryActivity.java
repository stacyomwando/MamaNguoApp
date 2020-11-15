/*
package com.example.mamanguo.getAvailableMamaNguo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.mamanguo.R;
import com.example.mamanguo.Retrofit.MamaNguoApi;
import com.example.mamanguo.Retrofit.RetrofitClient;
import com.example.mamanguo.ui.Activities.MapViewActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    private MyAdapter myAdapter;
    private RecyclerView myRecyclerView;
    private static String TAG = ChooseMamaNguoActivity.class.getSimpleName();
    private Button btn_history;
    private List<MamaNguo> listData;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_recycler);
        btn_history = findViewById(R.id.button_viewHistory);
        mContext = HistoryActivity.this;

        //Create a handler for the RetrofitInstance interface//
        MamaNguoApi retrofitInstance = RetrofitClient.createRetrofitInstance().create(MamaNguoApi.class);
        Call<List<MamaNguo>> call = retrofitInstance.getHistory(1);

        //Execute the request asynchronously//
        call.enqueue(new Callback<List<MamaNguo>>() {
            @Override
            //Handle a successful response//
            public void onResponse(Call<List<MamaNguo>> call, Response<List<MamaNguo>> response) {
                Log.d(TAG, "onResponse: Response Received");
                listData = response.body();

                if(listData == null) {
                    //Display that the user has no history
                    return;
                }
                loadDataList(listData);
            }

            @Override
            //Handle execution failures//
            public void onFailure(Call<List<MamaNguo>> call, Throwable throwable) {
                //If the request fails, then display the following toast//
                Log.d(TAG, String.format("onFailure: %s", throwable));
                Toast.makeText(mContext, "Unable to load users", Toast.LENGTH_SHORT).show();
            }
        });
        attachListener();
    }

    //Display the retrieved data as a list//
    private void loadDataList(List<MamaNguo> usersList) {
        //Get a reference to the RecyclerView//
        myRecyclerView = findViewById(R.id.myRecyclerView);
        myAdapter = new MyAdapter(usersList,this);

        //Use a LinearLayoutManager with default vertical orientation//
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        myRecyclerView.setLayoutManager(layoutManager);

        //Set the Adapter to the RecyclerView//
        myRecyclerView.setAdapter(myAdapter);
    }

    private void attachListener() {
        */
/* On click listeners: *//*

        btn_history.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HistoryActivity.class);
            startActivity(intent);
        });

        myAdapter.setOnItemClickListener(position -> {
            String itemSelected = listData.get(position).getFullName();
            Toast.makeText(this, itemSelected, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onCardClick(int position) {
        Intent intent = new Intent(this, MapViewActivity.class);
        startActivity(intent);
    }
}
*/
