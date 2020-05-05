package com.example.capstonedesignandroid.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesignandroid.DTO.DummyReservationList;
import com.example.capstonedesignandroid.R;

import java.util.ArrayList;

public class ClassofAdapter extends RecyclerView.Adapter<ClassofAdapter.ViewHolder>{

    private ArrayList<String> arrayList;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    //리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener;

    public ClassofAdapter(ArrayList<String> a){
        arrayList = a;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView classofTextView;
        Button deleteButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            classofTextView = itemView.findViewById(R.id.classofTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
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

        View view = inflater.inflate(R.layout.recyclerview_classof, parent, false) ;

        ClassofAdapter.ViewHolder vh = new ClassofAdapter.ViewHolder(view) ;

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.classofTextView.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
