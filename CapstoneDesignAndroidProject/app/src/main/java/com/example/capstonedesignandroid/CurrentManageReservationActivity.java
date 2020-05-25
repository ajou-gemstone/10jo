package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.capstonedesignandroid.Fragment.B_1_1;
import com.example.capstonedesignandroid.Fragment.B_1_2;
import com.example.capstonedesignandroid.Fragment.B_2_1;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

//Todo: 편의상 성호관, 율곡관 2개만 한다.
public class CurrentManageReservationActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private boolean floatingActionButtonClicked = false;
    private String buildingTag;
    private ScrollView scrollViewOneToThree;
    private ScrollView scrollViewOne;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_manage_reservation);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        scrollViewOneToThree = findViewById(R.id.scrollViewOneToThree);
        scrollViewOne = findViewById(R.id.scrollViewOne);

        Intent intent = getIntent();
        buildingTag = intent.getStringExtra("buildingTag");

        //건물에 따라서 다른 fragment를 띄운다. - 초기 fragment는 1층으로
        inflateFragmentView(buildingTag, "1");

        //층 선택 layout에 모두 setOnclicklistener를 설정해준다.
        ViewGroup ll1 = (ViewGroup) findViewById(R.id.ll1);
        assignSetOnClickListener(ll1);
        ViewGroup ll2 = (ViewGroup) findViewById(R.id.ll2);
        assignSetOnClickListener(ll2);

        //층수 선택 view를 보여주는 button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!floatingActionButtonClicked) {
                    floatingActionButtonClicked = true;
                    switch (buildingTag) {
                        case "1":
                            scrollViewOneToThree.setVisibility(View.VISIBLE);
                            scrollViewOneToThree.bringToFront();
                            break;
                        case "2":
                            scrollViewOne.setVisibility(View.VISIBLE);
                            scrollViewOne.bringToFront();
                            break;
                    }
                } else {
                    floatingActionButtonClicked = false;
                    switch (buildingTag) {
                        case "1":
                            scrollViewOneToThree.setVisibility(View.INVISIBLE);
                            break;
                        case "2":
                            scrollViewOne.setVisibility(View.INVISIBLE);
                            break;
                    }
                }
            }
        });
    }

    private void assignSetOnClickListener(ViewGroup ll){
        View v;
        for (int i = 0; i < ll.getChildCount(); i++) {
            v = ll.getChildAt(i);
            if (v instanceof TextView) v.setOnClickListener(customOnClickListener);
        }
    }

    View.OnClickListener customOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            inflateFragmentView(buildingTag,(String) view.getTag());
        }
    };

    private void inflateFragmentView(String buildingTag, String floor){
        Fragment B = new B_1_1();
        switch (buildingTag) {
            case "1":
                if(floor.equals("1")){
                    B= new B_1_1();
                }else if(floor.equals("2")){
                    B = new B_1_2();
                }else if(floor.equals("3")){
//                    B = new B_1_3();
                }else if(floor.equals("4")){
//                    B = new B_1_4();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.buildingLectureroomFrameLayout, B).commit();
                break;

            case "2":
                if(floor.equals("1")){
                Fragment B_2_1 = new B_2_1();
                getSupportFragmentManager().beginTransaction().replace(R.id.buildingLectureroomFrameLayout, B_2_1).commit();
                break;
                }
        }
    }

    public void goDetailedReservationActivity(String resId, String lecId){
        Intent intent = new Intent(getApplicationContext(), ManageReservationActivity.class);
        intent.putExtra("reservationId", resId);
        intent.putExtra("lectureRoomId", lecId);
        startActivity(intent);
    }

}
