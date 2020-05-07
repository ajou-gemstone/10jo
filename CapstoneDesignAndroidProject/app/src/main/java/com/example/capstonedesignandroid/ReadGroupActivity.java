package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.capstonedesignandroid.DTO.Group;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReadGroupActivity extends AppCompatActivity {
    Button enterbt, reservation, chatting;
    TextView title,maintext;
    Intent intent2;
    String userKey;
    String userId, userPassword;
    String name;
    String trust;
    int like;
    String emotion;
    TextView currentnum, totalnum;
    String like1;
    String groupId;
    String[] readpost;
    int oldchat = 0;
    int likebutton1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_group);

        enterbt =  (Button)findViewById(R.id.button_enter);
        title =  (TextView)findViewById(R.id.textview_title);
        maintext =  (TextView)findViewById(R.id.textview_maintext);
        currentnum = (TextView) findViewById(R.id.currentnum);
        totalnum = (TextView) findViewById(R.id.totalnum);
        reservation = (Button) findViewById(R.id.button_oldchat);
        chatting = (Button) findViewById(R.id.button_chat);


        Intent intent3 = getIntent();
//        groupId = intent3.getStringExtra("groupId");
//        title.setText(groupId);

//        readpost = intent3.getStringArrayExtra("readpost");
//
//        userId = userInfo[0];
//        userPassword = userInfo[1];
//        name = userInfo[3];
//        trust = userInfo[4];
//        emotion = userInfo[5];
//        like1 = readpost[2];
//
//        if(!userId.equals(readpost[3])){
//            reservation.setVisibility(View.GONE);
//        }

//        maintext.setText(readpost[1]);
//        liketext.setText(like1);

        Retrofit retrofit2 = new Retrofit.Builder()
        .baseUrl(MyConstants.BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        GroupService groupService = retrofit2.create(GroupService.class);
        Call<List<Group>> call2 = groupService.getStudyGroup(groupId);
        //call2.enqueue(studyDummies);
        //동기 호출, network를 사용한 thread는 main thread에서 처리를 할 수 없기 때문에
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Group> dummies = call2.execute().body();
                    maintext.setText(dummies.get(0).getTextBody());
                    title.setText(dummies.get(0).getTitle());
                    currentnum.setText(dummies.get(0).getStudyGroupNumCurrent());
                    totalnum.setText(dummies.get(0).getStudyGroupNumTotal());
                    Log.d("run: ", "run: ");
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

        enterbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),StudyBulletinBoardActivity.class);
            startActivity(intent);
//                Retrofit retrofit2 = new Retrofit.Builder()
//                        .baseUrl(BASE)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//                UserKeyInterface userKeyInterface = retrofit2.create(UserKeyInterface.class);
//                Call<List<Dummy>> call2 = userKeyInterface.listDummies(userInfo[0], userInfo[1]);
//                call2.enqueue(dummies2);
            }
        });

        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldchat = 1;
                Intent intent = new Intent(getApplicationContext(),LectureroomReservationActivity.class);
                startActivity(intent);

//                Retrofit retrofit2 = new Retrofit.Builder()
//                        .baseUrl(BASE)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//                UserKeyInterface userKeyInterface = retrofit2.create(UserKeyInterface.class);
//                Call<List<Dummy>> call2 = userKeyInterface.listDummies(userInfo[0], userInfo[1]);
//                call2.enqueue(dummies2);
            }
        });

        chatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldchat = 1;
                Intent intent = new Intent(getApplicationContext(),ChattingActivity.class);
                startActivity(intent);

//                Retrofit retrofit2 = new Retrofit.Builder()
//                        .baseUrl(BASE)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//                UserKeyInterface userKeyInterface = retrofit2.create(UserKeyInterface.class);
//                Call<List<Dummy>> call2 = userKeyInterface.listDummies(userInfo[0], userInfo[1]);
//                call2.enqueue(dummies2);
            }
        });
    }

    Callback studyDummies = new Callback<List<Group>>() {

        @Override
        public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
            if (response.isSuccessful()) {
                List<Group> dummies = response.body();

                maintext.setText(dummies.get(0).getTextBody());
                title.setText(dummies.get(0).getTitle());
                currentnum.setText(dummies.get(0).getStudyGroupNumCurrent());
                totalnum.setText(dummies.get(0).getStudyGroupNumTotal());
            }
        }

        @Override
        public void onFailure(Call<List<Group>> call1, Throwable t) {

        }
    };



}