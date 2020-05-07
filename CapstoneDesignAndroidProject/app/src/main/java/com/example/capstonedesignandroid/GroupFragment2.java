package com.example.capstonedesignandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
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

import com.example.capstonedesignandroid.Adapter.GroupListAdapter;
import com.example.capstonedesignandroid.DTO.Group;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GroupFragment2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    Intent intent,intent2;
    ArrayAdapter adapter;
    Button b1, b2, b3, b4, b5, b6, b7, whole, search;
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
        whole = view.findViewById(R.id.whole); b1 = view.findViewById(R.id.b1); b2 = view.findViewById(R.id.b2); b3 = view.findViewById(R.id.b3); b4 = view.findViewById(R.id.b4); b5 = view.findViewById(R.id.b5); b6 = view.findViewById(R.id.b6); b7 = view.findViewById(R.id.b7);
        mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
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
        CallThread(call);

        listview = (ListView)view.findViewById(R.id.listview1);
        listview.setAdapter(grouplistAdapter);

        for (int i = 0; i <= titleArray.size() - 1; i++) {
            if( ! categoryArray.get(i).equals("all") )
                grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), totalNumArray.get(i), currentNumArray.get(i));
        }

        //처음에는 전체 다보여주기
        whole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grouplistAdapter.clear();
                category="전체";
                for (int i = titleArray.size() - 1; i >= 0; i--)
                    if( ! categoryArray.get(i).equals("all") )
                        grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                grouplistAdapter.notifyDataSetChanged();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    grouplistAdapter.clear();
                    category = b1.getText().toString();
                    for (int i = titleArray.size() - 1; i >= 0; i--) {
                        if (categoryArray.get(i).equals(category))
                            grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                    }
                grouplistAdapter.notifyDataSetChanged();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grouplistAdapter.clear();
                category = b2.getText().toString();
                for (int i = titleArray.size() - 1; i >= 0; i--) {
                    if (categoryArray.get(i).equals(category))
                        grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                }
                grouplistAdapter.notifyDataSetChanged();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grouplistAdapter.clear();
                category = b3.getText().toString();
                for (int i = titleArray.size() - 1; i >= 0; i--) {
                    if (categoryArray.get(i).equals(category))
                        grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                }
                grouplistAdapter.notifyDataSetChanged();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grouplistAdapter.clear();
                category = b4.getText().toString();
                for (int i = titleArray.size() - 1; i >= 0; i--) {
                    if (categoryArray.get(i).equals(category))
                        grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                }
                grouplistAdapter.notifyDataSetChanged();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grouplistAdapter.clear();
                category = b5.getText().toString();
                for (int i = titleArray.size() - 1; i >= 0; i--) {
                    if (categoryArray.get(i).equals(category))
                        grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                }
                grouplistAdapter.notifyDataSetChanged();
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grouplistAdapter.clear();
                category = b6.getText().toString();
                for (int i = titleArray.size() - 1; i >= 0; i--) {
                    if (categoryArray.get(i).equals(category))
                        grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                }
                grouplistAdapter.notifyDataSetChanged();
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grouplistAdapter.clear();
                category = b7.getText().toString();
                for (int i = titleArray.size() - 1; i >= 0; i--) {
                    if (categoryArray.get(i).equals(category))
                        grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                }
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
                search(text, category);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent2 = new Intent(getActivity(), ReadGroupActivity.class);
                intent2.putExtra("groupId", idArray.get(position));
                startActivity(intent2);
            }
        });

        return view;
    } //onCreateView

    private void CallThread(Call<List<Group>> call) {
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
    }

    //모임 제목, 태그 검색
    public void search(String charText, String category) {

        grouplistAdapter.clear();

        if (charText.length() == 0) {
            //모든 목록 추가하기
            for (int i = 0; i <= titleArray.size() - 1; i++) {
                if( ! categoryArray.get(i).equals("all"))
                    grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), totalNumArray.get(i), currentNumArray.get(i));
            }
        }
        else{
            // 리스트의 모든 데이터를 검색한다.
            if(category.equals("전체")) {
                for(int i = titleArray.size()-1;i >=0; i--) {
                    if( ! categoryArray.get(i).equals("all")) {
                        if (titleArray.get(i).contains(charText)) {
                            grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                        } else if (tagArray.get(i).contains(charText)) {
                            grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                        }
                    }
                }
            }
            else{
                for(int i = titleArray.size()-1;i >=0; i--) {
                    if( categoryArray.get(i).equals(category)) {
                        if (titleArray.get(i).contains(charText)) {
                            grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                        } else if (tagArray.get(i).contains(charText)) {
                            grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                        }
                    }
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        grouplistAdapter.notifyDataSetChanged();
    }

    //새로고침 코드
    @Override
    public void onRefresh(){
        mSwipeRefreshLayout.setRefreshing(true);
        final String BASE = MyConstants.BASE;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { // 여기에 코드 추가
                Retrofit retrofit1 = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GroupService groupService = retrofit1.create(GroupService.class);
                Call<List<Group>> call1 = groupService.getStudyList();
                CallThread(call1);

            }
        },1000); // 1초후에 새로고침 끝

    }//onRefresh

}