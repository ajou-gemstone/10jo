package com.example.capstonedesignandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.capstonedesignandroid.R;

import java.util.ArrayList;

public class UserListAdapter extends BaseAdapter {
    private class listItem {

        private String userId;
        private int leader;
        private String name;

        // 매개변수가 있는 생성자로 받아와 값을 전달한다.
        public listItem(String userId, int leader, String name){
            this.userId = userId;
            this.leader = leader;
            this. name = name;
        }
    }

    // 외부에서 아이템 추가 요청 시 사용
    private ArrayList<listItem> list;

    public UserListAdapter(ArrayList<listItem> list){
        // Adapter 생성시 list값을 넘겨 받는다.
        this.list=list;
    }

    public UserListAdapter() {
        list = new ArrayList();
    }

    public void add(String userId, int leader, String name) {
        list.add(new listItem(userId, leader, name));
    }

    public void clear() {
        list.clear();
    }

    @Override
    public int getCount() {
        // list의 사이즈 만큼 반환
        return list.size();
    }

    @Override
    public listItem getItem(int position) {
        // 현재 position에 따른 list의 값을 반환 시켜준다.
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();


        CustomHolder    holder  = null;
        LinearLayout    layout  = null;
        ImageView       imageView = null;
        TextView        textView1 = null;


        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_group_list, parent, false);

            layout    = (LinearLayout) convertView.findViewById(R.id.layout);
            textView1    = (TextView) convertView.findViewById(R.id.name);
            imageView    = (ImageView) convertView.findViewById(R.id.profile_image);

            // 홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.layout = layout;
            holder.textView1 = textView1;
            holder.imageView = imageView;
            convertView.setTag(holder);
        }
        else {
            holder  = (CustomHolder) convertView.getTag();
            layout  = holder.layout;
            textView1 = holder.textView1;
            imageView = holder.imageView;
        }

        //imageView.setImageResource()
        switch(getItem(pos).leader) {
            case 0:
                imageView.setImageResource(R.drawable.member);
                break;
            case 1:
                imageView.setImageResource(R.drawable.leader);
                break;
            default: break;

        }
        textView1.setText(getItem(pos).name);

        return convertView;
    }

    private class CustomHolder {
        public TextView textView1;
        ImageView imageView;
        LinearLayout layout;
    }
}