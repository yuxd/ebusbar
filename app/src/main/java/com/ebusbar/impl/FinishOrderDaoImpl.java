package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.dao.FinishOrderDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * 结束订单
 * Created by Jelly on 2016/3/22.
 */
public class FinishOrderDaoImpl extends BaseImpl{
    /**
     * 操作对象
     */
    public FinishOrderDao finishOrderDao;

    public FinishOrderDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.order.cancel";
    }

    public FinishOrderDaoImpl(Context context) {
        super(context);
    }

    public void getFinishOrderDao(String Token,String OrderNo,String custid){
        if(NetParam.isEmpty(Token,OrderNo,custid)){
            return;
        }
        timestamp = NetParam.getTime();
        conditionMap.put("Token",Token);
        conditionMap.put("OrderNo",OrderNo);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, custid, sign_method, sign, execmode, fields, condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                if(b || TextUtils.isEmpty(s) || TextUtils.equals(s,"[]")){
                    return;
                }
                finishOrderDao = JsonUtil.arrayFormJson(s,FinishOrderDao[].class).get(0);
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
