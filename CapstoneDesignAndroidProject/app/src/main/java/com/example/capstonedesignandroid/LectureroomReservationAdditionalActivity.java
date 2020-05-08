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
import com.example.capstonedesignandroid.DTO.DummyResponse;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LectureroomReservationAdditionalActivity extends AppCompatActivity {

    private String resId;
    private RecyclerView classofRecyclerView;
    private ArrayList<String> classofArrayList;
    private EditText classofEdittext;
    private Button classofAddButton;
    private Button saveReservationDescButton;
    private Retrofit retrofit;
    private EditText reservationIntentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectureroom_reservation_additional);

        Intent intent = getIntent();
        resId = intent.getExtras().getString("reservationId");

        reservationIntentEditText = findViewById(R.id.reservationIntentEditText);
        classofEdittext = findViewById(R.id.classofEdittext);
        classofAddButton = findViewById(R.id.classofAddButton);

        //추가, 삭제되는 edittext + recyclerview구현
        classofRecyclerView = findViewById(R.id.classofRecyclerView);
        classofRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        classofArrayList = new ArrayList<String>();
        ClassofAdapter adapter = new ClassofAdapter(classofArrayList);
        classofRecyclerView.setAdapter(adapter);

        classofAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classofArrayList.add("" + classofEdittext.getText());
                classofEdittext.setText("");
                adapter.notifyDataSetChanged();
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

        retrofit = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        saveReservationDescButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                            Toast.makeText(getApplicationContext(), "저장 성공", Toast.LENGTH_LONG).show();
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
                    // TODO: handle exception
                }
            }
        });
    }
}