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
import com.ebusbar.bean.AllStation;
import com.ebusbar.bean.Error;
import com.ebusbar.fragments.UtilFragment;
import com.ebusbar.impl.AllStationDaoImpl;
import com.ebusbar.map.MyLocation;
import com.ebusbar.param.DefaultParam;
import com.ebusbar.pile.LoginActivity;
import com.ebusbar.pile.MainActivity;
import com.ebusbar.pile.NaviEmulatorActivity;
import com.ebusbar.pile.QRActivity;
import com.ebusbar.pile.R;
import com.ebusbar.pile.SearchActivity;
import com.ebusbar.pile.SelectPileActivity;
import com.ebusbar.pile.StationInfoActivity;
import com.ebusbar.utils.FloatUtil;
import com.ebusbar.utils.LogUtil;
import com.ebusbar.view.SlideSwitch;

import java.util.ArrayList;
import java.util.List;

/**
 * 电桩模块
 * Created by Jelly on 2016/2/25.
 */
public class AllStationFragment extends UtilFragment implements AMapLocationListener{
    /**
     * TAG
     */
    private String TAG = "AllStationFragment";
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
     * 充电点位置集合
     */
    private List<Marker> markers = new ArrayList<Marker>();
    /**
     * 是否是刚刚启动界面，如果是就调整缩放距离
     */
    private boolean isFirst = true;
    /**
     * AllPositionListImpl
     */
    private AllStationDaoImpl allStationDao;
    /**
     * 获取所有电桩集合
     */
    private final int msgAllStation = 0x002;
    /**
     * 充电点
     */
    private MarkerOptions markerOptions;
    /**
     * 充电点的PopupWindow
     */
    private PopupWindow markerPw;
    /**
     * 是否筛选使用
     */
    private boolean isUse = false;

