package com.example.mamanguo.ui.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mamanguo.R;
import com.example.mamanguo.Retrofit.MamaNguoApi;
import com.example.mamanguo.Retrofit.RequestedService;
import com.example.mamanguo.Retrofit.RetrofitClient;
import com.example.mamanguo.chooseServices.helperClasses.Order;
import com.example.mamanguo.helpers.SummaryTable;

import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mamanguo.chooseServices.helperClasses.Order.getBillTotal;
import static com.example.mamanguo.chooseServices.helperClasses.Order.orderItems;
import static com.example.mamanguo.helpers.Constants.USER_DATA;
import static com.example.mamanguo.helpers.Constants.USER_ID;

public class CheckoutActivity extends AppCompatActivity {
    //Class that contains methods to populate the table layout dynamically
    SummaryTable summaryTable = new SummaryTable(this);
    private MamaNguoApi retrofitInstance;
    private static final String TAG = CheckoutActivity.class.getSimpleName();
    private int mamanguoId, clientId;
    String description, quantity, cost;
    double longitude, latitude;
    String location;
    int totalCost;
    private Button makeRequestBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_);
        retrofitInstance = RetrofitClient.getRetrofitInstance();
        makeRequestBtn = findViewById(R.id.make_request_btn);
        initVariables();

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        //Attach the rows
        for(int i = 0; i<orderItems.length; i ++) {
            String item = orderItems[i];
            String unitPrice = Integer.toString(Order.unitPrice.get(i));
            String quantity = Integer.toString(Order.orderQuantity.get(i));
            String subtotal = Integer.toString(Order.orderSubtotal.get(i));
            TableRow newRow = summaryTable.createRow(item, unitPrice, quantity, subtotal);
            tableLayout.addView(newRow);
        }
        TableRow grandTotalRow = summaryTable.grandTotalRow(getBillTotal());
        tableLayout.addView(grandTotalRow);

        makeRequestBtn.setOnClickListener(v -> {
            makeRequest(mamanguoId, clientId, description, quantity, cost,
                    longitude, latitude, location, totalCost);
        });
    }

    private void initVariables() {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_DATA, MODE_PRIVATE);
        clientId = sharedPreferences.getInt(USER_ID, 0);
        mamanguoId = Order.getMamanguoId();
        description = Order.createStringFromArray(orderItems);
        quantity = Order.createStringFromList(Order.orderQuantity);
        cost = Order.createStringFromList(Order.orderSubtotal);
        longitude = Order.getLongitude();
        latitude = Order.getLatitude();
        location = Order.getLocation();
        totalCost = Order.getBillTotal();
        Log.d(TAG, String.format("initVariables: Quantity string: %s", quantity));
        Log.d(TAG, String.format("initVariables: Location: %s", location));
    }

    //userId = mamanguoId, requesteeId = (client) userId
    private void makeRequest(int userId, int requesteeId, String description,
                             String quantity, String cost, double longitude,
                             double latitude, String location, int totalCost) {
        RequestedService requestedService = new RequestedService(userId, requesteeId,
                description, quantity, cost, longitude, latitude, location, totalCost);
        Call<RequestedService> call = retrofitInstance.makeRequest(requestedService);

        call.enqueue(new Callback<RequestedService>() {
            @Override
            public void onResponse(Call<RequestedService> call, Response<RequestedService> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(CheckoutActivity.this, "Something went wrong. Please try again later.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, Objects.requireNonNull(response.body()).getMessage());

                } else {
                    //Store user data in shared preferences
                    Intent intent = new Intent(CheckoutActivity.this, BottomNavActivity.class);
                    intent.putExtra("ORDER_COMPLETE", true);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<RequestedService> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }


}
