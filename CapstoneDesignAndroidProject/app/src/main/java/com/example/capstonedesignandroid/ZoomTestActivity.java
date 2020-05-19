package com.example.capstonedesignandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ZoomTestActivity extends AppCompatActivity {

    private RelativeLayout rell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_test);

        rell = findViewById(R.id.rell);

        RelativeLayout.LayoutParams centerParams = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        centerParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        MyImageView imageView = new MyImageView(getApplicationContext());
        imageView.setBackgroundColor(Color.argb(0, 0, 255, 0));
        imageView.setLayoutParams(centerParams);

        rell.addView(imageView);
    }
}
