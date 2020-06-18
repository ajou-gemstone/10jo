package com.example.capstonedesignandroid.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capstonedesignandroid.Adapter.ReservationListAdapter;
import com.example.capstonedesignandroid.CurrentManageReservationActivity;
import com.example.capstonedesignandroid.DTO.DummyReservationList;
import com.example.capstonedesignandroid.GetService;
import com.example.capstonedesignandroid.GuardReservationCheckActivity;
import com.example.capstonedesignandroid.LectureroomCheckDetailedActivity;
import com.example.capstonedesignandroid.ManageReservationActivity;
import com.example.capstonedesignandroid.R;
import com.example.capstonedesignandroid.StaticMethodAndOthers.DefinedMethod;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GuardReservationList extends Fragment {

    private GuardReservationCheckActivity guardReservationCheckActivity;
    private Retrofit retrofit;
    private boolean IOexception;
    private ArrayList<DummyReservationList> dummyReservationListArrayList;
    private RecyclerView recyclerViewReservationList;
    private ReservationListAdapter reservationListAdapter;

    public GuardReservationList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_guard_reservation_list, container, false);

        //----------------서버에서 받기 코드-------------------
        //입력: 날짜, 과거, userid
        //입력: {date: "YYYY-M-D", tense: "future or past", userid: "userid"}
        //출력: [{reservationId: "reservationId", date: "YYYY-MM-DD", day(요일): "월", startTime: "8:00", lastTime:"10:00", lectureRoom:"성101"}, ]
        //출력: reservationId, 예약 날짜, 요일(day), 시작시간, 종료시간, 강의실 이름

        retrofit = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String tense = guardReservationCheckActivity.currentReservationListType;
        String buildingName = guardReservationCheckActivity.buildingName;

        GetService service = retrofit.create(GetService.class);
        Call<List<DummyReservationList>> call = service.getGuardReservationList(tense, buildingName);

        dummyReservationListArrayList = new ArrayList<DummyReservationList>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<DummyReservationList> dummies = call.execute().body();
                    dummyReservationListArrayList = new ArrayList<DummyReservationList>(dummies);
                    IOexception = false;
                    Log.d("run:", "run:dummyReservationListArrayList");
                    try{
                        Log.d("run", "startTime " + dummyReservationListArrayList.get(0).getStartTime() +
                                " reservationId " + dummyReservationListArrayList.get(0).getReservationId() +
                                " date " + dummyReservationListArrayList.get(0).getDate() +
                                " lectureRoom " + dummyReservationListArrayList.get(0).getLectureRoom() +
                                " score " + dummyReservationListArrayList.get(0).getScore());
                    }catch (Exception e){

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    IOexception = true;
                    Log.d("IOException: ", "IOException:dummyReservationListArrayList");
                }
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (Exception e) {
        }
        //----------------서버에서 받기 코드-------------------
        //출력: {reservationId: "reservationId", date: "YYYY-MM-DD", day(요일): "월", startTime: "8:00", lastTime:"10:00", lectureRoom:"성101"}

        //mockup data로 대체
        if(IOexception){
//            dummyReservationListArrayList.add(new DummyReservationList("resId0", "2020-05-01", "월", "2", "6", "성101"));
//            dummyReservationListArrayList.add(new DummyReservationList("1", "2020-05-02", "화", "2", "6", "성101"));
//            dummyReservationListArrayList.add(new DummyReservationList("resId2", "2020-05-03", "수", "2", "6", "성101"));
        }

        //dummyReservationListArrayList 정렬
        //dummyReservationListArrayList 정렬
        for(DummyReservationList dummyReservationList: dummyReservationListArrayList){
            ArrayList<Integer> ymz = DefinedMethod.getYearMonthDaybyDate(dummyReservationList.getDate());
            //날짜 순 정렬
            Date date = DefinedMethod.getDate(ymz.get(0), ymz.get(1), ymz.get(2));
            float dateTime = date.getTime();
            //startTime순 정렬
            dateTime += 1000000 * Integer.parseInt(dummyReservationList.getStartTime());
            Log.d("stringDate", "  "+ ymz.get(0) + " "+ ymz.get(1) + " " + ymz.get(2));
            Log.d("dateTimee", ""+dateTime);
            dummyReservationList.setTimePriority(dateTime);
        }

        if(tense.equals("past")){
            Collections.sort(dummyReservationListArrayList, new Comparator<DummyReservationList>() {
                @Override
                public int compare(DummyReservationList t1, DummyReservationList t2) {
                    if(t1.getTimePriority() > t2.getTimePriority()){
                        return -1;
                    }else if(t1.getTimePriority() < t2.getTimePriority()){
                        return 1;
                    }
                    return 0;
                }
            });
        }else{
            Collections.sort(dummyReservationListArrayList, new Comparator<DummyReservationList>() {
                @Override
                public int compare(DummyReservationList t1, DummyReservationList t2) {
                    if(t1.getTimePriority() > t2.getTimePriority()){
                        return 1;
                    }else if(t1.getTimePriority() < t2.getTimePriority()){
                        return -1;
                    }
                    return 0;
                }
            });
        }


        recyclerViewReservationList = rootView.findViewById(R.id.recyclerViewReservationList);

        //recycler view에 들어갈 layout을 정해주어야 한다.
        recyclerViewReservationList.setLayoutManager(new LinearLayoutManager(getContext()));
        reservationListAdapter = new ReservationListAdapter(getContext(),""+ tense+"Guard",dummyReservationListArrayList);
        recyclerViewReservationList.setAdapter(reservationListAdapter);

        reservationListAdapter.setOnItemClickListener(new ReservationListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getContext(), ManageReservationActivity.class);
                intent.putExtra("reservationId", dummyReservationListArrayList.get(position).getReservationId());
                intent.putExtra("tense", ""+tense);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        guardReservationCheckActivity = (GuardReservationCheckActivity) getActivity();

    }
    @Override
    public void onDetach() {
        super.onDetach();
        guardReservationCheckActivity = null;
    }
}
