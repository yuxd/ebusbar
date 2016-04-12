package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.ebusbar.bean.StationInfo;
import com.ebusbar.param.NetParam;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.LogUtil;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/4/11.
 */
public class StationInfoDaoImpl extends BaseDaoImpl{
    /**
     * 操作的数据
     */
    public StationInfo stationInfo;

    public StationInfoDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.station.get";
    }

    public StationInfoDaoImpl(Context context) {
        super(context);
    }

    public void getStationInfo(String OrgId){
        if(NetParam.isEmpty(OrgId)){
            return;
        }
        timestamp = NetParam.getTime();
        conditionMap.clear();
        conditionMap.put("OrgId",OrgId);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode,mode,timestamp,"1",sign_method,sign,execmode,fields,condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                LogUtil.v(TAG,s.trim());
                if(NetParam.isSuccess(b,s)){
                    stationInfo = JsonUtil.objectFromJson(s,StationInfo.class);
                    handler.sendEmptyMessage(msg);
                }
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
