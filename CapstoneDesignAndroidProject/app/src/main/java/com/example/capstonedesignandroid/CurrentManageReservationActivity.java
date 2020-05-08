package com.example.capstonedesignandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.capstonedesignandroid.Fragment.B_1_1;
import com.example.capstonedesignandroid.Fragment.B_2_1;
import com.example.capstonedesignandroid.Fragment.B_1_2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CurrentManageReservationActivity extends AppCompatActivity {

    private RelativeLayout baselayout;
    private FloatingActionButton floatingActionButton;
    private boolean floatingActionButtonClicked = false;
    private String buildingTag;
    private ScrollView scrollViewOneToTwo;
    private ScrollView scrollViewOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_manage_reservation);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        scrollViewOneToTwo = findViewById(R.id.scrollViewOneToTwo);
        scrollViewOne = findViewById(R.id.scrollViewOne);

        Intent intent = getIntent();
        buildingTag = intent.getStringExtra("buildingTag");
        //건물에 따라서 다른 xml을 inflate한다.

        switch (buildingTag) {
            case "1":
                Fragment B_1_1 = new B_1_1();
                getSupportFragmentManager().beginTransaction().replace(R.id.buildingLectureroomFrameLayout, B_1_1).commit();
                break;
            case "2":
                Fragment B_2_1 = new B_2_1();
                getSupportFragmentManager().beginTransaction().replace(R.id.buildingLectureroomFrameLayout, B_2_1).commit();
                break;
        }

        //층 선택 layout에 모두 setOnclicklistener를 설정해준다.
        View v;
        ViewGroup ll1 = (ViewGroup) findViewById(R.id.ll1);
        for (int i = 0; i < ll1.getChildCount(); i++) {
            v = ll1.getChildAt(i);
            if (v instanceof TextView) v.setOnClickListener(customOnClickListener);
        }

        ViewGroup ll2 = (ViewGroup) findViewById(R.id.ll2);
        for (int i = 0; i < ll1.getChildCount(); i++) {
            v = ll2.getChildAt(i);
            if (v instanceof TextView) v.setOnClickListener(customOnClickListener);
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!floatingActionButtonClicked) {
                    floatingActionButtonClicked = true;
                    switch (buildingTag) {
                        case "1":
                            scrollViewOneToTwo.setVisibility(View.VISIBLE);
                            scrollViewOneToTwo.bringToFront();
                            break;
                        case "2":
                            scrollViewOne.setVisibility(View.VISIBLE);
                            scrollViewOne.bringToFront();
                            break;
                    }
                } else {
                    floatingActionButtonClicked = false;
                    switch (buildingTag) {
                        case "1":
                            scrollViewOneToTwo.setVisibility(View.INVISIBLE);
                            break;
                        case "2":
                            scrollViewOne.setVisibility(View.INVISIBLE);
                            break;
                    }
                }
            }
        });

    }

    View.OnClickListener customOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (buildingTag.equals("1")) {
                switch ((String) view.getTag()) {
                    case "1":
                        Fragment B_1_1 = new B_1_1();
                        getSupportFragmentManager().beginTransaction().replace(R.id.buildingLectureroomFrameLayout, B_1_1).commit();
                        break;
                    case "2":
                        Fragment B_1_2 = new B_1_2();
                        getSupportFragmentManager().beginTransaction().replace(R.id.buildingLectureroomFrameLayout, B_1_2).commit();
                        break;
                }
            }else if(buildingTag.equals("2")){
                switch ((String) view.getTag()) {
                    case "1":
                        Fragment B_2_1 = new B_2_1();
                        getSupportFragmentManager().beginTransaction().replace(R.id.buildingLectureroomFrameLayout, B_2_1).commit();
                        break;
//                    case "2":
//                        Fragment B_1_2 = new B_1_2();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.buildingLectureroomFrameLayout, B_1_2).commit();
//                        break;
                }
            }
        }
    };
}
