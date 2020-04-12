package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReadGroupActivity extends AppCompatActivity {
    Button enterbt, likebutton, oldchatbutton;
    TextView title,maintext;
    Intent intent2;
    String userKey;
    String userId, userPassword;
    String name;
    String trust;
    int like;
    String emotion;
    TextView liketext;
    String like1;
    String[] userInfo;
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
        liketext = (TextView) findViewById(R.id.text_like);
        likebutton = (Button) findViewById(R.id.button_like);
        oldchatbutton = (Button) findViewById(R.id.button_oldchat);
//        Intent intent3 = getIntent();
//        userInfo = intent3.getStringArrayExtra("strings");
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
//            oldchatbutton.setVisibility(View.GONE);
//        }

//        title.setText(readpost[0]);
//        maintext.setText(readpost[1]);
//        liketext.setText(like1);

      //  final String BASE = SharedPreference.getAttribute(getApplicationContext(), "IP");

        enterbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


//        likebutton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if(likebutton1==0) {
//                    like = Integer.parseInt(like1) + 1;
//                    liketext.setText(Integer.toString(like));
//                    View view = View.inflate(getApplicationContext(), R.layout.toast_temp, null);
//                    Toast toast = new Toast(getApplicationContext());
//                    toast.setView(view);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.setDuration(Toast.LENGTH_SHORT);
//                    toast.show();
//
//                    Retrofit retrofit = new Retrofit.Builder()
//                            .baseUrl(BASE)
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build();
//
//                    LikeInterface likeInterface = retrofit.create(LikeInterface.class);
//                    Call<List<Dummy>> call = likeInterface.listDummies(readpost[0], Integer.toString(like));
//                    call.enqueue(dummies1);
//                }
//            }
//        });

        oldchatbutton.setOnClickListener(new View.OnClickListener() {
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
    }

}