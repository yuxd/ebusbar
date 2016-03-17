package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.dao.CheckCodeDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/3/5.
 */
public class CheckCodeDaoImpl extends BaseImpl{
    /**
     * 访问地址
     */
    private static final String path = NetParam.path + "ebusbar/checkcode";
    /**
     * 操作数据
     */
    public CheckCodeDao checkCodeDao;

    public CheckCodeDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
    }

    public CheckCodeDaoImpl(Context context) {
        super(context);
    }

    /**
     * 从网络上获取CheckCodeDao
     */
    public void getNetCheckCodeDao(String oper,String phone,String code){
        if(TextUtils.isEmpty(oper) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(code)) return;
        param.put("oper",oper);
        param.put("phone",phone);
        param.put("code",code);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String json) {
                if(b || TextUtils.isEmpty(json)) return;
                checkCodeDao = JsonUtil.objectFromJson(json, CheckCodeDao.class);
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }
}
