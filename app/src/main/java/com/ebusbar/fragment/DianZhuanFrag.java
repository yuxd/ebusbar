package com.ebusbar.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.ebusbar.dao.ErrorDao;
import com.ebusbar.dao.PositionListItemDao;
import com.ebusbar.fragments.UtilFragment;
import com.ebusbar.impl.PositionDaoImpl;
import com.ebusbar.myview.SlideSwitch;
import com.ebusbar.param.DefaultParam;
import com.ebusbar.pile.FragmentTabHostActivity;
import com.ebusbar.pile.LoginActivity;
import com.ebusbar.pile.NaviEmulatorActivity;
import com.ebusbar.pile.QRActivity;
import com.ebusbar.pile.R;
import com.ebusbar.pile.SelectPileActivity;
import com.ebusbar.utils.FloatUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 电桩模块
 * Created by Jelly on 2016/2/25.
 */
public class DianZhuanFrag extends UtilFragment implements AMapLocationListener{
    /**
     * TAG
     */
    private String TAG = "DianZhuanFrag";
    /**
     * 返回的界面
     */
    private View root;
    /**
     * 地图
     */
    private MapView mapView;
    /**
     * 会员
     */
    private ImageView member;
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
     * 定位按钮
     */
    private ImageView location;
    /**
     * 筛选按钮
     */
    private ImageView screen;
    /**
     * 关闭筛选菜单
     */
    private ImageView close;
    /**
     * 验证码扫码
     */
    private ImageView qrcode;
    /**
     * 附近
     */
    private TextView nearby;
    /**
     * 搜索
     */
    private TextView search;
    /**
     * 筛选菜单
     */
    private PopupWindow screenPw;
    /**
     * 充电点位=位置集合
     */
    private List<Marker> markers = new ArrayList<Marker>();
    /**
     * 是否是刚刚启动界面，如果是就调整缩放距离
     */
    private boolean isFirst = true;
    /**
     * PositionDaoImpl
     */
    private PositionDaoImpl positionDao;
    /**
     * 获取电桩位置集合消息
     */
    private int msgPositionList = 0x001;
    /**
     * 充电点
     */
    private MarkerOptions markerOptions;
    /**
     * 充电点的PopupWindow
     */
    private PopupWindow markerPw;
    /**
     * 当前Marker
     */
    private String currMarker;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        init(inflater, container);
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
        mapView.onResume();
        aMap.clear(true);
        setFragView(); //每次获取焦点时，重新刷新充电点
    }

    /**
     * 暂停
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause(); //必须调用高德地图的这个方法
        if(locationClient != null) {
            locationClient.stopLocation(); //停止定位
        }
        FragmentTabHostActivity activity = (FragmentTabHostActivity) getActivity();
        activity.drawerLayout.closeDrawer(Gravity.LEFT); //关闭左边的侧滑栏
        isFirst = true; //把isFirst设为true，切换模块的时候就不会在调整距离了
    }

    /**
     * 保存状态
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 销毁
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if(null != locationClient){
            locationClient.onDestroy();
        }
    }

    /**
     * 获取视图中的控件
     * @param inflater
     * @param container
     */
    @Override
    public void init(LayoutInflater inflater,ViewGroup container){
        root = inflater.inflate(R.layout.dianzhuan, container, false);
        qrcode = (ImageView) root.findViewById(R.id.qrcode);
        member = (ImageView)root.findViewById(R.id.member);
        location = (ImageView)root.findViewById(R.id.location);
        screen = (ImageView) root.findViewById(R.id.screen);
        nearby = (TextView) root.findViewById(R.id.nearby);
        search = (TextView) root.findViewById(R.id.search);
    }

    @Override
    public void loadObjectAttribute() {
        positionDao = new PositionDaoImpl(context,handler,msgPositionList);
    }

    @Override
    public void setListener() {
        setOpenDrawerListener(); //设置点击会员头像，打开抽屉
        setMarkerClickListener();
        setQRListener();
        setToLocationListener(); //设置定位按钮的监听事件
        setToScreenListener(); //设置筛选PopupWindow的监听事件
        setNearbyListener();
        setSearchListener();
    }

    @Override
    public void setFragView() {
        loadPosition();
    }


    /**
     * 加载电桩位置
     */
    public void loadPosition(){
        if(!TextUtils.isEmpty(application.getAdCode())){
            String adCode = application.getAdCode();
            adCode = adCode.substring(0,adCode.length()-2) + "00";
            positionDao.getNetPositionListDao(adCode);
        }
    }

    /**
     * 设置地图上设置电桩位置
     */
    public void setPositionOnMap(List<PositionListItemDao> positionDaoList){
        markers.clear();
        MarkerOptions markerOptions = null;
        Log.v(TAG, positionDaoList.size() + "");
        for(PositionListItemDao positionDao : positionDaoList){
            markerOptions = new MarkerOptions();
            if(TextUtils.equals(positionDao.getEvc_stations_get().getIsAvailable(),"1")) { //可用
                markerOptions.anchor(0.5f, 0.5f).draggable(false).position(new LatLng(Double.parseDouble(positionDao.getEvc_stations_get().getLatitude()), Double.parseDouble(positionDao.getEvc_stations_get().getLongitude()))).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_marker));
            }else{
                markerOptions.anchor(0.5f, 0.5f).draggable(false).position(new LatLng(Double.parseDouble(positionDao.getEvc_stations_get().getLatitude()), Double.parseDouble(positionDao.getEvc_stations_get().getLongitude()))).icon(BitmapDescriptorFactory.fromResource(R.drawable.red_marker));
            }
            markers.add(aMap.addMarker(markerOptions));
        }
    }


    /**
     * 设置搜索监听
     */
    public void setSearchListener(){
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(positionDao.positionDaoList.size() == 0){
                    Toast.makeText(context,"对不起，暂无数据，无法搜索！",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }

    /**
     * 设置附近电桩
     */
    public void setNearbyListener(){
        nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fm = DianZhuanFrag.this.getFragmentManager().beginTransaction();
                fm.remove(DianZhuanFrag.this);
                fm.add(R.id.content, new NearbyFrag());
                fm.commit();
            }
        });
    }


    /**
     * 定位按钮,设置跳转到当前位置
     */
    public void setToLocationListener(){
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //强制跳转到当前定位位置，定位位置2000毫秒(默认)刷新一次
                aMap.animateCamera(
                        new CameraUpdateFactory().newLatLngZoom
                                (new LatLng(aMap.getMyLocation().getLatitude(), aMap.getMyLocation().getLongitude()), aMap.getCameraPosition().zoom));
            }
        });
    }

    /**
     * 设置筛选按钮,点击筛选按钮时，打开PopupWindow
     */
    public void setToScreenListener(){
       screen.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //打开PopupWindow,显示操作栏
               screenPw = popupWindowUtil.getPopupWindow(getActivity(), R.layout.screen_layout, windowUtil.getScreenWidth(getActivity()) / 4 * 3, v.getHeight());
               int[] location = windowUtil.getViewLocation(v); //获取筛选按钮的x坐标
               //设置显示的属性，x=筛选按钮在屏幕的x坐标-PopupWindow的宽度+筛选按钮的宽度，y=筛选按钮在屏幕的y坐标
               screenPw.showAtLocation(v, Gravity.NO_GRAVITY, location[0] - screenPw.getWidth() + v.getWidth(), location[1]);
               setScreenClose(); //设置筛选关闭按钮的监听事件
               setSwitchListener();
           }
       });
    }

    /**
     * 设置点击开关的父容器的布局的时候能够改变开关状态
     */
    public void setSwitchListener(){
        LinearLayout use_layout = (LinearLayout) screenPw.getContentView().findViewById(R.id.use_layout);
        final SlideSwitch use_ss = (SlideSwitch) screenPw.getContentView().findViewById(R.id.use_switch);
        use_ss.setSlide(false);
        use_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                use_ss.changeSwitchStatus();
            }
        });
        LinearLayout enough_layout = (LinearLayout) screenPw.getContentView().findViewById(R.id.enough_layout);
        final SlideSwitch enough_ss = (SlideSwitch) screenPw.getContentView().findViewById(R.id.enough_switch);
        enough_ss.setSlide(false);
        enough_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enough_ss.changeSwitchStatus();
            }
        });
    }

    /**
     * 设置筛选菜单的关闭
     */
    public void setScreenClose(){
        close = (ImageView) screenPw.getContentView().findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "关闭筛选菜单");
                screenPw.dismiss(); //关闭筛选菜单
            }
        });
    }

    /**
     * 设置Marker的点击事件
     */
    public void setMarkerClickListener(){
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker == null) { //marker不能为空
                    return true;
                }
                if (markerPw != null && markerPw.isShowing()) return true; //如果已经是弹出转状态
                if (TextUtils.equals(currMarker, marker.getId())) {//已经弹出
                    currMarker = "";
                    return true;
                }
                int index = markers.indexOf(marker);
                if (index == -1) { //集合中必须有
                    return true;
                }
                currMarker = marker.getId();
                showDianZhuanPw(root, positionDao.positionDaoList.get(index));
                return true;
            }
        });
    }

    /**
     * 显示电桩PW
     */
    public void showDianZhuanPw(View v, final PositionListItemDao positionDao){
        View root = getActivity().getLayoutInflater().inflate(R.layout.dianzhuanpw_layout,null);
        TextView dianzhuan_name = (TextView) root.findViewById(R.id.dianzhuan_name);
        TextView free_text = (TextView) root.findViewById(R.id.free_text);
        TextView open_text = (TextView) root.findViewById(R.id.open_text);
        TextView duanzhuan_position = (TextView) root.findViewById(R.id.duanzhuan_position);
        TextView sum_text = (TextView) root.findViewById(R.id.sum_text);
        TextView spare_text = (TextView) root.findViewById(R.id.spare_text);
        TextView price = (TextView) root.findViewById(R.id.price);
        TextView distance_text = (TextView) root.findViewById(R.id.distance_text);
        PositionListItemDao.EvcStationsGetEntity entity = positionDao.getEvc_stations_get();
        LatLng naviLatLng = application.getLatLng();
        LatLng startLatLng = new LatLng(naviLatLng.latitude,naviLatLng.longitude);
        LatLng endLatLng = new LatLng(Double.parseDouble(entity.getLatitude()),Double.parseDouble(entity.getLongitude()));
        distance_text.setText(FloatUtil.mToKm(AMapUtils.calculateLineDistance(startLatLng,endLatLng)) + "km");
        dianzhuan_name.setText(entity.getOrgName());
        if(TextUtils.equals(entity.getIsAvailable(),"1")){
            open_text.setText("有空闲");
        }
        duanzhuan_position.setText(entity.getAddr());
        String sum = "0";
        if(!TextUtils.isEmpty(entity.getAvailableNum()) || !TextUtils.isEmpty(entity.getUnavailableNum())){
            sum = Integer.parseInt(entity.getAvailableNum()) + Integer.parseInt(entity.getUnavailableNum())+"";
        }
        sum_text.setText(sum);
        if(!TextUtils.equals(sum,"0")){
            spare_text.setText(entity.getAvailableNum());
        }
        LinearLayout nav_layout = (LinearLayout) root.findViewById(R.id.nav_layout);
        nav_layout.setOnClickListener(new View.OnClickListener() { //导航
            @Override
            public void onClick(View v) {
                markerPw.dismiss();
                NaviEmulatorActivity.startAppActivity(context, aMap.getMyLocation().getLatitude(), aMap.getMyLocation().getLongitude(), Double.parseDouble(positionDao.getEvc_stations_get().getLatitude()), Double.parseDouble(positionDao.getEvc_stations_get().getLongitude()));
            }
        });
        LinearLayout appoint_layout = (LinearLayout) root.findViewById(R.id.appoint_layout);
        appoint_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //预约
                markerPw.dismiss();
                if(!application.isLogin()){
                    LoginActivity.startAppActivity(context);
                }else {
                    SelectPileActivity.startAppActivity(context, positionDao.getEvc_stations_get().getOrgId());
                }
            }
        });
        markerPw = popupWindowUtil.getPopupWindow(context, root, windowUtil.getScreenWidth(getActivity()), windowUtil.getScreenHeight(getActivity()) / 3);
        markerPw.setAnimationStyle(R.style.markerpw_anim);
        markerPw.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 加载地图
     * @param savedInstanceState 退出界面时缓存的数据
     */
    public void loadMap(Bundle savedInstanceState){
        //下面两句话在加载地图时是必须得写的
        mapView = (MapView) root.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap(); //获取地图对象
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
        myLocationStyle.strokeColor(resourceUtil.getResourceColor(context,R.color.location_round_fill_color)); //设置圆形边框的边框颜色
        aMap.setMyLocationStyle(myLocationStyle);
    }


    /**
     * 设置定位
     */
    public void setLocation(){
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
                locationClient.setLocationListener(DianZhuanFrag.this);
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
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        locationChangedListener.onLocationChanged(aMapLocation);
        LatLng latLng = new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
        application.setLatLng(latLng);
//        application.setAdCode(aMapLocation.getAdCode());
        application.setAdCode("520300");
        Log.v(TAG,aMapLocation.getAdCode());
        if(isFirst){
            loadPosition(); //加载充电点
            aMap.moveCamera(CameraUpdateFactory.zoomTo(DefaultParam.ZOOM)); //修改缩放位置
            isFirst = !isFirst;
        }
    }

    /**
     * 当点击会员头像的时候,打开抽屉
     */
    public void setOpenDrawerListener(){
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTabHostActivity activity = (FragmentTabHostActivity) getActivity();
                activity.drawerLayout.openDrawer(Gravity.LEFT); //打开左边抽屉
            }
        });
    }

    /**
     * 设置验证码扫码按钮的点击事件
     */
    public void setQRListener(){
        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!application.isLogin()) { //没有登录
                    LoginActivity.startAppActivity(context);
                    return;
                }
                QRActivity.startAppActivity(context);
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == msgPositionList){
                if(positionDao.positionDaoList == null || positionDao.positionDaoList.size() == 0){
                    return;
                }
               if(TextUtils.equals(positionDao.positionDaoList.get(0).getEvc_stations_get().getIsSuccess(),"N")){
                   ErrorDao errorDao = errorParamUtil.checkReturnState(positionDao.positionDaoList.get(0).getEvc_stations_get().getReturnStatus());
                   toastUtil.toastError(context,errorDao,null);
                   return;
               }
                setPositionOnMap(positionDao.positionDaoList);
            }
        }
    };

    @Override
    public String getTAG() {
        return TAG;
    }
}
