package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.ebusbar.bean.ReCharge;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.param.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * 余额充值
 * Created by Jelly on 2016/3/23.
 */
public class ReChargeDaoImpl extends BaseDaoImpl {
    /**
     * 操作数据
     */
    public ReCharge reChargeDao;

    public ReChargeDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "crm.recharge";
    }

    public ReChargeDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获得充电数据
     */
    public void getReChargeDao(String Token,String Amount,String PayOrderNo,String Type,String custid){
        if(NetParam.isEmpty(Token,Amount,PayOrderNo,Type)){
            return;
        }
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("Token",Token);
        conditionMap.put("Amount",Amount);
        conditionMap.put("PayOrderNo",PayOrderNo);
        conditionMap.put("Type",Type);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, custid, sign_method, sign, execmode, fields, condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                if(NetParam.isSuccess(b,s)){
                    reChargeDao = JsonUtil.arrayFormJson(s, ReCharge[].class).get(0);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
