package com.example.mamanguo.ui.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.mamanguo.R;
import com.example.mamanguo.ui.Fragments.HomeFragment;
import com.example.mamanguo.ui.Fragments.OrdersFragment;
import com.example.mamanguo.ui.Fragments.ProfileEditFragment;
import com.example.mamanguo.ui.Fragments.ProfileOptionsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.MenuItem;

public class BottomNavActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);

        //Set the default fragment
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            if(extras.containsKey("ORDER_COMPLETE")) {
                loadFragment(new OrdersFragment());
                navView.setSelectedItemId(R.id.navigation_orders);
            }
        } else {
            loadFragment(new HomeFragment());
            navView.setSelectedItemId(R.id.navigation_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;
            case R.id.navigation_orders:
                fragment = new OrdersFragment();
                break;
            case R.id.navigation_profile:
                fragment = new ProfileOptionsFragment();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
