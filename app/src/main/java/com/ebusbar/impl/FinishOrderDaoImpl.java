package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.ebusbar.bean.FinishOrder;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.param.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * 结束订单
 * Created by Jelly on 2016/3/22.
 */
public class FinishOrderDaoImpl extends BaseDaoImpl {
    /**
     * 操作对象
     */
    public FinishOrder finishOrderDao;

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
                if(NetParam.isSuccess(b,s)){
                    finishOrderDao = JsonUtil.arrayFormJson(s, FinishOrder[].class).get(0);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
