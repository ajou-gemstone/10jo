package com.example.capstonedesignandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesignandroid.DTO.User;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText position;
    String primary_id = "";
    ArrayList<String> mylectureArray = new ArrayList<>();

    String[] usertitle;
    StringBuilder builder_like = new StringBuilder();
    StringBuilder builder_title = new StringBuilder();
    StringBuilder builder_category = new StringBuilder();
    StringBuilder builder_profile = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText id = (EditText) findViewById(R.id.id);
        final EditText password = (EditText) findViewById(R.id.edittext_password);
        final Button login = (Button) findViewById(R.id.login);
        CheckBox remember = findViewById(R.id.remember);
        Button developer = (Button) findViewById(R.id.button_developer);
        Button signup = findViewById(R.id.button_signup);

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
                        Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        //-------------------firebase-------------------

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

                String id1 = String.valueOf(id.getText().toString());
                String password1 = String.valueOf(password.getText().toString());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(MyConstants.BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                GetService service = retrofit.create(GetService.class);
                Call<User> call = service.login(id1, password1);
                Log.d("idid", "id: "+id1);
                CallThread(call);

                Intent intent = new Intent(getApplicationContext(),StudyBulletinBoardActivity.class);
                startActivityForResult(intent,100);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivityForResult(intent,100);
            }
        });

        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id1 = String.valueOf(id.getText().toString());
                String password1 = String.valueOf(password.getText().toString());

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("id", id1);
                intent.putExtra("pw", password1);
                startActivityForResult(intent,100);
            }
        });

    } //onCreate

    private void CallThread(Call<User> call) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    User dummies = call.execute().body();
                    primary_id = dummies.getId();
                    Log.d("primary_id", "primaryId:" + primary_id);
                    SharedPreference.removeAllAttribute(getApplicationContext());
                    SharedPreference.setAttribute(getApplicationContext(), "userId", primary_id);
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
    }//callthread

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

}

