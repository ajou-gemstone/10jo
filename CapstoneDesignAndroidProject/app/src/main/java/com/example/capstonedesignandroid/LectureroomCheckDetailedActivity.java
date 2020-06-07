package com.example.capstonedesignandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.capstonedesignandroid.Adapter.UserListAdapter;
import com.example.capstonedesignandroid.DTO.DummyReservationDetail;
import com.example.capstonedesignandroid.DTO.DummyReservationDetailGuard;
import com.example.capstonedesignandroid.DTO.DummyResponse;
import com.example.capstonedesignandroid.DTO.DummyStudentNameId;
import com.example.capstonedesignandroid.StaticMethodAndOthers.DefinedMethod;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//사진은 내부 저장소
public class LectureroomCheckDetailedActivity extends AppCompatActivity {

    private static final int CAPTURE_IMAGE = 99;
    private static final int REQUEST_IMAGE_CAPTURE = 11111;
    private Button takePictureButton1;
    private ImageView pictureImageView1;
    private Bitmap bitmap;
    private Button transportPictureButton1;
    private Bitmap img = null;
    private String imageFilePath1;
    private Uri photoUri1;
    private Uri photoUri2;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private String resId;
    private Retrofit retrofit;
    private boolean IOexception = true;
    private DummyReservationDetail dummy;
    private Button takePictureButton2;
    private ImageView pictureImageView2;
    private Button transportPictureButton2;
    private boolean before = false;
    private boolean after = false;
    private int beforeOrAfter = -1;
    private String imageFilePath2;
    private TextView date;
    private TextView day;
    private TextView lectureroom;
    private TextView startTime;
    private TextView lastTime;
    private boolean alreadyBefore = false;
    private boolean alreadyAfter = false;
    private TextView reservationIntent;
    private TextView beforeUploadTime;
    private TextView afterUploadTime;
    private Button cancelReservationButton;
    private String tense;
    private RatingBar scoreRatingBar;
    private TextView scoreDescription;
    private EditText scoreReasonEditText;
    private DummyReservationDetailGuard guardDummy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectureroom_checkdetailed);

        Intent intent = getIntent();
        resId = intent.getExtras().getString("reservationId");
        tense = intent.getExtras().getString("tense");

        Log.d("resId", ""+resId);

        //----------------서버에서 받기 코드-------------------
        retrofit = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetService service = retrofit.create(GetService.class);
        Call<DummyReservationDetail> call = service.getReservationDetail(resId);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dummy = call.execute().body();
                    IOexception = false;
                    Log.d("getDetailed", "getDetailed");
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

        //----------------서버에서 받기 코드-------------------

        Log.d("retrofitget", " date: "+ dummy.getDate() + " day:"+dummy.getDay()+ " lectureroom: "+dummy.getLectureRoom()
                + " startTime:" + dummy.getStartTime() + " lastTime:" + dummy.getLastTime() + " beforeUploadTime:" +
                dummy.getBeforeUploadTime() + " afterUploadTime:" + dummy.getAfterUploadTime());

