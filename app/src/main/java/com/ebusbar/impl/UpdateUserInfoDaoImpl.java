package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.dao.UpdateUserInfoDao;
import com.ebusbar.param.NetParam;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.LogUtil;
import com.jellycai.service.ResponseResultHandler;

import java.util.Map;

/**
 * Created by Jelly on 2016/4/5.
 */
public class UpdateUserInfoDaoImpl extends BaseImpl{

    public UpdateUserInfoDao dao;

    public UpdateUserInfoDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "crm.customer.update";
    }

    public UpdateUserInfoDaoImpl(Context context) {
        super(context);
    }

    public void getDao(String Token,String custid,String CustName,String Sex,String Addr,String Usericon){
        if(NetParam.isEmpty(Token,custid)){
            LogUtil.v(TAG,"参数为空");
        }
        timestamp = NetParam.getTime();
        conditionMap.clear();
        conditionMap.put("Token",Token);
        if(!TextUtils.isEmpty(CustName)){
            LogUtil.v(TAG,"CustName");
            conditionMap.put("CustName",CustName);
        }else if(!TextUtils.isEmpty(Sex)){
            LogUtil.v(TAG,"SEX");
            conditionMap.put("Sex",Sex);
        }else if(!TextUtils.isEmpty(Addr)){
            LogUtil.v(TAG,"Addr");
            conditionMap.put("Addr",Addr);
        }else if(!TextUtils.isEmpty(Usericon)){
            LogUtil.v(TAG,"Usericon");
            conditionMap.put("Usericon",Usericon);
        }
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode,mode,timestamp,custid,sign_method,sign,execmode,fields,condition);
        String paramStr = spliceParam(param);
        service.postGBKResult(path, paramStr, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                LogUtil.v(TAG,s.trim());
                if(NetParam.isSuccess(b,s)){
                    dao = JsonUtil.objectFromJson(s,UpdateUserInfoDao.class);
                    handler.sendEmptyMessage(msg);
                }
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

    /**
     * 拼接参数
     */
    public String spliceParam(Map<String,String> params){
        String param = "";
        for(Map.Entry<String, String> entry: params.entrySet()){
            param += entry.getKey()+"="+entry.getValue()+"&";
        }
        //截取最后一个字符"&"
        param = param.substring(0, param.length()-1);
        return param;
    }
}
