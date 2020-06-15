package com.example.capstonedesignandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.capstonedesignandroid.DTO.Dummy;
import com.example.capstonedesignandroid.DTO.DummyLectureRoomReservationState;
import com.example.capstonedesignandroid.DTO.DummyReservationDetail;
import com.example.capstonedesignandroid.DTO.DummyReservationDetailGuard;
import com.example.capstonedesignandroid.DTO.DummyResponse;
import com.example.capstonedesignandroid.StaticMethodAndOthers.DefinedMethod;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    private TextView date;
    private TextView day;
    private TextView lectureroom;
    private TextView startTime;
    private TextView lastTime;
    private TextView reservationIntent;
    private TextView beforeUploadTime;
    private TextView afterUploadTime;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private ImageView pictureImageView1;
    private ImageView pictureImageView2;

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

        }
        //        mockup data로 대체
        if(IOexception){
            dummy2 = new DummyReservationDetailGuard("10", "5.0", "", "2");
        }

        //----------------초기화------------------
        pictureImageView1 = findViewById(R.id.pictureImageView1);
        pictureImageView2 = findViewById(R.id.pictureImageView2);

        date = findViewById(R.id.date);
        day = findViewById(R.id.day);
        lectureroom = findViewById(R.id.lectureroom);
        startTime = findViewById(R.id.startTime);
        lastTime = findViewById(R.id.lastTime);
        reservationIntent = findViewById(R.id.reservationIntent);
        beforeUploadTime = findViewById(R.id.beforeUploadTime);
        afterUploadTime = findViewById(R.id.afterUploadTime);

        date.setText(""+DefinedMethod.getParsedDate(dummy.getDate()));
        day.setText(""+DefinedMethod.getDayNamebyAlpabet(dummy.getDay()));
        lectureroom.setText(""+dummy.getLectureRoom());
        startTime.setText(""+ DefinedMethod.getTimeByPosition(Integer.parseInt(dummy.getStartTime())));
        lastTime.setText(""+DefinedMethod.getTimeByPosition(Integer.parseInt(dummy.getLastTime())+1));
        if(DefinedMethod.isEmpty(dummy.getReservationIntent())){
            reservationIntent.setText("");
        }else{
            reservationIntent.setText(""+dummy.getReservationIntent());
        }
        if(DefinedMethod.isEmpty(dummy.getBeforeUploadTime()) || dummy.getBeforeUploadTime().length() < 4){
            beforeUploadTime.setText("업로드 되지 않음");
        }else{
            beforeUploadTime.setText(dummy.getBeforeUploadTime());
        }
        if(DefinedMethod.isEmpty(dummy.getBeforeUploadTime()) || dummy.getAfterUploadTime().length() < 4){
            afterUploadTime.setText("업로드 되지 않음");
        }else{
            afterUploadTime.setText(dummy.getAfterUploadTime());
        }

        ratingBar = findViewById(R.id.ratingBar);
        ratingTextView = findViewById(R.id.ratingTextView);
        scoreReasonEditText = findViewById(R.id.scoreReasonEditText);
        rateSaveButton = findViewById(R.id.rateSaveButton);

        //이미 강의실 예약 점수가 있는 경우 예약 점수를 바꿀 수 없다.
        if(!(dummy2.getScore()).equals("")){
            ratingBar.setRating(Integer.parseInt(dummy2.getScore()));
            ratingTextView.setText(dummy2.getScore());
            Log.d("getScoreReason", "onCreate: " + dummy2.getScoreReason());
            scoreReasonEditText.setText(dummy2.getScoreReason());
            ratingBar.setEnabled(false);
            rateSaveButton.setEnabled(false);
            scoreReasonEditText.setEnabled(false);
        }

        if(DefinedMethod.isEmpty(dummy2.getScoreReason())){
            scoreReasonEditText.setVisibility(View.GONE);
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingTextView.setText(""+ v);
                currentScore = v;
                if(v < 0.5){
                    ratingBar.setRating(1);
                }
                if(v <= 1){
                    scoreReasonEditText.setVisibility(View.VISIBLE);
                }else{
                    scoreReasonEditText.setVisibility(View.INVISIBLE);
                }
            }
        });

        String tmpguardId = "8";

        //평가내역 save
        rateSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentScore <= 1 && scoreReasonEditText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "점수를 낮게 준 이유를 작성해주세요.", Toast.LENGTH_LONG).show();
                    return;
                }
                AlertDialog.Builder alBuilder = new AlertDialog.Builder(ManageReservationActivity.this);
                alBuilder.setMessage("평가를 저장하시겠습니까?");
                alBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<DummyResponse> call3 = service.postSaveReservationDetailGuard(resId, currentScore, scoreReasonEditText.getText().toString(),
                                dummy2.getLeaderId(), tmpguardId);
                        call3.enqueue(response);
                    }
                });
                alBuilder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alBuilder.setTitle("평가 저장");
                alBuilder.show();
            }
        });

        //app에 등록된 firebase storage의 instance를 가져온다. (싱글톤)
        storage = FirebaseStorage.getInstance("gs://asmr-799cf.appspot.com");

        //스토리지의 레퍼런스(주소)를 가져온다.
        storageRef = storage.getReference();

        if(!dummy.getBeforeUri().equals("")){
            //downloadUrl을 이용하여 이미지를 다운로드한다.
            StorageReference storageReference = storage.getReference(""+dummy.getBeforeUri());
            storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // Glide 이용하여 이미지뷰에 로딩
                        Glide.with(getApplicationContext())
                                .load(task.getResult())
                                .into(pictureImageView1);
                    } else {
                        Toast.makeText(getApplicationContext(), "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if(!dummy.getAfterUri().equals("")){
            //downloadUrl을 이용하여 이미지를 다운로드한다.
            StorageReference storageReference = storage.getReference(""+dummy.getAfterUri());
            storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // Glide 이용하여 이미지뷰에 로딩
                        Glide.with(getApplicationContext())
                                .load(task.getResult())
                                .into(pictureImageView2);
                    } else {
                        Toast.makeText(getApplicationContext(), "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
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
