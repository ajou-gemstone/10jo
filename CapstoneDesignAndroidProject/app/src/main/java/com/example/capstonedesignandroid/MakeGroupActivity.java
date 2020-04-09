package com.example.capstonedesignandroid;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by user on 2017-11-07.
 */

public class MakeGroupActivity extends Activity{

    Button b1, b2;
    EditText e1, e2;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_group);

        b1 =  (Button)findViewById(R.id.b1);
        b2 =  (Button)findViewById(R.id.b2);
        e1 =  (EditText)findViewById(R.id.e1);
        e2 =  (EditText)findViewById(R.id.e2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MakeGroupActivity.this, "그런 일이 있었군요", Toast.LENGTH_SHORT).show();
                intent = new Intent(MakeGroupActivity.this, StudyBulletinBoardActivity.class);
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}



