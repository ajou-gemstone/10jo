package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CafeDetailedInfoActivity extends AppCompatActivity {

    TextView name, body, address, time;
    ImageView congestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_detailed_info);

        name = findViewById(R.id.cafename);
        congestion = findViewById(R.id.congestionImageView);
        time = findViewById(R.id.updated_time);
        address = findViewById(R.id.address);
        body = findViewById(R.id.cafebody);

        Intent intent = getIntent(); //이 액티비티를 부른 인텐트를 받는다.
        int cafeId = intent.getIntExtra("cafeId", 0);
        String cafename = intent.getStringExtra("cafename");
        int cafecongestion = intent.getIntExtra("congestion", 0);
        String cafebody = intent.getStringExtra("cafebody");
        String updatetime = intent.getStringExtra("time");
        String cafeaddress =  intent.getStringExtra("address");

        name.setText(cafename);
        body.setText(cafebody);
        time.setText(updatetime);
        address.setText(cafeaddress);
        if(cafecongestion==1) {
            ( (ImageView) findViewById(R.id.congestionImageView) ).setImageResource(R.drawable.congestion1);
        }else if(cafecongestion==2) {
            ( (ImageView) findViewById(R.id.congestionImageView) ).setImageResource(R.drawable.congestion2);
        }else if(cafecongestion==3) {
            ( (ImageView) findViewById(R.id.congestionImageView) ).setImageResource(R.drawable.congestion3);
        }else if(cafecongestion==4) {
            ( (ImageView) findViewById(R.id.congestionImageView) ).setImageResource(R.drawable.congestion4);
        }else if(cafecongestion==5) {
            ((ImageView) findViewById(R.id.congestionImageView)).setImageResource(R.drawable.congestion5);
        }else{
            ((ImageView) findViewById(R.id.congestionImageView)).setImageResource(R.drawable.star_grey);
        }

    }

}
