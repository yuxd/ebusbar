package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.ebusbar.dao.BillDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.NetParam;
import com.jellycai.service.ResponseResultHandler;

import java.util.List;

/**
 * Created by Jelly on 2016/3/24.
 */
public class BillDaoImpl extends BaseImpl{
    /**
     * 操作数据
     */
    public List<BillDao> billDaos;

    public BillDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "crm.balancelog.get";
    }

    public BillDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获得消费账单
     */
    public void getBillDaos(String Token,String custid){
        if(NetParam.isEmpty(Token,custid)){
            return;
        }
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("Token",Token);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, custid, sign_method, sign, execmode, fields, condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                Log.v("jsonBills",s.trim());
                if(NetParam.isSuccess(b,s)){
                    billDaos = JsonUtil.arrayFormJson(s,BillDao[].class);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }


}
