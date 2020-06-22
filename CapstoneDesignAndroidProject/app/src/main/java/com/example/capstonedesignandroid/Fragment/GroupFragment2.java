package com.example.capstonedesignandroid.Fragment;

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

public class GroupFragment2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    Intent intent,intent2;
    ArrayAdapter adapter;
    Button b1, b2, b3, b4, b5, b6, b7, whole, b11, b22, b33, b44, b55, b66, b77, whole11, search;
    String chattingroom_id = "0";
    TextView text, text_sorted;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Context context;
    ArrayAdapter<String> adapter1;
    ArrayList<String> list, titleArray, tagArray, categoryArray, mylectureArray;
    ArrayList<Integer> idArray, currentNumArray, totalNumArray;
    EditText editSearch;
    String category = "";
    String userId;
    GroupListAdapter grouplistAdapter = new GroupListAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView listview;
        View view = inflater.inflate(R.layout.group_fragment_2, container, false);
        whole = view.findViewById(R.id.whole); b1 = view.findViewById(R.id.b1); b2 = view.findViewById(R.id.b2); b3 = view.findViewById(R.id.b3); b4 = view.findViewById(R.id.b4); b5 = view.findViewById(R.id.b5); b6 = view.findViewById(R.id.b6); b7 = view.findViewById(R.id.b7);
        whole11 = view.findViewById(R.id.whole_clicked); b11 = view.findViewById(R.id.b1_clicked); b22 = view.findViewById(R.id.b2_clicked); b33 = view.findViewById(R.id.b3_clicked); b44 = view.findViewById(R.id.b4_clicked); b55 = view.findViewById(R.id.b5_clicked); b66 = view.findViewById(R.id.b6_clicked); b77 = view.findViewById(R.id.b7_clicked);

        mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
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
        mylectureArray = new ArrayList<>();
        list = new ArrayList<>();
        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,list);

        userId = SharedPreference.getAttribute(getActivity().getApplicationContext(), "userId");

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GroupService groupservice = retrofit2.create(GroupService.class);
        Call<List<Group>> call = groupservice.getStudyList();
        CallThread(call);

        GetService service = retrofit2.create(GetService.class);
        Call<User> call2 = service.getUserInfo(userId);
        CallThread2(call2);

        listview = (ListView)view.findViewById(R.id.listview1);
        listview.setAdapter(grouplistAdapter);

        for (int i = 0; i <= titleArray.size() - 1; i++) {
            if( ! categoryArray.get(i).equals("all") ) {
                for(String lec : mylectureArray) {
                    if(lec.equals(categoryArray.get(i)))
                        grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                }
            }
        }

        ButtonSetText(mylectureArray);

        whole.setVisibility(View.GONE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE); b55.setVisibility(View.GONE); b66.setVisibility(View.GONE); b77.setVisibility(View.GONE);

        ButtonToggleHandler();

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
                intent2.putExtra("groupId", idArray.get(position).toString());
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
                    tagArray.clear(); idArray.clear(); titleArray.clear(); categoryArray.clear(); currentNumArray.clear(); totalNumArray.clear();
                    for(int i = 0; i< dummies.size(); i++){
                        if(! dummies.get(dummies.size()-1-i).getCategory().equals("all")) {
                            String tag = "";
                            if (dummies.get(dummies.size()-1-i).getTagName().size() != 0) {
                                for (int t = 0; t < dummies.get(dummies.size()-1-i).getTagName().size(); t++) {
                                    tag = tag + "#" + dummies.get(dummies.size()-1-i).getTagName().get(t).getTagName() + " ";
                                }
                            }
                            tagArray.add(tag);
                            idArray.add(dummies.get(dummies.size()-1-i).getId());
                            titleArray.add(dummies.get(dummies.size()-1-i).getTitle());
                            categoryArray.add(dummies.get(dummies.size()-1-i).getCategory());
                            currentNumArray.add(dummies.get(dummies.size()-1-i).getStudyGroupNumCurrent());
                            totalNumArray.add(dummies.get(dummies.size()-1-i).getStudyGroupNumTotal());
                        }
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

    private void CallThread2(Call<User> call) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    User dummies = call.execute().body();
                    for( String lec : dummies.getLecture()){
                        mylectureArray.add(lec);
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

    //모임 제목, 태그 검색
    public void search(String charText, String category) {

        grouplistAdapter.clear();

        if (charText.length() == 0) {    //모든 목록 추가하기
            if(category.equals("전체")) {
                for (int i = 0; i <= titleArray.size() - 1; i++) {
                    if( ! categoryArray.get(i).equals("all") ) {
                        for(String lec : mylectureArray) {
                            if(lec.equals(categoryArray.get(i)))
                                grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                        }
                    }
                }
            }
            else{ //과목 선택했을 때
                for (int i = 0; i <= titleArray.size() - 1; i++) {
                    if (categoryArray.get(i).equals(category))
                        grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                }
            }

        }
        else{
            // 리스트의 모든 데이터를 검색한다.
            if(category.equals("전체")) {
                for (int i = 0; i <= titleArray.size() - 1; i++) {
                    if( ! categoryArray.get(i).equals("all")) {
                        for(String lec : mylectureArray) {
                            if(lec.equals(categoryArray.get(i))) {
                                if (titleArray.get(i).contains(charText)) {
                                    grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                                } else if (tagArray.get(i).contains(charText)) {
                                    grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                                }
                            }
                        }
                    }
                }
            }
            else{
                for (int i = 0; i <= titleArray.size() - 1; i++) {
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

                grouplistAdapter.clear();
                for (int i = 0; i <= titleArray.size() - 1; i++) {
                    if( ! categoryArray.get(i).equals("all") ) {
                        for(String lec : mylectureArray) {
                            if(lec.equals(categoryArray.get(i))){
                                grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                            }
                        }
                    }
                }
                grouplistAdapter.notifyDataSetChanged();

            }
        },100);
        // 새로고침 완료
        mSwipeRefreshLayout.setRefreshing(false);

    }//onRefresh

    public void ButtonSetText(ArrayList<String> lectures){
        int length = lectures.size();
        if(length==1){
            b1.setText(mylectureArray.get(0)); b11.setText(mylectureArray.get(0));
            b2.setVisibility(View.GONE); b3.setVisibility(View.GONE); b4.setVisibility(View.GONE); b5.setVisibility(View.GONE); b6.setVisibility(View.GONE); b7.setVisibility(View.GONE);
        }
        if(length==2){
            b1.setText(mylectureArray.get(0)); b2.setText(mylectureArray.get(1)); b11.setText(mylectureArray.get(0)); b22.setText(mylectureArray.get(1));
            b3.setVisibility(View.GONE); b4.setVisibility(View.GONE); b5.setVisibility(View.GONE); b6.setVisibility(View.GONE); b7.setVisibility(View.GONE);
        }
        if(length==3){
            b1.setText(mylectureArray.get(0)); b2.setText(mylectureArray.get(1)); b3.setText(mylectureArray.get(2)); b11.setText(mylectureArray.get(0)); b22.setText(mylectureArray.get(1)); b33.setText(mylectureArray.get(2));
            b4.setVisibility(View.GONE); b5.setVisibility(View.GONE); b6.setVisibility(View.GONE); b7.setVisibility(View.GONE);
        }
        if(length==4){
            b1.setText(mylectureArray.get(0)); b2.setText(mylectureArray.get(1)); b3.setText(mylectureArray.get(2)); b4.setText(mylectureArray.get(3)); b11.setText(mylectureArray.get(0)); b22.setText(mylectureArray.get(1)); b33.setText(mylectureArray.get(2)); b44.setText(mylectureArray.get(3));
            b5.setVisibility(View.GONE); b6.setVisibility(View.GONE); b7.setVisibility(View.GONE);
        }
        if(length==5){
            b1.setText(mylectureArray.get(0)); b2.setText(mylectureArray.get(1)); b3.setText(mylectureArray.get(2)); b4.setText(mylectureArray.get(3)); b5.setText(mylectureArray.get(4)); b11.setText(mylectureArray.get(0)); b22.setText(mylectureArray.get(1)); b33.setText(mylectureArray.get(2)); b44.setText(mylectureArray.get(3)); b55.setText(mylectureArray.get(4));
            b6.setVisibility(View.GONE); b7.setVisibility(View.GONE);
        }
        if(length==6){
            b1.setText(mylectureArray.get(0)); b2.setText(mylectureArray.get(1)); b3.setText(mylectureArray.get(2)); b4.setText(mylectureArray.get(3)); b5.setText(mylectureArray.get(4)); b6.setText(mylectureArray.get(5)); b11.setText(mylectureArray.get(0)); b22.setText(mylectureArray.get(1)); b33.setText(mylectureArray.get(2)); b44.setText(mylectureArray.get(3)); b55.setText(mylectureArray.get(4)); b66.setText(mylectureArray.get(5));
            b7.setVisibility(View.GONE);
        }
        if(length==7){
            b1.setText(mylectureArray.get(0)); b2.setText(mylectureArray.get(1)); b3.setText(mylectureArray.get(2)); b4.setText(mylectureArray.get(3)); b5.setText(mylectureArray.get(4)); b6.setText(mylectureArray.get(5)); b7.setText(mylectureArray.get(6)); b11.setText(mylectureArray.get(0)); b22.setText(mylectureArray.get(1)); b33.setText(mylectureArray.get(2)); b44.setText(mylectureArray.get(3)); b55.setText(mylectureArray.get(4)); b66.setText(mylectureArray.get(5)); b77.setText(mylectureArray.get(6));
        }
    }

    public void ButtonToggleHandler(){
        //처음에는 전체 다보여주기
        whole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grouplistAdapter.clear();
                whole.setVisibility(View.GONE);
                whole11.setVisibility(View.VISIBLE);
                if(mylectureArray.size()==1){b1.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE);}
                else if(mylectureArray.size()==2){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE);}
                else if(mylectureArray.size()==3){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE);}
                else if(mylectureArray.size()==4){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE);}
                else if(mylectureArray.size()==5){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE); b55.setVisibility(View.GONE);}
                else if(mylectureArray.size()==6){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b6.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE); b55.setVisibility(View.GONE); b66.setVisibility(View.GONE);}
                else if(mylectureArray.size()==7){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b6.setVisibility(View.VISIBLE); b7.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE); b55.setVisibility(View.GONE); b66.setVisibility(View.GONE); b77.setVisibility(View.GONE);}

                category="전체";
                for (int i = 0; i <= titleArray.size() - 1; i++) {
                    if (!categoryArray.get(i).equals("all")) {
                        for (String lec : mylectureArray) {
                            if (lec.equals(categoryArray.get(i)))
                                grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                        }
                    }
                }
                grouplistAdapter.notifyDataSetChanged();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grouplistAdapter.clear();
                b1.setVisibility(View.GONE);
                b11.setVisibility(View.VISIBLE);
                whole.setVisibility(View.VISIBLE);
                whole11.setVisibility(View.GONE);
                if(mylectureArray.size()==2){b2.setVisibility(View.VISIBLE); b22.setVisibility(View.GONE);}
                else if(mylectureArray.size()==3){b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE);}
                else if(mylectureArray.size()==4){b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE);}
                else if(mylectureArray.size()==5){b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE); b55.setVisibility(View.GONE);}
                else if(mylectureArray.size()==6){b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b6.setVisibility(View.VISIBLE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE); b55.setVisibility(View.GONE); b66.setVisibility(View.GONE);}
                else if(mylectureArray.size()==7){b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b6.setVisibility(View.VISIBLE); b7.setVisibility(View.VISIBLE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE); b55.setVisibility(View.GONE); b66.setVisibility(View.GONE); b77.setVisibility(View.GONE);}

                category = b1.getText().toString();
                for (int i = 0; i <= titleArray.size() - 1; i++) {
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
                b2.setVisibility(View.GONE);
                b22.setVisibility(View.VISIBLE);
                whole.setVisibility(View.VISIBLE);
                whole11.setVisibility(View.GONE);
                b1.setVisibility(View.VISIBLE);
                b11.setVisibility(View.GONE);
                if(mylectureArray.size()==3){b1.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b33.setVisibility(View.GONE);}
                else if(mylectureArray.size()==4){b1.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE);}
                else if(mylectureArray.size()==5){b1.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE); b55.setVisibility(View.GONE);}
                else if(mylectureArray.size()==6){b1.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b6.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE); b55.setVisibility(View.GONE); b66.setVisibility(View.GONE);}
                else if(mylectureArray.size()==7){b1.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b6.setVisibility(View.VISIBLE); b7.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE); b55.setVisibility(View.GONE); b66.setVisibility(View.GONE); b77.setVisibility(View.GONE);}

                category = b2.getText().toString();
                for (int i = 0; i <= titleArray.size() - 1; i++) {
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
                b3.setVisibility(View.GONE);
                b33.setVisibility(View.VISIBLE);
                whole.setVisibility(View.VISIBLE);
                whole11.setVisibility(View.GONE);
                if(mylectureArray.size()==3){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); }
                else if(mylectureArray.size()==4){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b44.setVisibility(View.GONE);}
                else if(mylectureArray.size()==5){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b44.setVisibility(View.GONE); b55.setVisibility(View.GONE);}
                else if(mylectureArray.size()==6){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b6.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b44.setVisibility(View.GONE); b55.setVisibility(View.GONE); b66.setVisibility(View.GONE);}
                else if(mylectureArray.size()==7){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b6.setVisibility(View.VISIBLE); b7.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b44.setVisibility(View.GONE); b55.setVisibility(View.GONE); b66.setVisibility(View.GONE); b77.setVisibility(View.GONE);}

                category = b3.getText().toString();
                for (int i = 0; i <= titleArray.size() - 1; i++) {
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
                b4.setVisibility(View.GONE);
                b44.setVisibility(View.VISIBLE);
                whole.setVisibility(View.VISIBLE);
                whole11.setVisibility(View.GONE);
                if(mylectureArray.size()==4){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE);}
                else if(mylectureArray.size()==5){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b55.setVisibility(View.GONE);}
                else if(mylectureArray.size()==6){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b6.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b55.setVisibility(View.GONE); b66.setVisibility(View.GONE);}
                else if(mylectureArray.size()==7){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b6.setVisibility(View.VISIBLE); b7.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b55.setVisibility(View.GONE); b66.setVisibility(View.GONE); b77.setVisibility(View.GONE);}

                category = b4.getText().toString();
                for (int i = 0; i <= titleArray.size() - 1; i++) {
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
                b5.setVisibility(View.GONE);
                b55.setVisibility(View.VISIBLE);
                whole.setVisibility(View.VISIBLE);
                whole11.setVisibility(View.GONE);
                if(mylectureArray.size()==5){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE); }
                else if(mylectureArray.size()==6){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b6.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE); b66.setVisibility(View.GONE);}
                else if(mylectureArray.size()==7){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b6.setVisibility(View.VISIBLE); b7.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE); b66.setVisibility(View.GONE); b77.setVisibility(View.GONE);}

                category = b5.getText().toString();
                for (int i = 0; i <= titleArray.size() - 1; i++) {
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
                b6.setVisibility(View.GONE);
                b66.setVisibility(View.VISIBLE);
                whole.setVisibility(View.VISIBLE);
                whole11.setVisibility(View.GONE);
                if(mylectureArray.size()==6){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE); b55.setVisibility(View.GONE);}
                else if(mylectureArray.size()==7){b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b7.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE); b55.setVisibility(View.GONE); b77.setVisibility(View.GONE);}

                category = b6.getText().toString();
                for (int i = 0; i <= titleArray.size() - 1; i++) {
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
                b7.setVisibility(View.GONE);
                b77.setVisibility(View.VISIBLE);
                whole.setVisibility(View.VISIBLE);
                whole11.setVisibility(View.GONE);
                b1.setVisibility(View.VISIBLE); b2.setVisibility(View.VISIBLE); b3.setVisibility(View.VISIBLE); b4.setVisibility(View.VISIBLE); b5.setVisibility(View.VISIBLE); b6.setVisibility(View.VISIBLE); b11.setVisibility(View.GONE); b22.setVisibility(View.GONE); b33.setVisibility(View.GONE); b44.setVisibility(View.GONE); b55.setVisibility(View.GONE); b66.setVisibility(View.GONE);

                category = b7.getText().toString();
                for (int i = 0; i <= titleArray.size() - 1; i++) {
                    if (categoryArray.get(i).equals(category))
                        grouplistAdapter.add(idArray.get(i), tagArray.get(i), titleArray.get(i), categoryArray.get(i), currentNumArray.get(i), totalNumArray.get(i));
                }
                grouplistAdapter.notifyDataSetChanged();
            }
        });
    }

}