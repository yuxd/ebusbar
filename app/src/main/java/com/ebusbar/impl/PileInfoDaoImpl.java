package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.ebusbar.bean.PileInfo;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.param.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * 通过二维码回去电桩详情
 * Created by Jelly on 2016/3/22.
 */
public class PileInfoDaoImpl extends BaseDaoImpl {
    /**
     * 操作数据
     */
    public PileInfo pileInfoDao;

    public PileInfoDaoImpl(Context context) {
        super(context);
    }

    public PileInfoDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.facility.get";
    }

    /**
     * 获得数据
     */
    public void getPileInfoDao(String QRCode){
        if(NetParam.isEmpty(QRCode)){
            return;
        }
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("QRCode",QRCode);
        conditionMap.put("Type","2");
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, "1", sign_method, sign, execmode, fields, condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                if(NetParam.isSuccess(b,s)){ //如果数据校验成功了，才会去解析数据
                    pileInfoDao = JsonUtil.arrayFormJson(s, PileInfo[].class).get(0);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
