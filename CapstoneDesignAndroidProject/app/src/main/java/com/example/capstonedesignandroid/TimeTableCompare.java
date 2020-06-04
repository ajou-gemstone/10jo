package com.example.capstonedesignandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TimeTableCompare extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_compare);

        String groupId = getIntent().getStringExtra("groupId");

    }
}
