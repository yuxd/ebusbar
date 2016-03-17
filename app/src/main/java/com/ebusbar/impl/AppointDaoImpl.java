package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.dao.AppointDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/3/9.
 */
public class AppointDaoImpl extends BaseImpl{
    /**
     * 访问地址
     */
    private static final String path = NetParam.path+"ebusbar/appoint";
    /**
     * 操作对象
     */
    public AppointDao appointDao;

    public AppointDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
    }

    public AppointDaoImpl(Context context) {
        super(context);
    }


    public void getNetAppointDao(String EPId,String uid,String payId,String token){
        if(TextUtils.isEmpty(EPId) || TextUtils.isEmpty(uid) || TextUtils.isEmpty(payId) || TextUtils.isEmpty(token)){
            return;
        }
        param.put("EPId",EPId);
        param.put("uid",uid);
        param.put("payId",payId);
        param.put("token",token);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                if(b || TextUtils.isEmpty(s)) return;
                appointDao = JsonUtil.objectFromJson(s,AppointDao.class);
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
