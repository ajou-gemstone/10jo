package com.example.capstonedesignandroid;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesignandroid.R;

public class ProfileActivity extends AppCompatActivity {

    TextView name, sinredo;
    Intent intent,intent1;
    ImageView emotion;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        intent1 = getIntent();
        emotion = (ImageView)findViewById(R.id.image_emotion);
        final String[] userInfo = intent1.getStringArrayExtra("strings");
        final String[] usertitle = intent1.getStringArrayExtra("usertitle");
        switch(Integer.parseInt(userInfo[5])) {
            case 0:
                emotion.setImageResource(R.drawable.profile);
                break;
            case 1:
                emotion.setImageResource(R.drawable.profile);
                break;

            default: break;
        }
        name =(TextView) findViewById(R.id.text_name);
        sinredo =(TextView) findViewById(R.id.text_sinredo);
        name.setText(userInfo[3].toString());
        sinredo.setText(userInfo[4].toString());

    }

}
