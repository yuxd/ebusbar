package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.dao.QRChargeDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/3/9.
 */
public class QRChargeDaoImpl extends BaseImpl{
    /**
     * 访问地址
     */
    private static final String path = NetParam.path + "ebusbar/qrcharge";
    /**
     * 操作对象
     */
    public QRChargeDao qrChargeDao;

    public QRChargeDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
    }

    public QRChargeDaoImpl(Context context) {
        super(context);
    }

    /**
     * 从网络上获取操作对象
     */
    public void getNetQRChargeDao(String EPid){
        if(TextUtils.isEmpty(EPid)) return;
        param.put("EPid",EPid);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                if(b || TextUtils.isEmpty(s)) return;
                qrChargeDao = JsonUtil.objectFromJson(s,QRChargeDao.class);
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