    private List<AllStation> dismissList = new ArrayList<>();


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
        MainActivity activity = (MainActivity) getActivity();
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
        allStationDao = new AllStationDaoImpl(context,handler, msgAllStation);
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
        if(application.getLocation() != null){
            String adCode = application.getLocation().getAdCode();
            LogUtil.v(TAG,"获取充电点："+adCode);
            adCode = adCode.substring(0,adCode.length()-2) + "00";
            allStationDao.getDaos();
        }
    }





    /**
     * 设置地图上所有电桩的位置
     */
    public void setAllStationOnMap(List<AllStation> daos){
        markers.clear();
        aMap.clear(true);
        LogUtil.v(TAG, "此次获取到了：" + daos.size());
        MarkerOptions markerOptions = null;
        for(AllStation dao : daos){
            markerOptions = new MarkerOptions();
            if(TextUtils.equals(dao.getEvc_stations_getall().getIsAvailable(),"1")) { //可用
                markerOptions.anchor(0.5f, 0.5f).draggable(false).position(new LatLng(Double.parseDouble(dao.getEvc_stations_getall().getLatitude()), Double.parseDouble(dao.getEvc_stations_getall().getLongitude()))).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_marker));
            }else{
                markerOptions.anchor(0.5f, 0.5f).draggable(false).position(new LatLng(Double.parseDouble(dao.getEvc_stations_getall().getLatitude()), Double.parseDouble(dao.getEvc_stations_getall().getLongitude()))).icon(BitmapDescriptorFactory.fromResource(R.drawable.red_marker));
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
                if (allStationDao.daos == null) {
                    Toast.makeText(context, "对不起，暂无数据，无法搜索！", Toast.LENGTH_SHORT).show();
                    return;
                }
                SearchActivity.startAppActivity(context, (ArrayList<AllStation>) allStationDao.daos);
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
                FragmentTransaction fm = AllStationFragment.this.getFragmentManager().beginTransaction();
                fm.remove(AllStationFragment.this);
                fm.add(R.id.content, new NearbyStationFragment());
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

    public List<AllStation> screenUse(List<AllStation> list){
        dismissList.clear();
        List<AllStation> show = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(TextUtils.equals("1", list.get(i).getEvc_stations_getall().getIsAvailable())){
                show.add(list.get(i));
            }else{
                dismissList.add(list.get(i));
            }
        }
        return show;
    }

    /**
     * 设置点击开关的父容器的布局的时候能够改变开关状态
     */
    public void setSwitchListener(){
        LinearLayout use_layout = (LinearLayout) screenPw.getContentView().findViewById(R.id.use_layout);
        final SlideSwitch use_ss = (SlideSwitch) screenPw.getContentView().findViewById(R.id.use_switch);
        use_ss.setEnabled(false);
        if(isUse){
            use_ss.changeSwitchStatus();
        }
        use_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                use_ss.changeSwitchStatus();
            }
        });
        use_ss.setOnSwitchChangedListener(new SlideSwitch.OnSwitchChangedListener() {
            @Override
            public void onSwitchChanged(SlideSwitch obj, int status) {
                if(status == 1){
                    isUse = true;
                    allStationDao.daos = screenUse(allStationDao.daos);
                    setAllStationOnMap(allStationDao.daos);
                }else{
                    isUse = false;
                    allStationDao.daos.addAll(dismissList);
                    setAllStationOnMap(allStationDao.daos);
                }
            }
        });
        LinearLayout enough_layout = (LinearLayout) screenPw.getContentView().findViewById(R.id.enough_layout);
        final SlideSwitch enough_ss = (SlideSwitch) screenPw.getContentView().findViewById(R.id.enough_switch);
        enough_ss.setEnabled(false);
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
                int index = markers.indexOf(marker);
                LogUtil.v(TAG,"marker:" + marker.getId());
                LogUtil.v(TAG, "index:" + index);
                if (index == -1) { //集合中必须有
                    return true;
                }
                showAllStationPw(root, allStationDao.daos.get(index));
                return true;
            }
        });
    }


    /**
     * 显示电桩，所有列表
     * @param v
     * @param dao
     */
    public void showAllStationPw(View v, final AllStation dao){
        ViewHolder viewHolder = null;
        View pwRoot = null;
        if(markerPw != null){
            pwRoot = markerPw.getContentView();
            viewHolder = (ViewHolder) pwRoot.getTag();
        }else{
            viewHolder = new ViewHolder();
            pwRoot = getActivity().getLayoutInflater().inflate(R.layout.stationpw_layout,null);
            viewHolder.orgName = (TextView) pwRoot.findViewById(R.id.orgName);
            viewHolder.open_text = (TextView) pwRoot.findViewById(R.id.open_text);
            viewHolder.addr = (TextView) pwRoot.findViewById(R.id.addr);
            viewHolder.sum_text = (TextView) pwRoot.findViewById(R.id.sum_text);
            viewHolder.spare_text = (TextView) pwRoot.findViewById(R.id.spare_text);
            viewHolder.distance_text = (TextView) pwRoot.findViewById(R.id.distance_text);
            viewHolder.nav_layout = (LinearLayout) pwRoot.findViewById(R.id.nav_layout);
            viewHolder.appoint_layout = (LinearLayout) pwRoot.findViewById(R.id.appoint_layout);
            markerPw = popupWindowUtil.getPopupWindow(context, pwRoot, windowUtil.getScreenWidth(getActivity()), windowUtil.getScreenHeight(getActivity()) / 3);
            pwRoot.setTag(viewHolder);
        }

        final AllStation.EvcStationsGetallEntity entity = dao.getEvc_stations_getall();

        LatLng startLatLng = new LatLng(Double.parseDouble(application.getLocation().getLatitude()),Double.parseDouble(application.getLocation().getLongitude()));
        LatLng endLatLng = new LatLng(Double.parseDouble(entity.getLatitude()),Double.parseDouble(entity.getLongitude()));
        viewHolder.distance_text.setText(FloatUtil.mToKm(AMapUtils.calculateLineDistance(startLatLng,endLatLng)) + "km");

        viewHolder.orgName.setText(entity.getOrgName());

        if(TextUtils.equals(entity.getIsAvailable(),"1")){
            viewHolder.open_text.setText("有空闲");
        }else{
            viewHolder.open_text.setText("繁忙中");
        }

        viewHolder.addr.setText(entity.getAddr());

        String sum = "0";
        if(!TextUtils.isEmpty(entity.getAvailableNum()) && !TextUtils.isEmpty(entity.getUnavailableNum())){
            sum = Integer.parseInt(entity.getAvailableNum()) + Integer.parseInt(entity.getUnavailableNum())+"";
        }
        viewHolder.sum_text.setText(sum);
        if(!TextUtils.equals(sum,"0")){
            viewHolder.spare_text.setText(entity.getAvailableNum());
        }else{
            viewHolder.spare_text.setText("0");
        }

        viewHolder.nav_layout.setOnClickListener(new View.OnClickListener() { //导航
            @Override
            public void onClick(View v) {
                popupWindowUtil.dismissPopupWindow(markerPw);
                NaviEmulatorActivity.startAppActivity(context, aMap.getMyLocation().getLatitude(), aMap.getMyLocation().getLongitude(), Double.parseDouble(entity.getLatitude()), Double.parseDouble(entity.getLongitude()));
            }
        });

        viewHolder.appoint_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //预约
                popupWindowUtil.dismissPopupWindow(markerPw);
                if(!application.isLogin()){
                    LoginActivity.startAppActivity(context);
                }else {
                    SelectPileActivity.startAppActivity(context, entity.getOrgId(),entity.getAddr());
                }
            }
        });

        pwRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowUtil.dismissPopupWindow(markerPw);
                StationInfoActivity.startActivity(context);
            }
        });

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
                locationClient.setLocationListener(AllStationFragment.this);
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
        if(TextUtils.isEmpty(aMapLocation.getCity())){ //定位失败
            return;
        }
        LogUtil.v(TAG, "缓存位置数据");
        MyLocation location = new MyLocation(aMapLocation.getAdCode(),aMapLocation.getLatitude()+"",aMapLocation.getAddress(),aMapLocation.getLongitude()+"");
        spCacheUtil.cacheMyLocation(context, location);
        application.setLocation(location);
        locationChangedListener.onLocationChanged(aMapLocation);
        //缓存当前位置和城市代码
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
                MainActivity activity = (MainActivity) getActivity();
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
            switch (msg.what){
                case msgAllStation:
                    if(allStationDao.daos == null){
                        return;
                    }
                    if(TextUtils.equals(allStationDao.daos.get(0).getEvc_stations_getall().getIsSuccess(), "N")){
                        Error errorDao = errorParamUtil.checkReturnState(allStationDao.daos.get(0).getEvc_stations_getall().getReturnStatus());
                        toastUtil.toastError(context,errorDao,null);
                        return;
                    }
                    setAllStationOnMap(allStationDao.daos);
                    break;
            }

        }
    };

    @Override
    public String getTAG() {
        return TAG;
    }

    /**
     * 用于缓存PopupWindow中的控件
     */
    private class ViewHolder{
        TextView orgName;
        TextView open_text;
        TextView addr;
        TextView sum_text;
        TextView spare_text;
        TextView distance_text;
        LinearLayout nav_layout;
        LinearLayout appoint_layout;
    }
}