//        mockup data로 대체
        if(IOexception){
            String[] userids = {"user1", "user2", "user3"};
            dummy = new DummyReservationDetail("2020-05-01", "월", "2", "6", "성101",
                    userids, "/images/TEST_20200412_190805_1263752076698054948.png",
                    "/images/TEST_20200427_203734_8578526890362646423.png", "studying algorithm",
                    "2020-05-01 08:05", "2020-05-01 10:23");
        }

        Log.d("getFilesDir", "" + getFilesDir());
        Log.d("getPackageName", "" + getPackageName());
        Log.d("getExternalFilesDir", "" + getExternalFilesDir(Environment.DIRECTORY_PICTURES));

        //모임원 정보 보기 리사이클러뷰는 스터디 액티비피 부분에서 재사용 한다.

        //------------초기 설정----------------
        takePictureButton1 = findViewById(R.id.takePictureButton1);
        pictureImageView1 = findViewById(R.id.pictureImageView1);
        transportPictureButton1 = findViewById(R.id.transportPictureButton1);

        takePictureButton2 = findViewById(R.id.takePictureButton2);
        pictureImageView2 = findViewById(R.id.pictureImageView2);
        transportPictureButton2 = findViewById(R.id.transportPictureButton2);

        cancelReservationButton = findViewById(R.id.cancelReservationButton);

        //예약 삭제하기
        cancelReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LectureroomCheckDetailedActivity.this);
                builder.setTitle("정말로 예약을 취소하시겠습니까?").setMessage("강의실 사용 전 3시간 이내에 취소하면 패널티가 부과됩니다.");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        GetService service = retrofit.create(GetService.class);
                        Call<DummyResponse> call = service.deleteMyReservation(resId, 0);
                        call.enqueue(response);
                        //화면을 다시 그려준다.
                        Intent intent = new Intent(getApplicationContext(), LectureroomCheckActivity.class);
                        startActivity(intent);
                        Toast toast = Toast.makeText(getApplicationContext(), "예약이 취소되었습니다.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        finish();
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        date = findViewById(R.id.date);
        day = findViewById(R.id.day);
        lectureroom = findViewById(R.id.lectureroom);
        startTime = findViewById(R.id.startTime);
        lastTime = findViewById(R.id.lastTime);
        reservationIntent = findViewById(R.id.reservationIntent);
        beforeUploadTime = findViewById(R.id.beforeUploadTime);
        afterUploadTime = findViewById(R.id.afterUploadTime);

        //경비원 관리 정보 가져오기
        scoreRatingBar = findViewById(R.id.scoreRatingBar);
        scoreDescription = findViewById(R.id.scoreDescription);
        scoreReasonEditText = findViewById(R.id.scoreReasonEditText);

        Call<DummyReservationDetailGuard> guardCall = service.getReservationDetailGuard(resId);
        Thread guardCallThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    guardDummy = guardCall.execute().body();
                    Log.d("retrofitget2", " leaderId: "+ guardDummy.getLeaderId() + " score:"+guardDummy.getScore()+ " scoreReason: "+guardDummy.getScoreReason()
                            + " guardId:" + guardDummy.getGuardId());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("IOException: ", "IOException: ");
                }
            }
        });

        guardCallThread.start();
        try {
            guardCallThread.join();
        } catch (Exception e) {
        }

        if(!DefinedMethod.isEmpty(guardDummy.getScore())){
            scoreRatingBar.setRating(Integer.parseInt(guardDummy.getScore()));
            scoreDescription.setText(guardDummy.getScore());
        }
        if(guardDummy.getScoreReason().equals("")){
            scoreReasonEditText.setVisibility(View.GONE);
        }else{
            scoreReasonEditText.setText(guardDummy.getScoreReason());
        }
        scoreRatingBar.setEnabled(false);
        scoreReasonEditText.setEnabled(false);

        date.setText(""+DefinedMethod.getParsedDate(dummy.getDate()));
        day.setText(""+DefinedMethod.getDayNamebyAlpabet(dummy.getDay()));
        lectureroom.setText(""+dummy.getLectureRoom());
        startTime.setText(""+ DefinedMethod.getTimeByPosition(Integer.parseInt(dummy.getStartTime())));
        lastTime.setText(""+DefinedMethod.getTimeByPosition(Integer.parseInt(dummy.getLastTime())+1));
        reservationIntent.setText(""+dummy.getReservationIntent());
        if(dummy.getBeforeUploadTime().length() < 4){
            beforeUploadTime.setText("업로드 되지 않음");
        }else{
            beforeUploadTime.setText(dummy.getBeforeUploadTime());
        }
        if(dummy.getAfterUploadTime().length() < 4){
            afterUploadTime.setText("업로드 되지 않음");
        }else{
            afterUploadTime.setText(dummy.getAfterUploadTime());
        }

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
                        alreadyBefore = true;
                        takePictureButton1.setVisibility(View.GONE);
                        pictureImageView1.setVisibility(View.VISIBLE);
                        transportPictureButton1.setText("제출 완료");
                        transportPictureButton1.setClickable(false);
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
                        alreadyAfter = true;
                        takePictureButton2.setVisibility(View.GONE);
                        pictureImageView2.setVisibility(View.VISIBLE);
                        transportPictureButton2.setText("제출 완료");
                        transportPictureButton2.setClickable(false);
                    } else {
                        Toast.makeText(getApplicationContext(), "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        //------------초기 설정----------------
        //-----------------------------------------------------------------------

        //미래, 과거의 예약일 때
        if(tense.equals("future") || tense.equals("past")){
            takePictureButton1.setBackgroundResource(R.drawable.camera_grey);
            takePictureButton2.setBackgroundResource(R.drawable.camera_grey);
            takePictureButton1.setClickable(false);
            takePictureButton2.setClickable(false);

            transportPictureButton1.setVisibility(View.GONE);
            transportPictureButton2.setVisibility(View.GONE);
            pictureImageView1.setVisibility(View.GONE);
            pictureImageView2.setVisibility(View.GONE);
        }
        if(tense.equals("past")) {//과거의 예약일 때
            cancelReservationButton.setVisibility(View.INVISIBLE);
        }
        //강의실 사용 인원 리스트
        String[] usersName = dummy.getUserName();
        String[] usersId = dummy.getUserId();

        //mockup data
        ArrayList<DummyStudentNameId> dummyStudentNameIdArrayList = new ArrayList<>();
        for(int i = 0; i < usersName.length; i++){
            dummyStudentNameIdArrayList.add(new DummyStudentNameId(usersId[i], usersName[i]));
        }

        UserListAdapter userListAdapter = new UserListAdapter();
        for(DummyStudentNameId d : dummyStudentNameIdArrayList){
            userListAdapter.add(d.getUserId(), 0, d.getName());
        }
        ListView listview = (ListView) findViewById(R.id.memberlistview);
        listview.setAdapter(userListAdapter);

        //가입된 유저 하나하나 눌렀을 때
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(getApplicationContext(), UserProfileActivity.class);
                intent2.putExtra("leaderormember", 0);
                intent2.putExtra("userId", dummyStudentNameIdArrayList.get(position).getUserId());
                startActivity(intent2);
            }
        });

    }

    Callback<DummyResponse> response = new Callback<DummyResponse>() {
        @Override
        public void onResponse(Call<DummyResponse> call, Response<DummyResponse> response) {
            if (response.isSuccessful()) {
                DummyResponse dummy = response.body();
                Log.d("response success", "");
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
