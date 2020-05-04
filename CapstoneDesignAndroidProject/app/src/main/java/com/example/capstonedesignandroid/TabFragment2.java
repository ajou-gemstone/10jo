package com.example.capstonedesignandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.capstonedesignandroid.Adapter.GroupListAdapter;

import java.util.ArrayList;

//public class TabFragment2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener   새로고침 사용하면 이걸로 바꿔야 함
public class TabFragment2 extends Fragment {

    Intent intent,intent2;
    ArrayAdapter adapter;
    Button b1, b2, b3, b4, b5, whole, search;
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
    GroupListAdapter groupAdapter = new GroupListAdapter();
    EditText editSearch;
    boolean likesorting;
    String category = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ListView listview;
        View view = inflater.inflate(R.layout.tab_fragment_2, container, false);
        whole = view.findViewById(R.id.whole); b1 = view.findViewById(R.id.b1); b2 = view.findViewById(R.id.b2); b3 = view.findViewById(R.id.b3); b4 = view.findViewById(R.id.b4); b5 = view.findViewById(R.id.b5);
        //mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        //mSwipeRefreshLayout.setOnRefreshListener(this);
        //mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
        context = container.getContext();
        editSearch = view.findViewById(R.id.editSearch);
        search = view.findViewById(R.id.search);

//        Intent intent1 = getActivity().getIntent();
//        userInfo = intent1.getStringArrayExtra("strings");
//        userId = userInfo[0];
//        userPassword = userInfo[1];
//        name = userInfo[3];
//        trust = userInfo[4];
//        emotion = userInfo[5];
        like = "1";
        text = (TextView) view.findViewById(R.id.text);
        list = new ArrayList<>();
        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,list);

        //db 가져오기
        listview = (ListView)view.findViewById(R.id.listview1);
        listview.setAdapter(groupAdapter);

//        titleArray=userInfo[2].split(",");
//        likeArray=userInfo[6].split(",");
//        categoryArray=userInfo[7].split(",");
//        profileArray=userInfo[8].split(",");

        //처음에는 전체 다보여주기
        groupAdapter.add(0, "캡디 에이쁠조", "과목별", "타이틀", "하반기무조건합격보장해드림", "캡디", 1, 4);
        groupAdapter.add(1, "인공지능 스터디 구해요", "과목별", "타이틀2", "야 너두 할 수 있어", "인공지능", 2, 10);

        whole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "전체";
                groupAdapter.clear();
//                for (int i = titleArray.length - 1; i >= 0; i--)
//                    groupAdapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                groupAdapter.add(0, "캡디 에이쁠조", "과목별", "타이틀", "하반기무조건합격보장해드림", "캡디", 1, 4);
                groupAdapter.add(1, "인공지능 스터디 구해요", "과목별", "타이틀2", "야 너두 할 수 있어", "인공지능", 2, 10);
                groupAdapter.notifyDataSetChanged();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "캡디";
                    groupAdapter.clear();
//                    for (int i = titleArray.length - 1; i >= 0; i--)
//                        if (categoryArray[i].equals("진로"))
//                            m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                groupAdapter.add(0, "캡디 에이쁠조", "과목별", "타이틀", "하반기무조건합격보장해드림", "캡디", 1, 4);
                groupAdapter.notifyDataSetChanged();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "자주연";
                groupAdapter.clear();
                groupAdapter.notifyDataSetChanged();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "환경과인간";
                groupAdapter.clear();
                groupAdapter.notifyDataSetChanged();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "인공지능";
                groupAdapter.clear();
                groupAdapter.add(1, "인공지능 스터디 구해요", "과목별", "타이틀2", "야 너두 할 수 있어", "인공지능", 2, 10);
                groupAdapter.notifyDataSetChanged();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "과학사";
                groupAdapter.clear();
                groupAdapter.notifyDataSetChanged();
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

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent2 = new Intent(getActivity(), ReadGroupActivity.class);
                intent2.putExtra("str", "과목별 제목 넘기기테스트");
                startActivity(intent2);
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