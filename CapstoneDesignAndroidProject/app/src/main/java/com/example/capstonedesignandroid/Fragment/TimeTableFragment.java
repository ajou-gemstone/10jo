package com.example.capstonedesignandroid.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.capstonedesignandroid.DTO.DummyResponse;
import com.example.capstonedesignandroid.DTO.DummyTile;
import com.example.capstonedesignandroid.DTO.DummyTile2;
import com.example.capstonedesignandroid.GetService;
import com.example.capstonedesignandroid.MyProfileActivity;
import com.example.capstonedesignandroid.R;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;
import com.example.capstonedesignandroid.TimetableModifyActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TimeTableFragment extends Fragment {

    private String cmode = "read"; //mode는 평상(read), 다수 선택(select)
    private Activity activity;
    private boolean canModify = false;
    private TimetableModifyActivity timetableModifyActivity;
    private ArrayList<LinearLayout> tileArrayList;
    private ViewGroup rootView;
    private ArrayList<DummyTile> dummiesDummyTile;
    private Retrofit retrofit2;
    private GetService service;
    private ArrayList<DummyTile> DummyTileArrayList;
    private String currentText1;
    private String currentText2;
    private String currentTextType;
    private ArrayList<LinearLayout> lectureLLArrayList;
    private ArrayList<LinearLayout> myTimeLLArrayList;

    public TimeTableFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.timetable, container, false);

        ViewGroup tableLayout = rootView.findViewById(R.id.tableLayout);
        //setOnClickListener
        for (int i = 1; i < tableLayout.getChildCount(); i++) {//맨 위는 클릭 불가
            ViewGroup tableRow = (ViewGroup) tableLayout.getChildAt(i);
            for (int j = 1; j < tableRow.getChildCount(); j++){//맨 왼쪽은 클릭 불가
                LinearLayout tile = (LinearLayout) tableRow.getChildAt(j);
                //확정된 강의면 클릭 불가
                tile.setOnClickListener(customOnClickListener);
            }
        }
        tileArrayList = new ArrayList<>();

        //---------시간표 retrofit----------
        //시간표 가져오기
        retrofit2 = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit2.create(GetService.class);
        String userId = SharedPreference.getAttribute(getContext(), "userId");
        //다른 유저의 개인 정보인 경우
        if(activity.getClass().toString().equals(MyProfileActivity.class.toString())){
            MyProfileActivity curActivity = (MyProfileActivity) activity;
            if(curActivity.fromReadgroup.equals("true")){
                userId = curActivity.memberId;
            }
        }
        Call<List<DummyTile>> callGetTimeTableInfo = service.getTimeTableInfo(userId);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<DummyTile> dummies = callGetTimeTableInfo.execute().body();
                    dummiesDummyTile = new ArrayList<DummyTile>(dummies);
                    Log.d("run: ", "dummiesDummyTileSize: "+ dummiesDummyTile.size());
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

        initializeTimeTable(dummiesDummyTile);

        return rootView;
    }

    View.OnClickListener customOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LinearLayout ll = (LinearLayout) view;
