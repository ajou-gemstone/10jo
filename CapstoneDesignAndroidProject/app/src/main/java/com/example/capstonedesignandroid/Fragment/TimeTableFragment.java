package com.example.capstonedesignandroid.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.capstonedesignandroid.MyProfileActivity;
import com.example.capstonedesignandroid.R;
import com.example.capstonedesignandroid.TimetableModifyActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class TimeTableFragment extends Fragment {

    private String cmode = "read"; //mode는 평상(read), 다수 선택(select)
    private Activity activity;
    private boolean canModify = false;
    private TimetableModifyActivity timetableModifyActivity;
    private ArrayList<LinearLayout> tileArrayList;

    public TimeTableFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.timetable, container, false);

        ViewGroup tableLayout = rootView.findViewById(R.id.tableLayout);
        //setOnClickListener
        for (int i = 1; i < tableLayout.getChildCount(); i++) {//맨 위는 클릭 불가
            ViewGroup tableRow = (ViewGroup) tableLayout.getChildAt(i);
            for (int j = 1; j < tableRow.getChildCount(); j++){//맨 왼쪽은 클릭 불가
                LinearLayout tile = (LinearLayout) tableRow.getChildAt(j);
                tile.setOnClickListener(customOnClickListener);
            }
        }
        tileArrayList = new ArrayList<>();

        return rootView;
    }

    View.OnClickListener customOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LinearLayout ll = (LinearLayout) view;
//            Toast.makeText(getContext(), ""+ ll.getTag(), Toast.LENGTH_SHORT).show();
            if(canModify){
                cmode = timetableModifyActivity.mode;
            }else{
                //상세 정보를 볼 수 있도록 activity에서 UI 호출 -> 그냥 toast로 대체
                if(activity.getClass().toString().equals(MyProfileActivity.class.toString())){
                    MyProfileActivity profileActivity = (MyProfileActivity) activity;
                    ViewGroup vg = (ViewGroup) ll;
                    TextView contentTV = (TextView) vg.getChildAt(0);
                    TextView locationTV = (TextView) vg.getChildAt(1);
                    if(!contentTV.getText().toString().equals("") && !locationTV.getText().toString().equals("")){
                        Toast toast = Toast.makeText(getContext(),""+contentTV.getText()+"\r\n"+locationTV.getText(), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
                return;
            }
            if(cmode.equals("read")){
                ViewGroup vg = (ViewGroup) ll;
                TextView contentTV = (TextView) vg.getChildAt(0);
                TextView locationTV = (TextView) vg.getChildAt(1);
                if(!contentTV.getText().toString().equals("") && !locationTV.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getContext(),""+contentTV.getText()+"\r\n"+locationTV.getText(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }else if(cmode.equals("select")){
                //이미 선택한 경우
                if(tileArrayList.contains(ll)) {
                    tileArrayList.remove(ll);
                    ll.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back));
                }else{//새로 선택한 경우
                    tileArrayList.add(ll);
                    ll.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.backselected));
                }
            }
//            else if(cmode.equals("copy")){
//
//            }
//            Intent activityIntent = new Intent();
//            activityIntent.putExtra("tileTag", tv.getTag().toString());
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
        //수정하는 액티비티인 경우 액티비티의 mode를 이용한다.
        if(activity.getClass().toString().equals(TimetableModifyActivity.class.toString())){
            canModify = true;
            timetableModifyActivity = (TimetableModifyActivity) activity;
            cmode = timetableModifyActivity.mode;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
        canModify = false;
    }

    public void selectAndModify(){
        //시간표를 업데이트하고 select된 background를 다시 지워준다.
        for(LinearLayout ll : tileArrayList){
            ViewGroup vg = (ViewGroup) ll;
            TextView contentTV = (TextView) vg.getChildAt(0);
            contentTV.setText(timetableModifyActivity.contentsEditText.getText().toString());
            TextView locationTV = (TextView) vg.getChildAt(1);
            locationTV.setText(timetableModifyActivity.locationEditText.getText().toString());
            vg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back));
        }
        timetableModifyActivity.contentsEditText.setText("");
        timetableModifyActivity.locationEditText.setText("");
        tileArrayList = new ArrayList<>();
    }

    public void selectAndDelete() {
        for(LinearLayout ll : tileArrayList){
            ViewGroup vg = (ViewGroup) ll;
            TextView contentTV = (TextView) vg.getChildAt(0);
            contentTV.setText("");
            TextView locationTV = (TextView) vg.getChildAt(1);
            locationTV.setText("");
            vg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back));
        }
        timetableModifyActivity.contentsEditText.setText("");
        timetableModifyActivity.locationEditText.setText("");
        tileArrayList = new ArrayList<>();
    }

    public void selectCancel() {
        for(LinearLayout ll : tileArrayList){
            ll.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back));
        }
        tileArrayList = new ArrayList<>();
    }

    public void copyAndPaste() {

    }


}
