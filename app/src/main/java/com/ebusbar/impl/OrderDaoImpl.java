package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.dao.OrderDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jelly on 2016/3/10.
 */
public class OrderDaoImpl extends BaseImpl{
    /**
     * 访问地址
     */
    private static final String path = NetParam.path + "ebusbar/orderList";
    /**
     * 操作对象
     */
    public List<OrderDao> orderDaos = new ArrayList<OrderDao>();

    public OrderDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
    }

    public OrderDaoImpl(Context context) {
        super(context);
    }


    public void getNetOrderDaos(String token,String uid){
        if(TextUtils.isEmpty(token) || TextUtils.isEmpty(uid)){
            return;
        }
        param.put("token",token);
        param.put("uid",uid);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                if(b || TextUtils.isEmpty(s)){
                    return;
                }
                orderDaos = JsonUtil.arrayFormJson(s,OrderDao[].class);
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
