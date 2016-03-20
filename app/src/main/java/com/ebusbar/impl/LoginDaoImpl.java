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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 登录Impl
 * Created by Jelly on 2016/3/3.
 */
public class LoginDaoImpl extends BaseImpl{
    /**
     * 正常登录的访问接口
     */
    private static final String LoginPath = NetParam.path;
    /**
     * 验证码登录
     */
    private static final String CodeLogin = NetParam.path;
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
        if(TextUtils.isEmpty(code)){ //正常登录
            param.put("Mobile",mobile);
            param.put("password", password);
            service.doPost(LoginPath, param, new ResponseResultHandler() {
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
            timestamp = "2016/3/18 15:35:50"; //获得当前时间
            conditionMap.put("Mobile",mobile);
            conditionMap.put("Type","1");
            conditionMap.put("Code",code);
            condition = NetParam.spliceCondition(conditionMap).trim();
            condition = "Mobile=\"18617050557\" Type=\"1\" Code=\"63000\"";

            param = NetParam.getParamMap(trancode, mode, timestamp, custid, sign_method, sign, execmode, fields, condition);
            Log.v("json","json");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            readContentFromPost(condition);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            service.doGet("http://www.genlex.com.cn/e848/interface/ev_app_cnt_api.do?sign=sign&sign_method=MD5&timestamp=2016/3/18%2015:35:50&trancode=APPAPI000&condition=Mobile=%2218617050557%22%20Type=%221%22%20Code=%2263000%22&execmode=crm.login&custid=1&fields=fields&mode=4", null, new ResponseResultHandler() {
                @Override
                public void response(boolean b, String json) {
                    byte[] bs = json.getBytes();
                    for(int i=0;i<bs.length;i++){
                        Log.v("sss",bs[i]+"");
                    }
                    try {
                        Log.v("json",new String(bs,"GB2312"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Log.v("json", "adada" + json.trim());
                    if (b || TextUtils.isEmpty(json)) return;
                    try {
//                        loginDao = JsonUtil.objectFromJson(json,LoginDao.class);
//                        handler.sendEmptyMessage(msg);
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
     * 获取保存验证码的返回结果
     * @param condition
     * @return
     * @throws IOException
     */
    public String readContentFromPost(String condition) throws IOException {
        URL postUrl = new URL("http://www.genlex.com.cn/e848/interface/ev_app_cnt_api.do");
        HttpURLConnection connection = (HttpURLConnection) postUrl
                .openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection
                .getOutputStream());
        String content = "trancode=" + trancode + "&";
        content += "mode=" + mode + "&";
        content += "custid=" + "1" + "&";
        content += "timestamp=" + timestamp + "&";
        content += "execmode=" + execmode + "&";
        content += "sign_method=" + sign_method + "&";
        content += "sign=" + sign + "&";
        content += "fields=" + fields + "&";
        content += "condition=" + condition;
        System.out.println(content);
        out.writeBytes(content);
        out.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String result = "";
        String line;
        while ((line = reader.readLine()) != null) {
            result += line;
        }
        reader.close();
        connection.disconnect();
        Log.v("json",result);
        return result;
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
