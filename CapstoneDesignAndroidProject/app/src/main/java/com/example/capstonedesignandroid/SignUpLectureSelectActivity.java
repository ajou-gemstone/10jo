package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesignandroid.Adapter.LectureListAdapter;
import com.example.capstonedesignandroid.DTO.Dummy;
import com.example.capstonedesignandroid.DTO.DummyResponse;
import com.example.capstonedesignandroid.DTO.Group;
import com.example.capstonedesignandroid.DTO.Lecture;
import com.example.capstonedesignandroid.DTO.User;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpLectureSelectActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Lecture> list = new ArrayList();
    private Boolean[] boolList = new Boolean[100];
    private String BASE = MyConstants.BASE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_lecture_select);

        final Button select = findViewById(R.id.button_select_lecture);
        recyclerView = findViewById(R.id.recyclerview);

        LectureListAdapter lectureListAdapter = new LectureListAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(lectureListAdapter);

        Intent intent3 = getIntent();
        String id = intent3.getStringExtra("id");
        String pw = intent3.getStringExtra("pw");
        String name = intent3.getStringExtra("name");
        String num = intent3.getStringExtra("num");
        String email = intent3.getStringExtra("email");
        Log.d("bbbbb", id+pw+name+num+email);

        Intent intent1 = getIntent();
        ArrayList<String> tempArray = (ArrayList<String>) intent1.getSerializableExtra("lectureArray");
        ArrayList<String> lectureArray = new ArrayList<>();
        for(String lecture : tempArray) {
            list.add(new Lecture(lecture));
        }

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolList = lectureListAdapter.getBoolean();
                for(int i=0; i<boolList.length; i++){
                    if(boolList[i] == true) {
                        Log.d("aaaaa", tempArray.get(i));
                        lectureArray.add(tempArray.get(i));
                    }
                }
                Retrofit retrofit2 = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GetService userservice = retrofit2.create(GetService.class);
                Call<DummyResponse> call = userservice.signup(id, pw, name, num, email, lectureArray);
                CallThread(call);


                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("signup", "fromsignup");
                startActivityForResult(intent,100);
            }
        });

    }//onCreate

    private void CallThread(Call<DummyResponse> call) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DummyResponse dummies = call.execute().body();
                    Log.d("aaaaaaaaaaaaaa", dummies.getResponse());

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
