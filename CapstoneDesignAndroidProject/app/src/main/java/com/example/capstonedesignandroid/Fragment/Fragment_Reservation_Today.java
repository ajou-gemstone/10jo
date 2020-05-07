package com.example.capstonedesignandroid.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.capstonedesignandroid.R;

public class Fragment_Reservation_Today extends Fragment {
    ViewPager viewPager;

    public Fragment_Reservation_Today(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_today_reservation, container, false);

        return view;
    }

}