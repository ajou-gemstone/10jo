package com.example.capstonedesignandroid.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesignandroid.Adapter.ReservationAdapter;
import com.example.capstonedesignandroid.Adapter.ReservationListAdapter;
import com.example.capstonedesignandroid.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Fragment_Reservation_Previous extends Fragment {

    private RecyclerView recyclerViewReservationList;
    private ReservationListAdapter reservationListAdapter;

    public Fragment_Reservation_Previous(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation_list,container,false);

        recyclerViewReservationList = view.findViewById(R.id.recyclerViewReservationList);

        //DB에서 예약정보 list를 받아온다.
        //쿼리:

        ArrayList<String> arrayListString = new ArrayList<String>();
        arrayListString.add("하나");
        arrayListString.add("둘");
        arrayListString.add("셋");
        Log.d("onCreateView: ", "onCreateView: ");

        //recycler view에 들어갈 layout을 정해주어야 한다.
        recyclerViewReservationList.setLayoutManager(new LinearLayoutManager(getContext()));
        reservationListAdapter = new ReservationListAdapter(arrayListString);
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
