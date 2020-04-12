package com.example.capstonedesignandroid.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.capstonedesignandroid.R;

import java.util.ArrayList;

public class PPDAdapter extends RecyclerView.Adapter<PPDAdapter.ViewHolder> {
    private ArrayList<String> mData = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        Button lectureRoomNameButton;
        ImageView lectureRoomInfo;

        ViewHolder(View itemView) {
            super(itemView) ;
            // 뷰 객체에 대한 참조. (hold strong reference)
            lectureRoomNameButton = itemView.findViewById(R.id.lectureRoomNameButton);
            lectureRoomInfo = itemView.findViewById(R.id.lectureRoomInfo);
            lectureRoomNameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view, getAdapterPosition());
                }
            });

            lectureRoomInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view, getAdapterPosition());//get adapter position으로 자신의 포지션을 가져올 수 있다.
                }
            });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public PPDAdapter(ArrayList<String> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public PPDAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_lectureroom_reservation_ppd, parent, false) ;
        PPDAdapter.ViewHolder vh = new PPDAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(PPDAdapter.ViewHolder holder, int position) {
        String text = mData.get(position);
        holder.lectureRoomNameButton.setText(text);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}