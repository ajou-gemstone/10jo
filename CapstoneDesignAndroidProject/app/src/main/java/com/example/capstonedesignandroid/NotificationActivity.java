package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesignandroid.Adapter.MyNotiListAdapter;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> timelist = new ArrayList<>();
    TextView delete;

    MyNotiListAdapter notiadapter = new MyNotiListAdapter(list, timelist);

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        delete = findViewById(R.id.delete_noti_list);

        ArrayList<String> title = SharedPreference.getStringArrayPref(getApplicationContext(), "notilist");
        ArrayList<String> time = SharedPreference.getStringArrayPref(getApplicationContext(), "notitimelist");

        for (int i=0; i<title.size(); i++) {
            list.add(title.get(i)) ;
        }
        for (int i=0; i<time.size(); i++) {
            timelist.add(time.get(i)) ;
        }
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.my_noti_list) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        notiadapter = new MyNotiListAdapter(list, timelist) ;
        recyclerView.setAdapter(notiadapter) ;
        notiadapter.notifyDataSetChanged();

        // setup swipe to remove item
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreference.removeStringArrayPref(getApplicationContext(), "notilist");
                SharedPreference.removeStringArrayPref(getApplicationContext(), "notitimelist");
               recyclerView.setAdapter(null);
               notiadapter.notifyDataSetChanged();
            }
        });

    }//onCreate

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            // 삭제되는 아이템의 포지션을 가져온다
            final int position = viewHolder.getAdapterPosition();
            // 아답타에게 알린다
            list.remove(position);
            notiadapter.notifyItemRemoved(position);
            notiadapter.notifyDataSetChanged();
        }
    };


}
