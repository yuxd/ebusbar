package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.ebusbar.dao.DeleteChargeCardDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.param.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/3/24.
 */
public class DeleteChargeCardDaoImpl extends BaseImpl{
    /**
     * 操作数据
     */
    public DeleteChargeCardDao deleteChargeCardDao;

    public DeleteChargeCardDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "crm.accounts.delete";
    }

    public DeleteChargeCardDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获得数据
     */
    public void getDeleteChargeCardDao(String Token,String AccountId,String AccountPwd,String custid){
        if(NetParam.isEmpty(Token,AccountId,AccountPwd,custid)){
            return;
        }
        conditionMap.clear();
        Log.v("Token",Token);
        Log.v("custid",custid);
        timestamp = NetParam.getTime();
        conditionMap.put("Token",Token);
        conditionMap.put("AccountId",AccountId);
        conditionMap.put("AccountPwd",AccountPwd);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, custid, sign_method, sign, execmode, fields, condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                Log.v("jsonDelete",s.trim());
                if(NetParam.isSuccess(b,s)){
                    deleteChargeCardDao = JsonUtil.arrayFormJson(s,DeleteChargeCardDao[].class).get(0);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }
}
