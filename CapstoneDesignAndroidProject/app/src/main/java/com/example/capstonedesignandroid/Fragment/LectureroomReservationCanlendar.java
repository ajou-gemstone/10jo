package com.example.capstonedesignandroid.Fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.capstonedesignandroid.LectureroomReservationActivity;
import com.example.capstonedesignandroid.R;
import com.example.capstonedesignandroid.StaticMethodAndOthers.DefinedMethod;

import java.util.Date;

public class LectureroomReservationCanlendar extends Fragment {

    private ImageButton calendarDateCancelButton;
    private ImageButton calendarDateReserveButton;
    private CalendarView calendarReservationView;
    private Date reserveDate;
    private Date tmpreserveDate;

    LectureroomReservationActivity lectureroomReservationActivity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //이 메소드가 호출될 때는 프래그먼트가 엑티비티위에 올라와있는거니깐 getActivity메소드로 엑티비티참조가능
        lectureroomReservationActivity = (LectureroomReservationActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //이제 더이상 엑티비티 참초가안됨
        lectureroomReservationActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //프래그먼트 메인을 인플레이트해주고 컨테이너에 붙여달라는 뜻임
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_lectureroom_reservation_canlendar, container, false);

        calendarDateCancelButton = rootView.findViewById(R.id.calendarDateCancelButton);
        calendarDateReserveButton = rootView.findViewById(R.id.calendarDateReserveButton);
        calendarReservationView = rootView.findViewById(R.id.calendarReservationView);

        //취소하기 버튼을 눌렀을 때
        calendarDateCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lectureroomReservationActivity.removeLectureroomReservationCanlendarFragment();
            }
        });

        //예약하기 버튼을 눌렀을 때
        calendarDateReserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isAvailableTimeReservation()){
                    return;
                }
                decideReservationType();
                lectureroomReservationActivity.getReservationDate(reserveDate);
                lectureroomReservationActivity.removeLectureroomReservationCanlendarFragment();
            }
        });

        //캘린더 날짜 구성 및 기능 정의
        //이미 activity에서 날짜 시간 가져오기
        calendarReservationView.setDate(lectureroomReservationActivity.reserveDate.getTime());
        reserveDate = new Date();
        reserveDate = lectureroomReservationActivity.reserveDate;
        tmpreserveDate = reserveDate;

        calendarReservationView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                tmpreserveDate = new Date();
                tmpreserveDate = DefinedMethod.getDate(year, month, dayOfMonth);
            }
        });

        return rootView;
    }

    //과거나 15일 이후의 예약을 막는다.
    public boolean isAvailableTimeReservation(){
        if(tmpreserveDate.getTime() < lectureroomReservationActivity.currentDate.getTime()){
            Toast.makeText(lectureroomReservationActivity.getApplicationContext(),"과거의 날짜는 예약할 수 없습니다.", Toast.LENGTH_LONG).show();
            return false;
        }else if((tmpreserveDate.getTime() - lectureroomReservationActivity.currentDate.getTime())/(24*60*60*1000) > 14){
            Toast.makeText(lectureroomReservationActivity.getApplicationContext(),"15일 이후의 날짜는 예약할 수 없습니다.", Toast.LENGTH_LONG).show();
            return false;
        }
        reserveDate = tmpreserveDate;

        return true;
    }

    //선착순인지 선지망 후추첨인지 결정한다.
    public void decideReservationType(){
        long diffDate = reserveDate.getTime() - lectureroomReservationActivity.currentDate.getTime();
        diffDate = diffDate / (24*60*60*1000);
        if(diffDate < 3){//선착순
            lectureroomReservationActivity.getReservationType(true);
        }else{
            lectureroomReservationActivity.getReservationType(false);
        }
    }

}
