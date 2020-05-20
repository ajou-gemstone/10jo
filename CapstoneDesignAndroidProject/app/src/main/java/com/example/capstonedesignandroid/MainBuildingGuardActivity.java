package com.example.capstonedesignandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainBuildingGuardActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_building_guard);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityintent;
                activityintent = new Intent(getApplicationContext(), CurrentManageReservationChoiceBuildingActivity.class);
                activityintent.putExtra("activityType", "current");
                startActivity(activityintent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityintent;
                activityintent = new Intent(getApplicationContext(), CurrentManageReservationChoiceBuildingActivity.class);
                activityintent.putExtra("activityType", "total");
                startActivity(activityintent);
            }
        });
    }
}
