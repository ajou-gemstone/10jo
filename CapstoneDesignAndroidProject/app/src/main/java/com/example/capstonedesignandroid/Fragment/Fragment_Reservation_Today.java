package com.example.capstonedesignandroid.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.capstonedesignandroid.Adapter.UserListAdapter;
import com.example.capstonedesignandroid.DTO.DummyReservationDetail;
import com.example.capstonedesignandroid.DTO.DummyReservationDetailGuard;
import com.example.capstonedesignandroid.DTO.DummyReservationList;
import com.example.capstonedesignandroid.DTO.DummyResponse;
import com.example.capstonedesignandroid.DTO.DummyStudentNameId;
import com.example.capstonedesignandroid.GetService;
import com.example.capstonedesignandroid.LectureroomCheckActivity;
import com.example.capstonedesignandroid.LectureroomCheckDetailedActivity;
import com.example.capstonedesignandroid.MyProfileActivity;
import com.example.capstonedesignandroid.R;
import com.example.capstonedesignandroid.StaticMethodAndOthers.DefinedMethod;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;
import com.example.capstonedesignandroid.UserProfileActivity;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_Reservation_Today extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 11111;
    ViewPager viewPager;
    private Retrofit retrofit;
    private ArrayList<DummyReservationList> dummyReservationListArrayList;
    private String resId;
    private DummyReservationDetail dummy;
    private FirebaseStorage storage;
    private Button takePictureButton1;
    private ImageView pictureImageView1;
    private Button transportPictureButton1;
    private Button takePictureButton2;
    private ImageView pictureImageView2;
    private Button transportPictureButton2;
    private Button cancelReservationButton;
    private TextView date;
    private TextView day;
    private TextView lectureroom;
    private TextView startTime;
    private TextView lastTime;
    private TextView reservationIntent;
    private TextView beforeUploadTime;
    private TextView afterUploadTime;
    private StorageReference storageRef;
    private boolean alreadyBefore;
    private boolean alreadyAfter;
    private int beforeOrAfter;
    private boolean before;
    private String imageFilePath1;
    private boolean after;
    private String imageFilePath2;
    private Uri photoUri1;
    private Uri photoUri2;
    private boolean thereisnoreservation = false;
    private View noReservationView;
    private LectureroomCheckActivity lectureroomCheckActivity;
    private boolean deleteReservation;
    private RatingBar scoreRatingBar;
    private DummyReservationDetailGuard guardDummy;;
    private TextView scoreDescription;
    private TextView scoreReasonEditText;
    private ArrayList<DummyStudentNameId> dummyStudentNameIdArrayList;

    public Fragment_Reservation_Today(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_lectureroom_checkdetailed, container, false);

        //----------------서버에서 받기 코드-------------------
        retrofit = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String tense = "today";
        String userid = SharedPreference.getAttribute(getContext(), "userId");
        Log.d("SharedPreference", " " + userid);

        GetService service = retrofit.create(GetService.class);
        Call<List<DummyReservationList>> call = service.getReservationList(tense, userid);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<DummyReservationList> dummies = call.execute().body();
                    dummyReservationListArrayList = new ArrayList<DummyReservationList>(dummies);
                    Log.d("run: runnn", "run: runnn");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("IOException: ", "IOException: ");
                    noReservationView = inflater.inflate(R.layout.there_is_no_reservation, container, false);
                    thereisnoreservation = true;
                }
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (Exception e) {

        }

        //받아오는데 오류면 예약 없음 화면
        if(thereisnoreservation){
            return noReservationView;
        }

        //예약이 없으면 바로 return
        if(dummyReservationListArrayList.size() == 0){
            noReservationView = inflater.inflate(R.layout.there_is_no_reservation, container, false);
            return noReservationView;
        }else{
            resId = dummyReservationListArrayList.get(0).getReservationId();
            Call<DummyReservationDetail> call2 = service.getReservationDetail(resId);
            Log.d("resId", "" + resId);

            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        dummy = call2.execute().body();
                        Log.d("getDetailed", "getDetailed");
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

            //----------------서버에서 받기 코드-------------------
            Log.d("retrofitget", " date: "+ dummy.getDate() + " day:"+dummy.getDay()+ " lectureroom: "+dummy.getLectureRoom()
                    + " startTime:" + dummy.getStartTime() + " lastTime:" + dummy.getLastTime() + " beforeUploadTime:" +
                    dummy.getBeforeUploadTime() + " afterUploadTime:" + dummy.getAfterUploadTime());

            //Todo: 유저 어댑터 코드 가져와서 쓰기

            Log.d("getFilesDir", "" + getContext().getFilesDir());
            Log.d("getPackageName", "" + getContext().getPackageName());
            Log.d("getExternalFilesDir", "" + getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));

            //모임원 정보 보기 리사이클러뷰는 스터디 액티비티 부분에서 재사용 한다.

            //------------초기 설정----------------
            takePictureButton1 = view.findViewById(R.id.takePictureButton1);
            pictureImageView1 = view.findViewById(R.id.pictureImageView1);
            transportPictureButton1 = view.findViewById(R.id.transportPictureButton1);

            takePictureButton2 = view.findViewById(R.id.takePictureButton2);
            pictureImageView2 = view.findViewById(R.id.pictureImageView2);
            transportPictureButton2 = view.findViewById(R.id.transportPictureButton2);

            cancelReservationButton = view.findViewById(R.id.cancelReservationButton);

            //예약 삭제하기
            cancelReservationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("정말로 예약을 취소하시겠습니까?").setMessage("강의실 사용 전 3시간 이내에 취소하면 패널티가 부과됩니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            int panelty;
                            if(DefinedMethod.compareTime2(DefinedMethod.getTimeByPosition(Integer.parseInt(dummy.getStartTime())))){
                                panelty = 1;
                            }else{
                                panelty = 0;
                            }
                            Log.d("panelty", "onClick: " + panelty);
                            GetService service = retrofit.create(GetService.class);
                            Call<DummyResponse> call = service.deleteMyReservation(resId, panelty);
                            //다시 화면을 그려준다.
                            lectureroomCheckActivity = (LectureroomCheckActivity) getActivity();
                            lectureroomCheckActivity.reInflateFragment("today");
                            deleteReservation = true;
                            call.enqueue(response1);
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

            date = view.findViewById(R.id.date);
            day = view.findViewById(R.id.day);
            lectureroom = view.findViewById(R.id.lectureroom);
            startTime = view.findViewById(R.id.startTime);
            lastTime = view.findViewById(R.id.lastTime);
            reservationIntent = view.findViewById(R.id.reservationIntent);
            beforeUploadTime = view.findViewById(R.id.beforeUploadTime);
            afterUploadTime = view.findViewById(R.id.afterUploadTime);

            //경비원 관리 정보 가져오기
            scoreRatingBar = view.findViewById(R.id.scoreRatingBar);
            scoreDescription = view.findViewById(R.id.scoreDescription);
            scoreReasonEditText = view.findViewById(R.id.scoreReasonEditText);

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

            pictureImageView1.setVisibility(View.GONE);
            pictureImageView2.setVisibility(View.GONE);

            if(!dummy.getBeforeUri().equals("")){
                //downloadUrl을 이용하여 이미지를 다운로드한다.
                StorageReference storageReference = storage.getReference(""+dummy.getBeforeUri());
                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            // Glide 이용하여 이미지뷰에 로딩
                            Glide.with(getContext())
                                    .load(task.getResult())
                                    .into(pictureImageView1);
                            alreadyBefore = true;
                            takePictureButton1.setVisibility(View.GONE);
                            pictureImageView1.setVisibility(View.VISIBLE);
                            transportPictureButton1.setText("제출 완료");
                            transportPictureButton1.setClickable(false);
                        } else {
                            Toast.makeText(getContext(), "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
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
                            Glide.with(getContext())
                                    .load(task.getResult())
                                    .into(pictureImageView2);
                            alreadyAfter = true;
                            takePictureButton2.setVisibility(View.GONE);
                            pictureImageView2.setVisibility(View.VISIBLE);
                            transportPictureButton2.setText("제출 완료");
                            transportPictureButton2.setClickable(false);
                        } else {
                            Toast.makeText(getContext(), "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            //------------초기 설정----------------
            //-----------------------------------------------------------------------

            takePictureButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(alreadyBefore){
                        Toast.makeText(getContext(),"이미 이미지를 업로드 했습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    beforeOrAfter = 1;
                    sendTakePhotoIntent();
                }
            });

            takePictureButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(alreadyAfter){
                        Toast.makeText(getContext(),"이미 이미지를 업로드 했습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    beforeOrAfter = 2;
                    sendTakePhotoIntent();
                }
            });

            pictureImageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    androidx.appcompat.app.AlertDialog.Builder alBuilder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
                    alBuilder.setMessage("다시 업로드하시나요?");
                    alBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            beforeOrAfter = 1;
                            sendTakePhotoIntent();
                        }
                    });
                    alBuilder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return; // 아무런 작업도 하지 않고 돌아간다
                        }
                    });
                    alBuilder.show();
                }
            });

            pictureImageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    androidx.appcompat.app.AlertDialog.Builder alBuilder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
                    alBuilder.setMessage("다시 업로드하시나요?");
                    alBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            beforeOrAfter = 2;
                            sendTakePhotoIntent();
                        }
                    });
                    alBuilder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return; // 아무런 작업도 하지 않고 돌아간다
                        }
                    });
                    alBuilder.show();
                }
            });

            transportPictureButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(alreadyBefore){
                        Toast.makeText(getContext(),"이미 이미지를 업로드 했습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!before){
                        Toast.makeText(getContext(), "사진을 찍어주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //photoUri는 content provider 경로이므로 file 경로로 재설정 해주어야 한다.
                    //content provider경로에서 file 경로로 재설정 하는 것은 굉장히 어렵기 때문에 이전에 구했던
                    //file경로인 externalFile경로를 이용하여 uri를 설정한다.
                    //실제 file path도
                    final Uri fileuri = Uri.fromFile(new File(imageFilePath1));//내장 데이터(앨범)의 uri를 가져옴
                    //파이어 베이스에 이미지를 업로드 하면서 node서버에 어떤 이미지가 어떤 사용자의 것인지도 추가를 해야 한다.
                    String firebasefileuri = "images/" + fileuri.getLastPathSegment();
                    StorageReference riversRef = storageRef.child(firebasefileuri);//저장소에 파일명으로 저장함
                    UploadTask uploadTask = riversRef.putFile(fileuri);//저장소에 파일을 넣음 + task로 처리하도록 함.(반환값이 task)

                    // Register observers to listen for when the download is done or if it fails
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(getContext(), "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {//firebase에서 자주 쓰이는 callback listener
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.

                            Toast.makeText(getContext(), "성공적으로 이미지 업로드가 되었습니다.", Toast.LENGTH_SHORT).show();
                            beforeUploadTime.setText("업로드 시간: "+DefinedMethod.getCurrentDate2());
                            //db에 파일 이름 저장
                            GetService service = retrofit.create(GetService.class);
                            Call<DummyResponse> call = service.postBeforePicture(resId, "/"+firebasefileuri, DefinedMethod.getCurrentDate2());
                            call.enqueue(response2);
                        }
                    });
                }
            });

            transportPictureButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(alreadyAfter){
                        Toast.makeText(getContext(),"이미 이미지를 업로드 했습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!after){
                        Toast.makeText(getContext(), "사진을 찍어주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //photoUri는 content provider 경로이므로 file 경로로 재설정 해주어야 한다.
                    //content provider경로에서 file 경로로 재설정 하는 것은 굉장히 어렵기 때문에 이전에 구했던
                    //file경로인 externalFile경로를 이용하여 uri를 설정한다.
                    //실제 file path도
                    final Uri fileuri = Uri.fromFile(new File(imageFilePath2));//내장 데이터(앨범)의 uri를 가져옴
                    //파이어 베이스에 이미지를 업로드 하면서 node서버에 어떤 이미지가 어떤 사용자의 것인지도 추강를 해야 한다.
                    String firebasefileuri = "images/" + fileuri.getLastPathSegment();
                    StorageReference riversRef = storageRef.child(firebasefileuri);//저장소에 파일명으로 저장함
                    UploadTask uploadTask = riversRef.putFile(fileuri);//저장소에 파일을 넣음 + task로 처리하도록 함.(반환값이 task)

                    // Register observers to listen for when the download is done or if it fails
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(getContext(), "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {//firebase에서 자주 쓰이는 callback listener
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            Toast.makeText(getContext(), "성공적으로 이미지 업로드가 되었습니다,", Toast.LENGTH_SHORT).show();
                            afterUploadTime.setText("업로드 시간: "+DefinedMethod.getCurrentDate2());
                            //db에 파일 이름 저장
                            GetService service = retrofit.create(GetService.class);
                            Call<DummyResponse> call = service.postAfterPicture(resId, "/"+firebasefileuri, DefinedMethod.getCurrentDate2());
                            call.enqueue(response3);
                        }
                    });
                }
            });
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
        ListView listview = (ListView) view.findViewById(R.id.memberlistview);
        listview.setAdapter(userListAdapter);

        //가입된 유저 하나하나 눌렀을 때
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(getContext(), MyProfileActivity.class);
                intent2.putExtra("leaderormember", "0");
                intent2.putExtra("userId", dummyStudentNameIdArrayList.get(position).getUserId());
                intent2.putExtra("fromReadgroup", "true");
                startActivity(intent2);
            }
        });

        return view;
    }

    private Callback<DummyResponse> response1 = new Callback<DummyResponse>() {
        @Override
        public void onResponse(Call<DummyResponse> call, Response<DummyResponse> response) {
            if (response.isSuccessful()) {
                DummyResponse dummy = response.body();
                Log.d("response success", "");
                if(deleteReservation){
                    lectureroomCheckActivity = (LectureroomCheckActivity) getActivity();
                    lectureroomCheckActivity.reInflateFragment("today");
                }
            } else {
                Log.d("onResponse:", "Fail, " + String.valueOf(response.code()));
            }
        }

        @Override
        public void onFailure(Call<DummyResponse> call, Throwable t) {
            if(deleteReservation){
                lectureroomCheckActivity = (LectureroomCheckActivity) getActivity();
                lectureroomCheckActivity.reInflateFragment("today");
            }
            Log.d("responsefail..", "onFailure: ");

        }
    };

    private Callback<DummyResponse> response2 = new Callback<DummyResponse>() {
        @Override
        public void onResponse(Call<DummyResponse> call, Response<DummyResponse> response) {
            if (response.isSuccessful()) {
                DummyResponse dummy = response.body();
                Log.d("response success", "");
                if(deleteReservation){
                    lectureroomCheckActivity = (LectureroomCheckActivity) getActivity();
                    lectureroomCheckActivity.reInflateFragment("today");
                }
            } else {
                Log.d("onResponse:", "Fail, " + String.valueOf(response.code()));
            }
        }

        @Override
        public void onFailure(Call<DummyResponse> call, Throwable t) {
            if(deleteReservation){
                lectureroomCheckActivity = (LectureroomCheckActivity) getActivity();
                lectureroomCheckActivity.reInflateFragment("today");
            }
            Log.d("responsefail..", "onFailure: ");

        }
    };

    private Callback<DummyResponse> response3 = new Callback<DummyResponse>() {
        @Override
        public void onResponse(Call<DummyResponse> call, Response<DummyResponse> response) {
            if (response.isSuccessful()) {
                DummyResponse dummy = response.body();
                Log.d("response success", "");
                if(deleteReservation){
                    lectureroomCheckActivity = (LectureroomCheckActivity) getActivity();
                    lectureroomCheckActivity.reInflateFragment("today");
                }
            } else {
                Log.d("onResponse:", "Fail, " + String.valueOf(response.code()));
            }
        }

        @Override
        public void onFailure(Call<DummyResponse> call, Throwable t) {
            if(deleteReservation){
                lectureroomCheckActivity = (LectureroomCheckActivity) getActivity();
                lectureroomCheckActivity.reInflateFragment("today");
            }
            Log.d("responsefail..", "onFailure: ");
        }
    };

    //사진을 찍는 인텐트를 실행하고, 인텐트 환경 설정을 한다.
    private void sendTakePhotoIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {

                if(beforeOrAfter == 1){
                    photoUri1 = FileProvider.getUriForFile(getActivity().getApplicationContext(), getActivity().getPackageName(), photoFile);
                    Log.d("photoUri", "" + photoUri1);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri1);
                }else if(beforeOrAfter == 2){
                    photoUri2 = FileProvider.getUriForFile(getActivity().getApplicationContext(), getActivity().getPackageName(), photoFile);
                    Log.d("photoUri", "" + photoUri2);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri2);
                }

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";

        //외부 저장 경로를 사용하지만 sdcard가 없어도 emulated가 되어서 가상의(심볼릭) 경로이고
        //나중에 provider를 통하여 사전에 지정한(files_paths.xml에 있음) 실제 기기에서 저장되는 경로에 파일을 지정해주어서
        //camera intent에서 좋은 화질의 사진을 실제 디렉토리에 저장 할 수 있도록 한다.
        //camera intent로 takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)을 추가하여 실제로 저장할 수 있도록 한다.
        //camera intent에서 카메라는 file path를 사용하지 않고 content provider path를 쓴다는 것을 유념하자.
        //(왜 그럴까?) 접근할 때는 어차피 external file path를 이용하기 때문에 나머지는 file path를 이용하여 처리하면 된다.

        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //내부 저장 경로를 사용할 경우 화질의 문제가 생기기 때문에 외부 저장 경로를 활용한다.
        //또한 provider를 활용하지 못한다. (내부 저장 경로는 앱에 종속되기 때문에)
//        File storageDir = getFilesDir();
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".png",         /* suffix */
                storageDir          /* directory */
        );

        if(beforeOrAfter == 1){
            imageFilePath1 = image.getAbsolutePath();
            Log.d("imageFilePath", "" + imageFilePath1);
        }else if(beforeOrAfter == 2){
            imageFilePath2 = image.getAbsolutePath();
            Log.d("imageFilePath", "" + imageFilePath2);
        }

        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            if(beforeOrAfter == 1){
                pictureImageView1.setVisibility(View.VISIBLE);
                pictureImageView1.setImageURI(photoUri1);
                takePictureButton1.setVisibility(View.GONE);
                before = true;
            }else if(beforeOrAfter == 2){
                pictureImageView2.setVisibility(View.VISIBLE);
                pictureImageView2.setImageURI(photoUri2);
                takePictureButton2.setVisibility(View.GONE);
                after = true;
            }
        }
    }
}