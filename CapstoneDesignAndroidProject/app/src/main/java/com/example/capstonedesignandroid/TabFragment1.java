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
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9,whole, search;
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
    //Switch sortingswitch;
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
        b1 = view.findViewById(R.id.b1); b2 = view.findViewById(R.id.b2); b3 = view.findViewById(R.id.b3); b4 = view.findViewById(R.id.b4);
        b5 = view.findViewById(R.id.b5); b6 = view.findViewById(R.id.b6); b7 = view.findViewById(R.id.b7); b8 = view.findViewById(R.id.b8);
        b9 = view.findViewById(R.id.b9); whole = view.findViewById(R.id.whole);
        //mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        //mSwipeRefreshLayout.setOnRefreshListener(this);
        //mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
        context = container.getContext();
        editSearch = view.findViewById(R.id.editSearch);
        //sortingswitch = view.findViewById(R.id.sorting);
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
        //공감 순 스위치 버튼 ON OFF 할 때
//        sortingswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                ischecked2 = isChecked;
//                if (isChecked) { //공감순 일 때
//                    likesorting=true;
//                    m_Adapter.clear();
//
//                    if(category.equals("학업")) {
//                        for (int i = 0; i <= titleArray.length - 1; i++)
//                            if (categoryArray[indexArray[i]].equals("학업"))
//                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
//                    }
//                    else if(category.equals("이성")) {
//                        for (int i = 0; i <= titleArray.length - 1; i++)
//                            if (categoryArray[indexArray[i]].equals("이성"))
//                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
//                    }
//                    else if(category.equals("진로")) {
//                        for (int i = 0; i <= titleArray.length - 1; i++)
//                            if (categoryArray[indexArray[i]].equals("진로"))
//                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
//                    }
//                    else if(category.equals("취업")) {
//                        for (int i = 0; i <= titleArray.length - 1; i++)
//                            if (categoryArray[indexArray[i]].equals("취업"))
//                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
//                    }
//                    else if(category.equals("기타")) {
//                        for (int i = 0; i <= titleArray.length - 1; i++)
//                            if (categoryArray[indexArray[i]].equals("기타"))
//                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
//                    }
//                    else if(category.equals("경제")) {
//                        for (int i = 0; i <= titleArray.length - 1; i++)
//                            if (categoryArray[indexArray[i]].equals("경제"))
//                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
//                    }
//                    else if(category.equals("인간관계")) {
//                        for (int i = 0; i <= titleArray.length - 1; i++)
//                            if (categoryArray[indexArray[i]].equals("인간관계"))
//                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
//                    }
//                    else if(category.equals("외모")) {
//                        for (int i = 0; i <= titleArray.length - 1; i++)
//                            if (categoryArray[indexArray[i]].equals("외모"))
//                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
//                    }
//                    else if(category.equals("가정")) {
//                        for (int i = 0; i <= titleArray.length - 1; i++)
//                            if (categoryArray[indexArray[i]].equals("가정"))
//                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
//                    }
//                    else{ //전체일때
//                        if(userInfo[2].equals("")){
//                        }
//                        else{
//                            for (int i = 0; i <= titleArray.length - 1; i++) {
//                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
//                            }
//                        }
//                    }
//                    m_Adapter.notifyDataSetChanged();
//                }
//
//                else{ //공감순 아닐 때
//                    likesorting=false;
//                    m_Adapter.clear();
//
//                    if(category.equals("진로")) {
//                        for (int i = titleArray.length - 1; i >= 0; i--)
//                            if (categoryArray[i].equals("진로"))
//                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
//                    }
//                    else if(category.equals("학업")) {
//                        for (int i = titleArray.length - 1; i >= 0; i--)
//                            if (categoryArray[i].equals("학업"))
//                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
//                    }
//                    else if(category.equals("이성")) {
//                        for (int i = titleArray.length - 1; i >= 0; i--)
//                            if (categoryArray[i].equals("이성"))
//                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
//                    }
//                    else if(category.equals("기타")) {
//                        for (int i = titleArray.length - 1; i >= 0; i--)
//                            if (categoryArray[i].equals("기타"))
//                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
//                    }
//                    else if(category.equals("가정")) {
//                        for (int i = titleArray.length - 1; i >= 0; i--)
//                            if (categoryArray[i].equals("가정"))
//                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
//                    }
//                    else if(category.equals("인간관계")) {
//                        for (int i = titleArray.length - 1; i >= 0; i--)
//                            if (categoryArray[i].equals("인간관계"))
//                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
//                    }
//                    else if(category.equals("취업")) {
//                        for (int i = titleArray.length - 1; i >= 0; i--)
//                            if (categoryArray[i].equals("취업"))
//                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
//                    }
//                    else if(category.equals("경제")) {
//                        for (int i = titleArray.length - 1; i >= 0; i--)
//                            if (categoryArray[i].equals("경제"))
//                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
//                    }
//                    else if(category.equals("외모")) {
//                        for (int i = titleArray.length - 1; i >= 0; i--)
//                            if (categoryArray[i].equals("외모"))
//                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
//                    }
//                    else {
//                        for (int i = titleArray.length - 1; i >= 0; i--)
//                            m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
//                    }
//                }
//                m_Adapter.notifyDataSetChanged();
//            }
//        });


//        editSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                // input창에 문자를 입력할때마다 호출된다.
//                // search 메소드를 호출한다.
//                String text = editSearch.getText().toString();
//                search(text);
//            }
//        });

//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                final String BASE = SharedPreference.getAttribute(context.getApplicationContext(), "IP");
//                selecttitle = m_Adapter.getPosition(position);
//
//                Retrofit retrofit2 = new Retrofit.Builder()
//                        .baseUrl(BASE)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//                ReadPostInterface readPostInterface = retrofit2.create(ReadPostInterface.class);
//                Call<List<Dummy2>> call2 = readPostInterface.listDummies(selecttitle);
//                call2.enqueue(dummies2);
//            }
//
//        });
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