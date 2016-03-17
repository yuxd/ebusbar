package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.dao.FinishChargeDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/3/11.
 */
public class FinishChargeDaoImpl extends BaseImpl{
    /**
     * 访问路径
     */
    private static final String path = NetParam.path  + "ebusbar/finishCharge";
    /**
     * 操作对象
     */
    public FinishChargeDao finishChargeDao;


    public FinishChargeDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
    }

    public FinishChargeDaoImpl(Context context) {
        super(context);
    }

    public void getNetFinishChargeDao(String EPId){
        if(TextUtils.isEmpty(EPId)){
            return;
        }
        param.put("EPId",EPId);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                if(b || TextUtils.isEmpty(s)){
                    return;
                }
                finishChargeDao = JsonUtil.objectFromJson(s,FinishChargeDao.class);
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }
}