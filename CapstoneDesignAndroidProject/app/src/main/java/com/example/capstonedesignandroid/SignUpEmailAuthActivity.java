package com.example.capstonedesignandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpEmailAuthActivity extends AppCompatActivity {

    boolean checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_email_auth);

        Intent intent3 = getIntent();
        String id = intent3.getStringExtra("id");
        String pw = intent3.getStringExtra("pw");
        String name = intent3.getStringExtra("name");
        String num = intent3.getStringExtra("num");
        String email = intent3.getStringExtra("email");
        String authnum = intent3.getStringExtra("authnum");

        final EditText authNumInput = (EditText) findViewById(R.id.edittext_num);
        final Button checkNum = (Button) findViewById(R.id.button_check_num);
        final Button ajou_auth = (Button) findViewById(R.id.button_ajou_auth);

        checkNum.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("authnum", authnum);
                Log.d("wwwww", authNumInput.getText().toString());
                if(authnum.equals(authNumInput.getText().toString())) {
                    AlertDialog.Builder alert_confirm = new AlertDialog.Builder(SignUpEmailAuthActivity.this);
                    alert_confirm.setMessage("인증이 완료되었습니다!");
                    alert_confirm.setPositiveButton("확인", null);
                    AlertDialog alert = alert_confirm.create();
                    alert.setIcon(R.drawable.app);
                    alert.show();
                    checked = true;
                }
                else{
                    AlertDialog.Builder alert_confirm = new AlertDialog.Builder(SignUpEmailAuthActivity.this);
                    alert_confirm.setMessage("인증번호가 올바르지 않습니다.");
                    alert_confirm.setPositiveButton("확인", null);
                    AlertDialog alert = alert_confirm.create();
                    alert.setIcon(R.drawable.app);
                    alert.show();
                }
            }
        });

        ajou_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checked) {
                    Intent intent = new Intent(getApplicationContext(), SignUpAjouAuthActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("pw", pw);
                    intent.putExtra("name", name);
                    intent.putExtra("num", num);
                    intent.putExtra("email", email);
                    startActivityForResult(intent, 100);
                }
                else{
                    AlertDialog.Builder alert_confirm = new AlertDialog.Builder(SignUpEmailAuthActivity.this);
                    alert_confirm.setMessage("인증번호를 확인해주세요.");
                    alert_confirm.setPositiveButton("확인", null);
                    AlertDialog alert = alert_confirm.create();
                    alert.setIcon(R.drawable.app);
                    alert.show();
                }
            }
        });

    } //onCreate
}
