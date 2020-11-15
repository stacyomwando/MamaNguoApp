package com.example.mamanguo.chooseServices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mamanguo.R;
import com.example.mamanguo.chooseMamaNguo.DummyChooseMamaNguoActivity;
import com.example.mamanguo.getAvailableMamaNguo.ChooseMamaNguoActivity;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mamanguo.chooseServices.ui.main.SectionsPagerAdapter;

import static com.example.mamanguo.chooseServices.helperClasses.Order.getBillTotal;
import static com.example.mamanguo.chooseServices.helperClasses.Order.orderItems;
import static com.example.mamanguo.chooseServices.helperClasses.Order.orderQuantity;
import static com.example.mamanguo.chooseServices.helperClasses.Order.orderSubtotal;
import static com.example.mamanguo.chooseServices.helperClasses.Order.unitPrice;

public class ServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    public void createIntent(View view) {
        //Exit function if no items have been selected
        if(orderItems != null) {
            if(orderItems.length<1) {
                Toast.makeText(this, "Pick at least one item", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(this, "Pick at least one item", Toast.LENGTH_SHORT).show();
            return;
        }


        Bundle extras = new Bundle();
        extras.putStringArray("ORDER_ITEMS", orderItems);
        extras.putIntegerArrayList("UNIT_PRICE", unitPrice);
        extras.putIntegerArrayList("ORDER_QUANTITY", orderQuantity);
        extras.putIntegerArrayList("ORDER_SUBTOTAL", orderSubtotal);
        extras.putInt("BILL_TOTAL", getBillTotal());
        Intent intent = new Intent(this, ChooseMamaNguoActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

}