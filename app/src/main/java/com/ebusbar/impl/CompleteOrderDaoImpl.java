package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.ebusbar.bean.CompleteOrder;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.param.NetParam;
import com.jellycai.service.ResponseResultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jelly on 2016/3/22.
 */
public class CompleteOrderDaoImpl extends BaseDaoImpl {
    /**
     * 操作数据
     */
    public List<CompleteOrder> completeOrderDaos = new ArrayList<CompleteOrder>();

    public CompleteOrderDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.orders.get";
    }

    public CompleteOrderDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获取数据
     */
    public void getCompleteOrderDaos(String Token,String custid){
        if(NetParam.isEmpty(Token,custid)){
            return;
        }
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("Token",Token);
        conditionMap.put("OrderStatus1","8");
        conditionMap.put("OrderStatus2","16");
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, custid, sign_method, sign, execmode, fields, condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                if(NetParam.isSuccess(b,s)){
                    completeOrderDaos = JsonUtil.arrayFormJson(s,CompleteOrder[].class);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
