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
    private MaterialDatePicker.Builder<Long> builder;
    private MaterialDatePicker<Long> picker;
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
        navigationView.setSelectedItemId(R.id.action_reservation);
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
                    currentReservationListType = "today";
                    Log.d("today", "onNavigationItemSelected: ");
                    break;
                case R.id.guard_reservation_future :
                    currentReservationListType = "future";
                    Log.d("future", "onNavigationItemSelected: ");
                    break;
                case R.id.guard_reservation_previous :
                    currentReservationListType = "past";
                    Log.d("past", "onNavigationItemSelected: ");
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.guardreservationlistframelayout, guardReservationList).commit();
            return false;
        }
    };
}
