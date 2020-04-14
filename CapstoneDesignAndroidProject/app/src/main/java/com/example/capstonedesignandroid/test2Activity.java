package com.example.capstonedesignandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.capstonedesignandroid.DTO.Dummy;

import java.net.HttpURLConnection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class test2Activity extends AppCompatActivity {

    private static final String BASE = "https://192.168.43.26:3001";

    EditText position;
    Button getButton;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HttpsURLConnection.setDefaultSSLSocketFactory(new NoSSLv3Factory());

        setContentView(R.layout.activity_test2);
        position = (EditText) findViewById(R.id.position);
        info = (TextView) findViewById(R.id.info);
        getButton = (Button) findViewById(R.id.button);
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GetService service = retrofit.create(GetService.class);
                Call<List<Dummy>> call = service.listDummies("5");
                call.enqueue(dummies);
            }
        });

    }

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
            Log.d("Adsf", "onFailure: " + t.getCause());
        }
    };

}
