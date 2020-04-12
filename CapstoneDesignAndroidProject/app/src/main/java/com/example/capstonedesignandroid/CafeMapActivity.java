package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesignandroid.DTO.CafeCoreInfo;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

public class CafeMapActivity extends AppCompatActivity implements MapView.POIItemEventListener{

    final static int activityIntentConstant = 100;
    final static String TAG = "CafeMapActivity";
    ArrayList<CafeCoreInfo> cafeCoreInfoArrayList;
    ArrayList<MapPOIItem> mapPOIItemArrayList;
    RelativeLayout mapViewContainer;
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cafe_map);

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
        cafeCoreInfoArrayList = new ArrayList<>();
        cafeCoreInfoArrayList.add(new CafeCoreInfo(1, "카페1", 1, 20, 37.279223, 127.043013));
        cafeCoreInfoArrayList.add(new CafeCoreInfo(2, "카페2", 2, 40, 37.278771, 127.044343));
        cafeCoreInfoArrayList.add(new CafeCoreInfo(3, "카페3", 3, 60, 37.278447, 127.043399));
        cafeCoreInfoArrayList.add(new CafeCoreInfo(4, "카페4", 3, 80, 37.277807, 127.043528));
        cafeCoreInfoArrayList.add(new CafeCoreInfo(5, "카페5", 5, 100, 37.278046, 127.044011));

        //커스텀 마커를 생성, DB에서 얻은 정보 입력
        mapPOIItemArrayList = new ArrayList<>();
        for(CafeCoreInfo cafeCoreInfo: cafeCoreInfoArrayList){
            MapPOIItem tmpCustomMarker = new MapPOIItem();
            tmpCustomMarker.setItemName("");//ItemName(없으면 표시가 안되므로 아무거나 지정)
            tmpCustomMarker.setTag(cafeCoreInfo.getCafeId());
            tmpCustomMarker.setUserObject(cafeCoreInfo);//marker는 cafeCoreInfo객체를 가진다.
            tmpCustomMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(cafeCoreInfo.getLatitude(), cafeCoreInfo.getLongitude()));//카페의 위치
            tmpCustomMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);//커스텀 이미지를 마커로 사용한다.
            tmpCustomMarker.setCustomImageResourceId(selectImageResourceIdBycongestion(cafeCoreInfo.getCafeCongestion()));//혼잡도에 따른 커스텀 이미지를 등록한다.
            tmpCustomMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.

            mapPOIItemArrayList.add(tmpCustomMarker);

            //지도에 마커를 표시한다.
            mapView.addPOIItem(tmpCustomMarker);

        }

        mapViewContainer.addView(mapView);
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
        CafeCoreInfo tmpCafeCoreInfo = (CafeCoreInfo)mapPOIItem.getUserObject();
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
            CafeCoreInfo tmpCafeCoreInfo = (CafeCoreInfo)poiItem.getUserObject();
            ((TextView) mCalloutBalloon.findViewById(R.id.cafeName)).setText(tmpCafeCoreInfo.getCafeName());
            ((TextView) mCalloutBalloon.findViewById(R.id.cafeCongestion)).setText("혼잡도: "+tmpCafeCoreInfo.getCafeCongestion() + "/5");
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
                return R.drawable.cafeplaceblack_18;//default
            case 2:
                return R.drawable.cafeplaceyellow_18;
            case 3:
                return R.drawable.cafeplacegreen_18;
            case 4:
                return R.drawable.cafeplaceblue_18;
            case 5:
                return R.drawable.cafeplacered_18;
        }

        return R.drawable.cafeplaceblack_18;//default
    }

}
