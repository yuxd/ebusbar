package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.ebusbar.bean.RegUser;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.param.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/3/4.
 */
public class RegUserDaoImpl extends BaseDaoImpl {
    /**
     * 访问地址
     */
    private static final String path = NetParam.path;
    /**
     * 操作数据
     */
    public RegUser regUserDao;

    public RegUserDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "crm.register";
    }

    public RegUserDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获取网络数据
     */
    public void getNetRegUserDao(String phone,String password,String code){
        conditionMap.clear();
        if(TextUtils.isEmpty(phone) && TextUtils.isEmpty(password) && TextUtils.isEmpty(code)){
            return;
        }
        timestamp = NetParam.getTime();
        conditionMap.put("Mobile",phone);
        conditionMap.put("Password",password);
        conditionMap.put("Code",code);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode,mode,timestamp,"1",sign_method,sign,execmode,fields,condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String json) {
                Log.v("json",json.trim());
                if(b || TextUtils.isEmpty(json)) return;
                if(NetParam.isSuccess(b,json)){
                    regUserDao = JsonUtil.arrayFormJson(json, RegUser[].class).get(0);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }
}

