package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.dao.LoginDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.ebusbar.utils.SharedPreferencesUtil;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/3/3.
 */
public class LoginDaoImpl extends BaseImpl{
    /**
     * 正常登录的访问接口
     */
    private static final String LoginPath = NetParam.path+"ebusbar/login";
    /**
     * 验证码登录
     */
    private static final String CodeLogin = NetParam.path+"ebusbar/smsLogin";
    /**
     * LoginDao
     */
    public  LoginDao loginDao;
    /**
     * 构造函数
     * @param context
     */
    public LoginDaoImpl(Context context,Handler handler,int msg){
        super(context,handler,msg);
        loginDao = new LoginDao();
    }

    public LoginDaoImpl(Context context){
        super(context);
        loginDao  = new LoginDao();
    }

    /**
     * 从服务器上获取LoginDao的对象，当验证码为空的时候，访问正常登录的接口，当密码为空的时候，访问快速登录接口
     * @param phone
     * @param password
     * @param code
     */
    public void getNetLoginDao(String phone,String password,String code){
        if(phone == null || "".equals(phone)) return ;
        param.clear(); //清空param的数据
        if(code == null || "".equals(code)){ //正常登录
            param.put("phone",phone);
            param.put("password", password);
            service.doPost(LoginPath, param, new ResponseResultHandler() {
                @Override
                public void response(boolean b, String json) {
                    if (b || TextUtils.isEmpty(json)) return;
                    try {
                        loginDao = JsonUtil.objectFromJson(json,LoginDao.class);
                        handler.sendEmptyMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void responseBitmap(boolean b, Bitmap bitmap) {

                }
            });
        }else if(password == null || "".equals(password)){ //验证码登录
            param.put("phone",phone);
            param.put("code",code);
            service.doPost(CodeLogin, param, new ResponseResultHandler() {
                @Override
                public void response(boolean b, String json) {
                    if(b || TextUtils.isEmpty(json)) return;
                    try {
                        loginDao = JsonUtil.objectFromJson(json,LoginDao.class);
                        handler.sendEmptyMessage(msg);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                @Override
                public void responseBitmap(boolean b, Bitmap bitmap) {

                }
            });
        }
    }

    /**
     * 缓存对象
     */
    public void cacheObject(){
        SharedPreferencesUtil.saveObject(context, loginDao);
    }

    /**
     * 获取缓存对象
     * @return
     */
    public LoginDao getCacheObject(){
        loginDao = (LoginDao) SharedPreferencesUtil.readObject(context,loginDao.getClass().getName());
        return loginDao;
    }

    /**
     * 清除缓存
     */
    public void clearCache(){
        SharedPreferencesUtil.clearObject(context,loginDao.getClass().getName());
    }
}
