package com.example.capstonedesignandroid.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesignandroid.DTO.DummyReservationList;
import com.example.capstonedesignandroid.R;
import com.example.capstonedesignandroid.StaticMethodAndOthers.DefinedMethod;

import java.util.ArrayList;

public class ReservationListAdapter extends RecyclerView.Adapter<ReservationListAdapter.ViewHolder> {

    private ArrayList<DummyReservationList> reservationArrayList;
    private String activityType;
    private Context context;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    //리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener;

    public ReservationListAdapter(Context context, String type, ArrayList<DummyReservationList> a) {
        this.context = context;
        activityType = type;
        reservationArrayList = a;
        Log.d("ReservationListAdapter", "");
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout reservationLL;
        TextView lastTimeTextView;
        TextView startTimeTextView;
        TextView dayTextView;
        TextView dateTextView;
        TextView lectureRoomTextView;
        ImageView scoreChecked;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            reservationLL = itemView.findViewById(R.id.reservationLL);
            lastTimeTextView = itemView.findViewById(R.id.lastTimeTextView);
            startTimeTextView = itemView.findViewById(R.id.startTimeTextView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            lectureRoomTextView = itemView.findViewById(R.id.lectureRoomTextView);
            scoreChecked = itemView.findViewById(R.id.scoreChecked);

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
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_reservation_list, parent, false);

        ReservationListAdapter.ViewHolder vh = new ReservationListAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String lastTime = DefinedMethod.getTimeByPosition(Integer.parseInt(reservationArrayList.get(position).getLastTime())+1);
        holder.lastTimeTextView.setText(lastTime);
        String startTime = DefinedMethod.getTimeByPosition(Integer.parseInt(reservationArrayList.get(position).getStartTime()));
        holder.startTimeTextView.setText(startTime);
        holder.dayTextView.setText(DefinedMethod.getDayNamebyAlpabet(reservationArrayList.get(position).getDay()));
        holder.dateTextView.setText(DefinedMethod.getParsedDate(reservationArrayList.get(position).getDate()));
        holder.lectureRoomTextView.setText(reservationArrayList.get(position).getLectureRoom());

        if(DefinedMethod.isEmpty(reservationArrayList.get(position).getScore())){
            holder.scoreChecked.setVisibility(View.INVISIBLE);
        }else{
            holder.scoreChecked.setImageResource(R.drawable.star);
        }

        //경비원 오늘 확인인 경우 색칠 startTime으로 정렬을 하였으니 lastTime을 가지고 아직 예약 시간이 남은 예약을 색깔로 표시해준다. (today인 경우만)
        if(activityType.equals("todayGuard")){
            if(!DefinedMethod.compareTime(startTime)){
                if(DefinedMethod.compareTime(lastTime)){
                    holder.reservationLL.setBackground(ContextCompat.getDrawable(context, R.drawable.reservation_selected));
                }else{
                    holder.reservationLL.setBackground(ContextCompat.getDrawable(context, R.drawable.reservation2));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return reservationArrayList.size();
    }

}
