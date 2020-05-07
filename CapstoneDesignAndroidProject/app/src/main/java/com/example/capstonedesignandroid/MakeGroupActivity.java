package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.capstonedesignandroid.DTO.Group;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Integer.parseInt;

public class MakeGroupActivity extends AppCompatActivity {
    EditText grouptitle, tag, body, totalnum;
    Button roommake;
    Intent intent, intent2;
    Spinner spinner_lecture;
    RadioGroup grouptype;
    RadioButton lecturechecked, allchecked;
    LinearLayout lecture_layout;
    int category = 0;

    String userId, userPassword;
    String name, trust1, emotion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_group);

        spinner_lecture = (Spinner) findViewById(R.id.spinner_lecture);
        roommake = (Button) findViewById(R.id.button_makeroom2);
        grouptype = (RadioGroup) findViewById(R.id.grouptype);
        lecturechecked= (RadioButton) findViewById(R.id.checkbox_lec);
        allchecked = (RadioButton) findViewById(R.id.checkbox_all);
        totalnum = (EditText) findViewById(R.id.totalnum);
        grouptitle = (EditText) findViewById(R.id.title);
        tag= (EditText) findViewById(R.id.tag);
        body = (EditText) findViewById(R.id.body);
        lecture_layout =findViewById(R.id.lecture_layout);

//        Intent intent1 = getIntent();
//        final String[] userInfo = intent1.getStringArrayExtra("strings");
//        userId = userInfo[0];
//        userPassword = userInfo[1];
//        name = userInfo[2];
//        trust1 = userInfo[3];
//        emotion = userInfo[4];

        ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<String>();
        list.add("캡디");
        list.add("자주연");
        list.add("환경과인간");
        list.add("인공지능");
        list.add("과학사");
        list.add("도분설");


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_lecture.setAdapter(adapter);

        grouptitle.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    body.requestFocus();
                    return true;
                }
                return false;
            }
        });


        //과목별 스터디 선택할 때만 보이기
        lecture_layout.setVisibility(View.GONE);
        grouptype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  // 라디오 그룹 리스너 입니다.
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = (RadioButton) grouptype.findViewById(checkedId); //체크된 것을 입력받습니다.
                if( rb.getText().toString().equals("과목별 스터디") ) {
                    lecture_layout.setVisibility(View.VISIBLE);
                    category=1;
                }
                else lecture_layout.setVisibility(View.GONE);
            }
        });

        roommake.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StudyBulletinBoardActivity.class);
                startActivity(intent);

                String title = grouptitle.getText().toString();
                String textBody = body.getText().toString();
                int studyGroupNumTot = parseInt(totalnum.getText().toString());
                String[] tagName = new String[10];

//                RadioButton rd = (RadioButton) findViewById(sex.getCheckedRadioButtonId());
//                String sex1 = String.valueOf(rd.getText().toString());
//                String trust1 = String.valueOf(trust.getText().toString());
//

                Retrofit retrofit2 = new Retrofit.Builder()
                        .baseUrl(MyConstants.BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GroupService groupService = retrofit2.create(GroupService.class);
                Call<List<Group>> call2 = groupService.postStudy(category, title, textBody, tagName, studyGroupNumTot);
                call2.enqueue(dummies);

//                ChattingRoomInterface chattingRoomInterface = retrofit.create(ChattingRoomInterface.class);
//                title = e1.getText().toString();
//                String maintext = e2.getText().toString();
//                RadioButton rd = (RadioButton) findViewById(sex.getCheckedRadioButtonId());
//                String sex1 = String.valueOf(rd.getText().toString());
//                String trust1 = String.valueOf(trust.getText().toString());
//                String category1 = String.valueOf(category.getSelectedItem().toString());
//
//                Call<List<Dummy2>> call2 = chattingRoomInterface.listDummies(title, maintext, category1, agemin1, agemax1, sex1, trust1, userInfo[0], userInfo[1], emotion);
//                call2.enqueue(dummies2);
            }
        });


    }//onCreate

    Callback dummies = new Callback<List<Group>>() {
        @Override
        public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
        }
        @Override
        public void onFailure(Call<List<Group>> call1, Throwable t) {
        }
    };


}
