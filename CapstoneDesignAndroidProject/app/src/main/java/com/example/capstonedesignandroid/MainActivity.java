package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    private Button LectureroomCheckActivityButton;
    private Button buildingGuardActivityButton;
    private Button test2button;
    private Button drawTestButton;
    private CheckBox pastAvailCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LectureroomCheckActivityButton = findViewById(R.id.LectureroomCheckActivityButton);
        buildingGuardActivityButton = findViewById(R.id.buildingGuardActivityButton);
        test2button = findViewById(R.id.test2Button);
        drawTestButton = findViewById(R.id.drawTestButton);
        pastAvailCheckBox = findViewById(R.id.pastAvailCheckBox);

        test2button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FirebaseTestActivity.class);
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

        drawTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DrawTestActivity.class);
                startActivity(intent);
            }
        });

        //로그아웃되면 값이 사라지는 것 주의
        if(SharedPreference.getAttribute(getApplicationContext(), "pastAvailCheckBox").equals("true")){
            pastAvailCheckBox.setChecked(true);
        }else {
            pastAvailCheckBox.setChecked(false);
        }
        pastAvailCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                Log.d("onCheckedChanged", "onCheckedChanged: " + b);
                SharedPreference.removeAttribute(getApplicationContext(), "pastAvailCheckBox");
                SharedPreference.setAttribute(getApplicationContext(), "pastAvailCheckBox", ""+b);
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

