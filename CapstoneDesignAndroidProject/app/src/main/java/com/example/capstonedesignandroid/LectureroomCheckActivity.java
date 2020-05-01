package com.example.capstonedesignandroid;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstonedesignandroid.Adapter.ReservationCheckAdapter;
import com.example.capstonedesignandroid.Fragment.Fragment_Reservation_Today;
import com.example.capstonedesignandroid.Fragment.Fragment_Reservation_Future;
import com.example.capstonedesignandroid.Fragment.Fragment_Reservation_Previous;
import com.google.android.material.tabs.TabLayout;

public class LectureroomCheckActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    ReservationCheckAdapter adapter = new ReservationCheckAdapter(getSupportFragmentManager());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectureroom_check);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LectureroomCheckPictureActivity.class);
                startActivity(intent);
            }
        });

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new Fragment_Reservation_Today(), "앞으로의 예약");
        adapter.addFragment(new Fragment_Reservation_Future(), "오늘의 예약");
        adapter.addFragment(new Fragment_Reservation_Previous(), "예전의 예약");
        viewPager.setAdapter(adapter);
    }
}
