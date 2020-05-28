package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.capstonedesignandroid.Fragment.GuardReservationList;
import com.example.capstonedesignandroid.StaticMethodAndOthers.DefinedMethod;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialDatePicker;

public class GuardReservationCheckActivity extends AppCompatActivity {

    private long today;
    private FragmentManager supportFragmentManager;
    private BottomNavigationView navigationView;
    private FrameLayout guardreservationlistframelayout;
    public String currentReservationListType = "today";
    public String buildingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_reservation_check);

        Intent intent = getIntent();
        buildingName = DefinedMethod.getBuildingNameByBuildingTag(intent.getStringExtra("buildingTag"));

        Fragment guardReservationList = new GuardReservationList();
        getSupportFragmentManager().beginTransaction().replace(R.id.guardreservationlistframelayout, guardReservationList).commit();

        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigationView.setSelectedItemId(R.id.guard_reservation_today);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        guardreservationlistframelayout = findViewById(R.id.guardreservationlistframelayout);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            Fragment guardReservationList = new GuardReservationList();
            switch (item.getItemId()){
                case R.id.guard_reservation_today :
                    Log.d("today", "onNavigationItemSelected: ");
                    currentReservationListType = "today";
                    break;
                case R.id.guard_reservation_future :
                    Log.d("future", "onNavigationItemSelected: ");
                    currentReservationListType = "future";
                    break;
                case R.id.guard_reservation_previous :
                    Log.d("past", "onNavigationItemSelected: ");
                    currentReservationListType = "past";
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.guardreservationlistframelayout, guardReservationList).commit();
            return true;
        }
    };
}
