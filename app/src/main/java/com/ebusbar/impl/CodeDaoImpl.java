package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.ebusbar.dao.CodeDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.param.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * 获取验证码
 * Created by Jelly on 2016/3/5.
 */
public class CodeDaoImpl extends BaseImpl{

    /**
     * 访问地址,暂时的本地服务器地址
     */
    private static final String path= "http://www.genlex.com.cn/e848/server/sendsms";

    /**
     * 操作对象
     */
    public CodeDao codeDao;

    public CodeDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "crm.validation";
    }

    public CodeDaoImpl(Context context) {
        super(context);
    }

    /**
     * 从网络上获得CodeDao
     */
    public void getNetCodeDao(String codeType,String mobile){
        if(TextUtils.isEmpty(codeType) || TextUtils.isEmpty(mobile)) return;
        timestamp = NetParam.getTime();
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode,mode,timestamp,"1",sign_method,sign,execmode,fields,condition);
        param.put("codeType",codeType);
        param.put("mobile",mobile);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String json) {
                Log.v("json",json);
                if(NetParam.isSuccess(b,json)){
                    codeDao = JsonUtil.arrayFormJson(json, CodeDao[].class).get(0);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });

    }
}
