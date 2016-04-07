package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.ebusbar.bean.AllStation;
import com.ebusbar.param.NetParam;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.LogUtil;
import com.jellycai.service.ResponseResultHandler;

import java.util.List;

/**
 * Created by Jelly on 2016/3/30.
 */
public class AllStationDaoImpl extends BaseDaoImpl {

    public List<AllStation> daos;

    public AllStationDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.stations.getall";
    }

    public AllStationDaoImpl(Context context) {
        super(context);
    }

    public void getDaos(){
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("Position","000000");
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, "0", sign_method, sign, execmode, fields, condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                if (NetParam.isSuccess(b, s)) {
                    LogUtil.v("jsonAll", s.trim());
                    daos = JsonUtil.arrayFormJson(s, AllStation[].class);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }



}
