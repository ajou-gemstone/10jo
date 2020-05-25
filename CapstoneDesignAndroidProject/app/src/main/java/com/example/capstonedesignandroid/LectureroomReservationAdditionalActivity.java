package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesignandroid.Adapter.ClassofAdapter;
import com.example.capstonedesignandroid.DTO.DummyLectureRoomReservationState;
import com.example.capstonedesignandroid.DTO.DummyResponse;
import com.example.capstonedesignandroid.DTO.User;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LectureroomReservationAdditionalActivity extends AppCompatActivity {

    private String resId, groupId;
    private RecyclerView classofRecyclerView;
    private ArrayList<String> classofArrayList, studentNumArray;
    private EditText classofEdittext;
    private Button classofAddButton;
    private Button saveReservationDescButton;
    private Retrofit retrofit;
    private EditText reservationIntentEditText;
    private boolean saveComplete = false;
    private boolean saveComplete2 = false;
    private User userInfo;

    //여기서는 뒤로가기를 막는다.
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectureroom_reservation_additional);

        Intent intent = getIntent();
        resId = intent.getExtras().getString("reservationId");
        groupId = intent.getStringExtra("groupId");
        studentNumArray = (ArrayList<String>) intent.getSerializableExtra("studentnumarray");
        classofArrayList = new ArrayList<String>();
        if(studentNumArray != null ) {
            for (String studentnum : studentNumArray) {
                classofArrayList.add(studentnum);
            }
        }
        reservationIntentEditText = findViewById(R.id.reservationIntentEditText);
        classofEdittext = findViewById(R.id.classofEdittext);
        classofAddButton = findViewById(R.id.classofAddButton);

        //추가, 삭제되는 edittext + recyclerview구현
        classofRecyclerView = findViewById(R.id.classofRecyclerView);
        classofRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ClassofAdapter adapter = new ClassofAdapter(classofArrayList);
        classofRecyclerView.setAdapter(adapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetService service = retrofit.create(GetService.class);
        String userId = SharedPreference.getAttribute(getApplicationContext(), "userId");
        Call<User> call1 = service.getUserInfo(userId);

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    userInfo = call1.execute().body();
                    Log.d("run: ", "run: ");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("IOException: ", "IOException: ");
                }
            }
        });
        thread2.start();
        try {
            thread2.join();
        } catch (Exception e) {

        }

        classofAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //자신이 학번을 쓸 경우 return
                if(userInfo.getStudentNum().equals(classofEdittext.getText().toString())){
                    Toast.makeText(getApplicationContext(), "자신의 학번은 입력하지 않아도 됩니다.", Toast.LENGTH_LONG).show();
                    return;
                }
                GetService service = retrofit.create(GetService.class);
                Call<DummyResponse> call = service.searchStudentId(classofEdittext.getText().toString());

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DummyResponse dummy = call.execute().body();
                            if(dummy.getResponse().equals("success")){
                                Log.d("saveAdditional", "학번이 추가되었습니다.");
                                saveComplete2 = true;
                            }else
                                saveComplete2 = false;
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("IOException: ", "IOException: ");
                            saveComplete2 = false;
                        }
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (Exception e) {
                }
                if(saveComplete2){
                    classofArrayList.add("" + classofEdittext.getText());
                    classofEdittext.setText("");
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "학번이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "유효하지 않은 학번입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        adapter.setOnItemClickListener(new ClassofAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                classofArrayList.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        saveReservationDescButton = findViewById(R.id.saveReservationDescButton);

        saveReservationDescButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(classofArrayList.size() == 0){
                    Toast.makeText(getApplicationContext(), "학번을 하나 이상 추가해주세요", Toast.LENGTH_LONG).show();
                }

                //예약에 강의실 목적, 모임원 정보 저장
                //입력: {reservationId: "reservationId", reservationIntent: "studying algorithm", userClassofsNum: "3",
                // userClassofs: ["201520971", "201520000", "201520001"]}
                //출력: {response: "success or fail"}
                GetService service = retrofit.create(GetService.class);
                Call<DummyResponse> call = service.postReservationDetail(resId, "" + reservationIntentEditText.getText(),
                        classofArrayList.size(), classofArrayList.toArray(new String[classofArrayList.size()]));

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DummyResponse dummy = call.execute().body();
                            Log.d("saveAdditional", "저장 성공");
                            saveComplete = true;
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
                if(saveComplete){
                    Toast.makeText(getApplicationContext(), "예약 신청이 성공했습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LectureroomCheckActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "학번이 유효한지 확인해보세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}