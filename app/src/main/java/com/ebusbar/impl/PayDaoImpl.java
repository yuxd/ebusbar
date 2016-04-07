package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.ebusbar.bean.Pay;
import com.ebusbar.param.NetParam;
import com.ebusbar.utils.JsonUtil;
import com.ebusbar.utils.LogUtil;
import com.jellycai.service.ResponseResultHandler;

/**
 * 订单支付
 * Created by Jelly on 2016/3/31.
 */
public class PayDaoImpl extends BaseDaoImpl {
    /**
     * 操作数据
     */
    public Pay dao;
    /**
     * 余额支付
     */
    public static final String BALANCEPAY = "1";
    /**
     * 支付宝支付
     */
    public static final String ALIPAY = "2";
    /**
     * 微信支付
     */
    public static final String WECHATPAY = "3";
    /**
     * 预约支付
     */
    public static final String APPOINTPAY = "11";
    /**
     * 充电支付
     */
    public static final String CHARGEPAY = "12";

    public PayDaoImpl(Context context) {
        super(context);
    }

    public PayDaoImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
        execmode = "evc.order.pay";
    }

    /**
     * 支付订单
     * @param Token
     * @param OrderNo
     * @param PayOrderNo 当为余额支付时可以为空
     * @param PayPassword 当为第三方支付时可以为空
     * @param Type 1:微信支付 2:支付宝支付
     * @param OrderType Charge_RealOrder:充电支付 Charge_PlanOrder:预约支付
     * @param custid
     */
    public void getDao(String Token,String OrderNo,String PayOrderNo,String PayPassword,String Type,String OrderType,String custid){
        if(NetParam.isEmpty(Token,OrderNo,PayOrderNo,Type,OrderType,custid)){
            LogUtil.v(TAG,"参数为空");
            return;
        }
        timestamp = NetParam.getTime();
        conditionMap.clear();
        conditionMap.put("Token", Token);
        conditionMap.put("OrderNo",OrderNo);
        conditionMap.put("PayOrderNo",PayOrderNo);
        conditionMap.put("PayPassword",PayPassword);
        conditionMap.put("Type",Type);
        conditionMap.put("OrderType",OrderType);
        condition = NetParam.spliceCondition(conditionMap);
        param = NetParam.getParamMap(trancode, mode, timestamp, custid, sign_method, sign, execmode, fields, condition);
        service.doPost(path, param, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String s) {
                LogUtil.v(TAG,s.trim());
                if(NetParam.isSuccess(b,s)){
                    dao = JsonUtil.objectFromJson(s,Pay.class);
                }
                handler.sendEmptyMessage(msg);
            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {

            }
        });
    }
}
