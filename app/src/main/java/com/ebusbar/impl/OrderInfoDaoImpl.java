package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.ebusbar.dao.OrderInfoDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * 获取电桩详情
 * Created by Jelly on 2016/3/23.
 */
public class OrderInfoDaoImpl extends BaseImpl{
    /**
     * 操作数据
     */
    public OrderInfoDao orderInfoDao;

    public OrderInfoDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.order.get";
    }

    public OrderInfoDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获得数据
     */
    public void getOrderInfoDaoImpl(String Token,String OrderNo,String custid){
        if(NetParam.isEmpty(Token,OrderNo,custid)){
            return;
        }
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("Token",Token);
        conditionMap.put("OrderNo",OrderNo);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, custid, sign_method, sign, execmode, fields, condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                Log.v("json12312321",s.trim());
                if(NetParam.isSuccess(b,s)){
                    orderInfoDao = JsonUtil.arrayFormJson(s,OrderInfoDao[].class).get(0);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }
}
