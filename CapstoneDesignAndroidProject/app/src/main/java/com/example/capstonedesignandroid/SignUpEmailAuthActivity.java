package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpEmailAuthActivity extends AppCompatActivity {

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

        Log.d("bbbbb", id+pw+name+num+email);


        final EditText authNum = (EditText) findViewById(R.id.edittext_num);
        final Button checkNum = (Button) findViewById(R.id.button_check_num);
        final Button ajou_auth = (Button) findViewById(R.id.button_ajou_auth);

//        checkNum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String num1 = authNum.getText().toString();
//
//            }
//        });

        ajou_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(), SignUpAjouAuthActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("pw", pw);
                intent.putExtra("name", name);
                intent.putExtra("num", num);
                intent.putExtra("email", email);
                startActivityForResult(intent,100);
            }
        });

    } //onCreate
}
