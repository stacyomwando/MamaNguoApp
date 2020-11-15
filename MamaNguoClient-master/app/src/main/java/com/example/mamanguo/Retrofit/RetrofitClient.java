package com.example.mamanguo.Retrofit;

import com.example.mamanguo.helpers.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {

    private static Retrofit retrofit;

//Define the base URL//

    private static final String BASE_URL = Constants.BASE_URL;

//Create the Retrofit instance//

    public static Retrofit createRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //Add the converter//
                    .addConverterFactory(GsonConverterFactory.create())
                    //Build the Retrofit instance//
                    .build();
        }
        return retrofit;
    }

    public static MamaNguoApi getRetrofitInstance() {
        return RetrofitClient.createRetrofitInstance().create(MamaNguoApi.class);
    }
}
