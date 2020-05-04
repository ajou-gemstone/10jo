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

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button StudyBulletinBoardActivityButton;
    private Button LectureroomReservationActivityButton;
    private Button CafeMapActivityButton;
    private Button testActivity;
    private Button LectureroomCheckActivityButton;
    private Button test2Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StudyBulletinBoardActivityButton = findViewById(R.id.StudyBulletinBoardActivityButton);
        LectureroomReservationActivityButton = findViewById(R.id.LectureroomReservationActivityButton);
        CafeMapActivityButton = findViewById(R.id.CafeMapActivityButton);
        testActivity = findViewById(R.id.testButton);
        LectureroomCheckActivityButton = findViewById(R.id.LectureroomCheckActivityButton);
        test2Button = findViewById(R.id.test2Button);

        StudyBulletinBoardActivityButton.setOnClickListener(this);
        LectureroomReservationActivityButton.setOnClickListener(this);
        CafeMapActivityButton.setOnClickListener(this);
        testActivity.setOnClickListener(this);
        LectureroomCheckActivityButton.setOnClickListener(this);
        test2Button.setOnClickListener(this);

        Log.d("asd", getKeyHash(getApplicationContext()));

        Intent intent3 = getIntent();
        String id = intent3.getStringExtra("id");
        String pw = intent3.getStringExtra("pw");
        Log.d("aa", id);
        Log.d("aa", pw);

        //Log.d("ofp", executeLogin(id, pw));
        String html = "<html><head><title>첫번째 에제입니다.</title></head>"
                + "<body><h1>테스트</h1><p>간단히 HTML을 파싱해 보는 샘플예제입니다.</p></body></html>";

        Document doc = Jsoup.parse(html);

        Elements title = doc.select("title");

    }
    public String executeLogin(String id, String pw){
        try {
            Connection.Response loginForm = Jsoup.connect("http://www.naver.com")
                    .method(Connection.Method.GET)
                    .execute();

//            Connection.Response mainPage = Jsoup.connect("http://www.naver.com")
//                    .data("user", id)
//                    .data("senha", pw)
//                    .cookies(loginForm.cookies())
//                    .execute();

            Map<String, String> cookies = loginForm.cookies();

            Document logindocument = loginForm.parse();



            return logindocument.select("input. ofp").val();


        }catch (IOException ioe) {
            return null;
        }

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
