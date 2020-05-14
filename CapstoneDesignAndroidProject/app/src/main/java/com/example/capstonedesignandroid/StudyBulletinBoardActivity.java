package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;

import com.example.capstonedesignandroid.Fragment.GroupFragment1;
import com.example.capstonedesignandroid.Fragment.GroupFragment2;
import com.example.capstonedesignandroid.Fragment.GroupFragment3;
import com.example.capstonedesignandroid.StaticMethodAndOthers.SharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudyBulletinBoardActivity extends AppCompatActivity {

    String userId;
    private long backKeyPressedTime = 0;
    private Toast toast;
    protected BottomNavigationView navigationView;

    // 우선 ArrayList 객체를 ArrayAdapter 객체에 연결합니다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_bulletin_board);

        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigationView.setSelectedItemId(R.id.action_group);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        userId = SharedPreference.getAttribute(getApplicationContext(), "userId");

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final ArrayList<String> list = new ArrayList<>();

        // 4. ArrayList 객체에 데이터를 집어넣습니다.
//
//        final ArrayAdapter<String> favoriteadapter = new ArrayAdapter<String>(
//                this, //context(액티비티 인스턴스)
//                android.R.layout.simple_list_item_1, // 한 줄에 하나의 텍스트 아이템만 보여주는 레이아웃 파일
//                // 한 줄에 보여지는 아이템 갯수나 구성을 변경하려면 여기에 새로만든 레이아웃을 지정하면 됩니다.
//                list  // 데이터가 저장되어 있는 ArrayList 객체
//        );
//
//        final String BASE = SharedPreference.getAttribute(getApplicationContext(), "IP");
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        FavoriteListInterface favoriteListInterface = retrofit.create(FavoriteListInterface.class);
//        Call<List<Dummy>> call = favoriteListInterface.listDummies(userInfo[0]);
//        call.enqueue(dummies);


//        switch(Integer.parseInt(userInfo[5])) {
//            case 0:
//                imageView.setImageResource(R.drawable.heart);
//                break;
//            case 1:
//                imageView.setImageResource(R.drawable.heart);
//                break;
//            default: break;
//        }
//        user.setText(userInfo[3].toString());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("모든스터디"));
        tabLayout.addTab(tabLayout.newTab().setText("과목별스터디"));
        tabLayout.addTab(tabLayout.newTab().setText("나의스터디"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                GroupFragment1 fragment1 = new GroupFragment1();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MakeGroupActivity.class);
                startActivityForResult(intent,100);
            }
        });

    }//onCreate

    @Override
    public void onBackPressed() { //super.onBackPressed();비워두면 실행안되서 뒤로가기 안됨
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId()){
                case R.id.action_group :
                    break;
                case R.id.action_reservation :
                    Intent intent2 = new Intent(StudyBulletinBoardActivity.this, LectureroomReservationActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.action_check :
                    Intent intent3 = new Intent(StudyBulletinBoardActivity.this, LectureroomCheckActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.action_cafe :
                    Intent intent4 = new Intent(StudyBulletinBoardActivity.this, CafeMapActivity.class);
                    startActivity(intent4);
                    break;
                case R.id.action_profile :
                    Intent intent5 = new Intent(StudyBulletinBoardActivity.this, ProfileActivity.class);
                    startActivity(intent5);
                    break;
            }
            return false;
        }
    };

}//MainActivity