package com.ebusbar.pile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.ebusbar.activities.UtilActivity;
import com.ebusbar.bean.Error;
import com.ebusbar.bean.Login;
import com.ebusbar.bean.PayResult;
import com.ebusbar.impl.ReChargeDaoImpl;
import com.ebusbar.utils.ActivityControl;
import com.ebusbar.utils.LogUtil;
import com.ebusbar.utils.PayUtil;
import com.jellycai.service.ThreadManage;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 充值
 * Created by Jelly on 2016/3/16.
 */
public class RechargeActivity extends UtilActivity implements View.OnClickListener,IWXAPIEventHandler {
    /**
     * TAG
     */
    public String TAG = "RechargeActivity";

    /**
     * 价格10
     */
    private TextView price10;
    /**
     * 价格20
     */
    private TextView price20;
    /**
     * 价格50
     */
    private TextView price50;
    /**
     * 价格100
     */
    private TextView price100;
    /**
     * 选中的价格
     */
    private TextView selectPrice;
    /**
     * 支付宝支付
     */
    private ImageView alipay_btn;
    /**
     * 微信支付
     */
    private ImageView wchatpay_btn;
    /**
     * 选择的支付方式
     */
    private ImageView selectPay;
    /**
     * 是否为支付测试
     */
    private Boolean isTest = true;
    /**
     * 支付订单ID
     */
    private String tradeNo;
    /**
     * ReChargeDaoImpl
     */
    private ReChargeDaoImpl reChargeDao;
    /**
     * 充值消息
     */
    private final int msgReCharge = 0x002;
    /**
     * 支付价格
     */
    private String price;
    /**
     * 充值成功
     */
    public static final int SUCCESS = 0x003;
    /**
     * 充值失败
     */
    public static final int FAILURE = 0X004;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.recharge);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {
        price10 = (TextView) this.findViewById(R.id.price10);
        price20 = (TextView) this.findViewById(R.id.price20);
        price50 = (TextView) this.findViewById(R.id.price50);
        price100 = (TextView) this.findViewById(R.id.price100);
        alipay_btn = (ImageView) this.findViewById(R.id.alipay_btn);
        wchatpay_btn = (ImageView) this.findViewById(R.id.wchatpay_btn);
    }

    @Override
    public void loadObjectAttribute() {
        reChargeDao = new ReChargeDaoImpl(this,handler,msgReCharge);
    }

    @Override
    public void setListener() {
        setPriceListener();
        setPayRadioBtnListener();
    }

    @Override
    public void setActivityView() {

    }

    /**
     * 设置价格按钮的监听器
     */
    public void setPriceListener(){
        price10.setOnClickListener(this);
        price20.setOnClickListener(this);
        price50.setOnClickListener(this);
        price100.setOnClickListener(this);
    }

    /**
     * 设置选择支付方式的单选按钮的监听事件
     */
    public void setPayRadioBtnListener(){
        alipay_btn.setOnClickListener(this);
        wchatpay_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.v(TAG, "点击");
        switch (v.getId()){
            case R.id.price10:
                selectPrice(price10);
                break;
            case R.id.price20:
                selectPrice(price20);
                break;
            case R.id.price50:
                selectPrice(price50);
                break;
            case R.id.price100:
                selectPrice(price100);
                break;
            case R.id.alipay_btn:
                selectPay(alipay_btn);
                break;
            case R.id.wchatpay_btn:
                selectPay(wchatpay_btn);
                break;
        }
    }

    /**
     * 开始支付
     * @param view
     * @return
     */
    public View pay(View view){
        if(selectPay == null){ //没有选择支付方式
            Toast.makeText(this,R.string.nopayId_hint,Toast.LENGTH_SHORT).show();
            return view;
        }
        if(selectPrice == null){ //没有选择充值金额
            Toast.makeText(this,R.string.noprice_hint,Toast.LENGTH_SHORT).show();
            return view;
        }
        switch (selectPay.getId()){
            case R.id.alipay_btn:
                price = selectPrice.getText().toString().replace("元","");
                String orderInfo = "";
                tradeNo = PayUtil.getOutTradeNo();
                if(isTest){
                    orderInfo = PayUtil.getOrderInfo("测试支付","测试支付详情","0.01",tradeNo);
                }else{
                    orderInfo = PayUtil.getOrderInfo(price + "元充值卡", "巴斯巴电桩" + price + "元充值卡", price,tradeNo);
                }
                String sign = PayUtil.sign(orderInfo);
                try {
                    /**
                     * 仅需对sign 做URL编码
                     */
                    sign = URLEncoder.encode(sign, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                /**
                 * 完整的符合支付宝参数规范的订单信息
                 */
                final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + PayUtil.getSignType();
                ThreadManage.start(new Runnable() {
                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(RechargeActivity.this);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(payInfo, true);
                        Message msg = new Message();
                        msg.what = PayUtil.SDK_PAY_FLAG;
                        msg.obj = result;
                        handler.sendMessage(msg);
                    }
                });
                break;
            case R.id.wchatpay_btn:
                LogUtil.v(TAG,"微信支付");
                final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
                // 将该app注册到微信
                api.registerApp("wx2faca1abbcec3d6b");
                PayReq request = new PayReq();
                request.appId = "wx2faca1abbcec3d6b";
                request.partnerId = "1900000109";
                request.prepayId= "1101000000140415649af9fc314aa427";
                request.packageValue = "Sign=WXPay";
                request.nonceStr= "1101000000140429eb40476f8896f4c9";
                request.timeStamp= "1398746574";
                request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
                api.sendReq(request);
                break;
        }
        return view;
    }

    /**
     * 选价格
     * @param price
     */
    public void selectPrice(TextView price){
        price.setBackgroundResource(R.drawable.select_price_btn_bg);
        price.setTextColor(Color.WHITE);
        if(selectPrice != null && selectPrice != price){ //如果选择的价格不为空，改变背景为未选中
            selectPrice.setBackgroundResource(R.drawable.deleteorder_btn_bg);
            selectPrice.setTextColor(Color.RED);
        }
        selectPrice = price;
    }

    /**
     * 选支付方式
     * @param pay
     */
    public void selectPay(ImageView pay){
        if(selectPay != null){ //如果选择的支付方式不为空，改变为未选中
            selectPay.setImageResource(R.drawable.pay_noselect_rb);
        }
        pay.setImageResource(R.drawable.pay_select_rb);
        selectPay = pay;
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtil.v(TAG,baseResp.errCode+"");
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Toast.makeText(this,"发送成功",Toast.LENGTH_SHORT).show();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(this,"发送取消",Toast.LENGTH_SHORT).show();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Toast.makeText(this,"发送被拒绝",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this,"发送返回",Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PayUtil.SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Login.DataEntity entity = application.getLoginDao().getData();
                        reChargeDao.getReChargeDao(entity.getToken(),price,tradeNo,"1",entity.getCustID());
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(RechargeActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(RechargeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                case msgReCharge:
                    if(reChargeDao.reChargeDao == null || TextUtils.equals(reChargeDao.reChargeDao.getCrm_recharge().getIsSuccess(),"N")){
                        Error errorDao = errorParamUtil.checkReturnState(reChargeDao.reChargeDao.getCrm_recharge().getReturnStatus());
                        toastUtil.toastError(context,errorDao,null);
                        RechargeActivity.this.setResult(FAILURE);
                        ActivityControl.finishAct(RechargeActivity.this);
                        return;
                    }
                    //重置缓存
                    application.getLoginDao().getData().setBalanceAmt(reChargeDao.reChargeDao.getCrm_recharge().getBalanceAmt());
                    application.cacheLogin();
                    //设置返回消息
                    Intent intent = getIntent();
                    intent.putExtra("ReChargePrice", price);
                    RechargeActivity.this.setResult(SUCCESS);
                    ActivityControl.finishAct(RechargeActivity.this);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 开启界面
     * @param context
     */
    public static void startAppActivity(Context context,int request){
        Intent intent = new Intent(context,RechargeActivity.class);
        Activity activity = (Activity) context;
        activity.startActivityForResult(intent,request);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
