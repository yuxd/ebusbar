package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.bean.Login;
import com.ebusbar.param.NetParam;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.LogUtil;
import com.ebusbar.utils.SharedPreferencesUtil;
import com.jellycai.service.ResponseResultHandler;

/**
 * 登录Impl
 * Created by Jelly on 2016/3/3.
 */
public class LoginDaoImpl extends BaseDaoImpl {
    /**
     * LoginDao
     */
    public Login dao;

    private SharedPreferencesUtil sharedPreferencesUtil = SharedPreferencesUtil.getInstance();

    /**
     * 构造函数
     * @param context
     */
    public LoginDaoImpl(Context context,Handler handler,int msg){
        super(context,handler,msg);
        dao = new Login();
        execmode = "crm.login";
    }

    public LoginDaoImpl(Context context){
        super(context);
        dao  = new Login();
    }

    /**
     * 从服务器上获取LoginDao的对象，当验证码为空的时候，访问正常登录的接口，当密码为空的时候，访问快速登录接口
     * @param mobile
     * @param password
     * @param code
     */
    public void getNetLoginDao(String mobile,String password,String code){
        if(TextUtils.isEmpty(mobile)) return ;
        conditionMap.clear(); //清空condition集合里面的数据，避免出现验证码登录失败后，账户和密码登录出现参数过多的情况
        if(TextUtils.isEmpty(code)){ //正常登录
            timestamp = NetParam.getTime();
            conditionMap.put("Mobile",mobile);
            conditionMap.put("Type","0");
            conditionMap.put("Password",password);
            condition = NetParam.spliceCondition(conditionMap);
            param = NetParam.getParamMap(trancode,mode,timestamp,"1",sign_method,sign,execmode,fields,condition);
            service.doPost(path, param, new ResponseResultHandler() {
                @Override
                public void response(boolean b, String json) {
                    LogUtil.v(TAG,json.trim());
                    if(NetParam.isSuccess(b,json)){
                        dao = JsonUtil.objectFromJson(json,Login.class);
                    }
                    handler.sendEmptyMessage(msg);
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
            param = NetParam.getParamMap(trancode, mode, timestamp, "1", sign_method, sign, execmode, fields, condition);
            service.doPost(path, param, new ResponseResultHandler() {
                @Override
                public void response(boolean b, String json) {
                    LogUtil.v(TAG,json.trim());
                    if(NetParam.isSuccess(b,json)){
                        dao = JsonUtil.objectFromJson(json,Login.class);
                        handler.sendEmptyMessage(msg);
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
    public void cacheObject(Login loginDao){
        sharedPreferencesUtil.saveObject(context, loginDao);
    }

    /**
     * 获取缓存对象
     * @return
     */
    public Login getCacheObject(){
        dao = (Login) sharedPreferencesUtil.readObject(context,dao.getClass().getName());
        if(dao == null){
            return null;
        }
        return dao;
    }

    /**
     * 清除缓存
     */
    public void clearCache(){
        sharedPreferencesUtil.clearObject(context,dao.getClass().getName());
    }
}
