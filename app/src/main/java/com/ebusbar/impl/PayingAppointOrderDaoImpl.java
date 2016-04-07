package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.ebusbar.bean.PayingAppointOrder;
import com.ebusbar.param.NetParam;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.LogUtil;
import com.jellycai.service.ResponseResultHandler;

/**
 * 生成待支付的预约订单
 * Created by Jelly on 2016/3/31.
 */
public class PayingAppointOrderDaoImpl extends BaseDaoImpl {
    /**
     * 操作数据
     */
    public PayingAppointOrder dao;

    public PayingAppointOrderDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.planorder.set";
    }

    public PayingAppointOrderDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获得数据
     */
    public void getDao(String Token,String custid,String FacilityID,String Minutes,String Cost){
        if(NetParam.isEmpty(Token,custid,FacilityID,Minutes,Cost)){
            LogUtil.v(TAG,"参数为空");
            return;
        }
        timestamp = NetParam.getTime();
        conditionMap.clear();
        conditionMap.put("Token", Token);
        conditionMap.put("FacilityID", FacilityID);
        conditionMap.put("Minutes",Minutes);
        conditionMap.put("Cost",Cost);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, custid, sign_method, sign, execmode, fields, condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                LogUtil.v(TAG,s.trim());
                if(NetParam.isSuccess(b,s)){
                    dao = JsonUtil.objectFromJson(s,PayingAppointOrder.class);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
