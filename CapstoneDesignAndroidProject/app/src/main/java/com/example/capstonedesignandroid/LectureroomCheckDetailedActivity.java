package com.example.capstonedesignandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.capstonedesignandroid.DTO.DummyReservationDetail;
import com.example.capstonedesignandroid.DTO.DummyResponse;
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
            // TODO: handle exception
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

        //Todo: 유저 어댑터 코드 가져와서 쓰기

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
                        Call<DummyResponse> call = service.deleteMyReservation(resId);
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

        date.setText(""+DefinedMethod.getParsedDate(dummy.getDate()));
        day.setText(""+DefinedMethod.getDayNamebyAlpabet(dummy.getDay()));
        lectureroom.setText(""+dummy.getLectureRoom());
        startTime.setText(""+ DefinedMethod.getTimeByPosition(Integer.parseInt(dummy.getStartTime())));
        lastTime.setText(""+DefinedMethod.getTimeByPosition(Integer.parseInt(dummy.getLastTime())+1));
        reservationIntent.setText(""+dummy.getReservationIntent());
        beforeUploadTime.setText("업로드 시간: "+dummy.getBeforeUploadTime());
        afterUploadTime.setText("업로드 시간: "+dummy.getAfterUploadTime());

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
                    } else {
                        Toast.makeText(getApplicationContext(), "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(),"이미 이미지를 업로드 했습니다.", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(),"이미 이미지를 업로드 했습니다.", Toast.LENGTH_LONG).show();
                    return;
                }
                beforeOrAfter = 2;
                sendTakePhotoIntent();
            }
        });

        transportPictureButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alreadyBefore){
                    Toast.makeText(getApplicationContext(),"이미 이미지를 업로드 했습니다.", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!before){
                    Toast.makeText(getApplicationContext(), "사진을 찍어주세요.", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(), "이미지 업로드 실패", Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {//firebase에서 자주 쓰이는 callback listener
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.

                        Toast.makeText(getApplicationContext(), "성공적으로 이미지 업로드가 되었습니다.", Toast.LENGTH_LONG).show();
                        beforeUploadTime.setText("업로드 시간: "+DefinedMethod.getCurrentDate2());
                        //db에 파일 이름 저장
                        GetService service = retrofit.create(GetService.class);
                        Call<DummyResponse> call = service.postBeforePicture(resId, "/"+firebasefileuri, DefinedMethod.getCurrentDate2());
                        call.enqueue(response);
                    }
                });
            }
        });

        transportPictureButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alreadyAfter){
                    Toast.makeText(getApplicationContext(),"이미 이미지를 업로드 했습니다.", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!after){
                    Toast.makeText(getApplicationContext(), "사진을 찍어주세요.", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(), "이미지 업로드 실패", Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {//firebase에서 자주 쓰이는 callback listener
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        Toast.makeText(getApplicationContext(), "성공적으로 이미지 업로드가 되었습니다,", Toast.LENGTH_LONG).show();
                        afterUploadTime.setText("업로드 시간: "+DefinedMethod.getCurrentDate2());
                        //db에 파일 이름 저장
                        GetService service = retrofit.create(GetService.class);
                        Call<DummyResponse> call = service.postAfterPicture(resId, "/"+firebasefileuri, DefinedMethod.getCurrentDate2());
                        call.enqueue(response);
                    }
                });
            }
        });

        //미래, 과거의 예약일 때
        if(tense.equals("future") || tense.equals("past")){
            takePictureButton1.setVisibility(View.INVISIBLE);
            transportPictureButton1.setVisibility(View.INVISIBLE);
            takePictureButton2.setVisibility(View.INVISIBLE);
            transportPictureButton2.setVisibility(View.INVISIBLE);
        }
        if(tense.equals("past")) {//과거의 예약일 때
            cancelReservationButton.setVisibility(View.INVISIBLE);
        }
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

    //사진을 찍는 인텐트를 실행하고, 인텐트 환경 설정을 한다.
    private void sendTakePhotoIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {

                if(beforeOrAfter == 1){
                    photoUri1 = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    Log.d("photoUri", "" + photoUri1);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri1);
                }else if(beforeOrAfter == 2){
                    photoUri2 = FileProvider.getUriForFile(this, getPackageName(), photoFile);
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

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if(beforeOrAfter == 1){
                pictureImageView1.setImageURI(photoUri1);
                before = true;
            }else if(beforeOrAfter == 2){
                pictureImageView2.setImageURI(photoUri2);
                after = true;
            }
        }
    }

}
