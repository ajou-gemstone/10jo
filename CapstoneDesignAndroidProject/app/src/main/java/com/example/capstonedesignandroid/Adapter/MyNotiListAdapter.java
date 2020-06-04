package com.example.capstonedesignandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesignandroid.R;

import java.util.ArrayList;

public class MyNotiListAdapter extends RecyclerView.Adapter<MyNotiListAdapter.ViewHolder>{


    private ArrayList<String> title = null ;
    private ArrayList<String> time = null ;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1 ;
        TextView textView2 ;
        ImageView imageView1;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            textView1 = itemView.findViewById(R.id.name) ;
            textView2 = itemView.findViewById(R.id.detail);
            imageView1 = itemView.findViewById(R.id.img);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public MyNotiListAdapter(ArrayList<String> list, ArrayList<String> timelist) {
        title = list ;
        time = timelist;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public MyNotiListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.my_noti_list, parent, false) ;
        MyNotiListAdapter.ViewHolder vh = new MyNotiListAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(MyNotiListAdapter.ViewHolder holder, int position) {
        String text = title.get(position) ;
        String timetext = time.get(position);
        holder.textView1.setText(text) ;
        holder.textView2.setText(timetext) ;

        if(text.contains("메시지")){
            holder.imageView1.setImageResource(R.drawable.chat_black);
        }
        else if(text.contains("수락")){
            holder.imageView1.setImageResource(R.drawable.check);
        }
        else if(text.contains("거절")){
            holder.imageView1.setImageResource(R.drawable.profile);
        }
        else{

        }

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return title.size() ;
    }
}
