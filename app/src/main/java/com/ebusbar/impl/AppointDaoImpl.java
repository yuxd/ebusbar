package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.bean.Appoint;
import com.ebusbar.param.NetParam;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.LogUtil;
import com.jellycai.service.ResponseResultHandler;

/**
 * 预约订单
 * Created by Jelly on 2016/3/9.
 */
public class AppointDaoImpl extends BaseDaoImpl {
    /**
     * 操作对象
     */
    public Appoint dao;

    public AppointDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.planorder.change";
    }

    public AppointDaoImpl(Context context) {
        super(context);
    }


    public void getAppointDao(String Token,String OrderNo,String custid){
        if(TextUtils.isEmpty(Token) || TextUtils.isEmpty(OrderNo) ||TextUtils.isEmpty(custid)){
            return;
        }
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("Token", Token);
        conditionMap.put("OrderNo",OrderNo);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode,mode,timestamp,custid,sign_method,sign,execmode,fields,condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                LogUtil.v(TAG,s.trim());
                if(NetParam.isSuccess(b,s)){
                    dao = JsonUtil.objectFromJson(s,Appoint.class);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
