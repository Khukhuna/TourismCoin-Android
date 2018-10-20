package com.toursimcoin.tourismcoin_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.toursimcoin.tourismcoin_android.UI.MainActivity;
import com.toursimcoin.tourismcoin_android.heplers.SharedPrefsUtil;
import com.toursimcoin.tourismcoin_android.model.ApiResponse;
import com.toursimcoin.tourismcoin_android.model.Credentials;
import com.toursimcoin.tourismcoin_android.model.User;
import com.toursimcoin.tourismcoin_android.network.TourismCoinService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    TourismCoinService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://intense-brushlands-86787.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(TourismCoinService.class);

        signIn("khabib1231", "khabib123");

    }

    public void signIn(String username, String password){
        Credentials credentials = new Credentials(username, password);
        service.login(credentials).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if(response.body() != null){
                    getUser(response.body().message);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getUser(String username){
        final Context context = this;
        service.getProfile(username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if(response.body() != null){
                    User user = response.body();
                    SharedPrefsUtil.setStringPreference(context, "username", user.getUsername());
                    SharedPrefsUtil.setStringPreference(context, "email", user.getEmail());
                    SharedPrefsUtil.setStringPreference(context, "first_name", user.getFirstName());
                    SharedPrefsUtil.setStringPreference(context, "last_name", user.getLastName());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
