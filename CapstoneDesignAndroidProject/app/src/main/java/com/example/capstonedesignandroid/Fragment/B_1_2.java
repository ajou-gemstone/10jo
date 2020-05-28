package com.example.capstonedesignandroid.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private boolean IOExceptionbool = true;
    private LinearLayout currentTmpLL;
    private LinearLayout l201;
    private LinearLayout l201_1;
    private LinearLayout l202;
    private LinearLayout l203;
    private LinearLayout l204;
    private LinearLayout l205;
    private LinearLayout l231;
    private LinearLayout l232;
    private LinearLayout l233;
    private LinearLayout l234;
    private LinearLayout l235;
    private LinearLayout l236;
    private LinearLayout l237;
    private LinearLayout l238;
    private LinearLayout l241;
    private LinearLayout l242;
    private LinearLayout l243;
    private LinearLayout l244;

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
        }

        //-----------복사 코드 (서버)----------------

        l201 = rootView.findViewById(R.id.l201);
        l201_1 = rootView.findViewById(R.id.l201_1);
        l202 = rootView.findViewById(R.id.l202);
        l203 = rootView.findViewById(R.id.l203);
        l204 = rootView.findViewById(R.id.l204);
        l205 = rootView.findViewById(R.id.l205);
        l231 = rootView.findViewById(R.id.l231);
        l232 = rootView.findViewById(R.id.l232);
        l233 = rootView.findViewById(R.id.l233);
        l234 = rootView.findViewById(R.id.l234);
        l235 = rootView.findViewById(R.id.l235);
        l236 = rootView.findViewById(R.id.l236);
        l237 = rootView.findViewById(R.id.l237);
        l238 = rootView.findViewById(R.id.l238);
        l241 = rootView.findViewById(R.id.l241);
        l242 = rootView.findViewById(R.id.l242);
        l243 = rootView.findViewById(R.id.l243);
        l244 = rootView.findViewById(R.id.l244);

        for(DummyCurrentReservationBuildingFloor d: dummyCurrentReservationBuildingFloorArrayList){
            findLLbyLectureRoomName(d.getLectureRoom());
            if(currentTmpLL != null){
                ViewGroup vg = (ViewGroup) currentTmpLL;
                //xml 초기화 코드
                currentTmpLL.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.reservation2));
                TextView v = (TextView) vg.getChildAt(1);
                Log.d("currentTmpLL", ""+v.getText());
                v.setText(DefinedMethod.getTimeByPosition(Integer.parseInt(d.getStartTime())) + "~" +
                        DefinedMethod.getTimeByPosition(Integer.parseInt(d.getLastTime())+1));
                v = (TextView) vg.getChildAt(2);
                v.setText(d.getReservationType());
                //강의실 클릭시 액티비티에서 나머지를 처리
                currentTmpLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentManageReservationActivity.goDetailedReservationActivity(
                                d.getReservationId(),
                                d.getLectureRoomId());
                    }
                });
            }
        }

        return rootView;
    }

    private void findLLbyLectureRoomName(String name){
        Log.d("findLLbyLectureRoomName", ""+name);
        switch (name){
            case "성244":
                currentTmpLL = l244;
                break;
            case "성243":
                currentTmpLL = l243;
                break;
            case "성242":
                currentTmpLL = l242;
                break;
            case "성241":
                currentTmpLL = l241;
                break;
            case "성238":
                currentTmpLL = l238;
                break;
            case "성237":
                currentTmpLL = l237;
                break;
            case "성236":
                currentTmpLL = l236;
                break;
            case "성235":
                currentTmpLL = l235;
                break;
            case "성234":
                currentTmpLL = l234;
                break;
            case "성233":
                currentTmpLL = l233;
                break;
            case "성232":
                currentTmpLL = l232;
                break;
            case "성231":
                currentTmpLL = l231;
                break;
            case "성205":
                currentTmpLL = l205;
                break;
            case "성204":
                currentTmpLL = l204;
                break;
            case "성203":
                currentTmpLL = l203;
                break;
            case "성202":
                currentTmpLL = l202;
                break;
            case "성201-1":
                currentTmpLL = l201_1;
                break;
            case "성201":
                currentTmpLL = l201;
                break;
            default:
                currentTmpLL = null;
        }
    }
}
