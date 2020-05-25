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
    private LinearLayout currentTmpLL;
    private LinearLayout l101;
    private LinearLayout l103;
    private LinearLayout l104;
    private LinearLayout l105;
    private LinearLayout l131;
    private LinearLayout l132;
    private LinearLayout l133_1;
    private LinearLayout l133;
    private LinearLayout l134;
    private LinearLayout l135;

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

        l101 = rootView.findViewById(R.id.l101);
        l103 = rootView.findViewById(R.id.l103);
        l104 = rootView.findViewById(R.id.l104);
        l105 = rootView.findViewById(R.id.l105);
        l131 = rootView.findViewById(R.id.l131);
        l132 = rootView.findViewById(R.id.l132);
        l133_1 = rootView.findViewById(R.id.l133_1);
        l133 = rootView.findViewById(R.id.l133);
        l134 = rootView.findViewById(R.id.l134);
        l135 = rootView.findViewById(R.id.l135);

        for(DummyCurrentReservationBuildingFloor d: dummyCurrentReservationBuildingFloorArrayList){
            findLLbyLectureRoomName(d.getLectureRoom());
            if(currentTmpLL != null){
                ViewGroup vg = (ViewGroup) l101;
                //xml 초기화 코드
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
            case "성101":
                currentTmpLL = l101;
                break;
            case "성103":
                currentTmpLL = l103;
                break;
            case "성104":
                currentTmpLL = l104;
                break;
            case "성105":
                currentTmpLL = l105;
                break;
            case "성131":
                currentTmpLL = l131;
                break;
            case "성132":
                currentTmpLL = l132;
                break;
            case "성133-1":
                currentTmpLL = l133_1;
                break;
            case "성133":
                currentTmpLL = l133;
                break;
            case "성134":
                currentTmpLL = l134;
                break;
            case "성135":
                currentTmpLL = l135;
                break;
            default:
                currentTmpLL = null;
        }
    }
}
