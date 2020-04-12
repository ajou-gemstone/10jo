package com.example.capstonedesignandroid;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//사진은 내부 저장소
public class LectureroomCheckPictureActivity extends AppCompatActivity {

    private static final int CAPTURE_IMAGE = 99;
    private Button takePictureButton;
    private ImageView pictureImageView;
    private Bitmap bitmap;
    private Button transportPictureButton;
    private ImageView tmpImageView;
    private Bitmap img = null;

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

        //사진을 촬영한 시간도 저장해준다.

        takePictureButton = findViewById(R.id.takePictureButton);
        pictureImageView = findViewById(R.id.pictureImageView);
        transportPictureButton = findViewById(R.id.transportPictureButton);
        tmpImageView = findViewById(R.id.tmpImageView);

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAPTURE_IMAGE);
            }
        });

        transportPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                //내부저장소 캐시 경로를 받아옵니다.
//                File storage = getCacheDir();
                File storage = getFilesDir();
                Log.d("getFilesDir", ""+getFilesDir());
//
                String name = "tmp";
                //저장할 파일 이름
                String fileName = name + ".png";
//
//                //storage 에 파일 인스턴스를 생성합니다.
                File tempFile = new File(storage, fileName);

                try {
                    // 자동으로 빈 파일을 생성합니다.
                    tempFile.createNewFile();
                    // 파일을 쓸 수 있는 스트림을 준비합니다. 스트림과 파일을 연결한다.
                    FileOutputStream out = new FileOutputStream(tempFile);
                    // compress 함수를 사용해 스트림에 비트맵을 저장합니다.
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    // 스트림 사용후 닫아줍니다.
                    out.close();
                } catch (FileNotFoundException e) {
                    Log.e("MyTag","FileNotFoundException : " + e.getMessage());
                } catch (IOException e) {
                    Log.e("MyTag","IOException : " + e.getMessage());
                }
                //내부저장소 캐시 경로를 받아옵니다.
//                File storage = getCacheDir();

                //storage 에 파일 인스턴스를 생성합니다.
                File tempFile2 = new File(storage, fileName);
                try {
                    FileInputStream in = new FileInputStream(tempFile2);
                    img = BitmapFactory.decodeStream(in);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                tmpImageView.setImageBitmap(img);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE && resultCode == Activity.RESULT_OK && data.hasExtra("data")) {
            bitmap = (Bitmap) data.getExtras().get("data");
            if (bitmap != null) {
                pictureImageView.setImageBitmap(bitmap);
            }
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
