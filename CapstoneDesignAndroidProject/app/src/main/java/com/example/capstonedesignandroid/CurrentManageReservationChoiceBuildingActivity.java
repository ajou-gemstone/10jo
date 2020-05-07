package com.example.capstonedesignandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CurrentManageReservationChoiceBuildingActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_manage_reservation_choice_building);

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button1.setTag("1");
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button2.setTag("2");
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button3.setTag("3");
        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button4.setTag("4");
        button5 = findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button5.setTag("5");
        button6 = findViewById(R.id.button6);
        button6.setOnClickListener(this);
        button6.setTag("6");
        button7 = findViewById(R.id.button7);
        button7.setOnClickListener(this);
        button7.setTag("7");
        button8 = findViewById(R.id.button8);
        button8.setOnClickListener(this);
        button8.setTag("8");
    }

    @Override
    public void onClick(View view) {
        Intent activityIntent;
        activityIntent = new Intent(getApplicationContext(), CurrentManageReservationActivity.class);
        activityIntent.putExtra("buildingTag", (String) view.getTag());
        startActivity(activityIntent);
    }
}
