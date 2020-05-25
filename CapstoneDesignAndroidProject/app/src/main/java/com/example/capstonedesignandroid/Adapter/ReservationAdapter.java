package com.example.capstonedesignandroid.Adapter;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.capstonedesignandroid.DTO.DummyLectureRoomReservationState;
import com.example.capstonedesignandroid.DTO.LectureRoomReservationState;
import com.example.capstonedesignandroid.LectureroomReservationActivity;
import com.example.capstonedesignandroid.R;

import java.util.ArrayList;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {
    private int timeCountNum = 0;
    private ArrayList<LectureRoomReservationState> mData = null ;
    private Context context;

    //OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    //이 메소드가 있어야 activity에서 override한 class를 instance로 객체화하여 method가 실제로 작동이 가능하도록 할 수 있다.
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    //Adapter에서 일어난 event를 activity에서 처리를 해야하기 때문에 interface를 통하여 activity에서
    //비동기적으로 event를 처리할 수 있도록 한다.
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
        void onTimeClick(View v, int position, View pv);
    }

    //리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        Button lectureRoomNameButton;
        LinearLayout lectureTimetable;

        ViewHolder(View itemView) {
            super(itemView) ;
            // 뷰 객체에 대한 참조. (hold strong reference)
            lectureRoomNameButton = itemView.findViewById(R.id.lectureRoomNameButton);
            lectureTimetable = itemView.findViewById(R.id.lectureTimetable);

            //-------------------------
            //강의실 별 TimeTable UI를 개수에 맞게 동적으로 그려준다.
            LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(150, LinearLayout.LayoutParams.MATCH_PARENT);
            String eachStateList = mData.get(0).getStateList();
            //강의실 별 TimeTable UI를 개수에 맞게 동적으로 그려준다.
            //여기서는 adapter position을 가져올 수 없다.
            String[] splitState = eachStateList.split("\\s+");
            int i = 0;
            for (String eachState : splitState) {
                TextView textView = new TextView(itemView.getContext());//이 itemview의 context를 가져옴
                textView.setLayoutParams(textViewParams);
//                resIdTextView.setText(eachState);
                //동적으로 할당하였을 때는 tag를 붙이는게 편하다.
                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.reservation));
                textView.setGravity(Gravity.CENTER);
                textView.setTag(""+i);
                i++;
                //click하여 생기는 이벤트는 activity에서 처리
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onTimeClick(view, getAdapterPosition(), itemView);
                    }
                });
                lectureTimetable.addView(textView);
            }
            timeCountNum = i;
            //-------------------

            lectureRoomNameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view, getAdapterPosition());
                }
            });
            //터치 2번으로 시간대 범위를 정한다.
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public ReservationAdapter(Context context, ArrayList<LectureRoomReservationState> list) {
        this.context = context;
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ReservationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_lectureroom_reservation_ppd, parent, false);

        ReservationAdapter.ViewHolder vh = new ReservationAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ReservationAdapter.ViewHolder holder, int position) {
        LectureRoomReservationState data = mData.get(position);

        holder.lectureRoomNameButton.setText(data.getLectureroom());

        String eachStateList =  data.getStateList();
        String[] splitState = eachStateList.split("\\s+");
        int i = 0;
        for(String eachState : splitState){
            //tag로 각각 강의실의 각각의 시간들을 접근한다.
            //여기에서야 비로소 time마다 state를 표시할 수 있다.
            TextView textView = holder.lectureTimetable.findViewWithTag(""+i);
            textView.setText(eachState);
            //강의실이 이미 예약되어 있는 경우 click을 못하도록 막는다.
            if(eachState.equals("R") || eachState.equals("L")){
                textView.setClickable(false);
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                textView.setTextColor(Color.argb(255, 255, 0, 0));
            }
            if(eachState.equals("A")){
                textView.setTextColor(Color.argb(0, 255, 255, 255));
            }
            i++;
        }
        //맨 윗 줄은 touch가 불가능 하도록 한다. 그리고 상단에 고정한다.
        if(position == 0){
            holder.lectureRoomNameButton.setClickable(false);
            for(i = 0; i < timeCountNum; i++){
                holder.lectureTimetable.findViewWithTag(""+i).setClickable(false);
                holder.lectureTimetable.findViewWithTag(""+i).setBackground(ContextCompat.getDrawable(context, R.drawable.reservation2));
            }
        }
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}