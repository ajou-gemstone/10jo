package com.example.capstonedesignandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstonedesignandroid.DTO.DummyLectureRoomReservationState;
import com.example.capstonedesignandroid.DTO.DummyResponse;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DrawTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_test);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetService service = retrofit.create(GetService.class);

        EditText date = findViewById(R.id.date);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateString = date.getText().toString();
                Call<DummyResponse> call = service.postDrawDate(dateString);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DummyResponse response = call.execute().body();
                            Log.d("runDrawTestActivity: ", "run: ");
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("IOExceptionDrawTestActivity: ", "IOException: ");
                        }
                    }
                });
                thread.start();
                try {
                    thread.join();
                }catch (Exception e) {

                }
            }
        });
    }
}
