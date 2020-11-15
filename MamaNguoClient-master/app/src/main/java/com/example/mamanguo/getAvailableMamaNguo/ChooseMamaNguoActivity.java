package com.example.mamanguo.getAvailableMamaNguo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mamanguo.R;
import com.example.mamanguo.Retrofit.Models.MamaNguo;
import com.example.mamanguo.Retrofit.MamaNguoApi;
import com.example.mamanguo.Retrofit.RetrofitClient;
import com.example.mamanguo.chooseServices.helperClasses.Order;
import com.example.mamanguo.ui.Activities.MapViewActivity;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseMamaNguoActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    private MyAdapter myAdapter;
    private RecyclerView myRecyclerView;
    private static String TAG = ChooseMamaNguoActivity.class.getSimpleName();
    private Button btn_history;
    private List<MamaNguo> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mamanguo);
        btn_history = findViewById(R.id.button_viewHistory);

        //Create a handler for the RetrofitInstance interface//
        MamaNguoApi retrofitInstance = RetrofitClient.createRetrofitInstance().create(MamaNguoApi.class);
        Call<List<MamaNguo>> call = retrofitInstance.getMamaNguo();

        //Execute the request asynchronously//
        call.enqueue(new Callback<List<MamaNguo>>() {
            @Override
            //Handle a successful response//
            public void onResponse(Call<List<MamaNguo>> call, Response<List<MamaNguo>> response) {
                Log.d(TAG, "onResponse: Response Received");
                listData = response.body();
                loadDataList(listData);
                attachListeners();
            }

            @Override
            //Handle execution failures//
            public void onFailure(Call<List<MamaNguo>> call, Throwable throwable) {
                //If the request fails, then display the following toast//
                Log.e(TAG, String.format("onFailure: %s", throwable));
                Toast.makeText(ChooseMamaNguoActivity.this, "Unable to load users", Toast.LENGTH_SHORT).show();
            }
        });
        attachListeners();
    }

    //Display the retrieved data as a list//
    private void loadDataList(List<MamaNguo> usersList) {
        //Get a reference to the RecyclerView//
        myRecyclerView = findViewById(R.id.recyclerView);
        myAdapter = new MyAdapter(usersList,this);

        //Use a LinearLayoutManager with default vertical orientation//
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChooseMamaNguoActivity.this);
        myRecyclerView.setLayoutManager(layoutManager);

        //Set the Adapter to the RecyclerView//
        myRecyclerView.setAdapter(myAdapter);
    }

    private void attachListeners() {
        /* On click listeners: */
       /* btn_history.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HistoryActivity.class);
            startActivity(intent);
        });*/
    }

    @Override
    public void onCardClick(int position) {
        int mamanguoSelected = listData.get(position).getMamanguoId();
        Order.mamanguoId = mamanguoSelected;
        Toast.makeText(this, String.format(Locale.getDefault(), "Mamanguo: %d", mamanguoSelected), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MapViewActivity.class);
        startActivity(intent);
    }
}