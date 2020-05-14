package com.example.capstonedesignandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesignandroid.DTO.Lecture;
import com.example.capstonedesignandroid.R;

import java.util.ArrayList;
import java.util.List;

public class LectureListAdapter extends RecyclerView.Adapter<LectureListAdapter.ViewHolder> {

    //데이터 배열 선언
    private ArrayList<Lecture> mList;
    private  Boolean[] boolList;

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_title;
        private CheckBox checkbox;

        public ViewHolder(View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.lecture_checkbox);
            textView_title = (TextView) itemView.findViewById(R.id.textView_title);
           // this.setIsRecyclable(false);
        }
    }

    //생성자
    public LectureListAdapter(ArrayList<Lecture> list) {
        this.mList = list;
    }

    // method to access in activity after updating selection
    public List<Lecture> getLectureList() {
        return mList;
    }

    @NonNull
    @Override
    public LectureListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lecture_list, parent, false);
        boolList = new Boolean[mList.size()];
        for(int i = 0; i<boolList.length; i++){
            boolList[i] = false;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureListAdapter.ViewHolder holder, int position) {

        final Lecture lecture = mList.get(position);

        holder.textView_title.setText(String.valueOf(mList.get(position).getTitle()));
        //in some cases, it will prevent unwanted situations
        holder.checkbox.setOnCheckedChangeListener(null);
        //if true, your checkbox will be selected, else unselected
        //lecture.setChecked(lecture.isSelected());
        //holder.checkbox.setChecked(lecture.isSelected());

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolList[position] = isChecked;
                //set your object's last status
                //lecture.setSelected(isChecked);
                //holder.checkbox.setChecked(isChecked);
            }
        });

//        // holder.checkBox.setTag(R.integer.btnplusview, convertView);
//        holder.checkbox.setTag(position);
//        holder.checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Integer pos = (Integer) holder.checkbox.getTag();
//
//                if (mList.get(pos).getSelected()) {
//                    mList.get(pos).setSelected(false);
//                } else {
//                    mList.get(pos).setSelected(true);
//                }
//            }
//        });

       // holder.textView_release.setText(String.valueOf(mList.get(position).getRelease()));
        //holder.texView_director.setText(String.valueOf(mList.get(position).getDirector()));
        //다 해줬는데도 GlideApp 에러가 나면 rebuild project를 해주자.
//        GlideApp.with(holder.itemView).load(mList.get(position).getImg_url())
//                .override(300,400)
//                .into(holder.imageView_img);
    }

    public Boolean[] getBoolean() {return boolList; }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
