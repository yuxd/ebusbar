package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.ebusbar.dao.AppointCostDao;
import com.ebusbar.param.NetParam;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.LogUtil;
import com.jellycai.service.ResponseResultHandler;

/**
 * 预约花费
 * Created by Jelly on 2016/3/31.
 */
public class AppointCostDaoImpl extends BaseImpl{
    /**
     * 操作数据
     */
    public AppointCostDao dao;

    public AppointCostDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.plancost.get";
    }

    public AppointCostDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获得数据
     */
    public void getDao(String Token,String custid,String Minutes){
        if(NetParam.isEmpty(Token,custid)){
            LogUtil.v(TAG,"参数为空");
            return;
        }
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("Token",Token);
        conditionMap.put("Minutes",Minutes);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, custid, sign_method, sign, execmode, fields, condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                if(NetParam.isSuccess(b,s)){
                    LogUtil.v(TAG,s.trim());
                    dao = JsonUtil.objectFromJson(s,AppointCostDao.class);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
