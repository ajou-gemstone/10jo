package com.example.capstonedesignandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstonedesignandroid.Fragment.GuardReservationList;
import com.example.capstonedesignandroid.Fragment.TimeTableFragment;
import com.otaliastudios.zoom.ZoomEngine;
import com.otaliastudios.zoom.ZoomLayout;

import java.util.Timer;
import java.util.TimerTask;


public class ZoomTest3Activity extends AppCompatActivity {

    private com.otaliastudios.zoom.ZoomImageView zoomImage;
    private com.otaliastudios.zoom.ZoomLayout zoomLayout;
    private TimerTask mTask;
    private FrameLayout timeTableFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_test3);

        zoomLayout = findViewById(R.id.zoomLayout);
        timeTableFrame = findViewById(R.id.timeTableFrame);

        Fragment timeTableFragment = new TimeTableFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.timeTableFrame, timeTableFragment).commit();

        //UI작업을 위해 handler로 처리, onCreate에서 바로 zoom같은 animation이 작동하지 않는다.
        Handler mHandler = new Handler();
        mTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    mHandler.post(new Runnable(){
                        @Override
                        public void run() {
                            zoomLayout.moveTo(2.5f, 0, 1, true);
                        }
                    });
                }catch (Exception e){
                    Log.d("ExceptionTimer", ": "+e);
                }
            }
        };

        Timer mTimer = new Timer();
        mTimer.schedule(mTask, 500);

    }
}
