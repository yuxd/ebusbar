package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.dao.AppointDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * 预约订单
 * Created by Jelly on 2016/3/9.
 */
public class AppointDaoImpl extends BaseImpl{
    /**
     * 操作对象
     */
    public AppointDao appointDao;

    public AppointDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.order.set";
    }

    public AppointDaoImpl(Context context) {
        super(context);
    }


    public void getAppointDao(String FacilityID,String Token,String Time,String custid){
        if(TextUtils.isEmpty(FacilityID) || TextUtils.isEmpty(Token) || TextUtils.isEmpty(Time)||TextUtils.isEmpty(custid)){
            return;
        }
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("Token",Token);
        conditionMap.put("FacilityID",FacilityID);
        conditionMap.put("OrderType","1");
        conditionMap.put("Time",Time);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode,mode,timestamp,custid,sign_method,sign,execmode,fields,condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                if(b || TextUtils.isEmpty(s)) return;
                appointDao = JsonUtil.arrayFormJson(s,AppointDao[].class).get(0);
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
