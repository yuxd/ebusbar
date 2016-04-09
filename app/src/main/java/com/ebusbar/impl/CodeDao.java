package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.bean.Code;
import com.ebusbar.param.NetParam;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.LogUtil;
import com.jellycai.service.ResponseResultHandler;

/**
 * 获取验证码
 * Created by Jelly on 2016/3/5.
 */
public class CodeDao extends BaseDaoImpl {

    /**
     * 访问地址,暂时的本地服务器地址
     */
    private static final String path= "http://www.genlex.com.cn/e848/server/sendsms";

    /**
     * 操作对象
     */
    public Code codeDao;

    public CodeDao(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "crm.validation";
    }

    public CodeDao(Context context) {
        super(context);
    }

    /**
     * 从网络上获得CodeDao
     */
    public void getNetCodeDao(String codeType,String mobile){
        if(TextUtils.isEmpty(codeType) || TextUtils.isEmpty(mobile)) return;
        timestamp = NetParam.getTime();
        conditionMap.clear();
        conditionMap.put("Mobile",mobile);
        conditionMap.put("CodeType",codeType);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode,mode,timestamp,"1",sign_method,sign,execmode,fields,condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String json) {
                LogUtil.v(TAG,json.trim());
                if(NetParam.isSuccess(b,json)){
                    codeDao = JsonUtil.arrayFormJson(json, Code[].class).get(0);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });

    }
}
