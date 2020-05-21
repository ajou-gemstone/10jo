package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesignandroid.DTO.User;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserProfileActivity extends AppCompatActivity {

    TextView name, num, email, myname;
    Intent intent;
    ImageView leader, member;
    String userId;
    protected BottomNavigationView navigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        Intent intent3 = getIntent();
        String leaderormember = intent3.getStringExtra("leaderormember");
        String userId = intent3.getStringExtra("userId");

        name =(TextView) findViewById(R.id.name);
        num = findViewById(R.id.studentnum);
        email = findViewById(R.id.email);
        leader = findViewById(R.id.leader_image);
        member = findViewById(R.id.member_image);

        if(leaderormember.equals("0"))
            leader.setVisibility(View.GONE);
        else
            member.setVisibility(View.GONE);

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetService service = retrofit2.create(GetService.class);
        Call<User> call = service.getUserInfo(userId);
        CallThread(call);


    }//onCreate

    private void CallThread(Call<User> call) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    User dummies = call.execute().body();
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

}
