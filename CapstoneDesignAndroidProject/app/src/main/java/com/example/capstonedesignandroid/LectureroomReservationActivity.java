package com.example.capstonedesignandroid;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstonedesignandroid.Adapter.ReservationAdapter;
import com.example.capstonedesignandroid.DTO.DummyLectureRoomReservationState;
import com.example.capstonedesignandroid.Fragment.LectureroomReservationCanlendar;
import com.example.capstonedesignandroid.StaticMethodAndOthers.DefinedMethod;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import androidx.appcompat.app.AppCompatActivity;


public class LectureroomReservationActivity extends AppCompatActivity {

    private Fragment lectureroomReservationCanlendarFragment;
    public boolean dataSelected = false;
    public Date reserveDate;
    public Date currentDate;
    private Button calendarReserveButton;
    private TextView reserveTimeTextView;
    private LinearLayout LectureBuildingFilterLayout;
    private CheckBox reserveTimeAllCheckbox;
    private Spinner reserveStartTimeSpinner;
    private Spinner reserveEndTimeSpinner;
    private ArrayList eightToTwentyoneTimeArrayList = new ArrayList<>();
    private long nowTime;
    private boolean isFCFS = true;
    private TextView reserveTypeTextView;
    private ImageButton reserveRandomButton;
    private ImageButton reserveButton;
    private RecyclerView recyclerView;
    private ArrayList<String> list;
    private RecyclerView recyclerViewFCFS;
    private RecyclerView recyclerViewReservation;
    private LinearLayout recyclerPPDLayout;
    private LinearLayout lectureRoomLayout;
    private CheckBox LectureBuildingAllCheckbox;
    private ArrayList<CheckBox> LectureBuildingCheckboxArrayList;
    private boolean checkControl = false;
    private LinearLayout reserveTimeSpinnerInnerLayout;
    private ArrayList<DummyLectureRoomReservationState> dummyLectureRoomReservationList = new ArrayList<DummyLectureRoomReservationState>();
    private String startTime;
    private String lastTime;
    private HorizontalScrollView lectureRoomScroll;
    private Boolean firstClick = false;
    private Boolean secondClick = false;
    private int firstTag =-1;
    private int secondTag =-1;
    private int currentPosition = -1;
    private View currentPositionView;
    private int startTimePosition;
    private int lastTimePosition;
    private ArrayList<Integer> priorityValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectureroom_reservation);

        //프래그먼트는 뷰와 다르게 context를 매개변수로 넣어줄 필요가 없다.
        lectureroomReservationCanlendarFragment = new LectureroomReservationCanlendar();
        calendarReserveButton = findViewById(R.id.calendarReserveButton);
        reserveTimeTextView = findViewById(R.id.reserveTimeTextView);
        FrameLayout reservation_calendar_container = findViewById(R.id.reservation_calendar_container);
        reservation_calendar_container.bringToFront();//최상단의 view로 보여주도록 한다.

        reserveTypeTextView = findViewById(R.id.reserveTypeTextView);

        calendarReserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //프래그먼트 추가하거나 할떄는 여러개 명령을 한꺼번에 쓸 수 있으므로
                //beginTransaction을 사용함
                //fragment1를 R.id.container에 넣어달라(add 또는 replace, replace는 기존에있던걸 대체 해줌)
                //트랜잭션안에서 수행되는것이므로 마지막에 꼭 commit을 해줘야 실행이된다.
                getSupportFragmentManager().beginTransaction().replace(R.id.reservation_calendar_container, lectureroomReservationCanlendarFragment).commit();
                /*getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();*/
            }
        });

        //현재시간 보여주기
        dataSelected = true;
        nowTime = System.currentTimeMillis();
        reserveDate = new Date(nowTime);
        //시, 분, 초를 없앤 년,월,일의 Date
        reserveDate = DefinedMethod.getDate(DefinedMethod.getYear(reserveDate), DefinedMethod.getMonth(reserveDate),DefinedMethod.getDay(reserveDate));
        currentDate = new Date();
        currentDate = reserveDate;

        reserveTimeTextView.setText(""+DefinedMethod.getYear(currentDate)+"-"+Math.addExact(DefinedMethod.getMonth(currentDate), 1)+"-"+DefinedMethod.getDay(currentDate));
        //--------------------------------------------

        //모든 강의실 checkBox처리
        LectureBuildingAllCheckbox = findViewById(R.id.LectureBuildingAllCheckbox);
        LectureBuildingAllCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(checkControl == true){
                    checkControl = false;
                    return;
                }
                if (isChecked) {
                    //옆의 모든 checkBox를 check해준다.
                    for (CheckBox eachCheckbox : LectureBuildingCheckboxArrayList) {
                        eachCheckbox.setChecked(true);
                    }
                } else {
                    //옆의 모든 checkBox를 uncheck해준다.
                    for (CheckBox eachCheckbox : LectureBuildingCheckboxArrayList) {
                        eachCheckbox.setChecked(false);
                    }
                }
            }
        });

        //checkbox 동적 생성, margin을 준다.
        LectureBuildingCheckboxArrayList = new ArrayList<CheckBox>();
        LectureBuildingFilterLayout = (LinearLayout)findViewById(R.id.LectureBuildingFilterLayout);
        LinearLayout.LayoutParams checkboxLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        checkboxLayoutParams.leftMargin = 40;
        for(String buildingName: MyConstants.LectureBuildingList){
            CheckBox checkBox = new CheckBox(getApplicationContext());
            checkBox.setLayoutParams(checkboxLayoutParams);
            checkBox.setText(buildingName);
            checkBox.setId(View.generateViewId());//generate view Id로 뷰의 id를 생성해준다.
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {//모든 건물물 풀어준다.
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if(isChecked){
                    }
                    else{
                        //모든 건물 checkBox를 uncheck해준다.
                        if(LectureBuildingAllCheckbox.isChecked()){
                            checkControl = true;
                            LectureBuildingAllCheckbox.setChecked(false);
                        }
                    }
                }
            });
            LectureBuildingFilterLayout.addView(checkBox);
            LectureBuildingCheckboxArrayList.add(checkBox);
        }

        reserveTimeSpinnerInnerLayout = findViewById(R.id.reserveTimeSpinnerInnerLayout);

        //모든 시간대 checkBox처리
        reserveTimeAllCheckbox = findViewById(R.id.reserveTimeAllCheckbox);
        reserveTimeAllCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    reserveStartTimeSpinner.setEnabled(false);
                    reserveEndTimeSpinner.setEnabled(false);
                    reserveTimeSpinnerInnerLayout.setBackgroundColor(Color.argb(51,17,17,17));
                }else{
                    reserveStartTimeSpinner.setEnabled(true);
                    reserveEndTimeSpinner.setEnabled(true);
                    reserveTimeSpinnerInnerLayout.setBackgroundColor(Color.argb(0,255,255,255));
                }
            }
        });

        //spinner, ArrayAdapter를 이용하여 구현한다.
        eightToTwentyoneTimeArrayList = DefinedMethod.declareEightToTwentyoneTimeArrayList(eightToTwentyoneTimeArrayList);

        reserveStartTimeSpinner = findViewById(R.id.reserveStartTimeSpinner);
        reserveEndTimeSpinner = findViewById(R.id.reserveEndTimeSpinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, eightToTwentyoneTimeArrayList);
        reserveStartTimeSpinner.setAdapter(arrayAdapter);
        reserveStartTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),eightToTwentyoneTimeArrayList.get(i)+"가 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        reserveEndTimeSpinner.setAdapter(arrayAdapter);
        reserveEndTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),eightToTwentyoneTimeArrayList.get(i)+"가 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        reserveRandomButton = findViewById(R.id.reserveRandomButton);
        reserveButton = findViewById(R.id.reserveButton);

        reserveButton.setOnClickListener(new View.OnClickListener() {
            //-------DB 조회-------
            //입력: 날짜(하나), 건물(리스트), 시작시간(하나), 종료시간(하나), //강의실 예약 인원 수(하나)
            //입력: {date??? building: "성호관 율곡관 연암관" startTime: "8:00" lastTime: "11:00"}
            //출력: {lectureroom: "성101", stateList: "R 0 0 0 1 L"}
            //출력: {lectureroom: "성103", stateList: "R A A A L L"}

            @Override
            public void onClick(View view){
                //날짜, 강의실 등의 데이터를 서버에 전달하여 필터링을 거쳐 목록을 받는다.
                //쿼리
                //날짜
                int year = DefinedMethod.getYear(reserveDate);
                int month = DefinedMethod.getMonth(reserveDate) + 1;
                int day = DefinedMethod.getDay(reserveDate);
                //건물
                for(CheckBox eachCheckBox : LectureBuildingCheckboxArrayList){
                    if(eachCheckBox.isChecked()){

                    }
                }
                //사용 시간대
                //모든 시간
                if(reserveTimeAllCheckbox.isChecked()){
                    startTime = "8:00";//position 0
                    startTimePosition = 0;
                    lastTime = "21:00";//position 26
                    lastTimePosition = 26;
                }else{
                    //시작 시간
                    startTime = reserveStartTimeSpinner.getSelectedItem().toString();
                    startTimePosition = reserveStartTimeSpinner.getSelectedItemPosition();
                    //마지막 시간
                    lastTime = reserveEndTimeSpinner.getSelectedItem().toString();
                    lastTimePosition = reserveEndTimeSpinner.getSelectedItemPosition();
                }

                //-----------------------------------------------------------
                //데이터 받기
                //-----------------------------------------------------------

                //DB에서 아래와 같은 정보를 dummy list로 가져왔다고 가정한다.
                startTimePosition = 2;
                lastTimePosition = 8;
                DummyLectureRoomReservationState dummyLectureRoomReservation1 = new DummyLectureRoomReservationState("성101", "R 0 0 0 1 L");
                DummyLectureRoomReservationState dummyLectureRoomReservation2 = new DummyLectureRoomReservationState("성102", "L L L L L L");
                DummyLectureRoomReservationState dummyLectureRoomReservation3 = new DummyLectureRoomReservationState("성103", "R R R L L L");
                DummyLectureRoomReservationState dummyLectureRoomReservation4 = new DummyLectureRoomReservationState("성104", "1 R 0 L 2 3");

                dummyLectureRoomReservationList.add(dummyLectureRoomReservation1);
                dummyLectureRoomReservationList.add(dummyLectureRoomReservation2);
                dummyLectureRoomReservationList.add(dummyLectureRoomReservation3);
                dummyLectureRoomReservationList.add(dummyLectureRoomReservation4);

                //선착순 sorting
                if(isFCFS){
//                    선착순인 경우 빈 시간이 많고, 연결되어있는 강의실 우선
                    String emptyL = "A";
                    int i = 0;
                    String previousState = "NULL";
                    priorityValue = new ArrayList<Integer>();
                    for(DummyLectureRoomReservationState data : dummyLectureRoomReservationList){
                        String eachStateList =  data.getStateList();
                        String[] splitState = eachStateList.split("\\s+");
                        priorityValue.add(0);
                        for(String eachState : splitState){
                            if(emptyL.contains(eachState)){
                                priorityValue.set(i, priorityValue.get(i));
                            }else{//아니면 최대 가중치인 20을 더한다.
                                priorityValue.set(i, priorityValue.get(i) + 20);
                            }
                            //연속으로 비어있는 강의실이면 -20을 해준다.
                            if(emptyL.contains(eachState) && emptyL.contains(previousState)){
                                priorityValue.set(i, priorityValue.get(i) - 20);
                            }
                            previousState = eachState;
                        }
                        Log.d("priorityValue", ""+priorityValue.get(i));
                        i++;
                    }
                    Collections.sort(dummyLectureRoomReservationList, new Comparator<DummyLectureRoomReservationState>() {
                        @Override
                        public int compare(DummyLectureRoomReservationState t1, DummyLectureRoomReservationState t2) {
                            if(priorityValue.get(dummyLectureRoomReservationList.indexOf(t1)) > priorityValue.get(dummyLectureRoomReservationList.indexOf(t2))){
                                return 1;
                            }else return -1;
                        }
                    });

                }else{//선지망 후추첨 sorting
                    //sorting은 사용가능한 강의실 중
                    //선지망 후추첨인 경우 시간대에 예약 팀수의 합이 가장 적은 강의실 우선
                    String num = "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19";

                    int i = 0;
                    priorityValue = new ArrayList<Integer>();
                    for(DummyLectureRoomReservationState data : dummyLectureRoomReservationList){
                        String eachStateList =  data.getStateList();
                        String[] splitState = eachStateList.split("\\s+");
                        priorityValue.add(0);
                        for(String eachState : splitState){
                            if(num.contains(eachState)){
                                priorityValue.set(i, priorityValue.get(i) + Integer.parseInt(eachState));
                            }else{//아니면 최대 가중치인 20을 더한다.
                                priorityValue.set(i, priorityValue.get(i) + 20);
                            }
                        }
                        Log.d("priorityValue", ""+priorityValue.get(i));
                        i++;
                    }
                    Collections.sort(dummyLectureRoomReservationList, new Comparator<DummyLectureRoomReservationState>() {
                        @Override
                        public int compare(DummyLectureRoomReservationState t1, DummyLectureRoomReservationState t2) {
                            if(priorityValue.get(dummyLectureRoomReservationList.indexOf(t1)) > priorityValue.get(dummyLectureRoomReservationList.indexOf(t2))){
                                return 1;
                            }else return -1;
                        }
                    });
                }
                inflateReservationUI();

                DummyLectureRoomReservationState dummyLectureRoomReservation0 = new DummyLectureRoomReservationState("강의실", "9:00 9:30 10:00 10:30 11:00 11:30");
                dummyLectureRoomReservationList.add(0, dummyLectureRoomReservation0);
            }
        });

        lectureRoomLayout = findViewById(R.id.lectureRoomPPDLayout);
        recyclerViewReservation = findViewById(R.id.recyclerPPDrecyclerView);
    }

    private void inflateReservationUI(){
        lectureRoomLayout.setVisibility(View.VISIBLE);
        recyclerViewReservation.setLayoutManager(new LinearLayoutManager(this));

//        selectMultipleTimeButton = findViewById(R.id.selectMultipleTimeButton);
//        selectMultipleTimeButton.setVisibility(View.VISIBLE);
//
//        selectMultipleTimeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(selectMultipleTimeButtonClicked == false){
//                    selectMultipleTimeButtonClicked = true;
//                    //횡 스크롤 고정
//                    lectureRoomScroll.setOnTouchListener(new View.OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View view, MotionEvent motionEvent) {
//                            return true;
//                        }
//                    });
//                    Toast.makeText(getApplicationContext(), "영역을 선택하세요", Toast.LENGTH_LONG).show();
//                }else{
//                    selectMultipleTimeButtonClicked = false;
//                    //횡 스크롤 고정 풀기
//                    lectureRoomScroll.setOnTouchListener(new View.OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View view, MotionEvent motionEvent) {
//                            return false;
//                        }
//                    });
//                }
//            }
//        });

        lectureRoomScroll = findViewById(R.id.lectureRoomScroll);

        ReservationAdapter adapter = new ReservationAdapter(dummyLectureRoomReservationList) ;
        recyclerViewReservation.setAdapter(adapter);

        //adapter의 click event를 listen할 수 있도록 액티비티에서 listener 객체를 생성, 등록하고
        //인터페이스를 통해 adapter에서 위임하여 처리하도록 하며
        //액티비티에서 adapter의 파라미터를 사용할 수 있도록 한다.
        adapter.setOnItemClickListener(new ReservationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(v.getId() == R.id.lectureRoomNameButton){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LectureroomReservationActivity.this);
                    builder.setTitle("이 시간대를 1순위로 예약하시겠습니까?").setMessage("" + dummyLectureRoomReservationList.get(position).getLectureroom());
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            //강의실 정보 가져오기, 확정은 다른 버튼으로한다.

                            //강의실 예약을 확정한다. 서버에 데이터를 넣는다.
                            //강의실 시간대를 적절히 잘 선택헀는지 확인
                            if(firstTag > 0 && secondTag > 0){
                                //입력: 날짜(하나), 강의실(하나), 시작시간(하나), 종료시간(하나), 본인id(하나)
                                //입력: {date??? lectureRoom: "성101" startTime: "9:00" lastTime: "10:00", userid: akdsnmkq}
                                //서버에서 입력내용을 예약내역 DB table에 저장한다.
                                //출력: {예약내역id: qninia} - 나중에 추가정보를 입력할 때 이 예약내역 id를 이용한다.
                                int year = DefinedMethod.getYear(reserveDate);
                                int month = DefinedMethod.getMonth(reserveDate) + 1;
                                int day = DefinedMethod.getDay(reserveDate);
                                dummyLectureRoomReservationList.get(currentPosition).getLectureroom();//강의실
                                //firstTag, secondTag 크기가 역전될 수도 있다.
                                //본인 id
                            }

                        }
                    });
                    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    Toast.makeText(LectureroomReservationActivity.this, dummyLectureRoomReservationList.get(position).getLectureroom(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onTimeClick(View v, int position, View pv) {
                int tag = Integer.parseInt((String) v.getTag());
                if(currentPosition == -1){//강의실 시간대를 처음 선택할 때
                    currentPosition = position;
                    currentPositionView = pv;
                }else if(currentPosition != position){//다른 강의실의 시간대를 선택할 때
                    firstClick = false;
                    firstTag = -1;
                    secondClick = false;
                    secondTag = -1;
                    //배경을 모두 지움
                    for(int i = 0; i < 6; i++){
                        currentPositionView.findViewWithTag(""+i).setBackgroundColor(Color.argb(0, 0x8B,0xC3,0x4A));
                    }
                    currentPosition = position;
                    currentPositionView = pv;
                }
                //----------------------------
                //아래 6은 나중에 lastTimePosition-startTimePosition 로 바뀌어야 한다.
                if(firstClick == false){
                    firstClick = true;
                    firstTag = tag;
                    //배경을 first view만 표시
                    v.setBackgroundColor(Color.argb(51,17,17,17));
                }else if(firstClick == true){
                    //first에서 똑같은 것을 선택하였을 때
                    if(firstTag == tag){
                        firstClick = false;
                        firstTag = -1;
                        secondClick = false;
                        secondTag = -1;
                        //배경을 모두 지움
                        for(int i = 0; i < 6; i++){
                            pv.findViewWithTag(""+i).setBackgroundColor(Color.argb(0, 0x8B,0xC3,0x4A));
                        }
                    }else{
                        if(secondTag == tag){
                            secondClick = false;
                            secondTag = -1;
                            //배경을 first view빼고 모두 지움
                            for(int i = 0; i < 6; i++){
                                pv.findViewWithTag(""+i).setBackgroundColor(Color.argb(0, 0x8B,0xC3,0x4A));
                            }
                            pv.findViewWithTag(""+firstTag).setBackgroundColor(Color.argb(51,17,17,17));
                        }else{
                            secondClick = true;
                            secondTag = tag;
                            for(int i = 0; i < 6; i++){
                                pv.findViewWithTag(""+i).setBackgroundColor(Color.argb(0, 0x8B,0xC3,0x4A));
                            }
                            //배경을 first view에서 second view까지 모두 표시
                            if(secondTag > firstTag){
                                for(int i = firstTag; i <= secondTag; i++){
                                    TextView textView = pv.findViewWithTag(""+i);
                                    pv.findViewWithTag(""+i).setBackgroundColor(Color.argb(51,17,17,17));
                                    //만약 예약이 불가한 날짜가 껴있다면 예약 불가
                                    if(textView.getText().equals("R") || textView.getText().equals("L")){
                                        secondClick = false;
                                        secondTag = -1;
                                        //배경을 first view빼고 모두 지움
                                        for(int j = 0; j < 6; j++){
                                            pv.findViewWithTag(""+j).setBackgroundColor(Color.argb(0, 0x8B,0xC3,0x4A));
                                        }
                                        pv.findViewWithTag(""+firstTag).setBackgroundColor(Color.argb(51,17,17,17));
                                        Toast.makeText(getApplicationContext(), "예약 불가능한 날짜가 있습니다.", Toast.LENGTH_LONG).show();
                                        break;
                                    }
                                    //------------------
                                }
                            }else{
                                for(int i = secondTag; i <= firstTag; i++){
                                    TextView textView = pv.findViewWithTag(""+i);
                                    pv.findViewWithTag(""+i).setBackgroundColor(Color.argb(51,17,17,17));
                                    //만약 예약이 불가한 날짜가 껴있다면 예약 불가
                                    if(textView.getText().equals("R") || textView.getText().equals("L")){
                                        secondClick = false;
                                        secondTag = -1;
                                        //배경을 first view빼고 모두 지움
                                        for(int j = 0; j < 6; j++){
                                            pv.findViewWithTag(""+j).setBackgroundColor(Color.argb(0, 0x8B,0xC3,0x4A));
                                        }
                                        pv.findViewWithTag(""+firstTag).setBackgroundColor(Color.argb(51,17,17,17));
                                        Toast.makeText(getApplicationContext(), "예약 불가능한 날짜가 있습니다.", Toast.LENGTH_LONG).show();
                                        break;
                                    }
                                    //------------------
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    // lectureroomReservationCanlendarFragment와 주고 받는 부분
    public void removeLectureroomReservationCanlendarFragment(){
        getSupportFragmentManager().beginTransaction().remove(lectureroomReservationCanlendarFragment).commit();
    }
    public void getReservationDate(Date reserveDate){
        dataSelected = true;
        this.reserveDate = reserveDate;
        reserveTimeTextView.setText(""+DefinedMethod.getYear(reserveDate)+"-"+Math.addExact(DefinedMethod.getMonth(reserveDate), 1)+"-"+DefinedMethod.getDay(reserveDate));
    }
    public void getReservationType(boolean isFCFS){
        this.isFCFS = isFCFS;
        if(isFCFS){
            reserveTypeTextView.setText("예약 타입: 선착순");
        }
        else{
            reserveTypeTextView.setText("예약 타입: 선지망 후추첨");
        }
    }
    //------------------------------------------------------

}
