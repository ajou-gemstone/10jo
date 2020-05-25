package com.example.capstonedesignandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesignandroid.DTO.User;
import com.example.capstonedesignandroid.R;

import java.util.ArrayList;
import java.util.List;

public class UserWaitingListAdapter extends RecyclerView.Adapter<UserWaitingListAdapter.ViewHolder>  {
    //데이터 배열 선언
    private ArrayList<User> mList;
    private Button yes, no;
    private Context context;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener ;
    }

    public interface OnItemClickListener {
        public void onYesClick(View view, int position);
        public void onNoClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, num;
        private ImageView imageview;
        private Button yes, no;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            num = (TextView) itemView.findViewById(R.id.studentnumber);
            imageview = itemView.findViewById(R.id.profile_image);
            yes = itemView.findViewById(R.id.yes);
            no = itemView.findViewById(R.id.no);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onYesClick(v, getAdapterPosition());
                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onNoClick(v, getAdapterPosition());
                }
            });
        }
    }

    //생성자
    public UserWaitingListAdapter(Context context, ArrayList<User> list) {
        this.context = context;
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

        return new UserWaitingListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserWaitingListAdapter.ViewHolder holder, int position) {

        final User user = mList.get(position);

        holder.name.setText(String.valueOf(mList.get(position).getName()));
        holder.num.setText(String.valueOf(mList.get(position).getStudentNum()));
        holder.imageview.setImageResource(R.drawable.profile);

    }

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
