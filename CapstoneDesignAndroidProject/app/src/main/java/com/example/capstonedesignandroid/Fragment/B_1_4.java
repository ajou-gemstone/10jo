package com.example.capstonedesignandroid.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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

public class B_1_4 extends Fragment {

    private CurrentManageReservationActivity currentManageReservationActivity;
    private Retrofit retrofit;
    private GetService service;
    private ArrayList<DummyCurrentReservationBuildingFloor> dummyCurrentReservationBuildingFloorArrayList;
    private boolean IOExceptionbool = true;
    private LinearLayout currentTmpLL;
    private LinearLayout l401;
    private LinearLayout l402;
    private LinearLayout l403;
    private LinearLayout l404;
    private LinearLayout l405;
    private LinearLayout l406;
    private LinearLayout l407;
    private LinearLayout l408;
    private LinearLayout l409;
    private LinearLayout l410;
    private LinearLayout l411;
    private LinearLayout l412;
    private LinearLayout l413;
    private LinearLayout l414;
    private LinearLayout l415;
    private LinearLayout l416;
    private LinearLayout l417;
    private LinearLayout l418;
    private LinearLayout l419;
    private LinearLayout l420;
    private LinearLayout l421;
    private LinearLayout l422;
    private LinearLayout l423;
    private LinearLayout l424;
    private LinearLayout l425;
    private LinearLayout l426;
    private LinearLayout l427;
    private LinearLayout l428;
    private LinearLayout l429;
    private LinearLayout l430;
    private LinearLayout l431;
    private LinearLayout l432;
    private LinearLayout l433;
    private LinearLayout l434;
    private LinearLayout l435;
    private LinearLayout l436;
    private LinearLayout l437;
    private LinearLayout l438;
    private LinearLayout l439;
    private LinearLayout l440;
    private LinearLayout l441;
    private LinearLayout l442;

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_b_1_4, container, false);

        //-----------복사 코드 (서버)----------------
        retrofit = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(GetService.class);

        //이 부분만 수정
        Call<List<DummyCurrentReservationBuildingFloor>> call = service.getCurrentReservationBuildingFloor(DefinedMethod.getBuildingNameByBuildingTag("1"), "4");

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

        l401 = rootView.findViewById(R.id.l401);
        l402 = rootView.findViewById(R.id.l402);
        l403 = rootView.findViewById(R.id.l403);
        l404 = rootView.findViewById(R.id.l404);
        l405 = rootView.findViewById(R.id.l405);
        l406 = rootView.findViewById(R.id.l406);
        l407 = rootView.findViewById(R.id.l407);
        l408 = rootView.findViewById(R.id.l408);
        l409 = rootView.findViewById(R.id.l409);
        l410 = rootView.findViewById(R.id.l410);
        l411 = rootView.findViewById(R.id.l411);
        l412 = rootView.findViewById(R.id.l412);
        l413 = rootView.findViewById(R.id.l413);
        l414 = rootView.findViewById(R.id.l414);
        l415 = rootView.findViewById(R.id.l415);
        l416 = rootView.findViewById(R.id.l416);
        l417 = rootView.findViewById(R.id.l417);
        l418 = rootView.findViewById(R.id.l418);
        l419 = rootView.findViewById(R.id.l419);
        l420 = rootView.findViewById(R.id.l420);
        l421 = rootView.findViewById(R.id.l421);
        l422 = rootView.findViewById(R.id.l422);
        l423 = rootView.findViewById(R.id.l423);
        l424 = rootView.findViewById(R.id.l424);
        l425 = rootView.findViewById(R.id.l425);
        l426 = rootView.findViewById(R.id.l426);
        l427 = rootView.findViewById(R.id.l427);
        l428 = rootView.findViewById(R.id.l428);
        l429 = rootView.findViewById(R.id.l429);
        l430 = rootView.findViewById(R.id.l430);
        l431 = rootView.findViewById(R.id.l431);
        l432 = rootView.findViewById(R.id.l432);
        l433 = rootView.findViewById(R.id.l433);
        l434 = rootView.findViewById(R.id.l434);
        l435 = rootView.findViewById(R.id.l435);
        l436 = rootView.findViewById(R.id.l436);
        l437 = rootView.findViewById(R.id.l437);
        l438 = rootView.findViewById(R.id.l438);
        l439 = rootView.findViewById(R.id.l439);
        l440 = rootView.findViewById(R.id.l440);
        l441 = rootView.findViewById(R.id.l441);
        l442 = rootView.findViewById(R.id.l442);

        for (DummyCurrentReservationBuildingFloor d : dummyCurrentReservationBuildingFloorArrayList) {
            findLLbyLectureRoomName(d.getLectureRoom());
            if (currentTmpLL != null) {
                ViewGroup vg = (ViewGroup) currentTmpLL;
                //xml 초기화 코드
                currentTmpLL.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.reservation4));
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
            case "성442":
                currentTmpLL = l442;
                break;
            case "성441":
                currentTmpLL = l441;
                break;
            case "성440":
                currentTmpLL = l440;
                break;
            case "성439":
                currentTmpLL = l439;
                break;
            case "성438":
                currentTmpLL = l438;
                break;
            case "성437":
                currentTmpLL = l437;
                break;
            case "성436":
                currentTmpLL = l436;
                break;
            case "성435":
                currentTmpLL = l435;
                break;
            case "성434":
                currentTmpLL = l434;
                break;
            case "성433":
                currentTmpLL = l433;
                break;
            case "성432":
                currentTmpLL = l432;
                break;
            case "성431":
                currentTmpLL = l431;
                break;
            case "성430":
                currentTmpLL = l430;
                break;
            case "성429":
                currentTmpLL = l429;
                break;
            case "성428":
                currentTmpLL = l428;
                break;
            case "성427":
                currentTmpLL = l427;
                break;
            case "성426":
                currentTmpLL = l426;
                break;
            case "성425":
                currentTmpLL = l425;
                break;
            case "성424":
                currentTmpLL = l424;
                break;
            case "성423":
                currentTmpLL = l423;
                break;
            case "성422":
                currentTmpLL = l422;
                break;
            case "성421":
                currentTmpLL = l421;
                break;
            case "성420":
                currentTmpLL = l420;
                break;
            case "성419":
                currentTmpLL = l419;
                break;
            case "성418":
                currentTmpLL = l418;
                break;
            case "성417":
                currentTmpLL = l417;
                break;
            case "성416":
                currentTmpLL = l416;
                break;
            case "성415":
                currentTmpLL = l415;
                break;
            case "성414":
                currentTmpLL = l414;
                break;
            case "성413":
                currentTmpLL = l413;
                break;
            case "성412":
                currentTmpLL = l412;
                break;
            case "성411":
                currentTmpLL = l411;
                break;
            case "성410":
                currentTmpLL = l410;
                break;
            case "성409":
                currentTmpLL = l409;
                break;
            case "성408":
                currentTmpLL = l408;
                break;
            case "성407":
                currentTmpLL = l407;
                break;
            case "성406":
                currentTmpLL = l406;
                break;
            case "성405":
                currentTmpLL = l405;
                break;
            case "성404":
                currentTmpLL = l404;
                break;
            case "성403":
                currentTmpLL = l403;
                break;
            case "성402":
                currentTmpLL = l402;
                break;
            case "성401":
                currentTmpLL = l401;
                break;
            default:
                currentTmpLL = null;
        }
    }
}
