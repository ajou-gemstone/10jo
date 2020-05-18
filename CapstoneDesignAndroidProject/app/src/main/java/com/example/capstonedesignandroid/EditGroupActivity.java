package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesignandroid.DTO.Group;
import com.example.capstonedesignandroid.DTO.TagName;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditGroupActivity extends AppCompatActivity {

    String userId;
    EditText grouptitle, tag, body, totalnum;
    Button roommake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        roommake = (Button) findViewById(R.id.button_makeroom2);
        totalnum = (EditText) findViewById(R.id.totalnum);
        grouptitle = (EditText) findViewById(R.id.title);
        tag= (EditText) findViewById(R.id.tag);
        body = (EditText) findViewById(R.id.body);

        Intent intent3 = getIntent();
        String groupId = intent3.getStringExtra("groupId");
        userId = SharedPreference.getAttribute(getApplicationContext(), "userId");
    
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GroupService groupService = retrofit2.create(GroupService.class);
        Call<Group> call = groupService.getStudyGroup(groupId);
        CallThread(call);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        roommake.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StudyBulletinBoardActivity.class);
                startActivity(intent);

                // String title = grouptitle.getText().toString();
                // String textBody = body.getText().toString();
                // int studyGroupNumTot = parseInt(totalnum.getText().toString());
                // String t = tag.getText().toString();
                // String[] tArray = t.split("[#| |,]");
                // ArrayList<String> tagName = new ArrayList<>();
                // for(String tag : tArray)
                //     if( !tag.equals("") )
                //         tagName.add(tag);

                // Retrofit retrofit2 = new Retrofit.Builder()
                //         .baseUrl(MyConstants.BASE)
                //         .addConverterFactory(GsonConverterFactory.create())
                //         .build();

                // GroupService groupService = retrofit2.create(GroupService.class);
                // Call<DummyResponse> call2 = groupService.createStudy(userId, category, title, textBody, tagName, studyGroupNumTot);
                // CallThread(call2);

            }
        });
    }//onCreate

    private void CallThread(Call<Group> call) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Group dummies = call.execute().body();
                    grouptitle.setText(dummies.getTitle());
                    body.setText(dummies.getTextBody());
                    totalnum.setText(dummies.getStudyGroupNumTotal().toString());
                    String tags = "";
                    for(TagName t : dummies.getTagName()){
                        tags = tags +"#"+t.getTagName()+" ";
                    }
                    tag.setText(tags);

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

}
