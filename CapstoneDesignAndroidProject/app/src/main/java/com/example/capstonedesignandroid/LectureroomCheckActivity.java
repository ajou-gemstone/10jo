package com.example.capstonedesignandroid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.capstonedesignandroid.Adapter.ReservationCheckAdapter;
import com.example.capstonedesignandroid.Fragment.Fragment_Reservation_Future;
import com.example.capstonedesignandroid.Fragment.Fragment_Reservation_Previous;
import com.example.capstonedesignandroid.Fragment.Fragment_Reservation_Today;
import com.google.android.material.tabs.TabLayout;

public class LectureroomCheckActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    ReservationCheckAdapter adapter = new ReservationCheckAdapter(getSupportFragmentManager());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectureroom_check);

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new Fragment_Reservation_Today(), "오늘의 예약");
        adapter.addFragment(new Fragment_Reservation_Future(), "앞으로의 예약");
        adapter.addFragment(new Fragment_Reservation_Previous(), "예전의 예약");
        viewPager.setAdapter(adapter);
    }
}
