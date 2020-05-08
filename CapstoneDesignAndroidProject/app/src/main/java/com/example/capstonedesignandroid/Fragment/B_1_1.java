package com.example.capstonedesignandroid.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.example.capstonedesignandroid.CurrentManageReservationActivity;
import com.example.capstonedesignandroid.LectureroomReservationActivity;
import com.example.capstonedesignandroid.R;

public class B_1_1 extends Fragment {

    private Button calendarDateCancelButton;
    private Button calendarDateReserveButton;
    private CurrentManageReservationActivity currentManageReservationActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //이 메소드가 호출될 때는 프래그먼트가 엑티비티위에 올라와있는거니깐 getActivity메소드로 엑티비티참조가능
        currentManageReservationActivity = (CurrentManageReservationActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //이제 더이상 엑티비티 참초가안됨
        currentManageReservationActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //프래그먼트 메인을 인플레이트해주고 컨테이너에 붙여달라는 뜻임
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_b_1_1, container, false);

        //Todo: 서버에서 건물, 층에 대한 data를 받고 고유의 강의실을 클릭하면 강의실의 실시간 reservationId를 activity로 전달
        //Todo: , activity에서는 예약 내용을 볼 수 있는 intent인 xml: activity_lectureroom_checkdetailed을 이용한 activity로 이동한다.


        return rootView;
    }
}
