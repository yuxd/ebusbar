package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.ebusbar.dao.PayAppointDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/3/9.
 */
public class PayAppointDaoImpl extends BaseImpl{
    /**
     * 访问地址
     */
    private static final String path = NetParam.path + "ebusbar/payAppoint";

    public PayAppointDao payAppointDao;

    public PayAppointDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
    }

    public PayAppointDaoImpl(Context context) {
        super(context);
    }

    /**
     *
     */
    public void getNetPayAppointDao(String uid,String token,String price){
        if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(token) || TextUtils.isEmpty(price)){
            return;
        }
        param.put("uid",uid);
        param.put("token",token);
        param.put("price",price);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                Log.v("json",s);
                if(b || TextUtils.isEmpty(s)) return;
                payAppointDao = JsonUtil.objectFromJson(s,PayAppointDao.class);
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }
}
