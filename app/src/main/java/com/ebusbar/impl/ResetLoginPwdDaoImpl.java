package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.ebusbar.bean.ResetLoginPwd;
import com.ebusbar.param.NetParam;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.LogUtil;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/4/7.
 */
public class ResetLoginPwdDaoImpl extends BaseDaoImpl{

    public ResetLoginPwd bean;

    public ResetLoginPwdDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "crm.pwd.reset";
    }

    public ResetLoginPwdDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获得数据
     */
    public void getData(String Mobile,String Code,String Password){
        if(NetParam.isEmpty(Mobile,Code,Password)){
            LogUtil.v(TAG,"参数为空");
            return;
        }
        timestamp = NetParam.getTime();
        conditionMap.clear();
        conditionMap.put("Mobile", Mobile);
        conditionMap.put("Code", Code);
        conditionMap.put("Password", Password);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, "1", sign_method, sign, execmode, fields, condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                LogUtil.v(TAG, s.trim());
                if(NetParam.isSuccess(b,s)){
                    bean = JsonUtil.objectFromJson(s, ResetLoginPwd.class);
                    handler.sendEmptyMessage(msg);
                }
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
