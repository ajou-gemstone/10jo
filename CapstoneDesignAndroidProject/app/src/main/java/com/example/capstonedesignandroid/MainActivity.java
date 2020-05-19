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
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kakao.util.maps.helper.Utility;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private Button testActivity;
    private Button LectureroomCheckActivityButton;
    private Button buildingGuardActivityButton;
    private Button zoomTest;
    private Button zoomTest2;
    private Button zoomTest3;
    private Button timeTableModifyActivity;
    private TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testActivity = findViewById(R.id.testButton);
        LectureroomCheckActivityButton = findViewById(R.id.LectureroomCheckActivityButton);
        buildingGuardActivityButton = findViewById(R.id.buildingGuardActivityButton);
        zoomTest = findViewById(R.id.zoomTest);
        zoomTest2 = findViewById(R.id.zoomTest2);
        zoomTest3 = findViewById(R.id.zoomTest3);
        timeTableModifyActivity = findViewById(R.id.timeTableModifyActivity);
        textView3 = findViewById(R.id.textView3);

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        timeTableModifyActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TimetableModifyActivity.class);
                startActivity(intent);
            }
        });

        zoomTest3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ZoomTest3Activity.class);
                startActivity(intent);
            }
        });

        zoomTest2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ZoomTest2Activity.class);
                startActivity(intent);
            }
        });

        zoomTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ZoomTestActivity.class);
                startActivity(intent);
            }
        });

        testActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),testActivity.class);
                startActivity(intent);
            }
        });
        LectureroomCheckActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LectureroomCheckActivity.class);
                startActivity(intent);
            }
        });
        buildingGuardActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainBuildingGuardActivity.class);
                startActivity(intent);
            }
        });

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