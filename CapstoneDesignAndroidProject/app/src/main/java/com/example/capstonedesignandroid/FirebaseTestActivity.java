package com.example.capstonedesignandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.capstonedesignandroid.DTO.Dummy;
import com.example.capstonedesignandroid.DTO.DummyLectureRoomReservationState;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirebaseTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_test);

        //----------------------firebase--------------
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("getInstanceId", "getInstanceId failed", task.getException());
                            Log.d("onComplete", "onComplete: ");
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        Log.d("token", token);
                        SharedPreference.removeAttribute(getApplicationContext(), "token");
                        SharedPreference.setAttribute(getApplicationContext(), "token", token);
                        Toast.makeText(FirebaseTestActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        //-------------------firebase-------------------

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //retrofit을 사용하기 위하여 singleton으로 build한다.
                //gson은 json구조를 띄는 직렬화된 데이터를 Java객체로 역직렬화, 직렬화를 해주는 자바 라이브러리이다.
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(MyConstants.BASE)//하이
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                //interface class를 retrofit을 이용하여 객체화하여 사용할 수 있도록 한다.
                //그리고 아마 interface인 GetService의 제대로 정의되지 않은 메소드를 retrofit 형식에 맞게 알아서 정의를 해줘서 사용할 수 있도록 변경해주는 역할도 한다.
                FirebaseService service = retrofit.create(FirebaseService.class);
                String token = SharedPreference.getAttribute(getApplicationContext(), "token");
                //retrofit service에 정의된 method를 사용하여
                Call<List<Dummy>> call = service.listDummies("3", token);
                call.enqueue(dummies);

//                Call<List<Dummy>> call = service.listDummies2("3");
//                call.enqueue(dummies);
            }
        });
    }

    Callback dummies = new Callback<List<Dummy>>() {
        @Override
        public void onResponse(Call<List<Dummy>> call, Response<List<Dummy>> response) {
            if (response.isSuccessful()) {
                Log.d("onResponse:", "Success, " + String.valueOf(response.code()));
            } else {
                Log.d("onResponse:", "Fail, " + String.valueOf(response.code()));
            }
        }

        @Override
        public void onFailure(Call<List<Dummy>> call, Throwable t) {
            Log.d("response fail", "onFailure: ");

        }
    }; //dummies
}
