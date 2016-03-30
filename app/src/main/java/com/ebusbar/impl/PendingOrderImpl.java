package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.ebusbar.dao.PendingOrderDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.param.NetParam;
import com.jellycai.service.ResponseResultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jelly on 2016/3/10.
 */
public class PendingOrderImpl extends BaseImpl{
    /**
     * 操作数据
     */
    public List<PendingOrderDao> pendingOrderDaos = new ArrayList<PendingOrderDao>();

    public PendingOrderImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.orders.get";
    }

    public PendingOrderImpl(Context context) {
        super(context);
    }


    /**
     * 从网络上获取数据
     * @param Token
     * @param custid
     */
    public void getNetPendingOrderList(String Token,String custid){
        if(NetParam.isEmpty(Token,custid)){
            return;
        }
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("Token",Token);
        conditionMap.put("OrderStatus1","2");
        conditionMap.put("OrderStatus2","4");
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode,mode,timestamp,custid,sign_method,sign,execmode,fields,condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                Log.v("json",s.trim());
                if (NetParam.isSuccess(b,s)) {
                    pendingOrderDaos = JsonUtil.arrayFormJson(s, PendingOrderDao[].class);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
