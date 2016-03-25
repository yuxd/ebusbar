package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.ebusbar.dao.FinishChargeDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * 结束充电
 * Created by Jelly on 2016/3/11.
 */
public class FinishChargeDaoImpl extends BaseImpl{
    /**
     * 操作对象
     */
    public FinishChargeDao finishChargeDao;


    public FinishChargeDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.order.change";
    }

    public FinishChargeDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获取数据
     * @param Token
     * @param OrderNo
     * @param custid
     */
    public void getFinishChargeDao(String Token,String OrderNo,String custid){
        if(NetParam.isEmpty(Token,OrderNo,custid)){
            return;
        }
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("Token",Token);
        conditionMap.put("OrderNo",OrderNo);
        conditionMap.put("ChangeType","2");
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode,mode,timestamp,custid,sign_method,sign,execmode,fields,condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                Log.v("jsonFinish",s.trim());
                if(NetParam.isSuccess(b,s)){
                    finishChargeDao = JsonUtil.arrayFormJson(s, FinishChargeDao[].class).get(0);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }
}