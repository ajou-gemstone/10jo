package com.example.capstonedesignandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LectureroomReservationAdditionalActivity extends AppCompatActivity {

    private String resId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectureroom_reservation_additional);

        Intent intent = getIntent();
        resId = intent.getExtras().getString("reservationId");

        //강의실 목적, 모임원 정보 입력
    }

}
