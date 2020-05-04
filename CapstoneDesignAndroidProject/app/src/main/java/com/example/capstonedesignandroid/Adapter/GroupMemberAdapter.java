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

public class GroupMemberAdapter extends BaseAdapter {
    private class listItem {
        private int groupId;
        private String groupName;
        private String groupType;
        private String title;
        private String intro;
        private String classCode;
        private int studentTotalNumber;
        private int studentCurrentNumber;

        // 매개변수가 있는 생성자로 받아와 값을 전달한다.
        public listItem(int groupId, String groupName, String groupType, String title, String intro, String classCode, int studentCurrentNumber,int studentTotalNumber){
            this.groupId = groupId;
            this.groupName = groupName;
            this.groupType = groupType;
            this.title = title;
            this.intro = intro;
            this.classCode = classCode;
            this. studentCurrentNumber = studentCurrentNumber;
            this.studentTotalNumber = studentTotalNumber;
        }
    }

    // 외부에서 아이템 추가 요청 시 사용
    private ArrayList<listItem> list;

    public GroupMemberAdapter(ArrayList<listItem> list){
        // Adapter 생성시 list값을 넘겨 받는다.
        this.list=list;
    }

    public GroupMemberAdapter() {
        list = new ArrayList();
    }

    public void add(int groupId, String groupName, String groupType, String title, String intro, String classCode, int studentTotalNumber,int studentCurrentNumber) {
        list.add(new listItem(groupId, groupName, groupType, title, intro, classCode, studentTotalNumber, studentCurrentNumber));
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

    public String getPosition(int position) {
        // 현재 position에 따른 list의 값을 반환 시켜준다.
        return list.get(position).groupName;
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
        TextView        textView2 = null;
        TextView        textView3  = null;
        TextView        textView4  = null;

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_study_group_list, parent, false);

            layout    = (LinearLayout) convertView.findViewById(R.id.layout);
            textView1    = (TextView) convertView.findViewById(R.id.groupname);
            textView2    = (TextView) convertView.findViewById(R.id.tag);
            imageView    = (ImageView) convertView.findViewById(R.id.profile_image);
            textView3    = (TextView) convertView.findViewById(R.id.currentnum);
            textView4    = (TextView) convertView.findViewById(R.id.totalnum);


            // 홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.layout = layout;
            holder.textView1 = textView1;
            holder.textView2 = textView2;
            holder.imageView = imageView;
            holder.textView3 = textView3;
            holder.textView4 = textView4;
            convertView.setTag(holder);
        }
        else {
            holder  = (CustomHolder) convertView.getTag();
            layout  = holder.layout;
            textView1 = holder.textView1;
            textView2 = holder.textView2;
            imageView = holder.imageView;
            textView3 = holder.textView3;
            textView4 = holder.textView4;

        }

        // Text 등록
        switch(getItem(pos).groupId) {
            case 0:
                imageView.setImageResource(R.drawable.profile);
                break;
            case 1:
                imageView.setImageResource(R.drawable.profile);
                break;
            case 2:
                imageView.setImageResource(R.drawable.profile);
                break;
            default: break;

        }
        //imageView.setImageResource()
        textView1.setText(getItem(pos).groupName);
        textView2.setText(getItem(pos).groupType);
        textView3.setText(""+getItem(pos).studentCurrentNumber); //int형 가져올땐 string으로 인식되기위해 ""+붙여야함
        textView4.setText(""+getItem(pos).studentTotalNumber);

        return convertView;
    }

    private class CustomHolder {
        public TextView textView1;
        public TextView textView2;
        ImageView imageView;
        LinearLayout layout;
        TextView textView3;
        TextView textView4;
    }
}