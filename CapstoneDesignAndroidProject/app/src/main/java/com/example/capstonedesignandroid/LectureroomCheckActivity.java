package com.example.capstonedesignandroid;

import android.content.Intent;

import androidx.annotation.NonNull;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.capstonedesignandroid.Adapter.ReservationCheckAdapter;
import com.example.capstonedesignandroid.Fragment.Fragment_Reservation_Future;
import com.example.capstonedesignandroid.Fragment.Fragment_Reservation_Previous;
import com.example.capstonedesignandroid.Fragment.Fragment_Reservation_Today;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class LectureroomCheckActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    ReservationCheckAdapter adapter = new ReservationCheckAdapter(getSupportFragmentManager());
    protected BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectureroom_check);

        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigationView.setSelectedItemId(R.id.action_check);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() { //super.onBackPressed();비워두면 실행안되서 뒤로가기 안됨
    }

    public void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new Fragment_Reservation_Today(), "오늘의 예약");
        adapter.addFragment(new Fragment_Reservation_Future(), "앞으로의 예약");
        adapter.addFragment(new Fragment_Reservation_Previous(), "예전의 예약");
        viewPager.setAdapter(adapter);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId()){
                case R.id.action_group :
                    Intent intent1 = new Intent(LectureroomCheckActivity.this, StudyBulletinBoardActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.action_reservation :
                    Intent intent2 = new Intent(LectureroomCheckActivity.this, LectureroomReservationActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.action_check :

                    break;
                case R.id.action_cafe :
                    Intent intent4 = new Intent(LectureroomCheckActivity.this, CafeMapActivity.class);
                    startActivity(intent4);
                    break;
                case R.id.action_profile :
                    Intent intent5 = new Intent(LectureroomCheckActivity.this, ProfileActivity.class);
                    startActivity(intent5);
                    break;

            }
            return false;
        }
    };

}
