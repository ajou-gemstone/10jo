package com.example.capstonedesignandroid;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesignandroid.DTO.DummyTile;
import com.example.capstonedesignandroid.DTO.User;
import com.example.capstonedesignandroid.Fragment.TimeTableFragment;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyProfileActivity extends AppCompatActivity {

    TextView num, email, myname, score;
    Intent intent;
    ImageView leader, member, waiting;
    Button noti_zero, noti_yes, logout, timeTableModifyButton;
    public String userId, fromReadgroup;
    protected BottomNavigationView navigationView;
    private TimeTableFragment timeTableFragment;
    private RelativeLayout timaTableBigRL;
    private TimeTableFragment timeTableBigFragment;
    private ArrayList<DummyTile> dummiesDummyTile;
    public String memberId;

    @Override
    protected void onStart() {
        super.onStart();

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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_my);

        myname =(TextView) findViewById(R.id.myname);
        num = findViewById(R.id.studentnum);
        email = findViewById(R.id.email);
        leader = findViewById(R.id.leader_image);
        member = findViewById(R.id.member_image);
        waiting = findViewById(R.id.waiting_image);
        noti_zero = findViewById(R.id.noti_zero);
        noti_yes = findViewById(R.id.noti_yes);
        logout = findViewById(R.id.logout);
        score = findViewById(R.id.penalty);
        timeTableModifyButton = findViewById(R.id.timeTableModifyButton);
        
        Intent intent = getIntent();
        String notiDeleted = intent.getStringExtra("notideleted");
        String leaderormember = intent.getStringExtra("leaderormember");
        memberId = intent.hasExtra("fromReadgroup") ? intent.getStringExtra("userId") : "000";
        fromReadgroup = intent.hasExtra("fromReadgroup") ? intent.getStringExtra("fromReadgroup") : "false";

        userId = SharedPreference.getAttribute(getApplicationContext(), "userId");

        ArrayList<String> title = SharedPreference.getStringArrayPref(getApplicationContext(), "notilist");
        if(title.size() != 0){
            noti_zero.setVisibility(View.GONE);
            noti_yes.setVisibility(View.VISIBLE);
        }
        if(intent.hasExtra("notideleted") && notiDeleted.equals("true")){
            noti_zero.setVisibility(View.VISIBLE);
            noti_yes.setVisibility(View.GONE);
        }

        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigationView.setSelectedItemId(R.id.action_profile);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        waiting.setVisibility(View.GONE);

        if(fromReadgroup.equals("true")){
            navigationView.findViewById(R.id.bottom_navigation_view).setVisibility(View.GONE); timeTableModifyButton.setVisibility(View.GONE);
            noti_zero.setVisibility(View.GONE); noti_yes.setVisibility(View.GONE); logout.setVisibility(View.GONE);

            userId = memberId;

            if(leaderormember.equals("0"))
                leader.setVisibility(View.GONE);
            else
                member.setVisibility(View.GONE);

            if(leaderormember.equals("-1")){
                leader.setVisibility(View.GONE);
                member.setVisibility(View.GONE);
                waiting.setVisibility(View.VISIBLE);
            }
            Log.d("memberid", memberId);
            Log.d("memberid", userId);
            Log.d("memberid", fromReadgroup);

        }
        else{
            leader.setVisibility(View.GONE); member.setVisibility(View.GONE); 
        }

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
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // AlertDialog 빌더를 이용해 종료시 발생시킬 창을 띄운다
                AlertDialog.Builder alBuilder = new AlertDialog.Builder(MyProfileActivity.this);
                alBuilder.setMessage("로그아웃 하시겠습니까?");
                // "예" 버튼을 누르면 실행되는 리스너
                alBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            SharedPreference.removeAllAttribute(getApplicationContext());
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

        //시간표 수정하기
        timeTableModifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityIntent = new Intent(getApplicationContext(), TimetableModifyActivity.class);
                startActivity(activityIntent);
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
                    num.setText(dummies.getStudentNum());
                    email.setText(dummies.getEmail());
                    score.setText(dummies.getScore().toString());
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
    public void onBackPressed() {
        if(timaTableBigRL.getVisibility()==View.VISIBLE){
            timaTableBigRL.setVisibility(View.INVISIBLE);
            getSupportFragmentManager().beginTransaction().remove(timeTableBigFragment).commit();
        }
        else if(fromReadgroup.equals("true")){
            super.onBackPressed();
        }
        else {
            // AlertDialog 빌더를 이용해 종료시 발생시킬 창을 띄운다
            AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
            alBuilder.setMessage("종료하시겠습니까?");

            // "예" 버튼을 누르면 실행되는 리스너
            alBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        finishAffinity();
                    }
                    System.runFinalization();
                    System.exit(0);
                }
            });
            // "아니오" 버튼을 누르면 실행되는 리스너
            alBuilder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return; // 아무런 작업도 하지 않고 돌아간다
                }
            });
            alBuilder.show(); // AlertDialog.Bulider로 만든 AlertDialog를 보여준다.
        }
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
                    Intent intent1 = new Intent(MyProfileActivity.this, StudyBulletinBoardActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.action_reservation :
                    Intent intent2 = new Intent(MyProfileActivity.this, LectureroomReservationActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.action_check :
                    Intent intent3 = new Intent(MyProfileActivity.this, LectureroomCheckActivity.class);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.action_cafe :
                    Intent intent4 = new Intent(MyProfileActivity.this, CafeMapActivity.class);
                    startActivity(intent4);
                    finish();
                    break;
            }
            return false;
        }
    };
}
