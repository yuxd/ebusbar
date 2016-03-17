package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.dao.CodeDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/3/5.
 */
public class CodeDaoImpl extends BaseImpl{

    /**
     * 访问地址
     */
    private static final String path= NetParam.path+"ebusbar/code";
    /**
     * 操作对象
     */
    public CodeDao codeDao;

    public CodeDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
    }

    public CodeDaoImpl(Context context) {
        super(context);
    }

    /**
     * 从网络上获得CodeDao
     */
    public void getNetCodeDao(String oper,String phone){
        if(TextUtils.isEmpty(oper) || TextUtils.isEmpty(phone)) return;
        param.put("oper",oper);
        param.put("phone",phone);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String json) {
                if(b || TextUtils.isEmpty(json)) return;
                codeDao = JsonUtil.objectFromJson(json,CodeDao.class);
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });

    }
}
