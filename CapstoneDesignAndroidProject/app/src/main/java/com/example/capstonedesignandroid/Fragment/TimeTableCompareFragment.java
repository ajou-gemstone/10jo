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

    private void initializeTimeTable(ArrayList<DummyTile> dummiesDummyTile){
        for(DummyTile d : dummiesDummyTile){
            Log.d("initializeTimeTable", ": d.getTime  d.getContents  "+d.getTime() +"  " + d.getContents());
            if(d.getTime().equals("undefined")){

            }else{
                LinearLayout ll = rootView.findViewWithTag(""+d.getTime());
                TextView tv = (TextView) ll.getChildAt(0);
                int tmpInt = Integer.parseInt(d.getContents());

                switch (tmpInt){
                    case 1: ll.setBackgroundColor(Color.argb(255, 220, 245, 132)); break;
                    case 2: ll.setBackgroundColor(Color.argb(255, 213, 240, 115)); break;
                    case 3: ll.setBackgroundColor(Color.argb(255, 198, 219, 118)); break;
                    case 4: ll.setBackgroundColor(Color.argb(255, 147, 186, 4)); break;
                    default: ll.setBackgroundColor(Color.argb(255, 140, 156, 82)); break;
                }
                tv.setTextSize(23);
                tv.setText(d.getContents());
            }
        }
    }
}