//            Toast.makeText(getContext(), ""+ ll.getTag(), Toast.LENGTH_SHORT).show();
            //강의면 click하는 순간 toast만 뜨도록 한다.
            if(lectureLLArrayList.contains(ll)){
                ViewGroup vg = (ViewGroup) ll;
                TextView contentTV = (TextView) vg.getChildAt(0);
                TextView locationTV = (TextView) vg.getChildAt(1);
                if(!contentTV.getText().toString().equals("") || !locationTV.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getContext(),""+contentTV.getText()+"\r\n"+locationTV.getText(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                return;
            }
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
                if(!contentTV.getText().toString().equals("") || !locationTV.getText().toString().equals("")){
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

    //시간표를 업데이트하고 select된 background를 다시 지워준다.
    public void selectAndModify(){
        for(LinearLayout ll : tileArrayList){
            //이미 들어가 있다면 지워주고 다시 넣는다.
            if(myTimeLLArrayList.contains(ll)){
                myTimeLLArrayList.remove(ll);
            }
            ViewGroup vg = (ViewGroup) ll;
            TextView contentTV = (TextView) vg.getChildAt(0);
            contentTV.setText(timetableModifyActivity.contentsEditText.getText().toString());
            TextView locationTV = (TextView) vg.getChildAt(1);
            locationTV.setText(timetableModifyActivity.locationEditText.getText().toString());
            vg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back));
            myTimeLLArrayList.add(ll);
        }
        timetableModifyActivity.contentsEditText.setText("");
        timetableModifyActivity.locationEditText.setText("");
        tileArrayList = new ArrayList<>();
    }

    public void selectAndDelete() {
        for(LinearLayout ll : tileArrayList){
            if(myTimeLLArrayList.contains(ll)){
                myTimeLLArrayList.remove(ll);
            }
            ViewGroup vg = (ViewGroup) ll;
            TextView contentTV = (TextView) vg.getChildAt(0);
            contentTV.setText("");
            TextView locationTV = (TextView) vg.getChildAt(1);
            locationTV.setText("");
            vg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back));
            myTimeLLArrayList.add(ll);
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

    private void initializeTimeTable(ArrayList<DummyTile> dummiesDummyTile){
        lectureLLArrayList = new ArrayList<>();
        myTimeLLArrayList = new ArrayList<>();
        for(DummyTile d : dummiesDummyTile){
            Log.d("initializeTimeTable", ": d.getTime  d.getContents  "+d.getTime() +"  " + d.getContents());
            if(d.getTime().equals("undefined")){

            }else{
                LinearLayout ll = rootView.findViewWithTag(""+d.getTime());
                TextView tv = (TextView) ll.getChildAt(0);
                //온 데이터를 파싱한다.
                parseTileContents(d.getContents());
                if(currentTextType.equals("L")){
                    //만약 강의면 데이터를 넣는다. ArrayList<LinearLayout> lectureLLArrayList
                    lectureLLArrayList.add(ll);
                    tv.setText(currentText1);
                    tv.setTextColor(Color.argb(255, 255, 0, 0));
                    tv = (TextView) ll.getChildAt(1);
                    tv.setText(currentText2);
                    tv.setTextColor(Color.argb(255, 255, 0, 0));
                }else{//강의가 아닌 개인적 활동 또한 데이터를 넣는다. ArrayList<LinearLayout> myTimeLLArrayList
                    myTimeLLArrayList.add(ll);
                    tv.setText(currentText1);
                    tv.setTextColor(Color.argb(255, 0, 255, 0));
                    tv = (TextView) ll.getChildAt(1);
                    tv.setText(currentText2);
                    tv.setTextColor(Color.argb(255, 0, 255, 0));
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(activity.getClass().toString().equals(MyProfileActivity.class.toString())){
            return;
        }

        ArrayList<DummyTile> dummyTileArrayList = mergeTileContents();
        String userId = SharedPreference.getAttribute(getContext(), "userId");
//        Call<DummyResponse> callGpostTimeTableInfo = service.postTimeTableInfo(userId, dummyTileArrayList);
        //---------------테스트-------------------------------------
        DummyTile2 dummyTile2 = new DummyTile2(userId, dummyTileArrayList);
        Call<DummyResponse> callpostTimeTableInfo = service.postTimeTableInfo(dummyTile2);
        //-----------------------------------

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DummyResponse response = callpostTimeTableInfo.execute().body();
                    Log.d("run: ", "response: " + response.getResponse());
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

    private void parseTileContents(String contents){
        if(contents.contains(",")){
            int tmpidx = contents.indexOf(",");
            currentText1 = contents.substring(0, tmpidx);
            currentText2 = contents.substring(tmpidx+1);
            currentTextType = "L";//강의
        }else if(contents.contains(".")){
            int tmpidx = contents.indexOf(".");
            currentText1 = contents.substring(0, tmpidx);
            currentText2 = contents.substring(tmpidx+1);
            currentTextType = "M";//개인적 활동
        }else{
            Log.d("parseTileContents", ": errorrr");
        }
    }

    private ArrayList<DummyTile> mergeTileContents(){
        ArrayList<DummyTile> dummyTileArrayList = new ArrayList<>();
        for(LinearLayout ll : myTimeLLArrayList){
            ViewGroup vg = (ViewGroup) ll;
            TextView contentTV = (TextView) vg.getChildAt(0);
            String ctv = contentTV.getText().toString();
            TextView locationTV = (TextView) vg.getChildAt(1);
            String ltv = locationTV.getText().toString();
            dummyTileArrayList.add(new DummyTile(ctv.concat("."+ltv), ll.getTag().toString()));
            Log.d("mergeTileContents", ": "+ ctv.concat("."+ltv) + "   " + ll.getTag().toString());
        }
        Log.d("mergeTileContents", ":");

        return dummyTileArrayList;
    }
}
