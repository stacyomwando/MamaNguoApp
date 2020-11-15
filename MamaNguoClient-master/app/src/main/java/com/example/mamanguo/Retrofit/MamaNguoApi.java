package com.example.mamanguo.Retrofit;

import com.example.mamanguo.Retrofit.Models.MamaNguo;
import com.example.mamanguo.Retrofit.Models.Rating;
import com.example.mamanguo.Retrofit.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface MamaNguoApi {

    //Display available Mamanguo
    @GET("getAvailableMamaNguo")
    Call<List<MamaNguo>> getMamaNguo();

    //Display user's history of Mamanguo
    @GET("getHistory/{userId}")
    Call<List<MamaNguo>> getHistory(int userId);

    //Registration validation
    @POST("emailExists")
    Call <User> emailExists(String email);

    //Registration
    @POST("addUser")
    Call <User> addUser(@Body User user);

    //Login
    @POST("userLogin")
    Call <User> userLogin(@Body User user);

    //Update profile
    @PUT("updateProfile")
    Call <User> updateProfile(@Body User user);

    //Make request
    @POST("makeRequest")
    Call <RequestedService> makeRequest(@Body RequestedService reqService);

    //Add rating
    @POST("addRating")
    Call <Rating> addRating(@Body Rating rating);
}
