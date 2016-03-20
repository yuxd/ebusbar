package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.dao.PositionDao;
import com.ebusbar.utils.JsonUtil;
import com.jellycai.service.ResponseResultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jelly on 2016/3/7.
 */
public class PositionDaoImpl extends BaseImpl{
    /**
     * 访问地址
     */
    private static final String path ="http://192.168.0.115:8081/ebusbar/positionlist";
    /**
     * 操作数据
     */
    public List<PositionDao> positionDaoList = new ArrayList<PositionDao>();


    public PositionDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
    }

    public PositionDaoImpl(Context context) {
        super(context);
    }

    public void getNetPositionListDao(){
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String json) {
                if(b || TextUtils.isEmpty(json)) return;
                positionDaoList = JsonUtil.arrayFormJson(json,PositionDao[].class);
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
