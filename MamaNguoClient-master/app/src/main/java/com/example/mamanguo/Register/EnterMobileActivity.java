package com.example.mamanguo.Register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mamanguo.R;
import com.example.mamanguo.helpers.Constants;
import com.example.mamanguo.helpers.CountryData;

public class EnterMobileActivity extends AppCompatActivity {

    private static final String TAG = EnterMobileActivity.class.getSimpleName();
    private Button btn_confirmPhone;
    private EditText input_phoneNumber;
    private Spinner countriesSpinner;
    private EditText countryCode;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile);
        btn_confirmPhone = findViewById(R.id.btn_confirmPhone);
        input_phoneNumber = findViewById(R.id.input_phone_number);
        countryCode = findViewById(R.id.country_code);

        countryCode.setFocusable(false);

        //initialize spinner
        countriesSpinner = findViewById(R.id.spinnerCountries);
        countriesSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));
        //Set default spinner value and country code
        ArrayAdapter spinnerAdapter = (ArrayAdapter) countriesSpinner.getAdapter();
        int position = spinnerAdapter.getPosition(Constants.DEFAULT_COUNTRY);
        countriesSpinner.setSelection(position);

        getMyExtras();

        btn_confirmPhone.setOnClickListener(v -> {
            String countryCode = CountryData.countryAreaCodes[countriesSpinner.getSelectedItemPosition()];
            String numberEntered = input_phoneNumber.getText().toString();
            String number = numberEntered.length() > 9 ? numberEntered.substring(1,10): numberEntered;
            String phoneNumber = String.format("+%s%s", countryCode, number);

            Log.d(TAG, "onCreate: "+phoneNumber);

            if (numberEntered.length() >= 9 && numberEntered.length() < 11) {
                createIntent(phoneNumber);
            } else {
                Log.d(TAG, String.format("onCreate > onClickListener: length: %d", phoneNumber.length()));
                input_phoneNumber.setError("Enter a valid mobile number");
                input_phoneNumber.requestFocus();
            }
        });

        countriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryCode.setText(String.format("+%s", CountryData.countryAreaCodes[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getMyExtras() {
        Bundle extras = getIntent().getExtras();
        firstName = extras.getString("firstName");
        lastName = extras.getString("lastName");
        email = extras.getString("email");
        password = extras.getString("password");
    }


    private void createIntent(String phoneNumber) {
        Bundle extras = new Bundle();
        extras.putString("phoneNumber", phoneNumber);
        extras.putString("firstName", firstName);
        extras.putString("lastName", lastName);
        extras.putString("email", email);
        extras.putString("password", password);
        Intent intent = new Intent(this, PinVerifyActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
