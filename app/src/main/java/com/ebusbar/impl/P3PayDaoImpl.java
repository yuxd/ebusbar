package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.ebusbar.dao.P3PayDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * 第3方支付
 * Created by Jelly on 2016/3/23.
 */
public class P3PayDaoImpl extends BaseImpl{
    /**
     * 操作数据
     */
    public P3PayDao p3PayDao;


    public P3PayDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.order.pay";
    }

    public P3PayDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获得数据
     */
    public void getP3PayDao(String Token,String OrderNo,String PayOrderNo,String Type,String custid){
        if(NetParam.isEmpty(Token,OrderNo,PayOrderNo,Type,custid)){
            return;
        }
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("Token",Token);
        conditionMap.put("OrderNo",OrderNo);
        conditionMap.put("PayOrderNo",PayOrderNo);
        conditionMap.put("Type",Type);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, custid, sign_method, sign, execmode, fields, condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                if(NetParam.isSuccess(b,s)){
                    p3PayDao = JsonUtil.arrayFormJson(s,P3PayDao[].class).get(0);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }
}
