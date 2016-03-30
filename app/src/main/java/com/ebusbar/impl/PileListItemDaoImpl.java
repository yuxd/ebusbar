package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.ebusbar.dao.PileListItemDao;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.param.NetParam;
import com.jellycai.service.ResponseResultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jelly on 2016/3/21.
 */
public class PileListItemDaoImpl extends BaseImpl{

    public List<PileListItemDao> piles = new ArrayList<PileListItemDao>();

    public PileListItemDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.facilities.get";
    }

    public PileListItemDaoImpl(Context context) {
        super(context);
    }

    /**
     * 从网络上获取数据集合
     * @param OrgId
     */
    public void getPiles(String OrgId){
        if(TextUtils.isEmpty(OrgId)){
            return;
        };
        conditionMap.clear();
        timestamp = NetParam.getTime();
        conditionMap.put("OrgId",OrgId);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode,mode,timestamp,"1",sign_method,sign,execmode,fields,condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                if(NetParam.isSuccess(b,s)){
                    piles = JsonUtil.arrayFormJson(s, PileListItemDao[].class);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }

}
