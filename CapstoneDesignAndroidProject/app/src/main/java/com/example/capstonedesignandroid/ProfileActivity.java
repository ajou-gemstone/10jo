package com.example.capstonedesignandroid;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesignandroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    TextView name, sinredo;
    Intent intent,intent1;
    ImageView emotion;
    protected BottomNavigationView navigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        intent1 = getIntent();
        emotion = (ImageView)findViewById(R.id.image_emotion);
        final String[] userInfo = intent1.getStringArrayExtra("strings");
        final String[] usertitle = intent1.getStringArrayExtra("usertitle");
//        switch(Integer.parseInt(userInfo[5])) {
//            case 0:
//                emotion.setImageResource(R.drawable.profile);
//                break;
//            case 1:
//                emotion.setImageResource(R.drawable.profile);
//                break;
//
//            default: break;
//        }
        name =(TextView) findViewById(R.id.text_name);
        sinredo =(TextView) findViewById(R.id.text_sinredo);
//        name.setText(userInfo[3].toString());
//        sinredo.setText(userInfo[4].toString());

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId()){
                case R.id.action_profile :

                    break;
                case R.id.action_group :
                    Intent intent1 = new Intent(ProfileActivity.this, StudyBulletinBoardActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.action_reservation :
                    Intent intent2 = new Intent(ProfileActivity.this, LectureroomReservationActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.action_check :
                    Intent intent3 = new Intent(ProfileActivity.this, LectureroomCheckActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.action_cafe :
                    Intent intent4 = new Intent(ProfileActivity.this, CafeMapActivity.class);
                    startActivity(intent4);
                    break;


            }
            return false;
        }
    };

}
