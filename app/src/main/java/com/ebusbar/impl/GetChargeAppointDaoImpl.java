package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.ebusbar.dao.GetChargeAppointDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/3/10.
 */
public class GetChargeAppointDaoImpl extends BaseImpl{
    /**
     * 操作对象
     */
    public GetChargeAppointDao getChargeAppointDao;

    public GetChargeAppointDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.orders.get";
    }

    public GetChargeAppointDaoImpl(Context context) {
        super(context);
    }

    public void getNetGetChargeAppointDao(String Token,String custid){
        Log.v("Token",Token);
        Log.v("custid",custid);
        if(TextUtils.isEmpty(Token) || TextUtils.isEmpty(custid)){
            return;
        }
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("Token",Token);
        conditionMap.put("OrderStatus1","1");
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode,mode,timestamp,custid,sign_method,sign,execmode,fields,condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                Log.v("jsonGetAppoint",s.trim());
                if(NetParam.isSuccess(b,s)) {
                    getChargeAppointDao = JsonUtil.arrayFormJson(s,GetChargeAppointDao[].class).get(0);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
