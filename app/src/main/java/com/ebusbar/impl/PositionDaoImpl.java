package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.ebusbar.dao.PositionListItemDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.param.NetParam;
import com.jellycai.service.ResponseResultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jelly on 2016/3/7.
 */
public class PositionDaoImpl extends BaseImpl{
    /**
     * 操作数据
     */
    public List<PositionListItemDao> positionDaoList = new ArrayList<PositionListItemDao>();

    public PositionDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.stations.get";
    }

    public PositionDaoImpl(Context context) {
        super(context);
    }

    public void getNetPositionListDao(String position){
        if(TextUtils.isEmpty(position)) return;
        timestamp = NetParam.getTime();
        conditionMap.put("Position",position);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode,mode,timestamp,"1",sign_method,sign,execmode,fields,condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String json) {
                Log.v("jsonPostion",json.trim());
                if(NetParam.isSuccess(b,json)){
                    positionDaoList = JsonUtil.arrayFormJson(json, PositionListItemDao[].class);
                };
                handler.sendEmptyMessage(msg);
            }
            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
