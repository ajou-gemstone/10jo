package com.example.capstonedesignandroid;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesignandroid.DTO.Group;
import com.example.capstonedesignandroid.DTO.TagName;
import com.example.capstonedesignandroid.DTO.User;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    TextView name, num, email, myname;
    Intent intent;
    ImageView leader, member;
    Button noti_zero, noti_yes;
    String userId;
    protected BottomNavigationView navigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        myname =(TextView) findViewById(R.id.myname);
        name =(TextView) findViewById(R.id.name);
        num = findViewById(R.id.studentnum);
        email = findViewById(R.id.email);
        leader = findViewById(R.id.leader_image);
        member = findViewById(R.id.member_image);
        noti_zero = findViewById(R.id.noti_zero);
        noti_yes = findViewById(R.id.noti_yes);

        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigationView.setSelectedItemId(R.id.action_profile);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        userId = SharedPreference.getAttribute(getApplicationContext(), "userId");

        Retrofit retrofit2 = new Retrofit.Builder()
        .baseUrl(MyConstants.BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        GetService service = retrofit2.create(GetService.class);
        Call<User> call = service.getUserInfo(userId);
        CallThread(call);

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

    }//onCreate

    private void CallThread(Call<User> call) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    User dummies = call.execute().body();
                    myname.setText(dummies.getName());
                    name.setText(dummies.getName());
                    num.setText(dummies.getStudentNum());
                    email.setText(dummies.getEmail());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("IOException: ", "IOException: ");
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
        }
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
