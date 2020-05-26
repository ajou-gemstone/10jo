package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesignandroid.DTO.User;
import com.example.capstonedesignandroid.Fragment.TimeTableFragment;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserProfileActivity extends AppCompatActivity {

    TextView name, num, email, myname;
    Intent intent;
    ImageView leader, member, waiting;
    String userId;
    protected BottomNavigationView navigationView;
    private TimeTableFragment timeTableFragment;
    private RelativeLayout timaTableBigRL;
    private TimeTableFragment timeTableBigFragment;

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
        waiting = findViewById(R.id.waiting_image);

        waiting.setVisibility(View.GONE);
        if(leaderormember.equals("0"))
            leader.setVisibility(View.GONE);
        else
            member.setVisibility(View.GONE);

        if(leaderormember.equals("-1")){
            leader.setVisibility(View.GONE);
            member.setVisibility(View.GONE);
            waiting.setVisibility(View.VISIBLE);
        }

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetService service = retrofit2.create(GetService.class);
        Call<User> call = service.getUserInfo(userId);
        CallThread(call);

        //------------시간표-------------
        timeTableFragment = new TimeTableFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.timeTableFrame, timeTableFragment).commit();

        //시간표 크게 보기
        Button timeTableBigButton = findViewById(R.id.timeTableBigButton);
        timaTableBigRL = findViewById(R.id.timaTableBigRL);
        timeTableBigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timaTableBigRL.setVisibility(View.VISIBLE);
                timeTableBigFragment = new TimeTableFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.timeTableBigFrame, timeTableBigFragment).commit();

                com.otaliastudios.zoom.ZoomLayout timeTableBigZoomLayout = findViewById(R.id.timeTableBigZoomLayout);
                //UI작업을 위해 handler로 처리, onCreate에서 바로 zoom같은 animation이 작동하지 않는다.
                Handler mHandler = new Handler();
                TimerTask mTask = new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    timeTableBigZoomLayout.moveTo(2.5f, 0, 1, true);
                                }
                            });
                        } catch (Exception e) {
                            Log.d("ExceptionTimer", ": " + e);
                        }
                    }
                };

                Timer mTimer = new Timer();
                mTimer.schedule(mTask, 200);
            }
        });



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
