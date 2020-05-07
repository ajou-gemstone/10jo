package com.example.capstonedesignandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.capstonedesignandroid.Adapter.GroupListAdapter;
import com.example.capstonedesignandroid.DTO.Group;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//public class GroupFragment1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener   새로고침 사용하면 이걸로 바꿔야 함
public class GroupFragment1 extends Fragment {

    Intent intent1,intent2;
    ArrayAdapter adapter;
    Button search;
    String userId,userPassword, maintext, name, trust,emotion, selecttitle, like;
    String chattingroom_id = "0";
    TextView text, text_sorted;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Context context;
    ArrayAdapter<String> adapter1;
    ArrayList<String> list, titleArray, tagArray, categoryArray;
    ArrayList<Integer> idArray, currentNumArray, totalNumArray;
    EditText editSearch;
    GroupListAdapter grouplistAdapter = new GroupListAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ListView listview;
        View view = inflater.inflate(R.layout.group_fragment_1, container, false);
        //mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        //mSwipeRefreshLayout.setOnRefreshListener(this);
        //mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
        context = container.getContext();
        editSearch = view.findViewById(R.id.editSearch);
        search = view.findViewById(R.id.search);
        titleArray = new ArrayList<>();
        tagArray = new ArrayList<>();
        categoryArray = new ArrayList<>();
        idArray = new ArrayList<>();
        currentNumArray = new ArrayList<>();
        totalNumArray = new ArrayList<>();

        Intent intent1 = getActivity().getIntent();

        text = (TextView) view.findViewById(R.id.text);
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
            if(categoryArray.get(i).equals("all"))
                grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), totalNumArray.get(i), currentNumArray.get(i));
        }

        editSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    //listview.requestFocus();
                    return true;
                }
                return false;
            }
        });
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                selecttitle = grouplistAdapter.getPosition(position);

                intent2 = new Intent(getActivity(), ReadGroupActivity.class);
                intent2.putExtra("groupId", idArray.get(position));
                startActivity(intent2);
            }

        });

        return view;
    } //onCreateView

    //모임 제목, 태그 검색
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        grouplistAdapter.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
//            if(userInfo[2].equals("")){
//            }
//            else{
                grouplistAdapter.add(0, "#삼성 #코테", "삼성코테같이준비해요", "x000", 1, 4);
                grouplistAdapter.add(1, "#오픽 #AL", "오픽AL5번연속배출모임", "ㄴㄴㄴ", 2,  10);
//            }
        }
        // 문자 입력을 할때..
        else{
           // titleArray = "삼성코테같이준비해요,오픽AL5번연속배출모임,test1,test2".split(",");
            // 리스트의 모든 데이터를 검색한다.
            for(int i = titleArray.size()-1;i >=0; i--)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (titleArray.get(i).contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    grouplistAdapter.add(0, "#삼성 #코테", "삼성코테같이준비해요", "x000", 1, 4);

                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        grouplistAdapter.notifyDataSetChanged();
    }

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