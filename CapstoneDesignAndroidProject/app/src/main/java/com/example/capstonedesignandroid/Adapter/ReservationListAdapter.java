package com.example.capstonedesignandroid.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesignandroid.DTO.DummyReservationList;
import com.example.capstonedesignandroid.R;

import java.util.ArrayList;

public class ReservationListAdapter extends RecyclerView.Adapter<ReservationListAdapter.ViewHolder>{

    private ArrayList<DummyReservationList> reservationArrayList;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    //리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener;

    public ReservationListAdapter(ArrayList<DummyReservationList> a){
        reservationArrayList = a;
        Log.d("ReservationListAdapter", "");
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_reservation_list, parent, false) ;

        ReservationListAdapter.ViewHolder vh = new ReservationListAdapter.ViewHolder(view) ;

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(reservationArrayList.get(position).getReservationId());
    }

    @Override
    public int getItemCount() {
        return reservationArrayList.size();
    }

}
