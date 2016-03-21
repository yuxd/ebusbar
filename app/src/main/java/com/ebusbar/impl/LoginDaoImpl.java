package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.ebusbar.dao.LoginDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.ebusbar.utils.SharedPreferencesUtil;
import com.jellycai.service.ResponseResultHandler;

/**
 * 登录Impl
 * Created by Jelly on 2016/3/3.
 */
public class LoginDaoImpl extends BaseImpl{
    /**
     * 正常登录的访问接口
     */
    private static final String Path = NetParam.path;
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
        execmode = "crm.login";
    }

    public LoginDaoImpl(Context context){
        super(context);
        loginDao  = new LoginDao();
    }

    /**
     * 从服务器上获取LoginDao的对象，当验证码为空的时候，访问正常登录的接口，当密码为空的时候，访问快速登录接口
     * @param mobile
     * @param password
     * @param code
     */
    public void getNetLoginDao(String mobile,String password,String code,String custid){
        if(TextUtils.isEmpty(mobile)) return ;
        conditionMap.clear(); //清空condition集合里面的数据，避免出现验证码登录失败后，账户和密码登录出现参数过多的情况
        if(TextUtils.isEmpty(code)){ //正常登录
            timestamp = NetParam.getTime();
            conditionMap.put("Mobile",mobile);
            conditionMap.put("Type","0");
            conditionMap.put("Password",password);
            condition = NetParam.spliceCondition(conditionMap);
            param = NetParam.getParamMap(trancode,mode,timestamp,custid,sign_method,sign,execmode,fields,condition);
            service.doPost(Path, param, new ResponseResultHandler() {
                @Override
                public void response(boolean b, String json) {
                    Log.v("json",json);
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
        }else if(TextUtils.isEmpty(password)){ //验证码登录
            timestamp = NetParam.getTime(); //获得当前时间
            conditionMap.put("Mobile",mobile);
            conditionMap.put("Type","1");
            conditionMap.put("Code",code);
            condition = NetParam.spliceCondition(conditionMap);
            Log.v("trancode",trancode);
            Log.v("mode",mode);
            Log.v("timestamp",timestamp);
            Log.v("custid",custid);
            Log.v("sign_method",sign_method);
            Log.v("sign",sign);
            Log.v("execmode",execmode);
            Log.v("fields",fields);
            Log.v("condition",condition);
            param = NetParam.getParamMap(trancode, mode, timestamp, custid, sign_method, sign, execmode, fields, condition);
            service.doPost(Path, param, new ResponseResultHandler() {
                @Override
                public void response(boolean b, String json) {
                    Log.v("json",json.trim());
                    if (b || TextUtils.isEmpty(json)) return;
                    try {
                        loginDao = JsonUtil.arrayFormJson(json, LoginDao[].class).get(0);
                        handler.sendEmptyMessage(msg);
                    } catch (Exception e) {
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
        Log.v("impl","保存验证码");
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
