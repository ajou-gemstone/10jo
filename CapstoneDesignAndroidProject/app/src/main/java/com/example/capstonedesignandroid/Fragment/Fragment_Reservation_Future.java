package com.example.capstonedesignandroid.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.capstonedesignandroid.Adapter.ReservationListAdapter;
import com.example.capstonedesignandroid.DTO.DummyReservationList;
import com.example.capstonedesignandroid.GetService;
import com.example.capstonedesignandroid.R;
import com.example.capstonedesignandroid.StaticMethodAndOthers.DefinedMethod;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_Reservation_Future extends Fragment {

    private RecyclerView recyclerViewReservationList;
    private ReservationListAdapter reservationListAdapter;
    private Retrofit retrofit;
    private ArrayList<DummyReservationList> dummyReservationListArrayList;
    private boolean IOexception = true;

    public ViewPager viewPager;

    public Fragment_Reservation_Future(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //----------------서버에서 받기 코드-------------------
        //입력: 날짜, 미래, userid
        //입력: {date: "YYYY-M-D", tense: "future or past", userid: "userid"}
        //출력: {reservationId: "reservationId", date: "YYYY-MM-DD", day(요일): "월", startTime: "8:00", lastTime:"10:00", lectureRoom:"성101"}
        //출력: reservationId, 예약 날짜, 요일(day), 시작시간, 종료시간, 강의실 이름

        String date = DefinedMethod.getCurrentDate();

        retrofit = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String tense = "future";
        String userid = "leehyunju";

        GetService service = retrofit.create(GetService.class);
        Call<List<DummyReservationList>> call = service.getReservationList(date, tense, userid);

        dummyReservationListArrayList = new ArrayList<DummyReservationList>();

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    List<DummyReservationList> dummies = call.execute().body();
//                    dummyReservationListArrayList = new ArrayList<DummyReservationList>(dummies);
//                    IOexception = false;
//                    Log.d("run: ", "run: ");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    IOexception = true;
//                    Log.d("IOException: ", "IOException: ");
//                }
//            }
//        });
//        thread.start();
//        try {
//            thread.join();
//        } catch (Exception e) {
//            // TODO: handle exception
//        }

        //----------------서버에서 받기 코드-------------------
        //출력: {reservationId: "reservationId", date: "YYYY-MM-DD", day(요일): "월", startTime: "8:00", lastTime:"10:00", lectureRoom:"성101"}

//        mockup data로 대체
        if(IOexception){
            dummyReservationListArrayList.add(new DummyReservationList("resId0", "2020-05-01", "월", "8:00", "10:00", "성101"));
            dummyReservationListArrayList.add(new DummyReservationList("resId1", "2020-05-02", "화", "8:00", "10:00", "성101"));
            dummyReservationListArrayList.add(new DummyReservationList("resId2", "2020-05-03", "수", "8:00", "10:00", "성101"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_reservation_list,container,false);

        recyclerViewReservationList = view.findViewById(R.id.recyclerViewReservationList);

        //recycler view에 들어갈 layout을 정해주어야 한다.
        recyclerViewReservationList.setLayoutManager(new LinearLayoutManager(getContext()));
        reservationListAdapter = new ReservationListAdapter(dummyReservationListArrayList);
        recyclerViewReservationList.setAdapter(reservationListAdapter);

        reservationListAdapter.setOnItemClickListener(new ReservationListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(getContext(), ""+position, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
