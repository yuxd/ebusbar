package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.ebusbar.bean.Logout;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.param.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/3/25.
 */
public class LogoutDaoImpl extends BaseDaoImpl {
    /**
     * 操作数据
     */
    public Logout logoutDao;

    public LogoutDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "crm.logout";
    }

    public LogoutDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获取数据
     */
    public void getLogoutDao(String Token,String custid){
        if(NetParam.isEmpty(Token,custid)){
            return;
        }
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("Token",Token);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, custid, sign_method, sign, execmode, fields, condition);
        service.doPost(path,param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                Log.v("jsonLogout",s.trim());
                if(NetParam.isSuccess(b,s)){
                    logoutDao = JsonUtil.arrayFormJson(s, Logout[].class).get(0);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }
}
