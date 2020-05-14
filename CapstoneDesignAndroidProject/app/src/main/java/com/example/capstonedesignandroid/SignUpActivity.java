package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesignandroid.DTO.DummyResponse;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    boolean already = false;
    boolean checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText id = (EditText) findViewById(R.id.edittext_id);
        final EditText password = (EditText) findViewById(R.id.edittext_password);
        final EditText name = (EditText) findViewById(R.id.edittext_name);
        final EditText studentNum = (EditText) findViewById(R.id.edittext_studentnumber);
        final EditText email = (EditText) findViewById(R.id.edittext_email);
        final Button checkID = (Button) findViewById(R.id.button_check_id);
        final Button login = (Button) findViewById(R.id.button_email_auth);

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
        checkID.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Retrofit retrofit2 = new Retrofit.Builder()
                        .baseUrl(MyConstants.BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GetService service = retrofit2.create(GetService.class);
                Call<DummyResponse> call2 = service.getIdConfirm(id.getText().toString());
                CallThread(call2);

                if(already){
                    checked=false;
                    AlertMessage("이미 존재하는 아이디입니다.");
                }
                else{
                    checked=true;
                    AlertMessage("사용할 수 있는 아이디입니다.");
                }

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
        name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    password.performClick();
                    return true;
                }
                return false;
            }
        });
        studentNum.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    password.performClick();
                    return true;
                }
                return false;
            }
        });
        email.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    studentNum.performClick();
                    return true;
                }
                return false;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checked){
                    AlertMessage("아이디를 확인해주세요.");
                }
                else if(already){
                    AlertMessage("이미 존재하는 아이디입니다.");
                }
                else if(id.getText().toString().equals("") || password.getText().toString().equals("") || name.getText().toString().equals("") || studentNum.getText().toString().equals("") || email.getText().toString().equals("")){
                    AlertMessage("모든 정보를 입력해주세요.");
                }
                else {
                    String id1 = id.getText().toString();
                    String password1 = password.getText().toString();
                    String name1 = name.getText().toString();
                    String studentNum1 = studentNum.getText().toString();
                    String email1 = email.getText().toString();

                    Intent intent = new Intent(getApplicationContext(), SignUpEmailAuthActivity.class);
                    intent.putExtra("id", id1);
                    intent.putExtra("pw", password1);
                    intent.putExtra("name", name1);
                    intent.putExtra("num", studentNum1);
                    intent.putExtra("email", email1);

                    startActivityForResult(intent, 100);
                }
            }
        });

    } //onCreate

    private void CallThread(Call<DummyResponse> call) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DummyResponse dummies = call.execute().body();
                    if(dummies.getResponse().equals("fail"))
                        already = true;
                    if(dummies.getResponse().equals("success"))
                        already = false;

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
    }

    public void AlertMessage(String str){
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(SignUpActivity.this);
        alert_confirm.setMessage(str);
        alert_confirm.setPositiveButton("확인", null);
        AlertDialog alert = alert_confirm.create();
        alert.setIcon(R.drawable.app);
        alert.show();
    }

}
