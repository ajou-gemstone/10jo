package com.example.capstonedesignandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//public class TabFragment1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener   새로고침 사용하면 이걸로 바꿔야 함
public class TabFragment1 extends Fragment {

    Intent intent,intent2;
    ArrayAdapter adapter;
    Button search;
    String userId,userPassword, maintext, name, trust,emotion, selecttitle, like;
    String chattingroom_id = "0";
    TextView text, text_sorted;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Context context;
    ArrayAdapter<String> adapter1;
    ArrayList<String> list;
    String[] userInfo, titleArray, categoryArray, profileArray, likeArray,tempArray;
    int[] indexArray;
    Boolean ischecked2;
    StudyGroupAdapter m_Adapter = new StudyGroupAdapter();
    EditText editSearch;
    boolean likesorting;
    String category = "";
    int s =0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ListView listview;
        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        //mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        //mSwipeRefreshLayout.setOnRefreshListener(this);
        //mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
        context = container.getContext();
        editSearch = view.findViewById(R.id.editSearch);
        search = view.findViewById(R.id.search);

        Intent intent1 = getActivity().getIntent();
        userInfo = intent1.getStringArrayExtra("strings");
//        userId = userInfo[0];
//        userPassword = userInfo[1];
//        name = userInfo[3];
//        trust = userInfo[4];
//        emotion = userInfo[5];
        like = "1";
        text = (TextView) view.findViewById(R.id.text);
        list = new ArrayList<>();
        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,list);
        listview = (ListView)view.findViewById(R.id.listview1);
        listview.setAdapter(m_Adapter);

//        titleArray=userInfo[2].split(",");
//        likeArray=userInfo[6].split(",");
//        categoryArray=userInfo[7].split(",");
//        profileArray=userInfo[8].split(",");

        //m_Adapter.clear();

        //처음에는 최신순으로 고민 방 리스트 보여줌




        editSearch.setVisibility(View.GONE);

        //설정 누르면 보이기, 안보이기 반복
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s%2 == 0) {
                    editSearch.setVisibility(View.VISIBLE);
                }
                else {
                    editSearch.setVisibility(View.GONE);
                }
                s++;
            }
        });

        editSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    listview.requestFocus();
                    return true;
                }
                return false;
            }
        });

        return view;
    } //onCreateView


    //새로고침 코드
//    @Override
//    public void onRefresh(){
//        mSwipeRefreshLayout.setRefreshing(true);
//        final String BASE = SharedPreference.getAttribute(context.getApplicationContext(), "IP");
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() { // 여기에 코드 추가
//                Retrofit retrofit1 = new Retrofit.Builder()
//                        .baseUrl(BASE)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//                ChattingInformationInterface chattingInformationInterface = retrofit1.create(ChattingInformationInterface.class);
//                Call<List<Dummy3>> call1 = chattingInformationInterface.listDummies(userId);
//                call1.enqueue(dummies1);
//
//            }
//        },1000); // 1초후에 새로고침 끝
//
//    }

}