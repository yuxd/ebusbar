package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.ebusbar.dao.PendingOrderDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jelly on 2016/3/10.
 */
public class PendingOrderImpl extends BaseImpl{
    /**
     * 访问地址
     */
    private static final String path = NetParam.path + "ebusbar/pendingOrderList";

    /**
     * 操作数据
     */
    public List<PendingOrderDao> pendingOrderDaos = new ArrayList<PendingOrderDao>();

    public PendingOrderImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
    }

    public PendingOrderImpl(Context context) {
        super(context);
    }


    /**
     * 从网络上获取数据
     * @param token
     * @param uid
     */
    public void getNetPendingOrderList(String token,String uid){
        if(TextUtils.isEmpty(token) || TextUtils.isEmpty(uid)){
            return;
        }
        param.put("token",token);
        param.put("uid",uid);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                Log.v("json",s);
                if(b || TextUtils.isEmpty(s)){
                    return;
                }
                pendingOrderDaos = JsonUtil.arrayFormJson(s,PendingOrderDao[].class);
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
