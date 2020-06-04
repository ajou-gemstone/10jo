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
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    private Button testActivity;
    private Button LectureroomCheckActivityButton;
    private Button buildingGuardActivityButton;
    private Button zoomTest;
    private Button zoomTest2;
    private Button zoomTest3;
    private Button timeTableModifyActivity;
    private TextView textView3;
    private Button test2button;

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
        test2button = findViewById(R.id.test2Button);

        test2button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FirebaseTestActivity.class);
                startActivity(intent);
            }
        });

        timeTableModifyActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TimetableModifyActivity.class);
                startActivity(intent);
            }
        });

        zoomTest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ZoomTest3Activity.class);
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
                Intent intent = new Intent(getApplicationContext(), testActivity.class);
                startActivity(intent);
            }
        });

        LectureroomCheckActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LectureroomCheckActivity.class);
                startActivity(intent);
            }
        });
        buildingGuardActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainBuildingGuardActivity.class);
                startActivity(intent);
            }
        });

        //textView3
        //total 0~13
        //예약1번(a) : 0~3
        //2번(b) : 4~6
        //3번(c) : 6~9
        //4번(d) : 9~13
        //5번(e) : 2~6
        ArrayList<MyAlgoTest> myAlgoTestArrayList = new ArrayList<>();
        myAlgoTestArrayList.add(new MyAlgoTest("a", 0, 3)); //index 0
        myAlgoTestArrayList.add(new MyAlgoTest("b", 4, 6)); //index 1
        myAlgoTestArrayList.add(new MyAlgoTest("c", 6, 9)); //index 2
        myAlgoTestArrayList.add(new MyAlgoTest("d", 9, 13)); //index 3
        myAlgoTestArrayList.add(new MyAlgoTest("e", 2, 6)); //index 4
        int timeStart = 0;
        int timeLast = 13;
        String[] seatOfRes = new String[14];

        //초기화
        for(int i = timeStart; i <= timeLast ; i++){
            seatOfRes[i] = "";
        }

        //각각의 예약의 index를 리스트의 startTime ~ lastTime에 해당하는 칸에 넣는다.
        for(MyAlgoTest m : myAlgoTestArrayList){
            for(int i = m.startTime ; i <= m.lastTime ; i++){
                seatOfRes[i] = seatOfRes[i].concat(String.valueOf(myAlgoTestArrayList.indexOf(m)));
            }
        }

        //다시 순회하면서 예약의 겹치는 시간 수와 겹치는 예약 수를 증가시킨다.
        for(MyAlgoTest m : myAlgoTestArrayList){
            for(int i = m.startTime ; i <= m.lastTime ; i++){
                m.stacksTime += seatOfRes[i].length(); // 겹치는 시간 수 증가
                for(int j = 0 ; j < seatOfRes[i].length() ; j++){
                    if(!m.stacksRes.contains(seatOfRes[i].substring(j,j+1))){
                        m.stacksRes = m.stacksRes.concat(seatOfRes[i].substring(j,j+1));//겹치는 예약 수 증가
                    }
                }
            }
        }

        double prioritySum = 0.0;
        //우선 순위를 정해준다. 우선순위(높으면 좋음) = 시간 차지 수 / (겹치는 예약 수 + 겹치는 시간 수)
        for(MyAlgoTest m : myAlgoTestArrayList){
            m.priority = (double)(m.lastTime-m.startTime+1)/(m.stacksTime-(m.lastTime-m.startTime+1)+m.stacksRes.length());
            Log.d("MyAlgoTest", "  " + m.stacksTime + "  " + m.stacksRes.length() + "  " + Math.log(1+m.priority));
            //로그를 취하여 편차를 조금 줄여준다.
            prioritySum += Math.log(1+m.priority);
            m.probabilityZone = prioritySum;
        }

        //0~1사이 값이 되도록 나눠준다.
        for(MyAlgoTest m : myAlgoTestArrayList){
            m.probabilityZone = m.probabilityZone/prioritySum;
        }

        //확률적으로 순위를 정한다.
        int currentAssignedNum = 0;
        while(true){
            Random rand = new Random();
            double randDouble = rand.nextDouble();
            double beforeProbability = 0;
            for(MyAlgoTest m : myAlgoTestArrayList) {
                if(beforeProbability <= randDouble &&  randDouble <= m.probabilityZone){
                    if(m.realPriority == 0){
                        m.realPriority = currentAssignedNum;
                        currentAssignedNum++;
                        break;
                    }
                }else{
                    beforeProbability = m.probabilityZone;
                }
            }
            //모든 순위가 정해질 때까지 계속 랜덤변수를 생성한다.
            if(currentAssignedNum == myAlgoTestArrayList.size())
                break;
        }

        //확률로 분배한 실제 예약 순위(낮을 수록 좋음)
        for(MyAlgoTest m : myAlgoTestArrayList) {
            Log.d("realPriority", ": " + m.realPriority);
        }

    }

    public class MyAlgoTest{

        String resId;
        int startTime;
        int lastTime;
        double priority;  //우선순위(높으면 좋음) = 시간 차지 수 / (겹치는 예약 수 + 겹치는 시간 수)
        double probabilityZone;
        int realPriority;
        int stacksTime = 0; // 겹치는 시간 수
        String stacksRes = ""; //겹치는 예약 수

        public MyAlgoTest(String a, int b, int c){
            resId = a;
            startTime = b;
            lastTime = c;
        }
    }
}

