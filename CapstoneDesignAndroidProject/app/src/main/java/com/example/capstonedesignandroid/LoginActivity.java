package com.example.capstonedesignandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstonedesignandroid.StaticMethodAndOthers.Dummy3;
import com.example.capstonedesignandroid.StaticMethodAndOthers.GetService;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static final String BASE = "http://200.200.15.130:3000";

    EditText position;
    Button getButton, button_developer;
    String info_id, info_password;
    String title;
    String result_id;
    String name;
    String trust;
    String emotion;
    int admin = 0;
    int login = 0;
    int user = 0;

    String[] usertitle;
    StringBuilder builder_like = new StringBuilder();
    StringBuilder builder_title = new StringBuilder();
    StringBuilder builder_category = new StringBuilder();
    StringBuilder builder_profile = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText id = (EditText) findViewById(R.id.edittext_id);
        //SharedPreference.setAttribute(getApplicationContext(), "IP", BASE);
        final EditText password = (EditText) findViewById(R.id.edittext_password);
        final Button login = (Button) findViewById(R.id.button_login);
        Button button_developer = (Button) findViewById(R.id.button_developer);
        CheckBox remember = findViewById(R.id.remember);

        id.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    password.requestFocus();
                    return true;
                }
                return false;
            }
        });
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    login.performClick();
                    return true;
                }
                return false;
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GetService service = retrofit.create(GetService.class);
                String id1 = String.valueOf(id.getText().toString());
                String password1 = String.valueOf(password.getText().toString());
                info_id=id1;
                info_password=password1;
                Call<List<Dummy3>> call = service.listDummies(id1);
                call.enqueue(dummies);


//                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                startActivityForResult(intent,100);
            }
        });

        button_developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin = 1;

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivityForResult(intent,100);
            }
        });
    } //onCreate

    @Override
    public void onBackPressed() {
        // AlertDialog 빌더를 이용해 종료시 발생시킬 창을 띄운다
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setMessage("종료하시겠습니까?");

        // "예" 버튼을 누르면 실행되는 리스너
        alBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }
                System.runFinalization();
                System.exit(0);
            }
        });
        // "아니오" 버튼을 누르면 실행되는 리스너
        alBuilder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return; // 아무런 작업도 하지 않고 돌아간다
            }
        });
        alBuilder.setTitle("프로그램 종료");
        alBuilder.show(); // AlertDialog.Bulider로 만든 AlertDialog를 보여준다.
    }

    Callback dummies = new Callback<List<Dummy3>>() {

        @Override
        public void onResponse(Call<List<Dummy3>> call, Response<List<Dummy3>> response) {
            if (response.isSuccessful()) {
                List<Dummy3> dummies = response.body();
                StringBuilder builder = new StringBuilder();
                for (Dummy3 dummy : dummies) {
                    builder.append(dummy.toString()+",");
                }

                String[] result;
                result = builder.toString().split(",");
                result_id = result[0];
                name = result[1];
                trust = result[2];
                emotion = result[3];
            }
        }

        @Override
        public void onFailure(Call<List<Dummy3>> call, Throwable t) {

        }
    }; //dummies


}

