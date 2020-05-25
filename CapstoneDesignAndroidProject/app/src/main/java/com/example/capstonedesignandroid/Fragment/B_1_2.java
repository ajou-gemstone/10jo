package com.example.capstonedesignandroid.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capstonedesignandroid.CurrentManageReservationActivity;
import com.example.capstonedesignandroid.DTO.DummyCurrentReservationBuildingFloor;
import com.example.capstonedesignandroid.GetService;
import com.example.capstonedesignandroid.R;
import com.example.capstonedesignandroid.StaticMethodAndOthers.DefinedMethod;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class B_1_2 extends Fragment {

    private CurrentManageReservationActivity currentManageReservationActivity;
    private Retrofit retrofit;
    private GetService service;
    private ArrayList<DummyCurrentReservationBuildingFloor> dummyCurrentReservationBuildingFloorArrayList;
    private boolean IOExceptionbool;

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_b_1_2, container, false);

        //-----------복사 코드 (서버)----------------
        retrofit = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(GetService.class);

        //이 부분만 수정
        Call<List<DummyCurrentReservationBuildingFloor>> call = service.getCurrentReservationBuildingFloor(DefinedMethod.getBuildingNameByBuildingTag("1"), "2");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<DummyCurrentReservationBuildingFloor> dummies = call.execute().body();
                    dummyCurrentReservationBuildingFloorArrayList = new ArrayList<DummyCurrentReservationBuildingFloor>(dummies);
                    Log.d("dummyLectureRoomReservationList", "run: " + dummies.size());
                    if(dummies.size() > 0){
                        Log.d("dummyLectureRoomReservationList", ""+dummyCurrentReservationBuildingFloorArrayList.get(0).getLectureRoom());
                        Log.d("dummyLectureRoomReservationList", ""+dummyCurrentReservationBuildingFloorArrayList.get(0).getStartTime());
                    }
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

        return rootView;
    }
}
