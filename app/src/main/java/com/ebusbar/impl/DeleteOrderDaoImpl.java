package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.ebusbar.bean.DeleteOrder;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.param.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/3/23.
 */
public class DeleteOrderDaoImpl extends BaseDaoImpl {
    /**
     * 操作数据
     */
    public DeleteOrder deleteOrderDao;

    public DeleteOrderDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.order.delete";
    }

    public DeleteOrderDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获得数据
     */
    public void getDeleteOrderDao(String Token,String OrderNo,String custid){
        if(NetParam.isEmpty(Token,OrderNo)){
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
                Log.v("json",s.trim());
                if(NetParam.isSuccess(b,s)){
                    deleteOrderDao = JsonUtil.arrayFormJson(s,DeleteOrder[].class).get(0);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
