package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesignandroid.StaticMethodAndOthers.PrefManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PrefManager prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        else { //앱 설치 후 처음일때만 튜토리얼 페이지로
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
        }
    }
}

