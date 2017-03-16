package com.emotte.mobile.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import com.emotte.mobile.R;

import demo.temperature.com.locationlib.LocationUtil;


public class LocationActivity extends Activity implements LocationUtil.LocationListener {
    private BaiduMap mBaiduMap = null;
    private boolean isFirstLoc = true;
    MapView mMapView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //打开测试，用于测试状态切换是否正常
//        LocationUtil.test=true;
        getLocation();
    }

    private void getLocation() {
        //重试两次，成功
        LocationUtil.getLocation(LocationActivity.this, 3000, false, false);
    }

    @Override
    public void onGetLocationStart() {

    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        mMapView.setVisibility(View.VISIBLE);
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
        if (isFirstLoc) {
            isFirstLoc = false;
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(15.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
    }

    @Override
    public void onGetLocationTimeOut() {

    }

    @Override
    protected void onDestroy() {
        LocationUtil.stopLoacation();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }


}
