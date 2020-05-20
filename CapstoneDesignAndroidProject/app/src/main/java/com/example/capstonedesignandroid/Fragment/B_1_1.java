package com.example.capstonedesignandroid.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.capstonedesignandroid.CurrentManageReservationActivity;
import com.example.capstonedesignandroid.DTO.DummyCurrentReservationBuildingFloor;
import com.example.capstonedesignandroid.DTO.DummyLectureRoomReservationState;
import com.example.capstonedesignandroid.GetService;
import com.example.capstonedesignandroid.LectureroomReservationActivity;
import com.example.capstonedesignandroid.R;
import com.example.capstonedesignandroid.StaticMethodAndOthers.DefinedMethod;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class B_1_1 extends Fragment {

    private CurrentManageReservationActivity currentManageReservationActivity;
    private Retrofit retrofit;
    private GetService service;
    private ArrayList<DummyCurrentReservationBuildingFloor> dummyCurrentReservationBuildingFloorArrayList;
    private boolean IOExceptionbool = true;

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
        //Todo: , activity에서는 예약 내용을 볼 수 있는 intent인 xml: activity_lectureroom_checkdetailed을 참고하여 작성한다.
        //Todo: 다른 건물의 층도 fragment, xml을 각각 다로 작성을 해야 한다.

        //-----------복사 코드 (서버)----------------
        retrofit = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(GetService.class);

        //이 부분만 수정
        Call<List<DummyCurrentReservationBuildingFloor>> call = service.getCurrentReservationBuildingFloor(DefinedMethod.getBuildingNameByBuildingTag("1"), "1");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<DummyCurrentReservationBuildingFloor> dummies = call.execute().body();
                    dummyCurrentReservationBuildingFloorArrayList = new ArrayList<DummyCurrentReservationBuildingFloor>(dummies);
//                    Log.d("dummyLectureRoomReservationList", ""+dummyCurrentReservationBuildingFloorArrayList.get(0).getLectureRoom());
//                    Log.d("dummyLectureRoomReservationList", ""+dummyCurrentReservationBuildingFloorArrayList.get(0).getStartTime());
                    IOExceptionbool = false;
                    Log.d("run: ", "run: ");
                } catch (IOException e) {
                    e.printStackTrace();
                    IOExceptionbool = true;
                    Log.d("IOException: ", "IOException: ");
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            // TODO: handle exception
        }
        if(IOExceptionbool){
            dummyCurrentReservationBuildingFloorArrayList = new ArrayList<DummyCurrentReservationBuildingFloor>();
            String[] tmpstrarr = {"1", "user2", "user3"};
            dummyCurrentReservationBuildingFloorArrayList.add(new DummyCurrentReservationBuildingFloor(
                    "강의실id", "성101", "예약id", "2", "6", "R",  tmpstrarr));
        }
        //-----------복사 코드 (서버)----------------

        //xml 초기화 코드
        LinearLayout l101 = rootView.findViewById(R.id.l101);
        TextView l101t = rootView.findViewById(R.id.l101t);
//        l101t.setText("시작시간: "+ DefinedMethod.getTimeByPosition(Integer.parseInt(dummyCurrentReservationBuildingFloorArrayList.get(0).getStartTime())) +
//                "종료시간: " +  DefinedMethod.getTimeByPosition(Integer.parseInt(dummyCurrentReservationBuildingFloorArrayList.get(0).getLastTime()))
//                + " resId:" + dummyCurrentReservationBuildingFloorArrayList.get(0).getReservationId());

        //강의실 클릭시 액티비티에서 나머지를 처리

        //0번째
        l101.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentManageReservationActivity.goDetailedReservationActivity(
                        dummyCurrentReservationBuildingFloorArrayList.get(0).getReservationId(),
                        dummyCurrentReservationBuildingFloorArrayList.get(0).getLectureRoomId());
            }
        });

        return rootView;
    }
}
