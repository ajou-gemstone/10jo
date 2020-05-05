package com.example.capstonedesignandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.capstonedesignandroid.Adapter.ClassofAdapter;
import com.example.capstonedesignandroid.Adapter.ReservationAdapter;
import com.example.capstonedesignandroid.DTO.Dummy;
import com.example.capstonedesignandroid.DTO.DummyReservationList;
import com.example.capstonedesignandroid.DTO.DummyResponse;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;

import java.util.ArrayList;
import java.util.List;

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
                classofArrayList.add(""+classofEdittext.getText());
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
                Call<DummyResponse> call = service.postReservationDetail(resId, ""+reservationIntentEditText.getText(),
                        classofArrayList.size(), classofArrayList.toArray(new String[classofArrayList.size()]));

                //        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    List<DummyReservationList> dummies = call.execute().body();
//                    dummyReservationListArrayList = new ArrayList<DummyReservationList>(dummies);
//                    IOexception = false;
//                    Log.d("run: ", "run: ");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    IOexception = true;
//                    Log.d("IOException: ", "IOException: ");
//                }
//            }
//        });
//        thread.start();
//        try {
//            thread.join();
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
                //----------------서버에서 받기 코드-------------------
                //출력: {reservationId: "reservationId", date: "YYYY-MM-DD", day(요일): "월", startTime: "8:00", lastTime:"10:00", lectureRoom:"성101"}

            }
        });
    }
}
