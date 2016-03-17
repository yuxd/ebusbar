package com.ebusbar.pile;

import android.app.Application;

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

    @Override
    public void onCreate() {
        super.onCreate();
        loadCacheLogin();
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
        LoginDaoImpl loginDaoImpl = new LoginDaoImpl(this);
        loginDao = loginDaoImpl.getCacheObject();
        if(loginDao != null){ //如果LoginDao不为空，说明在缓存中已经有登录缓存，不需要重新登录
            isLogin = true;
        }
    }

    /**
     * 注销登录
     */
    public void loginOut(){
        LoginDaoImpl loginDaoImpl = new LoginDaoImpl(this);
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
