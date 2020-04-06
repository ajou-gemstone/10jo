package com.example.capstonedesignandroid;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class CafeMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cafe_map);

        MapView mapView = new MapView(this);

        ViewGroup mapViewContainer = findViewById(R.id.map_view);
        // 중심점 변경 + 줌 레벨 변경
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.278677, 127.043775), 5, true);
        mapView.zoomIn(true);// 줌 인
        mapView.zoomOut(true);// 줌 아웃
        mapViewContainer.addView(mapView);

    }

}
