package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.ebusbar.bean.CheckCode;
import com.ebusbar.param.NetParam;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.LogUtil;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/4/7.
 */
public class CheckCodeDaoImpl extends BaseDaoImpl{

    public CheckCode bean;
    /**
     * 登录
     */
    public static final String LOGIN = "3";
    /**
     * 支付密码
     */
    public static final String PAY = "2";

    public CheckCodeDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "crm.chkcode";
    }

    public CheckCodeDaoImpl(Context context) {
        super(context);
    }

    public void getData(String Mobile,String Code,String Type){
        if(NetParam.isEmpty(Mobile,Code)){
            LogUtil.v(TAG,"参数为空");
            return;
        }
        timestamp = NetParam.getTime();
        conditionMap.clear();
        conditionMap.put("Mobile", Mobile);
        conditionMap.put("Code", Code);
        conditionMap.put("Type",Type);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode,mode,timestamp,"1",sign_method,sign,execmode,fields,condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                LogUtil.v(TAG,s.trim());
                if(NetParam.isSuccess(b,s)){
                    bean = JsonUtil.objectFromJson(s,CheckCode.class);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }
}
