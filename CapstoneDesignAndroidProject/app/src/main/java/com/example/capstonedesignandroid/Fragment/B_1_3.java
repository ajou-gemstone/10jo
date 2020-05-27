package com.example.capstonedesignandroid.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class B_1_3 extends Fragment {

    private CurrentManageReservationActivity currentManageReservationActivity;
    private Retrofit retrofit;
    private GetService service;
    private ArrayList<DummyCurrentReservationBuildingFloor> dummyCurrentReservationBuildingFloorArrayList;
    private boolean IOExceptionbool = true;
    private LinearLayout currentTmpLL;
    private LinearLayout l301;
    private LinearLayout l302;
    private LinearLayout l303;
    private LinearLayout l304;
    private LinearLayout l305;
    private LinearLayout l306;
    private LinearLayout l331;
    private LinearLayout l332;
    private LinearLayout l333;
    private LinearLayout l334;
    private LinearLayout l335;
    private LinearLayout l336;
    private LinearLayout l337;
    private LinearLayout l338;
    private LinearLayout l339;

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_b_1_3, container, false);

        //-----------복사 코드 (서버)----------------
        retrofit = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(GetService.class);

        //이 부분만 수정
        Call<List<DummyCurrentReservationBuildingFloor>> call = service.getCurrentReservationBuildingFloor(DefinedMethod.getBuildingNameByBuildingTag("1"), "3");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<DummyCurrentReservationBuildingFloor> dummies = call.execute().body();
                    dummyCurrentReservationBuildingFloorArrayList = new ArrayList<DummyCurrentReservationBuildingFloor>(dummies);
                    Log.d("dummyLectureRoomReservationList", "run: " + dummies.size());
                    if (dummies.size() > 0) {
                        Log.d("dummyLectureRoomReservationList", "" + dummyCurrentReservationBuildingFloorArrayList.get(0).getLectureRoom());
                        Log.d("dummyLectureRoomReservationList", "" + dummyCurrentReservationBuildingFloorArrayList.get(0).getStartTime());
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

        l301 = rootView.findViewById(R.id.l301);
        l302 = rootView.findViewById(R.id.l302);
        l303 = rootView.findViewById(R.id.l303);
        l304 = rootView.findViewById(R.id.l304);
        l305 = rootView.findViewById(R.id.l305);
        l306 = rootView.findViewById(R.id.l306);
        l331 = rootView.findViewById(R.id.l331);
        l332 = rootView.findViewById(R.id.l332);
        l333 = rootView.findViewById(R.id.l333);
        l334 = rootView.findViewById(R.id.l334);
        l335 = rootView.findViewById(R.id.l335);
        l336 = rootView.findViewById(R.id.l336);
        l337 = rootView.findViewById(R.id.l337);
        l338 = rootView.findViewById(R.id.l338);
        l339 = rootView.findViewById(R.id.l339);

        for (DummyCurrentReservationBuildingFloor d : dummyCurrentReservationBuildingFloorArrayList) {
            findLLbyLectureRoomName(d.getLectureRoom());
            if (currentTmpLL != null) {
                ViewGroup vg = (ViewGroup) currentTmpLL;
                //xml 초기화 코드
                TextView v = (TextView) vg.getChildAt(1);
                Log.d("currentTmpLL", "" + v.getText());
                v.setText(DefinedMethod.getTimeByPosition(Integer.parseInt(d.getStartTime())) + "~" +
                        DefinedMethod.getTimeByPosition(Integer.parseInt(d.getLastTime()) + 1));
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

    private void findLLbyLectureRoomName(String name) {
        Log.d("findLLbyLectureRoomName", "" + name);
        switch (name) {
            case "성339":
                currentTmpLL = l339;
                break;
            case "성338":
                currentTmpLL = l338;
                break;
            case "성337":
                currentTmpLL = l337;
                break;
            case "성336":
                currentTmpLL = l336;
                break;
            case "성335":
                currentTmpLL = l335;
                break;
            case "성334":
                currentTmpLL = l334;
                break;
            case "성333":
                currentTmpLL = l333;
                break;
            case "성332":
                currentTmpLL = l332;
                break;
            case "성331":
                currentTmpLL = l331;
                break;
            case "성306":
                currentTmpLL = l306;
                break;
            case "성305":
                currentTmpLL = l305;
                break;
            case "성304":
                currentTmpLL = l304;
                break;
            case "성303":
                currentTmpLL = l303;
                break;
            case "성302":
                currentTmpLL = l302;
                break;
            case "성301":
                currentTmpLL = l301;
                break;
            default:
                currentTmpLL = null;
        }
    }
}

