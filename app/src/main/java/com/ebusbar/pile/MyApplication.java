package com.ebusbar.pile;

import android.app.Application;
import android.util.Log;

import com.amap.api.navi.model.NaviLatLng;
import com.ebusbar.dao.LoginDao;
import com.ebusbar.impl.LoginDaoImpl;

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
    private NaviLatLng latLng;
    /**
     * 城市代码
     */
    private String adCode;

    @Override
    public void onCreate() {
        super.onCreate();
        loadCacheLogin();
    }


    /**
     * 设置城市代码
     * @param adCode
     */
    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getAdCode() {
        return adCode;
    }

    /**
     * 设置当前位置
     * @param latLng
     */
    public void setLatLng(NaviLatLng latLng) {
        this.latLng = latLng;
    }

    /**
     * 获取当前位置
     * @return
     */
    public NaviLatLng getLatLng() {
        return latLng;
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
     * 加载缓存登录,如果缓存中存在登录缓存，不需要重新登录
     */
    public void loadCacheLogin(){
        loginDaoImpl = new LoginDaoImpl(this);
        loginDao = loginDaoImpl.getCacheObject();
        if(loginDao != null){ //如果LoginDao不为空，说明在缓存中已经有登录缓存，不需要重新登录
            isLogin = true;
        }else{
            Log.v("获取缓存中的登录数据为空","true");
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
