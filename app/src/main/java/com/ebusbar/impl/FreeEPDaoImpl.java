package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.dao.FreeEPDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/3/9.
 */
public class FreeEPDaoImpl extends BaseImpl{
    /**
     * 访问地址
     */
    private static final String path = NetParam.path + "ebusbar/getFreeEP";
    /**
     * 操作对象
     */
    public FreeEPDao freeEPDao;

    public FreeEPDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
    }

    public FreeEPDaoImpl(Context context) {
        super(context);
    }

    /**
     * 从网络上获取对象
     */
    public void getNetFreeEPDao(String pid){
        if(TextUtils.isEmpty(pid)) return;
        param.put("pid",pid);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                if(b || TextUtils.isEmpty(s)) return;
                freeEPDao = JsonUtil.objectFromJson(s,FreeEPDao.class);
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }
}
