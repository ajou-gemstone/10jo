package com.example.capstonedesignandroid;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//사진은 내부 저장소
public class LectureroomCheckPictureActivity extends AppCompatActivity {

    private static final int CAPTURE_IMAGE = 99;
    private static final int REQUEST_IMAGE_CAPTURE = 11111;
    private Button takePictureButton;
    private ImageView pictureImageView;
    private Bitmap bitmap;
    private Button transportPictureButton;
    private ImageView tmpImageView;
    private Bitmap img = null;

    private String imageFilePath;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectureroom_check_picture);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //퍼미션 상태 확인
            if (!hasPermissions(PERMISSIONS)) {
                //퍼미션 허가 안되어있다면 사용자에게 요청
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }else{
            }
        }

        Log.d("getFilesDir", ""+getFilesDir());
        Log.d("getPackageName", ""+getPackageName());
        Log.d("getExternalFilesDir", ""+getExternalFilesDir(Environment.DIRECTORY_PICTURES));

        //사진을 촬영한 시간도 저장해준다.

        takePictureButton = findViewById(R.id.takePictureButton);
        pictureImageView = findViewById(R.id.pictureImageView);
        transportPictureButton = findViewById(R.id.transportPictureButton);
        tmpImageView = findViewById(R.id.tmpImageView);

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendTakePhotoIntent();
            }
        });

        transportPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tmpImageView.setImageURI(photoUri);
            }
        });

    }

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
                photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";

        //외부 저장 경로를 사용하지만 sdcard가 없어도 emulated가 되어서 가상의(심볼릭) 경로이고
        //나중에 provider를 통하여 사전에 지정한(files_paths.xml에 있음) 실제 기기에서 저장되는 경로에 파일을 지정해주고
        //camera intent로 takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)을 추가하여 실제로 저장할 수 있도록 한다.

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //내부 저장 경로를 사용할 경우 화질의 문제가 생기기 때문에 외부 저장 경로를 활용한다.
        //또한 provider를 활용하지 못한다. (내부 저장 경로는 앱에 종속되기 때문에)
//        File storageDir = getFilesDir();
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".png",         /* suffix */
                storageDir          /* directory */
        );
        imageFilePath = image.getAbsolutePath();
        Log.d("imageFilePath", ""+imageFilePath);
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            pictureImageView.setImageURI(photoUri);
        }
    }

    // 여기서부터는 퍼미션 관련 코드입니다.
    static final int PERMISSIONS_REQUEST_CODE = 1000;
    String[] PERMISSIONS  = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private boolean hasPermissions(String[] permissions) {
        int result;

        //스트링 배열에 있는 퍼미션들의 허가 상태 여부 확인
        for (String perms : permissions){
            result = ContextCompat.checkSelfPermission(this, perms);
            if (result == PackageManager.PERMISSION_DENIED){
                //허가 안된 퍼미션 발견
                return false;
            }
        }
        //모든 퍼미션이 허가되었음
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraPermissionAccepted = grantResults[0]
                            == PackageManager.PERMISSION_GRANTED;
                    boolean diskPermissionAccepted = grantResults[1]
                            == PackageManager.PERMISSION_GRANTED;
                    if (!cameraPermissionAccepted || !diskPermissionAccepted)
                        showDialogForPermission("앱을 실행하려면 퍼미션을 허가하셔야합니다.");
                    else
                    {
                    }
                }
                break;
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder( this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id){
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        builder.create().show();
    }

}
