package com.example.capstonedesignandroid;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.capstonedesignandroid.Adapter.ReservationCheckAdapter;
import com.example.capstonedesignandroid.Fragment.Fragment_Reservation_Future;
import com.example.capstonedesignandroid.Fragment.Fragment_Reservation_Previous;
import com.example.capstonedesignandroid.Fragment.Fragment_Reservation_Today;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class LectureroomCheckActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    ReservationCheckAdapter adapter = new ReservationCheckAdapter(getSupportFragmentManager());
    protected BottomNavigationView navigationView;
    private static final int REQUEST_IMAGE_CAPTURE = 11111;
    private Fragment_Reservation_Today fragment_Reservation_Today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectureroom_check);

        //------------------퍼미션 코드----------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //퍼미션 상태 확인
            if (!hasPermissions(PERMISSIONS)) {
                //퍼미션 허가 안되어있다면 사용자에게 요청
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            } else {

            }
        }
        //------------------퍼미션 코드----------------

        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigationView.setSelectedItemId(R.id.action_check);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void setupViewPager(ViewPager viewPager) {
        fragment_Reservation_Today = new Fragment_Reservation_Today();
        adapter.addFragment(fragment_Reservation_Today, "오늘의 예약");
        adapter.addFragment(new Fragment_Reservation_Future(), "앞으로의 예약");
        adapter.addFragment(new Fragment_Reservation_Previous(), "예전의 예약");
        viewPager.setAdapter(adapter);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId()){
                case R.id.action_group :
                    Intent intent1 = new Intent(LectureroomCheckActivity.this, StudyBulletinBoardActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.action_reservation :
                    Intent intent2 = new Intent(LectureroomCheckActivity.this, LectureroomReservationActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.action_check :

                    break;
                case R.id.action_cafe :
                    Intent intent4 = new Intent(LectureroomCheckActivity.this, CafeMapActivity.class);
                    startActivity(intent4);
                    finish();
                    break;
                case R.id.action_profile :
                    Intent intent5 = new Intent(LectureroomCheckActivity.this, ProfileActivity.class);
                    startActivity(intent5);
                    finish();
                    break;

            }
            return false;
        }
    };

    //어떠한 이벤트가 발생 시, UI를 즉각 다시 그려준다.
    public void reInflateFragment(String tense){
        switch (tense){
            case "today":
                Log.d("reent", "reInflateFragment: ");
                adapter = new ReservationCheckAdapter(getSupportFragmentManager());
                setupViewPager(mViewPager);
                adapter.notifyDataSetChanged();
        }
    }

    //****-------------------------------------------------------------------------------------------------------------
    //****-------------------------------------------------------------------------------------------------------------
    //****-------------------------------------------------------------------------------------------------------------
    // 여기서부터는 퍼미션 관련 코드
    static final int PERMISSIONS_REQUEST_CODE = 1000;
    String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private boolean hasPermissions(String[] permissions) {
        int result;

        //스트링 배열에 있는 퍼미션들의 허가 상태 여부 확인
        for (String perms : permissions) {
            result = ContextCompat.checkSelfPermission(this, perms);
            if (result == PackageManager.PERMISSION_DENIED) {
                //허가 안된 퍼미션 발견
                return false;
            }
        }
        //모든 퍼미션이 허가되었음
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraPermissionAccepted = grantResults[0]
                            == PackageManager.PERMISSION_GRANTED;
                    boolean diskPermissionAccepted = grantResults[1]
                            == PackageManager.PERMISSION_GRANTED;
                    if (!cameraPermissionAccepted || !diskPermissionAccepted)
                        showDialogForPermission("앱을 실행하려면 퍼미션을 허가하셔야합니다.");
                    else {
                    }
                }
                break;
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        builder.create().show();
    }

}
