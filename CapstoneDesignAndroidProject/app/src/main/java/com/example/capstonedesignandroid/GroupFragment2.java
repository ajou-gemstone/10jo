package com.example.capstonedesignandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
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
import com.example.capstonedesignandroid.DTO.Group;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//public class GroupFragment2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener   새로고침 사용하면 이걸로 바꿔야 함
public class GroupFragment2 extends Fragment {

    Intent intent,intent2;
    ArrayAdapter adapter;
    Button b1, b2, b3, b4, b5, whole, search;
    String userId,userPassword, maintext, name, trust,emotion, selecttitle, like;
    String chattingroom_id = "0";
    TextView text, text_sorted;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Context context;
    ArrayAdapter<String> adapter1;
    ArrayList<String> list, titleArray, tagArray, categoryArray;
    ArrayList<Integer> idArray, currentNumArray, totalNumArray;
    EditText editSearch;
    String category = "";
    GroupListAdapter grouplistAdapter = new GroupListAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ListView listview;
        View view = inflater.inflate(R.layout.group_fragment_2, container, false);
        whole = view.findViewById(R.id.whole); b1 = view.findViewById(R.id.b1); b2 = view.findViewById(R.id.b2); b3 = view.findViewById(R.id.b3); b4 = view.findViewById(R.id.b4); b5 = view.findViewById(R.id.b5);
        //mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        //mSwipeRefreshLayout.setOnRefreshListener(this);
        //mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
        context = container.getContext();
        editSearch = view.findViewById(R.id.editSearch);
        search = view.findViewById(R.id.search);
        text = (TextView) view.findViewById(R.id.text);
        titleArray = new ArrayList<>();
        tagArray = new ArrayList<>();
        categoryArray = new ArrayList<>();
        idArray = new ArrayList<>();
        currentNumArray = new ArrayList<>();
        totalNumArray = new ArrayList<>();
        list = new ArrayList<>();
        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,list);

        final String BASE = MyConstants.BASE;

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GroupService groupservice = retrofit2.create(GroupService.class);
        Call<List<Group>> call = groupservice.getStudyList();
        //call.enqueue(studylistDummies);
        //동기 호출, network를 사용한 thread는 main thread에서 처리를 할 수 없기 때문에
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Group> dummies = call.execute().body();
                    for(int i = 0; i< dummies.size(); i++){
                        String tag = "";
                        if(dummies.get(i).getTagName().size() != 0) {
                            for (int t = 0; t < dummies.get(i).getTagName().size(); t++) {
                                tag = tag + "#" + dummies.get(i).getTagName().get(t).getTagName() + " ";
                            }
                        }
                        Log.d("tag",tag);
                        tagArray.add(tag);
                        idArray.add(dummies.get(i).getId());
                        titleArray.add(dummies.get(i).getTitle());
                        categoryArray.add(dummies.get(i).getCategory());
                        currentNumArray.add(dummies.get(i).getStudyGroupNumCurrent());
                        totalNumArray.add(dummies.get(i).getStudyGroupNumTotal());

                        Log.d("1", tagArray.get(i));
                    }
                    Log.d("run: ", "run: ");
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

        listview = (ListView)view.findViewById(R.id.listview1);
        listview.setAdapter(grouplistAdapter);

        for (int i = 0; i <= titleArray.size() - 1; i++) {
            if(!categoryArray.get(i).equals("all"))
                grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), totalNumArray.get(i), currentNumArray.get(i));
        }

        //처음에는 전체 다보여주기
        whole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "전체";
                grouplistAdapter.clear();
//                for (int i = titleArray.length - 1; i >= 0; i--)
//                    groupAdapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                grouplistAdapter.add(0, "#삼성 #코테", "삼성코테같이준비해요", "x000", 1, 4);
                grouplistAdapter.add(1, "#오픽 #AL", "오픽AL5번연속배출모임", "ㄴㄴㄴ", 2,  10);
                grouplistAdapter.notifyDataSetChanged();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "캡디";
                    grouplistAdapter.clear();
//                    for (int i = titleArray.length - 1; i >= 0; i--)
//                        if (categoryArray[i].equals("진로"))
//                            m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                grouplistAdapter.add(0, "#삼성 #코테", "삼성코테같이준비해요", "x000", 1, 4);
                grouplistAdapter.notifyDataSetChanged();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "자주연";
                grouplistAdapter.clear();
                grouplistAdapter.notifyDataSetChanged();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "환경과인간";
                grouplistAdapter.clear();
                grouplistAdapter.notifyDataSetChanged();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "인공지능";
                grouplistAdapter.clear();
                grouplistAdapter.add(1, "#오픽 #AL", "오픽AL5번연속배출모임", "ㄴㄴㄴ", 2,  10);
                grouplistAdapter.notifyDataSetChanged();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "과학사";
                grouplistAdapter.clear();
                grouplistAdapter.notifyDataSetChanged();
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
//                Call<List<Group>> call1 = chattingInformationInterface.listDummies(userId);
//                call1.enqueue(dummies1);
//
//            }
//        },1000); // 1초후에 새로고침 끝
//
//    }

}