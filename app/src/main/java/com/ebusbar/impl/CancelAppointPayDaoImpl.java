package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.ebusbar.dao.CancelAppointPayDao;
import com.ebusbar.param.NetParam;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.LogUtil;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/4/1.
 */
public class CancelAppointPayDaoImpl extends BaseImpl{

    public CancelAppointPayDao dao;

    public CancelAppointPayDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.planorder.cancel";
    }

    public CancelAppointPayDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获得数据
     */
    public void getDao(String Token,String custid,String OrderNo){
        if(NetParam.isEmpty(Token,custid,OrderNo)){
            LogUtil.v(TAG,"参数为空");
            return;
        }
        timestamp = NetParam.getTime();
        conditionMap.clear();
        conditionMap.put("Token", Token);
        conditionMap.put("OrderNo", OrderNo);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, custid, sign_method, sign, execmode, fields, condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                LogUtil.v(TAG,s.trim());
                if(NetParam.isSuccess(b,s)){
                    dao = JsonUtil.objectFromJson(s,CancelAppointPayDao.class);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
