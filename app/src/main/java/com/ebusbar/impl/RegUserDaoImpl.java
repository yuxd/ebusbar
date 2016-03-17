package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.dao.RegUserDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/3/4.
 */
public class RegUserDaoImpl extends BaseImpl{
    /**
     * 访问地址
     */
    private static final String path = NetParam.path + "ebusbar/regUser";
    /**
     * 操作数据
     */
    public RegUserDao regUserDao;

    public RegUserDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
    }

    public RegUserDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获取网络数据
     */
    public void getNetRegUserDao(String phone,String password,String code){
        if(TextUtils.isEmpty(phone) && TextUtils.isEmpty(password) && TextUtils.isEmpty(code)){
            return;
        }
        param.put("phone",phone);
        param.put("password",password);
        param.put("code",code);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String json) {
                if(b || TextUtils.isEmpty(json)) return;
                regUserDao = JsonUtil.objectFromJson(json, RegUserDao.class);
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }
}
