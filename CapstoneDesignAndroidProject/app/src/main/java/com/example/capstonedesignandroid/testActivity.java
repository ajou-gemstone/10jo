package com.example.capstonedesignandroid;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class testActivity extends AppCompatActivity {

    private Button addButton;

    private RelativeLayout layoutAbove;
    private RelativeLayout layoutCenter;
    private RelativeLayout layoutBelow;
    private RelativeLayout layoutTrash;

    private TrashDragListener trashDragListener;
    private LinearLayout ll;

    private RelativeLayout.LayoutParams layoutParams;
    private LayoutInflater layoutInflater;

    private ArrayList<LinearLayout> llal;
    private ArrayList<RelativeLayout> rlal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setFullScreen();
        setContentView(R.layout.activity_test);

        addButton = findViewById(R.id.addButton);
        layoutAbove = findViewById(R.id.layoutAbove);
        layoutCenter = findViewById(R.id.layoutCenter);
        layoutBelow = findViewById(R.id.layoutBelow);
        layoutTrash = findViewById(R.id.layoutTrash);

        //setOnDragListener는 이미 정의된 listener다.
        //드래그 & 드랍은 특별하게 드래그 하는 대상과 대상이 들어갈 공간을 나누어
        //드래그 할 대상은 view.startDragAndDrop을 하고,
        //대상의 들어갈 공간에 setOnDragListener를 하여
        //두 객체사이의 event를 중심으로 동작하도록 한다.
        //드래그 할 대상은 event.getLocalState()로, 공간은 v로 접근할 수 있다.
        layoutAbove.setOnDragListener(new TrashDragListener());
        layoutCenter.setOnDragListener(new TrashDragListener());
        layoutBelow.setOnDragListener(new TrashDragListener());
        layoutTrash.setOnDragListener(new TrashDragListener());

        layoutParams = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        llal = new ArrayList<LinearLayout>();

        rlal = new ArrayList<RelativeLayout>();
        rlal.add(layoutAbove);
        rlal.add(layoutCenter);
        rlal.add(layoutBelow);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(llal.size() >= 3){
                    return;
                }
                ll = new LinearLayout(getApplicationContext());
                ll.setLayoutParams(layoutParams);
                layoutInflater.inflate(R.layout.testtextlayout, ll, true);
                TextView tv = ll.findViewById(R.id.ttext);
                tv.setText("안녕" + llal.size());
                ll.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                        //paper 객체가 드래그를 한다고 시스템에게 알림(view.startDragAndDrop)
                        view.startDragAndDrop(data, shadowBuilder, view, 0);

                        return true;
                    }
                });
                llal.add(ll);
                if(llal.size() == 1){
                    layoutAbove.addView(ll);
                }else if(llal.size() == 2){
                    layoutCenter.addView(ll);
                }else if(llal.size() == 3){
                    layoutBelow.addView(ll);
                }
            }
        });

    }

    //이미 정의된 OnDragListener를 사용한다.
    private class TrashDragListener implements View.OnDragListener {

        private static final String TAG = "TrashDragListener";

        @Override
        public boolean onDrag(View ll, DragEvent event) {
            final RelativeLayout containerView = (RelativeLayout) ll;
            final LinearLayout draggedView = (LinearLayout) event.getLocalState();

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d(TAG, "onDrag: ACTION_DRAG_STARTED");
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d(TAG, "onDrag: ACTION_DRAG_ENTERED");
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d(TAG, "onDrag: ACTION_DRAG_EXITED");
                    return true;
                case DragEvent.ACTION_DROP:
                    Log.d(TAG, "onDrag: ACTION_DROP");
                    int llidx = llal.indexOf(draggedView);
                    //요소를 버릴 때
                    if(containerView.getId() == R.id.layoutTrash){
                        //일단 전부 삭제한다.
                        int i = 0;
                        for(LinearLayout tmpll: llal){
                            rlal.get(i).removeView(tmpll);
                            i++;
                        }

                        //다시 일괄적으로 추가한다.
                        llal.remove(llidx);
                        i =0;
                        for(LinearLayout tmpll: llal){
                            rlal.get(i).addView(tmpll);
                            i++;
                        }
                        return true;
                    }

                    int rlidx = rlal.indexOf(containerView);
                    if(rlidx >= llal.size()){
                        return true;
                    }
                    if(rlidx == llidx){
                        return true;
                    }
                    rlal.get(rlidx).removeView(llal.get(rlidx));
                    rlal.get(llidx).removeView(llal.get(llidx));
                    rlal.get(rlidx).addView(llal.get(llidx));
                    rlal.get(llidx).addView(llal.get(rlidx));
                    LinearLayout tmpll = llal.get(rlidx);
                    llal.set(rlidx, llal.get(llidx));
                    llal.set(llidx, tmpll);
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d(TAG, "onDrag: ACTION_DRAG_ENDED");
                    return true;
                default:
                    return true;
            }
        }
    }

//    private void setFullScreen() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//    }
}