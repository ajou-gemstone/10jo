package com.example.capstonedesignandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstonedesignandroid.Adapter.UserListAdapter;
import com.example.capstonedesignandroid.Adapter.UserWaitingListAdapter;
import com.example.capstonedesignandroid.DTO.DummyResponse;
import com.example.capstonedesignandroid.DTO.Group;
import com.example.capstonedesignandroid.DTO.TagName;
import com.example.capstonedesignandroid.DTO.User;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReadGroupActivity extends AppCompatActivity {
    Button register, reservation, chatting, edit, full, yes ,no;
    TextView title, maintext, currentnum, totalnum, tags, waiting, notyet;
    String userId;
    int leaderormember = 0;
    String tag = "";
    String username = "";
    String grouptitle = "";
    ListView listview;
    boolean registered = false;
    boolean leader = false;
    private Boolean[] yesList = new Boolean[100];
    ArrayList<String> useridarray = new ArrayList<>();
    ArrayList<String> leaderarray = new ArrayList<>();
    ArrayList<String> usernamearray = new ArrayList<>();
    ArrayList<String> studentnumarray = new ArrayList<>();
    ArrayList<String> waitingUserIdarray = new ArrayList<>();
    UserListAdapter userListAdapter = new UserListAdapter();
    private RecyclerView recyclerView;
    private ArrayList<User> list = new ArrayList();
    ArrayList<User> waitinguserArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_group);

        register =  (Button)findViewById(R.id.button_enter);
        title =  (TextView)findViewById(R.id.textview_title);
        maintext =  (TextView)findViewById(R.id.textview_maintext);
        currentnum = (TextView) findViewById(R.id.currentnum);
        totalnum = (TextView) findViewById(R.id.totalnum);
        waiting = findViewById(R.id.waiting_textview);
        tags = findViewById(R.id.textview_tag);
        reservation = (Button) findViewById(R.id.button_oldchat);
        chatting = (Button) findViewById(R.id.button_chat);
        edit = findViewById(R.id.button_edit);
        full = findViewById(R.id.button_full);
        notyet = findViewById(R.id.notyet_textview);
        recyclerView = findViewById(R.id.recyclerview);

        UserWaitingListAdapter userWaitingListAdapter = new UserWaitingListAdapter(getApplicationContext(), list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userWaitingListAdapter);

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
        CallThread_GetUser(call);

        full.setVisibility(View.GONE);
        waiting.setVisibility(View.GONE);
        notyet.setVisibility(View.GONE);

        if(registered){ //모임 참여 중이면
            reservation.setVisibility(View.VISIBLE);
            register.setVisibility(View.GONE);
            full.setVisibility(View.GONE);
        } else{
            reservation.setVisibility(View.GONE);
            if(currentnum.getText().equals(totalnum.getText())){ //참여 중 아닌데 꽉 찼을 때
                register.setVisibility(View.GONE);
                full.setVisibility(View.VISIBLE);
            }
            else {
                register.setVisibility(View.VISIBLE);
            }
            chatting.setVisibility(View.GONE);
        }

        Call<List<User>> call3 = groupService.getWaitingList(groupId);
        CallThread_GetWaitingUser(call3);

        if(leader){
            edit.setVisibility(View.VISIBLE);
            full.setVisibility(View.GONE);
            waiting.setVisibility(View.VISIBLE);
            notyet.setVisibility(View.VISIBLE);

            if(waitinguserArray.size() != 0)
                notyet.setVisibility(View.GONE);

            for(User user : waitinguserArray)
                list.add(user);
        } else{
            edit.setVisibility(View.GONE);
        }

         for(String id : waitingUserIdarray){
             if(id.equals(userId)){
                 register.setText("신청 완료");
                 register.setEnabled(false);
             }
         }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Call<DummyResponse> call2 = groupService.registerStudy(groupId, userId);
            CallThread_Register(call2);

            Intent intent = new Intent(getApplicationContext(),StudyBulletinBoardActivity.class);
            startActivity(intent);
            }
        });
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LectureroomReservationActivity.class);
                intent.putExtra("groupId", groupId);
                intent.putExtra("studentnumarray", studentnumarray);
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
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),EditGroupActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
            }
        });
        chatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),ChattingActivity.class);
                intent.putExtra("leaderormember", leaderormember);
                intent.putExtra("username", username);
                intent.putExtra("grouptitle", grouptitle);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
            }
        });
        //가입된 유저 하나하나 눌렀을 때
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(getApplicationContext(), UserProfileActivity.class);
                intent2.putExtra("leaderormember", leaderarray.get(position));
                intent2.putExtra("userId", useridarray.get(position));
                startActivity(intent2);
            }
        });
        
        if(leader) {
           //신청자 하나하나 눌렀을 때
            userWaitingListAdapter.setOnItemClickListener(new UserWaitingListAdapter.OnItemClickListener() {
                @Override
                public void onYesClick(View view, int position) {
                    Call<DummyResponse> call4 = groupService.acceptStudy(groupId, waitingUserIdarray.get(position));
                    CallThread_Accept(call4);
                    list.remove(position);
                    userWaitingListAdapter.notifyDataSetChanged();
                }
                @Override
                public void onNoClick(View view, int position) {
                    Call<DummyResponse> call5 = groupService.rejectStudy(groupId, waitingUserIdarray.get(position));
                    CallThread_Reject(call5);
                    list.remove(position);
                    userWaitingListAdapter.notifyDataSetChanged();
                }
            });
        }


    } // onCreate

    private void CallThread_GetUser(Call<Group> call) {
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
                        useridarray.add(user.getUserId());
                        leaderarray.add(Integer.toString(user.getLeader()));
                        usernamearray.add(user.getName());
                        studentnumarray.add(user.getStudentNum());
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

    private void CallThread_Register(Call<DummyResponse> call) {
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

    private void CallThread_GetWaitingUser(Call<List<User>> call) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<User> dummies = call.execute().body();
                    for(User user : dummies){
                        waitinguserArray.add(new User(user.getName(), user.getStudentNum()));
                        waitingUserIdarray.add(user.getId());
                        Log.d("waiting", waitingUserIdarray.toString());
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
    }//callthread3

    private void CallThread_Accept(Call<DummyResponse> call) {
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
    }
    private void CallThread_Reject(Call<DummyResponse> call) {
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
    }

}