package com.example.capstonedesignandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;

public class MainBuildingGuardActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_building_guard);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        logoutButton = findViewById(R.id.logoutButton);

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

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // AlertDialog 빌더를 이용해 종료시 발생시킬 창을 띄운다
                AlertDialog.Builder alBuilder = new AlertDialog.Builder(MainBuildingGuardActivity.this);
                alBuilder.setMessage("로그아웃 하시겠습니까?");
                // "예" 버튼을 누르면 실행되는 리스너
                alBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            SharedPreference.removeAttribute(getApplicationContext(), "userId");
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                // "아니오" 버튼을 누르면 실행되는 리스너
                alBuilder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return; // 아무런 작업도 하지 않고 돌아간다
                    }
                });
                alBuilder.setTitle("로그아웃");
                alBuilder.show();
            }
        });
    }
}
