package com.example.capstonedesignandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesignandroid.DTO.DummyCafeCoreInfo;
import com.example.capstonedesignandroid.StaticMethodAndOthers.MyConstants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CafeMapActivity extends AppCompatActivity implements MapView.POIItemEventListener{

    final static int activityIntentConstant = 100;
    final static String TAG = "CafeMapActivity";
    ArrayList<DummyCafeCoreInfo> cafeCoreInfoArrayList;
    ArrayList<MapPOIItem> mapPOIItemArrayList;
    RelativeLayout mapViewContainer;
    MapView mapView;
    protected BottomNavigationView navigationView;
    private Retrofit retrofit;
    private boolean Ioexception = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_map);

        //-----------서버에서 데이터 받기-------------
        retrofit = new Retrofit.Builder()
                .baseUrl(MyConstants.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetService service = retrofit.create(GetService.class);
        Call<List<DummyCafeCoreInfo>> call = service.getCafeInfoList();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<DummyCafeCoreInfo> cafeCoreInfoList = call.execute().body();
                    cafeCoreInfoArrayList = new ArrayList<DummyCafeCoreInfo>(cafeCoreInfoList);
                    Log.d(TAG, "runCafe: " + cafeCoreInfoArrayList.get(0).getCafeId() + "  " + cafeCoreInfoArrayList.get(0).getName() + "  "
                            + cafeCoreInfoArrayList.get(0).getCongestion() + "  " + cafeCoreInfoArrayList.get(0).getCafeBody() + "  "
                            + cafeCoreInfoArrayList.get(0).getLatitude() + "  " + cafeCoreInfoArrayList.get(0).getLongitude() + "  " );
                    Log.d("run: ", "run: ");
                    Ioexception = false;
                } catch (IOException e) {
                    e.printStackTrace();
                    Ioexception = true;
                    Log.d("IOException: ", "IOException: ");
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
        }
        //-----------서버에서 데이터 받기-------------

        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigationView.setSelectedItemId(R.id.action_cafe);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //맵 뷰 생성 및 환경 설정
        mapView = new MapView(this);
        mapViewContainer = findViewById(R.id.map_view);
        // 중심점 변경 + 줌 레벨 변경
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.278677, 127.043775), 2, true);
        mapView.zoomIn(true);// 줌 인
        mapView.zoomOut(true);// 줌 아웃
        mapView.setPOIItemEventListener(this);//핀 이벤트 리스너
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());//커스텀 말풍선, 위쪽에 있어야 작동한다.

        //데이터베이스에서 아주대학교 모든 카페의 id, 카페명, 혼잡도, 카페 수용 인원, 위치를 받는다.
        if(Ioexception){
            cafeCoreInfoArrayList = new ArrayList<>();
            cafeCoreInfoArrayList.add(new DummyCafeCoreInfo(1, "카페1", 1, 20, 37.279223, 127.043013, "어서와"));
            cafeCoreInfoArrayList.add(new DummyCafeCoreInfo(2, "카페2", 2, 40, 37.278771, 127.044343, "어서와"));
            cafeCoreInfoArrayList.add(new DummyCafeCoreInfo(3, "카페3", 3, 60, 37.278447, 127.043399, "어서와"));
            cafeCoreInfoArrayList.add(new DummyCafeCoreInfo(4, "카페4", 3, 80, 37.277807, 127.043528, "어서와"));
            cafeCoreInfoArrayList.add(new DummyCafeCoreInfo(5, "카페5", 5, 100, 37.278046, 127.044011, "어서와"));
        }

        //커스텀 마커를 생성, DB에서 얻은 정보 입력
        mapPOIItemArrayList = new ArrayList<>();
        for(DummyCafeCoreInfo cafeCoreInfo: cafeCoreInfoArrayList){
            MapPOIItem tmpCustomMarker = new MapPOIItem();
            tmpCustomMarker.setItemName("");//ItemName(없으면 표시가 안되므로 아무거나 지정)
            tmpCustomMarker.setTag(cafeCoreInfo.getCafeId());
            tmpCustomMarker.setUserObject(cafeCoreInfo);//marker는 cafeCoreInfo객체를 가진다.
            tmpCustomMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(cafeCoreInfo.getLatitude(), cafeCoreInfo.getLongitude()));//카페의 위치
            tmpCustomMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);//커스텀 이미지를 마커로 사용한다.
            tmpCustomMarker.setCustomImageResourceId(selectImageResourceIdBycongestion(cafeCoreInfo.getCongestion()));//혼잡도에 따른 커스텀 이미지를 등록한다.
            tmpCustomMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.

            Log.d(TAG, "getLatitude: "+cafeCoreInfo.getLatitude());

            mapPOIItemArrayList.add(tmpCustomMarker);
            //지도에 마커를 표시한다.
            mapView.addPOIItem(tmpCustomMarker);
        }

        mapViewContainer.addView(mapView);
    }

    @Override
    public void onBackPressed() {
        // AlertDialog 빌더를 이용해 종료시 발생시킬 창을 띄운다
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setMessage("종료하시겠습니까?");

        // "예" 버튼을 누르면 실행되는 리스너
        alBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }
                System.runFinalization();
                System.exit(0);
            }
        });
        // "아니오" 버튼을 누르면 실행되는 리스너
        alBuilder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return; // 아무런 작업도 하지 않고 돌아간다
            }
        });
        alBuilder.setTitle("프로그램 종료");
        alBuilder.show(); // AlertDialog.Bulider로 만든 AlertDialog를 보여준다.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapViewContainer.removeView(mapView);
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override //marker의 balloon이 눌렸을 때
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        DummyCafeCoreInfo tmpCafeCoreInfo = (DummyCafeCoreInfo)mapPOIItem.getUserObject();
        Intent activityIntent = new Intent(this, CafeDetailedInfoActivity.class);
        activityIntent.putExtra("cafeId", tmpCafeCoreInfo.getCafeId());
        startActivityForResult(activityIntent, activityIntentConstant);

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    // CalloutBalloonAdapter 인터페이스 구현
    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.activity_cafe_map_custom_marker_balloon, null);
        }

        @Override//디폴트 값
        public View getCalloutBalloon(MapPOIItem poiItem) {
            DummyCafeCoreInfo tmpCafeCoreInfo = (DummyCafeCoreInfo)poiItem.getUserObject();
            ((TextView) mCalloutBalloon.findViewById(R.id.name)).setText(tmpCafeCoreInfo.getName());
           // ((TextView) mCalloutBalloon.findViewById(R.id.cafeCongestion)).setText("혼잡도: "+tmpCafeCoreInfo.getCongestion() + "/5");
            ((TextView) mCalloutBalloon.findViewById(R.id.cafeTotalSeat)).setText("총 자리수: "+tmpCafeCoreInfo.getCafeTotalSeat());

            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            mCalloutBalloon.findViewById(R.id.ballonlayout).setBackgroundResource(R.drawable.custom_marker_ballon);//색깔 유지되도록 한다.
            return mCalloutBalloon;
        }
    }

    private int selectImageResourceIdBycongestion(int congestion) {
        switch (congestion) {
            case 1:
                return R.drawable.cafeplace1;//default
            case 2:
                return R.drawable.cafeplace2;
            case 3:
                return R.drawable.cafeplace3;
            case 4:
                return R.drawable.cafeplace4;
            case 5:
                return R.drawable.cafeplace5;
        }

        return R.drawable.cafeplace_default;//default
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId()){
                case R.id.action_group :
                    Intent intent1 = new Intent(CafeMapActivity.this, StudyBulletinBoardActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.action_reservation :
                    Intent intent2 = new Intent(CafeMapActivity.this, LectureroomReservationActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.action_check :
                    Intent intent3 = new Intent(CafeMapActivity.this, LectureroomCheckActivity.class);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.action_cafe :

                    break;
                case R.id.action_profile :
                    Intent intent4 = new Intent(CafeMapActivity.this, MyProfileActivity.class);
                    startActivity(intent4);
                    finish();
                    break;

            }
            return false;
        }
    };
}
