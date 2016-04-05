package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.dao.NearbyStationDao;
import com.ebusbar.param.NetParam;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.LogUtil;
import com.jellycai.service.ResponseResultHandler;

import java.util.List;

/**
 * Created by Jelly on 2016/3/7.
 */
public class NearbyStationDaoImpl extends BaseImpl{
    /**
     * 操作数据
     */
    public List<NearbyStationDao> daos;

    public NearbyStationDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.stations.get";
    }

    public NearbyStationDaoImpl(Context context) {
        super(context);
    }

    public void getDaos(String adCode){
        if(TextUtils.isEmpty(adCode)) return;
        timestamp = NetParam.getTime();
        conditionMap.put("Position",adCode);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode,mode,timestamp,"1",sign_method,sign,execmode,fields,condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String json) {
                LogUtil.v("jsonPostion", json.trim());
                if(NetParam.isSuccess(b,json)){
                    daos = JsonUtil.arrayFormJson(json, NearbyStationDao[].class);
                };
                handler.sendEmptyMessage(msg);
            }
            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
