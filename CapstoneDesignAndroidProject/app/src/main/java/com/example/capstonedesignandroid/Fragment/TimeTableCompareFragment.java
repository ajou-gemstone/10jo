package com.example.capstonedesignandroid.Fragment;

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
import androidx.fragment.app.Fragment;

import com.example.capstonedesignandroid.DTO.DummyTile;
import com.example.capstonedesignandroid.R;

import java.util.ArrayList;

public class TimeTableCompareFragment extends Fragment {

    ArrayList<DummyTile> dummyTiles;
    private ViewGroup rootView;

    public TimeTableCompareFragment(ArrayList<DummyTile> dt) {
        dummyTiles = dt;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.timetable, container, false);

        initializeTimeTable(dummyTiles);
        Log.d("dummyTiles", "onCreateView: " + dummyTiles.size());

        return rootView;
    }

    //Todo: 터치 기능은 그냥 하지 말고 색깔로 표시만 잘 하자.
    private void initializeTimeTable(ArrayList<DummyTile> dummiesDummyTile){
        for(DummyTile d : dummiesDummyTile){
            Log.d("initializeTimeTable", ": d.getTime  d.getContents  "+d.getTime() +"  " + d.getContents());
            if(d.getTime().equals("undefined")){

            }else{
                LinearLayout ll = rootView.findViewWithTag(""+d.getTime());
                TextView tv = (TextView) ll.getChildAt(0);
                tv.setText(d.getContents());
            }
        }
    }
}
