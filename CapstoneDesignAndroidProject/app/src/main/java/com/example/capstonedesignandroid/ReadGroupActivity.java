package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.capstonedesignandroid.Adapter.UserListAdapter;
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
    TextView title, maintext;
    TextView currentnum, totalnum;

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

        ListView listview;
        UserListAdapter userListAdapter = new UserListAdapter();
        listview = (ListView)findViewById(R.id.memberlistview);
        listview.setAdapter(userListAdapter);

        userListAdapter.add("aaa","aaa","aaa",0,"강찬혁");
        userListAdapter.add("aaa","aaa","aaa",0,"이현주");
        userListAdapter.add("aaa","aaa","aaa",0,"곽명섭");
        userListAdapter.add("aaa","aaa","aaa",0,"한정우");
/*
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
*/
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

        //유저 하나하나 눌렀을 때
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(getApplicationContext(), ProfileActivity.class);
                intent2.putExtra("userId", "아이디");
                startActivity(intent2);
            }
        });
    }

}