package com.toursimcoin.tourismcoin_android.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.toursimcoin.tourismcoin_android.R;
import com.toursimcoin.tourismcoin_android.adapters.SightseeingAdapter;
import com.toursimcoin.tourismcoin_android.heplers.CoinsListener;
import com.toursimcoin.tourismcoin_android.heplers.Constants;
import com.toursimcoin.tourismcoin_android.heplers.SharedPrefsUtil;
import com.toursimcoin.tourismcoin_android.model.QRStatus;
import com.toursimcoin.tourismcoin_android.model.Sightseeing;
import com.toursimcoin.tourismcoin_android.network.TourismCoinService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.mrasif.libs.easyqr.EasyQR;
import in.mrasif.libs.easyqr.QRScanner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CoinsListener {

    TextView nameLabel;
    TextView emailLabel;

    private RecyclerView mRecyclerView;
    private SightseeingAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Toolbar toolbar;

    SeekBar level_bar;
    TextView level_label;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        level_bar = headerView.findViewById(R.id.navbar_level);
        level_label = headerView.findViewById(R.id.level_indic);
        level_bar.setProgress(85);
        level_bar.setEnabled(true);

        mRecyclerView = findViewById(R.id.ccrecycler);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new SightseeingAdapter(new ArrayList<Sightseeing>());
        mRecyclerView.setAdapter(mAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://shielded-bastion-73886.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TourismCoinService service = retrofit.create(TourismCoinService.class);

        service.getSightseeings().enqueue(new Callback<List<Sightseeing>>() {
            @Override
            public void onResponse(@NonNull Call<List<Sightseeing>> call, @NonNull Response<List<Sightseeing>> response) {
                List<Sightseeing> data = response.body();
                if(data != null){
                    mAdapter.addItems(data);
                    Toast.makeText(MainActivity.this, data.get(0).description, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Sightseeing>> call, @NonNull Throwable t) {

            }
        });


        nameLabel = headerView.findViewById(R.id.navbar_name);
        emailLabel = headerView.findViewById(R.id.navbar_mail);
        String name = "";
        name += SharedPrefsUtil.getStringPreference(this, "first_name") + " " + SharedPrefsUtil.getStringPreference(this, "last_name");
        nameLabel.setText(name);
        emailLabel.setText(SharedPrefsUtil.getStringPreference(this, "email"));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        findViewById(R.id.main_qr_wrapper).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR();
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_scanner){
            scanQR();
        }else if(id == R.id.nav_signOut){
            signOut();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void signOut(){
        SharedPrefsUtil.setStringPreference(this, Constants.FIRSTNAME, "");
        SharedPrefsUtil.setStringPreference(this, Constants.LASTNAME, "");
        SharedPrefsUtil.setStringPreference(this, Constants.MAIL, "");
        SharedPrefsUtil.setStringPreference(this, Constants.USERNAME, "");

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void scanQR(){
        Intent intent=new Intent(MainActivity.this, QRScanner.class);
        startActivityForResult(intent, EasyQR.QR_SCANNER_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case EasyQR.QR_SCANNER_REQUEST: {
                if (resultCode==RESULT_OK){
                    Gson gson = new Gson();
                    QRStatus status = gson.fromJson(data.getStringExtra(EasyQR.DATA), QRStatus.class);
                    mAdapter.checkItem(status.getTitle().trim(), this);
                }
            } break;
        }
    }

    @Override
    public void addCoin(String title, int points) {
        Menu menu = toolbar.getMenu();
        MenuItem balanceBar = menu.getItem(1);
        int balance = Integer.valueOf(balanceBar.getTitle().toString());
        balance = balance + points;
        balanceBar.setTitle(String.valueOf(balance));

        int progress = level_bar.getProgress();
        int level = Integer.valueOf(level_label.getText().toString());
        if(progress + (points*10) > 100){
            level+=1;
            progress = (progress-100)*-1;
            Snackbar.make(findViewById(R.id.content_main), "Congratulations! You leveled up", Snackbar.LENGTH_LONG).show();
        }else {
            progress += (points*10);
        }
        level_bar.setProgress(progress);
        level_label.setText(String.valueOf(level));
    }
}
