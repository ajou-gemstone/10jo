package com.example.capstonedesignandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class StudyBulletinBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageView imageView;
    Intent intent3,intent2,intent4,intent5,intent6,intent7;
    String[] userInfo, usertitle;
    ListView favorite;
    StudyGroupAdapter m_Adapter1 = new StudyGroupAdapter();
    String[] titleArray;
    String[] likeArray;
    String[] categoryArray;
    String[] profileArray;
    String selecttitle;
    private long backKeyPressedTime = 0;
    private Toast toast;

    // 우선 ArrayList 객체를 ArrayAdapter 객체에 연결합니다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_bulletin_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View nav_header_view = navigationView.getHeaderView(0);
        TextView user = (TextView) nav_header_view.findViewById(R.id.name);
        imageView = (ImageView) nav_header_view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.profile);
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
                TabFragment1 fragment1 = new TabFragment1();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        View llBottomSheet = findViewById(R.id.bottom_sheet);
        favorite = (ListView) findViewById(R.id.list_favorite);

        favorite.setAdapter(m_Adapter1);

        // init the bottom sheet behavior
//        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
//        // set callback for changes
//        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                favorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                        selecttitle = m_Adapter1.getPosition(position);
//
//                        Retrofit retrofit2 = new Retrofit.Builder()
//                                .baseUrl(BASE)
//                                .addConverterFactory(GsonConverterFactory.create())
//                                .build();
//
//                        ReadPostInterface readPostInterface = retrofit2.create(ReadPostInterface.class);
//                        Call<List<Dummy2>> call2 = readPostInterface.listDummies(selecttitle);
//                        call2.enqueue(dummies2);
//                    }
//                });
//            }
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//            }
//        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivityForResult(intent,100);
            }
        });

    }//onCreate

        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();
                toast = Toast.makeText(StudyBulletinBoardActivity.this, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                toast.cancel();
            }

            AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
            alBuilder.setMessage("종료하시겠습니까?");

            // "예" 버튼을 누르면 실행되는 리스너
            alBuilder.setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            // "아니오" 버튼을 누르면 실행되는 리스너
            alBuilder.setNegativeButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        finishAffinity();
                    }
                    System.runFinalization();
                    System.exit(0); // 아무런 작업도 하지 않고 돌아간다
                }
            });
            alBuilder.setTitle("프로그램 종료");
            alBuilder.show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")@Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            intent3 = getIntent();
            intent3 = new Intent(getApplicationContext(),MainActivity.class);
            intent3.putExtra("strings",userInfo) ;
            intent3.putExtra("usertitle",usertitle);
            startActivity(intent3);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            intent3 = getIntent();
            intent3 = new Intent(getApplicationContext(),MainActivity.class);
            intent3.putExtra("strings",userInfo) ;
            intent3.putExtra("usertitle",usertitle);
            startActivity(intent3);
        }  else if (id == R.id.nav_send) {
            intent7 = getIntent();
            intent7 = new Intent(getApplicationContext(),MainActivity.class);
            intent7.putExtra("strings",userInfo);
            intent7.putExtra("usertitle",usertitle) ;
            startActivity(intent7);

        } else if (id == R.id.nav_question){
            intent2 = getIntent();
            Toasty.normal(StudyBulletinBoardActivity.this, "궁금했던점을 말해주세요!", Toast.LENGTH_SHORT).show();
            intent2 = new Intent(getApplicationContext(),MainActivity.class);
            intent2.putExtra("strings",userInfo) ;
            intent2.putExtra("usertitle",usertitle) ;
            startActivity(intent2);
        }
        else if(id == R.id.nav_logout){
            intent4 = getIntent();
            Toasty.success(StudyBulletinBoardActivity.this, "이용해주셔서 감사합니다!", Toast.LENGTH_SHORT).show();
            intent4 = new Intent(getApplicationContext(),Login.class);
            intent4.putExtra("strings",userInfo) ;
            intent4.putExtra("usertitle",usertitle) ;
            startActivity(intent4);
        }
        else if(id == R.id.nav_docs){
            intent5 = getIntent();
            Toasty.normal(StudyBulletinBoardActivity.this, "고민한잔 이용약관입니다", Toast.LENGTH_SHORT).show();
            intent5 = new Intent(getApplicationContext(),MainActivity.class);
            intent5.putExtra("strings",userInfo);
            intent5.putExtra("usertitle",usertitle) ;
            startActivity(intent5);
        }
        else if(id == R.id.nav_promise){
            intent6 = getIntent();
            Toasty.normal(StudyBulletinBoardActivity.this, "모두의 약속", Toast.LENGTH_SHORT).show();
            intent6 = new Intent(getApplicationContext(),MainActivity.class);
            intent6.putExtra("strings",userInfo);
            intent6.putExtra("usertitle",usertitle) ;
            startActivity(intent6);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
         * one of the sections/tabs/pages.
         */
        public class SectionsPagerAdapter extends FragmentPagerAdapter {

            public SectionsPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                // getItem is called to instantiate the fragment for the given page.
                // Return a PlaceholderFragment (defined as a static inner class below).
                return PlaceholderFragment.newInstance(position + 1);
            }

            @Override
            public int getCount() {
                // Show 3 total pages.
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        TabFragment1 tab1 = new TabFragment1();
                    case 1:
                        TabFragment1 tab2 = new TabFragment1();
                    case 2:
                        TabFragment1 tab3 = new TabFragment1();
                }
                return null;
            }
        }//FragmentPagerAdapter
    }//PlaceholderFragment
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

}//MainActivity