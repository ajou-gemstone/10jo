package com.example.capstonedesignandroid;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    TextView name;
    Intent intent,intent1;
    ImageView emotion;
    Button noti_zero, noti_yes;
    protected BottomNavigationView navigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name =(TextView) findViewById(R.id.name);
        noti_zero = findViewById(R.id.noti_zero);
        noti_yes = findViewById(R.id.noti_yes);

        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigationView.setSelectedItemId(R.id.action_profile);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        intent1 = getIntent();
        emotion = (ImageView)findViewById(R.id.image_emotion);

        final String[] userInfo = intent1.getStringArrayExtra("strings");
        final String[] usertitle = intent1.getStringArrayExtra("usertitle");
//        switch(Integer.parseInt(userInfo[5])) {
//            case 0:
//                emotion.setImageResource(R.drawable.profile);
//                break;
//            case 1:
//                emotion.setImageResource(R.drawable.profile);
//                break;
//
//            default: break;
//        }

//        name.setText(userInfo[3].toString());
//        sinredo.setText(userInfo[4].toString());

        noti_zero.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });
        noti_yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() { //super.onBackPressed();비워두면 실행안되서 뒤로가기 안됨
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId()){
                case R.id.action_profile :

                    break;
                case R.id.action_group :
                    Intent intent1 = new Intent(ProfileActivity.this, StudyBulletinBoardActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.action_reservation :
                    Intent intent2 = new Intent(ProfileActivity.this, LectureroomReservationActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.action_check :
                    Intent intent3 = new Intent(ProfileActivity.this, LectureroomCheckActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.action_cafe :
                    Intent intent4 = new Intent(ProfileActivity.this, CafeMapActivity.class);
                    startActivity(intent4);
                    break;
            }
            return false;
        }
    };

}
