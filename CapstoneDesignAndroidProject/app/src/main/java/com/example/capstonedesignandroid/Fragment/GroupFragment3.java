package com.example.capstonedesignandroid.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.capstonedesignandroid.Adapter.GroupListAdapter;
import com.example.capstonedesignandroid.Adapter.MyNotiListAdapter;
import com.example.capstonedesignandroid.DTO.Group;
import com.example.capstonedesignandroid.DTO.User;
import com.example.capstonedesignandroid.GetService;
import com.example.capstonedesignandroid.GroupService;
import com.example.capstonedesignandroid.R;
import com.example.capstonedesignandroid.ReadGroupActivity;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GroupFragment3 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    Intent intent2;
    Button search;
    String userId;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Context context;
    ArrayList<String> titleArray, tagArray;
    ArrayList<Integer> idArray, currentNumArray, totalNumArray;
    EditText editSearch;
    GroupListAdapter groupAdapter = new GroupListAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ListView listview;
        View view = inflater.inflate(R.layout.group_fragment_3, container, false);
        mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        titleArray = new ArrayList<>();
        tagArray = new ArrayList<>();
        idArray = new ArrayList<>();
        currentNumArray = new ArrayList<>();
        totalNumArray = new ArrayList<>();
        context = container.getContext();
        editSearch = view.findViewById(R.id.editSearch);
        search = view.findViewById(R.id.search);

        userId = SharedPreference.getAttribute(getActivity().getApplicationContext(), "userId");

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GroupService groupservice = retrofit2.create(GroupService.class);
        Call<List<Group>> call = groupservice.getMyStudyList(userId);
        CallThread(call);

        //db에서 가져오기
        listview = (ListView)view.findViewById(R.id.listview1);
        listview.setAdapter(groupAdapter);

        for (int i = 0; i <= titleArray.size() - 1; i++) {
            groupAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), "", currentNumArray.get(i), totalNumArray.get(i));
        }

        //groupAdapter.clear();

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
                search(text);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent2 = new Intent(getActivity(), ReadGroupActivity.class);
                intent2.putExtra("groupId", idArray.get(position).toString());
                startActivity(intent2);
            }

        });

        return view;
    } //onCreateView

    //모임 제목, 태그 검색
    public void search(String charText) {

        groupAdapter.clear();

        if (charText.length() == 0) {
            for (int i = 0; i <= titleArray.size() - 1; i++) {
                groupAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i),"", currentNumArray.get(i), totalNumArray.get(i));
            }
        }
        else{
            for(int i = titleArray.size()-1;i >=0; i--) {
                if (titleArray.get(i).contains(charText)) {
                    groupAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i),"", currentNumArray.get(i), totalNumArray.get(i));
                } else if (tagArray.get(i).contains(charText)) {
                    groupAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i),"", currentNumArray.get(i), totalNumArray.get(i));
                }   
            }
        }
        groupAdapter.notifyDataSetChanged();
    }


    //새로고침 코드
    @Override
    public void onRefresh(){
        mSwipeRefreshLayout.setRefreshing(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { // 여기에 코드 추가

                Retrofit retrofit2 = new Retrofit.Builder()
                        .baseUrl(MyConstants.BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GroupService groupservice = retrofit2.create(GroupService.class);
                Call<List<Group>> call = groupservice.getMyStudyList(userId);
                CallThread(call);
            }
        },2000); // 1초후에 새로고침 끝
        // 새로고침 완료
        mSwipeRefreshLayout.setRefreshing(false);

    }

    private void CallThread(Call<List<Group>> call) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Group> dummies = call.execute().body();

                    for(int i = 0; i< dummies.size(); i++){
                            String tag = "";
                            if (dummies.get(dummies.size()-1-i).getTagName().size() != 0) {
                                for (int t = 0; t < dummies.get(dummies.size()-1-i).getTagName().size(); t++) {
                                    tag = tag + "#" + dummies.get(dummies.size()-1-i).getTagName().get(t).getTagName() + " ";
                                }
                            }
                            tagArray.clear(); idArray.clear(); titleArray.clear(); currentNumArray.clear(); totalNumArray.clear();
                            tagArray.add(tag);
                            idArray.add(dummies.get(dummies.size()-1-i).getId());
                            titleArray.add(dummies.get(dummies.size()-1-i).getTitle());
                            currentNumArray.add(dummies.get(dummies.size()-1-i).getStudyGroupNumCurrent());
                            totalNumArray.add(dummies.get(dummies.size()-1-i).getStudyGroupNumTotal());
                    }
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



}