package com.example.capstonedesignandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
    TextView title, maintext, currentnum, totalnum, tags, waiting;
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
        recyclerView = findViewById(R.id.recyclerview);

        UserWaitingListAdapter lectureListAdapter = new UserWaitingListAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(lectureListAdapter);

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

        full.setVisibility(View.GONE);
        waiting.setVisibility(View.GONE);

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

        if(leader){
            edit.setVisibility(View.VISIBLE);
            full.setVisibility(View.GONE);
            waiting.setVisibility(View.VISIBLE);
            Call<List<User>> call3 = groupService.getWaitingList(groupId);
            CallThread3(call3);

            for(User user : waitinguserArray)
                list.add(user);
        } else{
            edit.setVisibility(View.GONE);
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
//            //신청자 하나하나 눌렀을 때
//            registerlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent intent2 = new Intent(getApplicationContext(), UserProfileActivity.class);
//                    intent2.putExtra("leaderormember", "-1");
//                    intent2.putExtra("userId", waitingUserIdarray.get(position));
//                    startActivity(intent2);
//                }
//                @Override
//                public void onYesNoClick(int position){
//                    yes.getTag();
//                    Toast.makeText(ReadGroupActivity.this, "hihi", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
        //yesList = userRegisterListAdapter.getYes();
//        for(int i=0; i<yesList.length; i++){
//            if(yesList[i]) {
//                Log.d("ddddddd", waitingUserIdarray.get(i).toString());
//            }
//        }

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

    private void CallThread3(Call<List<User>> call) {
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
    }//callthread2
//
//    @Override
//    public void onClick(View v) {
////        View oParentView = (View)v.getParent(); // 부모의 View를 가져온다. 즉, 아이템 View임.
////        String position = (String) oParentView.getTag();
////
////        AlertDialog.Builder oDialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);
////
////        String strMsg = "선택한 아이템의 position 은 "+position+" 입니다.";
////        oDialog.setMessage(strMsg)
////                .setPositiveButton("확인", null)
////                .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
////                .show();
//        switch (v.getId()) {
//
//            case R.id.yes:
//                Intent i = new Intent(this, StudyBulletinBoardActivity.class);
//                startActivity(i);
//                break;
//            case R.id.no:
//                //buttonAction.welarmListOnOff(this, v.getTag().toString());
//                userRegisterListAdapter.notifyDataSetChanged();
//                Log.d("ddddd","ddddd");
//                //Log.d("MainActivity.class", "test: myAdapter_switch:"+myAdapter);
//                // onResume();
//                break;
//            default:
//                break;
//        }
//    }
//
}