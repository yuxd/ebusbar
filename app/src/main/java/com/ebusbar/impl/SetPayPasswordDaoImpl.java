package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.ebusbar.dao.SetPayPasswordDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * 设置支付密码
 * Created by Jelly on 2016/3/23.
 */
public class SetPayPasswordDaoImpl extends BaseImpl{
    /**
     * 操作数据
     */
    public SetPayPasswordDao setPayPasswordDao;

    public SetPayPasswordDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "crm.paypassword.set";
    }

    public SetPayPasswordDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获得数据
     */
    public void getSetPasswordDao(String Token,String PayPassword,String custid){
        if(NetParam.isEmpty(Token,PayPassword,custid)){
            return;
        }
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("Token",Token);
        conditionMap.put("PayPassword",PayPassword);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, custid, sign_method, sign, execmode, fields, condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                if(NetParam.isSuccess(b,s)){
                    setPayPasswordDao = JsonUtil.arrayFormJson(s,SetPayPasswordDao[].class).get(0);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
