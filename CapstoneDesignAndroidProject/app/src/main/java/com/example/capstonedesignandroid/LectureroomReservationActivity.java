package com.example.capstonedesignandroid;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstonedesignandroid.Adapter.FCFSAdapter;
import com.example.capstonedesignandroid.Adapter.PPDAdapter;
import com.example.capstonedesignandroid.Fragment.LectureroomReservationCanlendar;
import com.example.capstonedesignandroid.StaticMethodAndOthers.DefinedMethod;

import java.util.ArrayList;
import java.util.Date;
import androidx.appcompat.app.AppCompatActivity;


public class LectureroomReservationActivity extends AppCompatActivity {

    private Fragment lectureroomReservationCanlendarFragment;
    public boolean dataSelected = false;
    public Date reserveDate;
    public Date currentDate;
    private Button calendarReserveButton;
    private TextView reserveTimeTextView;
    private LinearLayout LectureroomFilterLayout;
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
    private RecyclerView recyclerViewPPD;
    private Boolean PPDFirstOrderSelected = false;
    private LinearLayout recyclerPPDLayout;

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

        //checkBox처리
        reserveTimeAllCheckbox = findViewById(R.id.reserveTimeAllCheckbox);
        reserveTimeAllCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    //옆의 모든 checkBox를 check해준다.

                }else{
                    //옆의 모든 checkBox를 uncheck해준다.
                }
            }
        });


        //checkbox 동적 생성, margin주기
        CheckBox checkBox = new CheckBox(getApplicationContext());
        LectureroomFilterLayout = (LinearLayout)findViewById(R.id.LectureroomFilterLayout);
        LinearLayout.LayoutParams checkboxLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        checkboxLayoutParams.leftMargin = 40;
        checkBox.setLayoutParams(checkboxLayoutParams);
        checkBox.setText("성호관");
        checkBox.setId(0);
        LectureroomFilterLayout.addView(checkBox);

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

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //----------------------------------------------
        reserveRandomButton = findViewById(R.id.reserveRandomButton);
        reserveButton = findViewById(R.id.reserveButton);

        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //선착순 UI
                if(isFCFS){
                    int year = DefinedMethod.getYear(reserveDate);
                    int month = DefinedMethod.getMonth(reserveDate) + 1;
                    int day = DefinedMethod.getDay(reserveDate);
                    //날짜, 강의실 등의 데이터를 서버에 전달하여 필터링을 거쳐 목록을 받는다.

                    inflateFCFSUI();
                }else{//선지망 후추첨 UI
                    inflateFCFSPPDUI();
                }
            }
        });
        recyclerViewFCFS = findViewById(R.id.lectureRoomFCFSrecyclerView);
        recyclerPPDLayout = findViewById(R.id.recyclerPPDLayout);
        recyclerViewPPD = findViewById(R.id.recyclerPPDrecyclerView);
    }

    private void inflateFCFSUI(){
        recyclerViewFCFS.setVisibility(View.VISIBLE);
        recyclerPPDLayout.setVisibility(View.INVISIBLE);
        //---------------------------------------
        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        // 강의실 목록 UI만 정의 및 초기화를 해준다.
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerViewFCFS.setLayoutManager(new GridLayoutManager(this, 3)) ;

        list = new ArrayList<>();
        for(int i=0; i<100; i++) {
            list.add(String.format("TEXTT %d", i)) ;
        }

        // 리사이클러뷰에 FCFSAdapter 객체 지정.
        FCFSAdapter adapter = new FCFSAdapter(list) ;
        recyclerViewFCFS.setAdapter(adapter);

        //adapter의 click event를 listen할 수 있도록 액티비티에서 listener 객체를 생성, 등록하고
        //인터페이스를 통해 adapter에서 위임하여 처리하도록 하며
        //액티비티에서 adapter의 파라미터를 사용할 수 있도록 한다.
        //adapter상의 리스너를 직접 정의해야 하기 때문에 setOnItemClickListener, OnItemClickListener 둘 다 정의를 해야 한다.
        adapter.setOnItemClickListener(new FCFSAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LectureroomReservationActivity.this);
                builder.setTitle("강의실 정보").setMessage("" + list.get(position));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void inflateFCFSPPDUI(){
        recyclerPPDLayout.setVisibility(View.VISIBLE);
        recyclerViewFCFS.setVisibility(View.INVISIBLE);

        recyclerViewPPD.setLayoutManager(new LinearLayoutManager(this)) ;

        list = new ArrayList<>();
        for(int i=0; i<100; i++) {
            list.add(String.format("TEXT %d", i)) ;
        }

        PPDAdapter adapter = new PPDAdapter(list) ;
        recyclerViewPPD.setAdapter(adapter);

        //adapter의 click event를 listen할 수 있도록 액티비티에서 listener 객체를 생성, 등록하고
        //인터페이스를 통해 adapter에서 위임하여 처리하도록 하며
        //액티비티에서 adapter의 파라미터를 사용할 수 있도록 한다.
        adapter.setOnItemClickListener(new PPDAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(v.getId() == R.id.lectureRoomInfo){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LectureroomReservationActivity.this);
                    builder.setTitle("이 시간대를 1순위로 예약하시겠습니까?").setMessage("" + list.get(position));
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            PPDFirstOrderSelected = true;
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
                else if(v.getId() == R.id.lectureRoomNameButton){
                    Toast.makeText(LectureroomReservationActivity.this, list.get(position), Toast.LENGTH_LONG).show();
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
