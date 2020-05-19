package com.example.capstonedesignandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.capstonedesignandroid.DTO.Dummy;
import com.example.capstonedesignandroid.DTO.DummyLectureRoomReservationState;
import com.example.capstonedesignandroid.DTO.DummyReservationDetail;
import com.example.capstonedesignandroid.DTO.DummyReservationDetailGuard;
import com.example.capstonedesignandroid.DTO.DummyResponse;
import com.example.capstonedesignandroid.StaticMethodAndOthers.DefinedMethod;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManageReservationActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private GetService service;
    private DummyReservationDetail dummy;
    private boolean IOexception;
    private RatingBar ratingBar;
    private TextView ratingTextView;
    private Button rateSaveButton;
    private EditText scoreReasonEditText;
    private DummyReservationDetailGuard dummy2;
    private float currentScore;
    private View date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_reservation);

        Intent intent = getIntent();
        String resId = intent.getStringExtra("reservationId");
        String lecId = intent.getStringExtra("lectureRoomId");

        retrofit = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(GetService.class);

        //하나의 예약 내역 가져오기 (재사용)
        Call<DummyReservationDetail> call = service.getReservationDetail(resId);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dummy = call.execute().body();
                    IOexception = false;
                    Log.d("retrofitget", " date: "+ dummy.getDate() + " day:"+dummy.getDay()+ " lectureroom: "+dummy.getLectureRoom()
                            + " startTime:" + dummy.getStartTime() + " lastTime:" + dummy.getLastTime() + " beforeUploadTime:" +
                            dummy.getBeforeUploadTime() + " afterUploadTime:" + dummy.getAfterUploadTime());
                } catch (IOException e) {
                    e.printStackTrace();
                    IOexception = true;
                    Log.d("IOException: ", "IOException: ");
                }
            }
        });

        //경비원 관리 정보 가져오기
        Call<DummyReservationDetailGuard> call2 = service.getReservationDetailGuard(resId);

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dummy2 = call2.execute().body();
                    IOexception = false;
                    Log.d("retrofitget2", " leaderId: "+ dummy2.getLeaderId() + " score:"+dummy2.getScore()+ " scoreReason: "+dummy2.getScoreReason()
                            + " guardId:" + dummy2.getGuardId());
                } catch (IOException e) {
                    e.printStackTrace();
                    IOexception = true;
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

//        mockup data로 대체
        if(IOexception){
            String[] userids = {"user1", "user2", "user3"};
            dummy = new DummyReservationDetail("2020-05-01", "월", "2", "6", "성101",
                    userids, "/images/TEST_20200412_190805_1263752076698054948.png",
                    "/images/TEST_20200427_203734_8578526890362646423.png", "studying algorithm",
                    "2020-05-01 08:05", "2020-05-01 10:23");
        }

        thread2.start();
        try {
            thread2.join();
        } catch (Exception e) {
            // TODO: handle exception
        }
        //        mockup data로 대체
        if(IOexception){
            dummy2 = new DummyReservationDetailGuard("10", "5.0", "", "2");
        }


        //----------------초기화------------------
        ratingBar = findViewById(R.id.ratingBar);
        ratingTextView = findViewById(R.id.ratingTextView);
        scoreReasonEditText = findViewById(R.id.scoreReasonEditText);
        rateSaveButton = findViewById(R.id.rateSaveButton);

        //이미 강의실 예약 점수가 있는 경우 예약 점수를 바꿀 수 없다.
        if(!(dummy2.getScore()).equals("")){
            ratingBar.setRating(Integer.parseInt(dummy2.getScore()));
            ratingTextView.setText(dummy2.getScore());
            scoreReasonEditText.setText(dummy2.getScoreReason());
            ratingBar.setEnabled(false);
            rateSaveButton.setEnabled(false);
            scoreReasonEditText.setEnabled(false);
        }

        //기본 뷰들 초기화
//        date = findViewById(R.id.date);
//        day = findViewById(R.id.day);
//        lectureroom = findViewById(R.id.lectureroom);
//        startTime = findViewById(R.id.startTime);
//        lastTime = findViewById(R.id.lastTime);
//        reservationIntent = findViewById(R.id.reservationIntent);
//        beforeUploadTime = findViewById(R.id.beforeUploadTime);
//        afterUploadTime = findViewById(R.id.afterUploadTime);
//
//        date.setText(""+ DefinedMethod.getParsedDate(dummy.getDate()));
//        day.setText(""+DefinedMethod.getDayNamebyAlpabet(dummy.getDay()));
//        lectureroom.setText(""+dummy.getLectureRoom());
//        startTime.setText(""+ DefinedMethod.getTimeByPosition(Integer.parseInt(dummy.getStartTime())));
//        lastTime.setText(""+DefinedMethod.getTimeByPosition(Integer.parseInt(dummy.getLastTime())));
//        reservationIntent.setText(""+dummy.getReservationIntent());
//        beforeUploadTime.setText("업로드 시간: "+dummy.getBeforeUploadTime());
//        afterUploadTime.setText("업로드 시간: "+dummy.getAfterUploadTime());
        //----------------초기화------------------

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingTextView.setText(""+ v);
                currentScore = v;
                if(v <= 1){
                    scoreReasonEditText.setVisibility(View.VISIBLE);
                }else{
                    scoreReasonEditText.setVisibility(View.INVISIBLE);
                }
            }
        });

        String tmpguardId = "2";

        //평가내역 save
        rateSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<DummyResponse> call3 = service.postSaveReservationDetailGuard(resId, currentScore, scoreReasonEditText.getText().toString(),
                        dummy2.getLeaderId(), tmpguardId);
                call3.enqueue(response);
            }
        });
    }

    Callback<DummyResponse> response = new Callback<DummyResponse>() {
        @Override
        public void onResponse(Call<DummyResponse> call, Response<DummyResponse> response) {
            if (response.isSuccessful()) {
                DummyResponse dummy = response.body();
                Log.d("onResponse", ""+dummy.getResponse());
            } else {
                Log.d("onResponse:", "Fail, " + String.valueOf(response.code()));
            }
        }

        @Override
        public void onFailure(Call<DummyResponse> call, Throwable t) {
            Log.d("response fail", "onFailure: ");

        }
    };
}
