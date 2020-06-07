package com.example.capstonedesignandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.capstonedesignandroid.DTO.DummyLectureRoomReservationState;
import com.example.capstonedesignandroid.DTO.DummyTile;
import com.example.capstonedesignandroid.Fragment.TimeTableCompareFragment;
import com.example.capstonedesignandroid.Fragment.TimeTableFragment;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TimeTableCompare extends AppCompatActivity {

    private Retrofit retrofit;
    private ArrayList<DummyTile> dummyTiles;
    private TimeTableCompareFragment timeTableCompareFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_compare);

        String groupId = getIntent().getStringExtra("groupId");

        retrofit = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetService service = retrofit.create(GetService.class);

        Log.d("00000", "onCreate: " + groupId);
        Call<List<DummyTile>> call = service.getTimeTableCompared(groupId);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<DummyTile> dummies = call.execute().body();
                    dummyTiles = new ArrayList<DummyTile>(dummies);
                    Log.d("run: ", "run: dummyTiles" + dummyTiles.size());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("IOException: ", "IOException: ");
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {

        }

        timeTableCompareFragment = new TimeTableCompareFragment(dummyTiles);
        getSupportFragmentManager().beginTransaction().replace(R.id.timeTableFrame, timeTableCompareFragment).commit();

    }
}
