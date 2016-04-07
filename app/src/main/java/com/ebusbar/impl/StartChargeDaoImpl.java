package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.ebusbar.bean.StartCharge;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.param.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * 开始充电
 * Created by Jelly on 2016/3/22.
 */
public class StartChargeDaoImpl extends BaseDaoImpl {
    /**
     * 操作数据
     */
    public StartCharge startChargeDao;

    public StartChargeDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.order.change";
    }

    public StartChargeDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获得数据
     */
    public void getStartChargeDao(String Token,String OrderNo,String custid){
        if(NetParam.isEmpty(Token,OrderNo,custid)){
            return;
        }
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("Token", Token);
        conditionMap.put("OrderNo",OrderNo);
        conditionMap.put("ChangeType","1");
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, custid, sign_method, sign, execmode, fields, condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                Log.v("jsonStart",s.trim());
                if(NetParam.isSuccess(b,s)){
                    startChargeDao = JsonUtil.arrayFormJson(s,StartCharge[].class).get(0);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }
}
