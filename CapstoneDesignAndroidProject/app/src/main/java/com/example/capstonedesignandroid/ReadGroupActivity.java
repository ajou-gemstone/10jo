package com.example.capstonedesignandroid;

import android.app.AlertDialog;
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
import com.example.capstonedesignandroid.DTO.DummyReservationDetail;
import com.example.capstonedesignandroid.DTO.DummyResponse;
import com.example.capstonedesignandroid.DTO.Group;
import com.example.capstonedesignandroid.DTO.TagName;
import com.example.capstonedesignandroid.DTO.User;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReadGroupActivity extends AppCompatActivity {
    Button register, reservation, chatting, edit, full;
    TextView title, maintext, currentnum, totalnum, tags;
    String userId;
    int leaderormember = 0;
    String tag = "";
    String username = "";
    String grouptitle = "";
    ListView listview;
    boolean registered = false;
    boolean leader = false;
    UserListAdapter userListAdapter = new UserListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_group);

        register =  (Button)findViewById(R.id.button_enter);
        title =  (TextView)findViewById(R.id.textview_title);
        maintext =  (TextView)findViewById(R.id.textview_maintext);
        currentnum = (TextView) findViewById(R.id.currentnum);
        totalnum = (TextView) findViewById(R.id.totalnum);
        tags = findViewById(R.id.textview_tag);
        reservation = (Button) findViewById(R.id.button_oldchat);
        chatting = (Button) findViewById(R.id.button_chat);
        edit = findViewById(R.id.button_edit);
        full = findViewById(R.id.button_full);

        Intent intent3 = getIntent();
        String groupId = intent3.getStringExtra("groupId");
        userId = SharedPreference.getAttribute(getApplicationContext(), "userId");

        listview = (ListView)findViewById(R.id.memberlistview);
        listview.setAdapter(userListAdapter);

        Retrofit retrofit2 = new Retrofit.Builder()
        .baseUrl(MyConstants.BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        GroupService groupService = retrofit2.create(GroupService.class);
        Call<Group> call = groupService.getStudyGroup(groupId);
        CallThread(call);

        if(registered){ //모임 참여 중이면
            reservation.setVisibility(View.VISIBLE);
            register.setVisibility(View.GONE);
        } else{
            reservation.setVisibility(View.GONE);
            register.setVisibility(View.VISIBLE);
            chatting.setVisibility(View.GONE);
        }

        if(leader){
            edit.setVisibility(View.VISIBLE);
        } else{
            edit.setVisibility(View.GONE);
        }

        full.setVisibility(View.GONE);
        if(currentnum.getText().equals(totalnum.getText())){
            register.setVisibility(View.GONE);
            full.setVisibility(View.VISIBLE);
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Retrofit retrofit2 = new Retrofit.Builder()
                    .baseUrl(MyConstants.BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            GroupService service = retrofit2.create(GroupService.class);
            Call<DummyResponse> call2 = service.registerStudy(groupId, userId);
            CallThread2(call2);

            Intent intent = new Intent(getApplicationContext(),StudyBulletinBoardActivity.class);
            startActivity(intent);
            }
        });
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LectureroomReservationActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
            }
        });
        full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(ReadGroupActivity.this);
                alert_confirm.setMessage("모집 완료된 모임입니다.");
                alert_confirm.setPositiveButton("확인", null);
                AlertDialog alert = alert_confirm.create();
                alert.setIcon(R.drawable.app);
                alert.show();
            }
        });

        chatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),ChattingActivity.class);
                intent.putExtra("leaderormember", leaderormember);
                intent.putExtra("username", username);
                intent.putExtra("grouptitle", grouptitle);
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
                startActivity(intent2);
            }
        });
    } // onCreate


    private void CallThread(Call<Group> call) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Group dummies = call.execute().body();
                    title.setText(dummies.getTitle());
                    maintext.setText(dummies.getTextBody());
                    currentnum.setText(dummies.getStudyGroupNumCurrent().toString());
                    totalnum.setText(dummies.getStudyGroupNumTotal().toString());
                    grouptitle = dummies.getTitle();
                    for(TagName t : dummies.getTagName()){
                        tag = tag +"#"+t.getTagName()+" ";
                    }
                    tags.setText(tag);
                    for(User user : dummies.getUser()){
                        userListAdapter.add(user.getUserId(), user.getLeader(), user.getName());
                        if(userId.equals(user.getUserId())) {
                            registered = true;
                            username = user.getName();
                            if(userId.equals(user.getUserId()) && user.getLeader() == 1) {
                                leader = true;
                                leaderormember = 1;
                            }
                        }

                    }

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
    }//callthread

    private void CallThread2(Call<DummyResponse> call) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DummyResponse dummies = call.execute().body();
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
    }//callthread2

}