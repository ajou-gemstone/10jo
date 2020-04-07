package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class CafeDetailedInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_detailed_info);

        Intent intent = getIntent(); //이 액티비티를 부른 인텐트를 받는다.
        int cafeId = intent.getIntExtra("cafeId", 0);

        //cafeID에 해당하는 정보들을 불러온다.


    }

}
