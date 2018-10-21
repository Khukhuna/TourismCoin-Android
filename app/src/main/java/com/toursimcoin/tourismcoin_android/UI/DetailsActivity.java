package com.toursimcoin.tourismcoin_android.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.toursimcoin.tourismcoin_android.R;
import com.toursimcoin.tourismcoin_android.heplers.Constants;

public class DetailsActivity extends AppCompatActivity {

    TextView detailsLabel;
    TextView titleLabel;
    ImageView imagePL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        detailsLabel = findViewById(R.id.details_desc);
        titleLabel = findViewById(R.id.details_title);
        imagePL = findViewById(R.id.details_image);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        if(extras != null){
            String title = extras.getString(Constants.TITLE);
            String image_url = extras.getString(Constants.IMAGE_URL);
            String description = extras.getString(Constants.DESCRIPTION);
            String points = extras.getString(Constants.POINTS);

            Toast.makeText(this, description, Toast.LENGTH_SHORT).show();
            detailsLabel.setText(description);
            titleLabel.setText(title);
            Glide.with(this).load(image_url).into(imagePL);

        }else {
            startActivity(new Intent(DetailsActivity.this, MainActivity.class));
        }


    }
}
