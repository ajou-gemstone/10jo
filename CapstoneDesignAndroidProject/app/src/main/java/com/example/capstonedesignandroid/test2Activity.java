package com.example.capstonedesignandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.capstonedesignandroid.DTO.Dummy;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class test2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE) //http://192.168.0.3:3001 서버 호스트
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetService service = retrofit.create(GetService.class);
        Call<List<Dummy>> call = service.listDummies("5");
        call.enqueue(dummies);

        Callback dummies = new Callback<List<Dummy>>(){
            @Override
            public void onResponse(Call<List<Dummy>> call, Response<List<Dummy>> response) {
                if (response.isSuccessful()) {
                    List<Dummy> dummies = response.body();
                    StringBuilder builder = new StringBuilder();
                    for (Dummy dummy: dummies) {
                        builder.append(dummy.toString()+"\n");
                    }
                    info.setText(builder.toString());
                } else
                {
                    info.setText("Fail, "+ String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<List<Dummy>> call, Throwable t) {
                info.setText("Fail");
            }
        };
    }
}
