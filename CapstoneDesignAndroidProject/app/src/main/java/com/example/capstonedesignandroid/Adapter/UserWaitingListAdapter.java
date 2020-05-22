package com.example.capstonedesignandroid.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesignandroid.DTO.Lecture;
import com.example.capstonedesignandroid.DTO.User;
import com.example.capstonedesignandroid.R;

import java.util.ArrayList;
import java.util.List;

public class UserWaitingListAdapter extends RecyclerView.Adapter<UserWaitingListAdapter.ViewHolder>  {
    //데이터 배열 선언
    private ArrayList<User> mList;
    private  Boolean[] boolList;

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, num;
        private ImageView imageview;
        private Button yes, no;
        private CheckBox checkbox;


        public ViewHolder(View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.lecture_checkbox);
            name = (TextView) itemView.findViewById(R.id.name);
            num = (TextView) itemView.findViewById(R.id.studentnumber);
            imageview = itemView.findViewById(R.id.profile_image);
            yes = itemView.findViewById(R.id.yes);
            // this.setIsRecyclable(false);
        }
    }

    //생성자
    public UserWaitingListAdapter(ArrayList<User> list) {
        this.mList = list;
    }

    // method to access in activity after updating selection
    public List<User> getUserWaitingListAdapter() {
        return mList;
    }

    @NonNull
    @Override
    public UserWaitingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_register_list, parent, false);

        boolList = new Boolean[mList.size()];
        for(int i = 0; i<boolList.length; i++){
            boolList[i] = false;
        }
        return new UserWaitingListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserWaitingListAdapter.ViewHolder holder, int position) {

        final User user = mList.get(position);

        holder.name.setText(String.valueOf(mList.get(position).getName()));
        holder.num.setText(String.valueOf(mList.get(position).getStudentNum()));
        holder.imageview.setImageResource(R.drawable.profile);

        //in some cases, it will prevent unwanted situations
        //holder.checkbox.setOnCheckedChangeListener(null);
        //if true, your checkbox will be selected, else unselected
        //lecture.setChecked(lecture.isSelected());
        //holder.checkbox.setChecked(lecture.isSelected());

//        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                boolList[position] = isChecked;
//                //set your object's last status
//                //lecture.setSelected(isChecked);
//                //holder.checkbox.setChecked(isChecked);
//            }
//        });

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
