package com.ebusbar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.ebusbar.fragments.UtilFragment;
import com.ebusbar.map.MyLocation;
import com.ebusbar.param.DefaultParam;
import com.ebusbar.pile.MainActivity;
import com.ebusbar.pile.R;
import com.ebusbar.utils.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 租车模块
 * Created by Jelly on 2016/2/25.
 */
public class RentCarFragment extends UtilFragment implements AMapLocationListener{
    /**
     * TAG
     */
    public String TAG = "RentCarFragment";
    @Bind(R.id.map)
    MapView mapView;
    /**
     * aMap
     */
    private AMap aMap;
    /**
     * 定位客户端
     */
    private AMapLocationClient locationClient;
    /**
     * 定位参数
     */
    private AMapLocationClientOption locationClientOption;
    /**
     * aMap定位改变监听器
     */
    private LocationSource.OnLocationChangedListener locationChangedListener;

    /**
     * 是否是刚刚启动界面，如果是就调整缩放距离
     */
    private boolean isFirst = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        root = inflater.inflate(R.layout.rentcar, container, false);
        ButterKnife.bind(this, root);
        init();
        loadObjectAttribute();
        loadMap(savedInstanceState);
        setListener();
        setFragView();
        return root;
    }

    /**
     * 获取焦点
     */
    @Override
    public void onResume() {
        super.onResume();
        if(mapView != null){
            mapView.onResume();
        }
    }

    /**
     * 暂停
     */
    @Override
    public void onPause() {
        super.onPause();
        if(mapView != null){
            mapView.onPause(); //必须调用高德地图的这个方法
        }
        if (locationClient != null) {
            locationClient.stopLocation(); //停止定位
        }
        MainActivity activity = (MainActivity) getActivity();
        activity.drawerLayout.closeDrawer(Gravity.LEFT); //关闭左边的侧滑栏
        isFirst = true; //把isFirst设为true，切换模块的时候就不会在调整距离了
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 保存状态
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mapView != null){
            mapView.onSaveInstanceState(outState);
        }
    }

    /**
     * 销毁
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mapView != null){
            mapView.onDestroy();
        }
        if (null != locationClient) {
            locationClient.onDestroy();
        }
    }

    @Override
    public void init() {
    }

    /**
     * 加载地图
     *
     * @param savedInstanceState 退出界面时缓存的数据
     */
    public void loadMap(Bundle savedInstanceState) {
        //下面两句话在加载地图时是必须得写的
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap(); //获取地图对象
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(application.getCameraPosition())); //初始化位置和缩放
        setLocation();
        aMap.getUiSettings().setZoomControlsEnabled(false); //设置缩放按钮不可以见
        aMap.getUiSettings().setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER); //设置地图logo在中间显示
        aMap.setMyLocationEnabled(true); //显示定位层，并且可以触发定位
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE); //只在第一次定位移动到地图中心点。
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.location));
        myLocationStyle.radiusFillColor(resourceUtil.getResourceColor(context, R.color.location_round_fill_color));//设置圆形边框的填充颜色
        myLocationStyle.strokeColor(resourceUtil.getResourceColor(context, R.color.location_round_fill_color)); //设置圆形边框的边框颜色
        aMap.setMyLocationStyle(myLocationStyle);
    }

    @Override
    public void loadObjectAttribute() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void setFragView() {

    }

    /**
     * 设置定位
     */
    public void setLocation() {
        aMap.setLocationSource(new LocationSource() {
            @Override
            public void activate(OnLocationChangedListener onLocationChangedListener) {
                if (onLocationChangedListener == null) { //不能为空
                    return;
                }
                //初始化定位参数
                locationChangedListener = onLocationChangedListener;
                locationClient = new AMapLocationClient(context);
                locationClientOption = new AMapLocationClientOption();
                locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                locationClientOption.setNeedAddress(true); //设置需要返回地址
                locationClient.setLocationListener(RentCarFragment.this);
                locationClient.setLocationOption(locationClientOption);
                locationClient.startLocation();
            }

            @Override
            public void deactivate() {
                locationChangedListener = null;
                if (locationClient != null) {
                    locationClient.stopLocation();
                    locationClient.onDestroy();
                }
                locationClient = null;
            }
        });
    }

    /**
     * 定位改变后的监听事件
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (TextUtils.isEmpty(aMapLocation.getCity())) { //定位失败
            return;
        }
        LogUtil.v(TAG, "缓存位置数据");
        MyLocation location = new MyLocation(aMapLocation.getAdCode(), aMapLocation.getLatitude() + "", aMapLocation.getAddress(), aMapLocation.getLongitude() + "");
        spCacheUtil.cacheMyLocation(context, location);
        application.setLocation(location);
        locationChangedListener.onLocationChanged(aMapLocation);
        //缓存当前位置和城市代码
        if (isFirst) {
            aMap.moveCamera(CameraUpdateFactory.zoomTo(DefaultParam.ZOOM)); //修改缩放位置
            isFirst = !isFirst;
        }
    }

    @Override
    public String getTAG() {
        return TAG;
    }

}
