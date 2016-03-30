package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.ebusbar.dao.AddChargeCardDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.param.NetParam;
import com.jellycai.service.ResponseResultHandler;

/**
 * Created by Jelly on 2016/3/23.
 */
public class AddChargeCardDaoImpl extends BaseImpl{
    /**
     * 操作数据
     */
    public AddChargeCardDao addChargeCardDao;

    public AddChargeCardDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "crm.accounts.insert";
    }

    public AddChargeCardDaoImpl(Context context) {
        super(context);
    }

    /**
     * 获取数据
     */
    public void addAddChargeCardDao(String Token,String AccountNo,String AccountPwd,String custid){
        if(NetParam.isEmpty(Token,AccountNo,AccountPwd,custid)){
            return;
        }
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("Token",Token);
        conditionMap.put("AccountNo", AccountNo);
        conditionMap.put("AccountPwd",AccountPwd);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, custid, sign_method, sign, execmode, fields, condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                Log.v("jsonAdd",s.trim());
                if(NetParam.isSuccess(b,s)){
                    addChargeCardDao = JsonUtil.arrayFormJson(s,AddChargeCardDao[].class).get(0);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }
}
