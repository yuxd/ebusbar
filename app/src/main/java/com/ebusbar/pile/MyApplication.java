package com.ebusbar.pile;

import android.app.Application;

import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.ebusbar.dao.LoginDao;
import com.ebusbar.impl.LoginDaoImpl;
import com.ebusbar.map.MyLocation;
import com.ebusbar.param.DefaultParam;
import com.ebusbar.utils.LogUtil;
import com.ebusbar.utils.SPCacheUtil;

/**
 * MyApplication
 * Created by Jelly on 2016/3/1.
 */
public class MyApplication extends Application{
    /**
     * TAG
     */
    private static String TAG = "MyApplication";
    /**
     * SharePreference操作工具
     */
    private SPCacheUtil spCacheUtil = SPCacheUtil.getInstance();
    /**
     * 是否登录
     */
    private boolean isLogin;
    /**
     * 登录Dao
     */
    private LoginDao loginDao;
    /**
     * LoginDaoImpl
     */
    private LoginDaoImpl loginDaoImpl;
    /**
     * 当前位置
     */
    private MyLocation location;
    /**
     * 地图的默认设置
     */
    private CameraPosition cameraPosition;

    @Override
    public void onCreate() {
        super.onCreate();
        loadCacheLogin();
        loadCacheLocation();
        setMapOption();
    }


    public CameraPosition getCameraPosition() {
        return cameraPosition;
    }

    public void setCameraPosition(CameraPosition cameraPosition) {
        this.cameraPosition = cameraPosition;
    }

    public MyLocation getLocation() {
        return location;
    }

    public void setLocation(MyLocation location) {
        this.location = location;
    }

    /**
     * 返回在APP中用户的登录状态
     * @return
     */
    public boolean isLogin(){
        if(loginDao != null){
            return  true;
        }else {
            return false;
        }
    }

    /**
     * 设置地图的初始显示位置
     */
    public void setMapOption(){
        AMapOptions aMapOptions = new AMapOptions();
        LatLng latLng = new LatLng(22,144);
        if(location != null) {
            latLng = new LatLng(Double.parseDouble(location.getLatitude()), Double.parseDouble(location.getLongitude()));
        }
        aMapOptions.camera(CameraPosition.fromLatLngZoom(latLng, DefaultParam.ZOOM));
        cameraPosition = aMapOptions.getCamera();
    }

    /**
     * 加载缓存登录,如果缓存中存在登录缓存，不需要重新登录
     */
    public void loadCacheLogin(){
        loginDaoImpl = new LoginDaoImpl(this);
        loginDao = loginDaoImpl.getCacheObject();
        if(loginDao != null){ //如果LoginDao不为空，说明在缓存中已经有登录缓存，不需要重新登录
            isLogin = true;
        }else{
            LogUtil.v(TAG,"登录缓存为空");
        }
    }

    /**
     * 加载缓存中的位置信息
     */
    public void loadCacheLocation(){
        location = spCacheUtil.getMyLocation(this);
        if(location != null){
            LogUtil.v(TAG,"成功获取缓存中的位置："+location.getAdCode());
        }else{
            LogUtil.v(TAG,"位置缓存为空");
        }
    }

    /**
     * 缓存对象
     */
    public void cacheLogin(){
        loginDaoImpl = new LoginDaoImpl(this);
        loginDaoImpl.cacheObject(loginDao);
    }


    /**
     * 注销登录
     */
    public void loginOut(){
        loginDaoImpl = new LoginDaoImpl(this);
        loginDaoImpl.clearCache();
        loginDao = null;
    }



    public LoginDao getLoginDao() {
        return loginDao;
    }

    public void setLoginDao(LoginDao loginDao) {
        this.loginDao = loginDao;
    }


}
