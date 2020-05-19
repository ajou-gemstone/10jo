package com.example.capstonedesignandroid;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ZoomTest2Activity extends AppCompatActivity {

    private RelativeLayout rell;
    private View timeTable;
    private TableLayout tableLayout;
    private ZoomableViewGroup zoomableViewGroup;
    private RelativeLayout.LayoutParams centerParams;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_test);

//        rell = findViewById(R.id.rell);
//
//        centerParams = new RelativeLayout.LayoutParams
//                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//        centerParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//
//        zoomableViewGroup = new ZoomableViewGroup(getApplicationContext());
////        zoomableViewGroup.setBackgroundColor(Color.argb(0, 0, 255, 0));
//        zoomableViewGroup.setLayoutParams(centerParams);
//
//        LayoutInflater layoutInflater = getLayoutInflater();
//        timeTable = (View) layoutInflater.inflate(R.layout.timetable, zoomableViewGroup, true);
//        tableLayout = timeTable.findViewById(R.id.tableLayout);
////        TextView textView = timeTable.findViewById(R.id.timeCol0);
////        textView.setText("오");
//
////        rell.addView(zoomableViewGroup);
//        rell.addView(zoomableViewGroup);
//
//        //-----------------------------------------------------------
//        Button but = new Button(getApplicationContext());
////        but.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View timeTable) {
////                Toast.makeText(getApplicationContext(), "누름", Toast.LENGTH_SHORT).show();
////            }
////        });
//        but.setText("버튼");
//        TextView tv = new TextView(getApplicationContext());
//        tv.setText("텍스트");
//        tv.setTextSize(50);
//
//        tv.setClickable(true);//이 문장을 추가해야 ACTION_UP이 들어온다.
//        tv.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Log.d("onTouch", ""+motionEvent.getAction());
//                //Todo: onTouched로 클릭 이벤트를 처리하기 위해 Action Down과 Action Up사이의 time interval의 길이가 짧음을 인식하여 처리하도록 한다.
//                switch (motionEvent.getAction()){
//                    case MotionEvent.ACTION_UP:
//                        Toast.makeText(getApplicationContext(), "터치", Toast.LENGTH_SHORT).show();
//                        return false;
//                }
//                //view가 viewgroup안에 있다고 view의 event가 발생했다고 timeTable group의 event를 발생시키지는 않는다.
//                //!!하지만 viewgroup의 dispatchTouchEvent는 발생시킨다!!
//                zoomableViewGroup.onTouchEvent(motionEvent);//이렇게 추가해버리면 둘 다 터치 이벤트가 발생됨. 핵심!
//                return false;
//            }
//        });
//
//        zoomableViewGroup.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                int[] location = new int[2];
//                Log.d("onTouchrell: ", " a");
//                textView.getLocationOnScreen(location);
//                Log.d("DEBUGCurrent2", "X: "+ location[0] +" Y: "+ location[1]);
//                return false;
//            }
//        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.d("tableWidth, tableHeight", ": "+ tableLayout.getWidth() + "  " + tableLayout.getHeight());
        Log.d("ViewWidth, ViewHeight", ": "+ timeTable.getWidth() + "  " + timeTable.getHeight());
        Log.d("ViewWidth, ViewHeight", ": "+ zoomableViewGroup.getWidth() + "  " + zoomableViewGroup.getHeight());

        zoomableViewGroup.firstScale((float) zoomableViewGroup.getWidth()/tableLayout.getWidth());
    }
}
