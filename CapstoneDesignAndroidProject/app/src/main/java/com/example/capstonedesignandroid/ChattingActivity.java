package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.capstonedesignandroid.Adapter.ChattingAdapter;
import com.example.capstonedesignandroid.DTO.Dummy;
import com.example.capstonedesignandroid.DTO.DummyResponse;
import com.example.capstonedesignandroid.DTO.User;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ChattingActivity extends AppCompatActivity {

    int userKey = 0;
    EditText sendChatText;
    Button sendButton;
    private Socket socket;
    ChattingAdapter m_Adapter;
    int num;
    TextView chattingroomname;
    String userId, msg, myname, membername, title, memberId, groupId;
    RelativeLayout layout1;
    ScrollView scrollview_chatting;
    TextView roomnum;
    int num1 = 0;
    int leaderormember;
    int tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        m_Adapter = new ChattingAdapter();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // listview 생성 및 adapter 지정.
        final ListView listview = (ListView) findViewById(R.id.chatting);
        listview.setAdapter(m_Adapter);
        listview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        layout1 = (RelativeLayout) findViewById(R.id.chattingview);
        sendChatText = (EditText) findViewById(R.id.chat_content);
        sendButton = (Button) findViewById(R.id.send_btn);
        chattingroomname = (TextView) findViewById(R.id.chattingroomname);
        scrollview_chatting = (ScrollView) findViewById(R.id.scrollview_chatting);
        roomnum = (TextView) findViewById(R.id.roomnum);

//        scrollview.post(new Runnable() {
//            @Override
//            public void run() {
//                scrollview.fullScroll(ScrollView.FOCUS_DOWN);
//            }
//        });
//        scrollview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                scrollview.post(new Runnable() {
//                    public void run() {
//                        scrollview.fullScroll(View.FOCUS_DOWN);
//                    }
//                });
//            }
//        });
//        scrollview_chatting.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                scrollview_chatting.fullScroll(ScrollView.FOCUS_DOWN);
//            }
//        },1000);

        Intent intent1 = getIntent();
        leaderormember = intent1.getIntExtra("leaderormember", -1);
        myname = intent1.getStringExtra("username");
        title = intent1.getStringExtra("grouptitle");
        groupId = intent1.getStringExtra("groupId");
        chattingroomname.setText(title);

        //고민한잔 userkey랑 같은 변수
        userId = SharedPreference.getAttribute(getApplicationContext(), "userId");
        userKey = Integer.parseInt(SharedPreference.getAttribute(getApplicationContext(), "userId"));

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChattingService service = retrofit2.create(ChattingService.class);
        Call<List<User>> call3 = service.getChat(groupId, userId);
        CallThread_get(call3);

        try {
            socket = IO.socket(MyConstants.BASE); //로컬호스트 ip주소 수정하기
        } catch (Exception e) {
            Log.i("THREADSERVICE", "Server not connected");
            e.printStackTrace();
        }
        socket.connect();

        JSONObject obj1 = new JSONObject();
        try {
            obj1.put("roomname", title);
            obj1.put("roomnum", num1);
            obj1.put("groupId", groupId);
            obj1.put("userId", userId);
            socket.emit("join", obj1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sendChatText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    sendButton.performClick();
                    return true;
                }
                return false;
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( ! sendChatText.getText().toString().equals("")){

                    JSONObject obj = new JSONObject();
                    String message = sendChatText.getText().toString(); //전송할 메시지
                    num1 = 1;

                    try {
                        obj.put("roomname", title);
                        obj.put("message", message);
                        obj.put("key", userKey);
                        obj.put("profile", leaderormember);
                        obj.put("roomnum", num1);
                        obj.put("groupId", groupId);
                        obj.put("userId", userId);
                        socket.emit("message", obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    sendChatText.setText("");

                    ChattingService service = retrofit2.create(ChattingService.class);
                    Call<DummyResponse> call2 = service.postChat(groupId, userId, message);
                    CallThread_post(call2);
                }
            }
        });

        //말풍선 클릭했을 때
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                memberId = m_Adapter.getId(position);
                String leaderornot = m_Adapter.getProfile(position);
                Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
                intent.putExtra("userId", memberId);
                intent.putExtra("leaderormember", leaderornot);
                intent.putExtra("fromReadgroup", "true");
                startActivity(intent);

            }
        });

        listview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollview_chatting.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        Emitter.Listener onMessageReceived = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject received = (JSONObject) args[0];
                        String msg1 = null;
                        String key = null;
                        String profile = null;
                        try {
                            msg1 = received.get("message").toString(); //받는 메시지
                            key = received.get("key").toString(); //유저 식별키
                            profile = received.get("profile").toString();

                            GetService service = retrofit2.create(GetService.class);
                            Call<User> call = service.getUserInfo(key);
                            CallThread(call);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (key.equals(Integer.toString(userKey))) { //나
                            num = 0;
                            if (msg1.length() > 24) {
                                for (int i = 0; i < (msg1.length() / 24); i++) {
                                    if (i == 0) {
                                        msg = msg1.substring((i * 25), 24 * (i + 1)) + "\n";
                                    } else {
                                        msg = msg + msg1.substring((i * 25), 24 * (i + 1) + 1) + "\n";
                                    }
                                }
                                msg = msg + msg1.substring(msg1.length() - (msg1.length() % 24), msg1.length()) + "\n";
                            } else {
                                msg = msg1;
                            }

                            m_Adapter.add(Integer.parseInt(profile), msg, num, myname, userId);
                            m_Adapter.notifyDataSetChanged();
                        } else { //다른사람
                            num = 1;
                            if (msg1.length() > 24) {
                                for (int i = 0; i < (msg1.length() / 24); i++) {
                                    if (i == 0) {
                                        msg = msg1.substring((i * 25), 25 * (i + 1) - 1) + "\n";
                                    } else {
                                        msg = msg + msg1.substring((i * 25), 25 * (i + 1) - 1) + "\n";
                                    }
                                }
                                msg = msg + msg1.substring(msg1.length() - (msg1.length() % 24), msg1.length()) + "\n";
                            } else {
                                msg = msg1;
                            }
                            m_Adapter.add(Integer.parseInt(profile), msg, num, membername, key);
                            m_Adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        };
        socket.on("receiveMsg", onMessageReceived);

        Emitter.Listener roomleave = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject received = (JSONObject) args[0];
                        int exit;
                        try {
                            exit = (int) received.get("currentNum"); //받는 메시지
                            roomnum.setText(Integer.toString(exit));
                            tmp = exit - 1;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        socket.on("exit", roomleave);

        Emitter.Listener roomenter = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject received = (JSONObject) args[0];
                        String enter = null;
                        try {
                            enter = received.get("currentNum").toString(); //받는 메시지
                            roomnum.setText(enter.toString());
                            tmp = Integer.parseInt(enter);
//                            if(num1<Integer.parseInt(enter)){
//
//                            }
//                            else{
//                                roomnum.setText(Integer.toString(num1));
//                                tmp = num1;
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        socket.on("enter", roomenter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                JSONObject obj2 = new JSONObject();
                try {
                    obj2.put("roomname", title);
                    obj2.put("roomnum", tmp);
                    obj2.put("groupId", groupId);
                    obj2.put("userId", userId);
                    socket.emit("leave", obj2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("disconnect", "");
                socket.disconnect();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        JSONObject obj2 = new JSONObject();
        try {
            obj2.put("roomname", title);
            obj2.put("roomnum", tmp);
            obj2.put("groupId", groupId);
            obj2.put("userId", userId);
            socket.emit("leave", obj2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("disconnect", "");
        socket.disconnect();

        Intent intent2 = new Intent(ChattingActivity.this, StudyBulletinBoardActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent2);
    }

    private void CallThread(Call<User> call) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    User dummies = call.execute().body();
                    membername = dummies.getName();
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

    private void CallThread_post(Call<DummyResponse> call) {
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

    private void CallThread_get(Call<List<User>> call) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<User> dummies = call.execute().body();
                    m_Adapter.clear();
                    for(User dummy : dummies){
                        if(dummy.getUserId().equals(userId)) {
                            m_Adapter.add(dummy.getLeader(), dummy.getMessage(), 0, dummy.getName(), dummy.getUserId());
                            m_Adapter.notifyDataSetChanged();
                        }
                        else {
                            m_Adapter.add(dummy.getLeader(), dummy.getMessage(), 1, dummy.getName(), dummy.getUserId());
                            m_Adapter.notifyDataSetChanged();
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
    }

    @Override
    protected void onRestart() {
        Log.d("1", "onRestart: 1");
        super.onRestart();

        JSONObject obj1 = new JSONObject();
        try {
            obj1.put("roomname", title);
            obj1.put("roomnum", num1);
            obj1.put("groupId", groupId);
            obj1.put("userId", userId);
            socket.emit("join", obj1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        JSONObject obj2 = new JSONObject();
        try {
            obj2.put("roomname", title);
            obj2.put("roomnum", tmp);
            obj2.put("groupId", groupId);
            obj2.put("userId", userId);
            socket.emit("leave", obj2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("11111111", "onStop: 1");
        super.onStop();
    }

    //    Callback dummies1 = new Callback<List<Group>>() {
//
//        @Override
//        public void onResponse(Call<List<Group>> call1, Response<List<Group>> response) {
//            if (response.isSuccessful()) {
//                List<Group> dummies = response.body();
//                String[] build;
//                builder_like = new StringBuilder();
//                builder_title = new StringBuilder();
//                builder_category = new StringBuilder();
//                builder_profile = new StringBuilder();
//                for (Group dummy : dummies) {
//                    build = dummy.toString().split(",");
//                    builder_like.append(build[0] + ",");
//                    builder_title.append(build[1] + ",");
//                    builder_category.append(build[2] + ",");
//                    builder_profile.append(build[3] + ",");
//                }
//
//                if (mainbutton1 == 1) {
//                    Retrofit retrofit1 = new Retrofit.Builder()
//                            .baseUrl(BASE)
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build();
//
//                    GetTrustInterface getTrustInterface = retrofit1.create(GetTrustInterface.class);
//                    Call<List<Dummy>> call3 = getTrustInterface.listDummies(userId);
//                    call3.enqueue(dummies5);
//                }
//
//                if (backbutton == 1) {
//                    Retrofit retrofit1 = new Retrofit.Builder()
//                            .baseUrl(BASE)
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build();
//
//                    GetTrustInterface getTrustInterface = retrofit1.create(GetTrustInterface.class);
//                    Call<List<Dummy>> call3 = getTrustInterface.listDummies(userId);
//                    call3.enqueue(dummies5);
//                }
//            }
//        }
//
//        @Override
//        public void onFailure(Call<List<Group>> call1, Throwable t) {
//
//        }
//    };
//
//    Callback dummies5 = new Callback<List<Dummy>>() {
//
//        @Override
//        public void onResponse(Call<List<Dummy>> call3, Response<List<Dummy>> response) {
//            if (response.isSuccessful()) {
//                List<Dummy> dummies = response.body();
//                StringBuilder builder = new StringBuilder();
//                for (Dummy dummy : dummies) {
//                    builder.append(dummy.toString());
//                }
//                updatetrust = builder.toString();
//
//                Retrofit retrofit2 = new Retrofit.Builder()
//                        .baseUrl(BASE)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//                UserInterface userInterface = retrofit2.create(UserInterface.class);
//                Call<List<Group>> call2 = userInterface.listDummies(userId);
//                call2.enqueue(dummies2);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<List<Dummy>> call3, Throwable t) {
//
//        }
//    };
//
//    Callback dummies2 = new Callback<List<Group>>() {
//
//        @Override
//        public void onResponse(Call<List<Group>> call2, Response<List<Group>> response) {
//            if (response.isSuccessful()) {
//                List<Group> dummies = response.body();
//                String[] build1;
//                StringBuilder builder_title1 = new StringBuilder();
//                StringBuilder builder_category1 = new StringBuilder();
//                StringBuilder builder_profile1 = new StringBuilder();
//                StringBuilder builder_like1 = new StringBuilder();
//                for (Group dummy : dummies) {
//                    build1 = dummy.toString().split(",");
//                    builder_title1.append(build1[0] + ",");
//                    builder_category1.append(build1[1] + ",");
//                    builder_profile1.append(build1[2] + ",");
//                    builder_like1.append(build1[3] + ",");
//                }
//                String[] usertitle = new String[]{String.valueOf(builder_title1), String.valueOf(builder_category1), String.valueOf(builder_profile1), String.valueOf(builder_like1)};
//
//                Intent intent2 = new Intent(ChattingActivity.this, MainActivity.class);
//                String[] information1 = new String[]{userId, userPassword, String.valueOf(builder_title), name, updatetrust, emotion, String.valueOf(builder_like), String.valueOf(builder_category), String.valueOf(builder_profile)};
//                intent2.putExtra("strings", information1);
//                intent2.putExtra("usertitle", usertitle);
//                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent2);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<List<Group>> call1, Throwable t) {
//
//        }
//    };
//
//
//    Callback dummies6 = new Callback<List<Dummy>>() {
//
//        @Override
//        public void onResponse(Call<List<Dummy>> call6, Response<List<Dummy>> response) {
//            if (response.isSuccessful()) {
//                List<Dummy> dummies = response.body();
//                StringBuilder builder = new StringBuilder();
//
//                for (Dummy dummy : dummies) {
//                    num1 = Integer.parseInt(dummy.toString())+1;
//                }
//                chattingroomname.setText(title+"   ");
//
//
//            }
//        }
//
//        @Override
//        public void onFailure(Call<List<Dummy>> call6, Throwable t) {
//
//        }
//    };
}