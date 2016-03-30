package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.ebusbar.dao.ChargeCardItemDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.param.NetParam;
import com.jellycai.service.ResponseResultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 充电卡列表
 * Created by Jelly on 2016/3/23.
 */
public class ChargeCardItemDaoImpl extends BaseImpl{
    /**
     * 数据
     */
    public List<ChargeCardItemDao> chargeCardItemDaos = new ArrayList<ChargeCardItemDao>();

    public ChargeCardItemDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "crm.accounts.get";
    }

    public ChargeCardItemDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获取数据
     * @param Token
     * @param custid
     */
    public void getChargeCardItemDao(String Token,String custid){
        if(NetParam.isEmpty(Token,custid)){
            return;
        }
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("Token",Token);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode,mode,timestamp,custid,sign_method,sign,execmode,fields,condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                Log.v("jsonCard",s.trim());
                if(NetParam.isSuccess(b,s)){
                    chargeCardItemDaos = JsonUtil.arrayFormJson(s,ChargeCardItemDao[].class);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }
}
