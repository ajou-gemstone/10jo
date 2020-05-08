package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class StudyBulletinBoardActivity extends AppCompatActivity {

    ImageView imageView;
    Intent intent3,intent2,intent4,intent5,intent6,intent7;
    String[] userInfo, usertitle;
    ListView favorite;
    String[] titleArray;
    String[] likeArray;
    String[] categoryArray;
    String[] profileArray;
    String selecttitle;
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

        Intent intent1 = getIntent();
//        userInfo = intent1.getStringArrayExtra("strings");
//        usertitle = intent1.getStringArrayExtra("usertitle");
//
//        titleArray=userInfo[2].split(",");
//        likeArray=userInfo[6].split(",");
//        categoryArray=userInfo[7].split(",");
//        profileArray=userInfo[8].split(",");

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
                    finish();
                    break;
                case R.id.action_check :
                    Intent intent3 = new Intent(StudyBulletinBoardActivity.this, LectureroomCheckActivity.class);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.action_cafe :
                    Intent intent4 = new Intent(StudyBulletinBoardActivity.this, CafeMapActivity.class);
                    startActivity(intent4);
                    finish();
                    break;
                case R.id.action_profile :
                    Intent intent5 = new Intent(StudyBulletinBoardActivity.this, ProfileActivity.class);
                    startActivity(intent5);
                    finish();
                    break;
            }
            return false;
        }
    };

}//MainActivity