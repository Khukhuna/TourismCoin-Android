package com.toursimcoin.tourismcoin_android.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.toursimcoin.tourismcoin_android.R;
import com.toursimcoin.tourismcoin_android.heplers.Constants;
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

    Button signInBtn;

    TextInputEditText username;
    TextInputEditText password;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://intense-brushlands-86787.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(TourismCoinService.class);

        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);

        signInBtn = findViewById(R.id.sign_in);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(username.getText().toString(), password.getText().toString());
                changeButtonStatus(false);
            }
        });
        

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
                changeButtonStatus(true);
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
                    SharedPrefsUtil.setStringPreference(context, Constants.USERNAME, user.getUsername());
                    SharedPrefsUtil.setStringPreference(context, Constants.MAIL, user.getEmail());
                    SharedPrefsUtil.setStringPreference(context, Constants.FIRSTNAME, user.getFirstName());
                    SharedPrefsUtil.setStringPreference(context, Constants.LASTNAME, user.getLastName());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
                changeButtonStatus(true);
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                changeButtonStatus(true);
            }
        });
    }

    public void changeButtonStatus(boolean status){
        signInBtn.setClickable(status);
        if(!status){
            dialog = ProgressDialog.show(this , "", "Loading. Please wait...", false);
        }else{
            dialog.dismiss();
        }
    }
}
