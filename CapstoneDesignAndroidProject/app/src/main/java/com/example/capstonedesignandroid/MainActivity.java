package com.example.capstonedesignandroid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kakao.util.maps.helper.Utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button StudyBulletinBoardActivityButton;
    private Button LectureroomReservationActivityButton;
    private Button CafeMapActivityButton;
    private Button testActivity;
    private Button LectureroomCheckActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StudyBulletinBoardActivityButton = findViewById(R.id.StudyBulletinBoardActivityButton);
        LectureroomReservationActivityButton = findViewById(R.id.LectureroomReservationActivityButton);
        CafeMapActivityButton = findViewById(R.id.CafeMapActivityButton);
        testActivity = findViewById(R.id.testButton);
        LectureroomCheckActivityButton = findViewById(R.id.LectureroomCheckActivityButton);

        StudyBulletinBoardActivityButton.setOnClickListener(this);
        LectureroomReservationActivityButton.setOnClickListener(this);
        CafeMapActivityButton.setOnClickListener(this);
        testActivity.setOnClickListener(this);
        LectureroomCheckActivityButton.setOnClickListener(this);

        Log.d("asd", getKeyHash(getApplicationContext()));

    }
    public String getKeyHash(final Context context) {
        PackageInfo packageInfo = Utility.getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        Intent activityintent;
        switch (view.getId()){

            case R.id.StudyBulletinBoardActivityButton:
                activityintent = new Intent(this, StudyBulletinBoardActivity.class);
                startActivity(activityintent);
                break;
            case R.id.LectureroomReservationActivityButton:
                activityintent = new Intent(this, LectureroomReservationActivity.class);
                startActivity(activityintent);
                break;
            case R.id.CafeMapActivityButton:
                activityintent = new Intent(this, CafeMapActivity.class);
                startActivity(activityintent);
                break;
            case R.id.testButton:
                activityintent = new Intent(this, testActivity.class);
                startActivity(activityintent);
                break;
            case R.id.LectureroomCheckActivityButton:
                activityintent = new Intent(this, LectureroomCheckActivity.class);
                startActivity(activityintent);
                break;
        }
    }














//    public static String getSigneture(Context context){
//        PackageManager pm = context.getPackageManager();
//        try{
//            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
//
//            for(int i = 0; i < packageInfo.signatures.length; i++){
//                Signature signature = packageInfo.signatures[i];
//                try {
//                    MessageDigest md = MessageDigest.getInstance("SHA");
//                    md.update(signature.toByteArray());
//                    return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        }catch(PackageManager.NameNotFoundException e){
//            e.printStackTrace();
//        }
//        return null;
//    }
}
