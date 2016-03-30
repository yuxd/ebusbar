package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.dao.SetPasswordDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.param.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/3/5.
 */
public class SetPasswordDaoImpl extends BaseImpl{
    /**
     * 访问路径
     */
    private static final String path = NetParam.path + "ebusbar/setpassword";
    /**
     * 操作数据
     */
    public SetPasswordDao setPasswordDao;

    public SetPasswordDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
    }

    public SetPasswordDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获取网络数据
     */
    public void getNetSetPasswordDao(String phone,String password){
        if(TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) return;
        param.put("phone",phone);
        param.put("password",password);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b,String json) {
                if(NetParam.isSuccess(b,json)){
                    setPasswordDao = JsonUtil.objectFromJson(json, SetPasswordDao.class);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
