package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.enums.PathPlanningStrategy;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.ebusbar.activities.UtilActivity;
import com.ebusbar.utils.TTSController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jelly on 2016/3/8.
 */
public class NaviEmulatorActivity extends UtilActivity implements AMapNaviListener, AMapNaviViewListener {
    /**
     * TAG
     */
    public String TAG = "NaviEmulatorActivity";
    /**
     * STAG
     */
    public static String STAG = "NaviEmulatorActivity";

    AMapNaviView mAMapNaviView;
    AMapNavi mAMapNavi;
    TTSController mTtsManager;
    NaviLatLng mEndLatlng;
    NaviLatLng mStartLatlng;
    List<NaviLatLng> mStartList = new ArrayList<NaviLatLng>();
    List<NaviLatLng> mEndList = new ArrayList<NaviLatLng>();
    List<NaviLatLng> mWayPointList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.standernavimap);
        mAMapNaviView = (AMapNaviView) this.findViewById(R.id.map);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
//        //科大语音包
        mTtsManager = TTSController.getInstance(getApplicationContext());
        mTtsManager.init();
        mTtsManager.startSpeaking();
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());

        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.addAMapNaviListener(mTtsManager);
        mAMapNavi.setEmulatorNaviSpeed(150); //设置模拟导航的速度
    }

    @Override
    public void init() {

    }

    @Override
    public void loadObjectAttribute() {

    }

    @Override
    public void setListener() {
    }

    @Override
    public void setActivityView() {

    }

    /**
     * 在这里规划路径
     */
    @Override
    public void onInitNaviSuccess() {
        mAMapNavi.calculateDriveRoute(mStartList,mEndList, mWayPointList, PathPlanningStrategy.DRIVING_DEFAULT);
    }

    /**
     * 路径规划成功之后调用
     */
    @Override
    public void onCalculateRouteSuccess() {
        mAMapNavi.startNavi(AMapNavi.GPSNaviMode);
    }

    /**
     * 初始化导航失败
     */
    @Override
    public void onInitNaviFailure() {
        Toast.makeText(this, "init navi Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
        Intent intent = getIntent();
        double startLat = intent.getDoubleExtra("startlat", 0);
        double startLong = intent.getDoubleExtra("startlong", 0);
        double endLat = intent.getDoubleExtra("endlat", 0);
        double endLong = intent.getDoubleExtra("endlong",0);
        mStartLatlng = new NaviLatLng(startLat,startLong);
        mEndLatlng = new NaviLatLng(endLat,endLong);
        mStartList.add(mStartLatlng);
        mEndList.add(mEndLatlng);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapNaviView.onPause();

//        仅仅是停止你当前在说的这句话，一会到新的路口还是会再说的
        mTtsManager.stopSpeaking();
//
//        停止导航之后，会触及底层stop，然后就不会再有回调了，但是讯飞当前还是没有说完的半句话还是会说完
//        mAMapNavi.stopNavi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAMapNaviView.onDestroy();
        //since 1.6.0
        //不再在naviview destroy的时候自动执行AMapNavi.stopNavi();
        //请自行执行
        mAMapNavi.stopNavi();
        mAMapNavi.destroy();
        mTtsManager.destroy();
    }





    @Override
    public void onStartNavi(int type) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation location) {

    }

    @Override
    public void onGetNavigationText(int type, String text) {

    }

    /**
     * 结束模拟导航
     */
    @Override
    public void onEndEmulatorNavi() {
        Log.v(TAG,"结束模拟导航");
    }

    @Override
    public void onArriveDestination() {
        Log.v(TAG,"到达目的地");
    }



    @Override
    public void onCalculateRouteFailure(int errorInfo) {
    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int wayID) {

    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
    }

    @Override
    public void onNaviSetting() {
    }

    @Override
    public void onNaviMapMode(int isLock) {

    }

    @Override
    public void onNaviCancel() {
        finish();
    }


    @Override
    public void onNaviTurnClick() {

    }

    @Override
    public void onNextRoadClick() {

    }


    @Override
    public void onScanViewButtonClick() {
    }

    @Deprecated
    @Override
    public void onNaviInfoUpdated(AMapNaviInfo naviInfo) {
    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
    }

    @Override
    public void hideCross() {
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateMultipleRoutesSuccess(int[] ints) {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }


    @Override
    public void onLockMap(boolean isLock) {
    }

    @Override
    public void onNaviViewLoaded() {
        Log.d("wlx", "导航页面加载成功");
        Log.d("wlx", "请不要使用AMapNaviView.getMap().setOnMapLoadedListener();会overwrite导航SDK内部画线逻辑");
    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    /**
     * 开启界面
     */
    public static void startAppActivity(Context context,double startLat,double startLong,double endLat,double endLong){
        Intent intent = new Intent(context,NaviEmulatorActivity.class);
        intent.putExtra("startlat",startLat);
        intent.putExtra("startlong",startLong);
        intent.putExtra("endlat",endLat);
        intent.putExtra("endlong",endLong);
        context.startActivity(intent);
    }

}
