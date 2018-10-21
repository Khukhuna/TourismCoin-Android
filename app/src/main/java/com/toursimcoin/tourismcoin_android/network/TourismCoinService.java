package com.toursimcoin.tourismcoin_android.network;

import com.toursimcoin.tourismcoin_android.model.ApiResponse;
import com.toursimcoin.tourismcoin_android.model.Credentials;
import com.toursimcoin.tourismcoin_android.model.Sightseeing;
import com.toursimcoin.tourismcoin_android.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TourismCoinService {
    @POST("/login")
    Call<ApiResponse> login(@Body Credentials credentials);

    @GET("/profiles/{user}")
    Call<User> getProfile(@Path("user") String user);

    @GET("/sightseeing")
    Call<List<Sightseeing>> getSightseeings();
}