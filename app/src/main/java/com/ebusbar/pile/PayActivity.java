package com.ebusbar.pile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.ebusbar.activities.UtilActivity;
import com.ebusbar.dao.ErrorDao;
import com.ebusbar.dao.LoginDao;
import com.ebusbar.dao.OrderInfoDao;
import com.ebusbar.dao.PayResult;
import com.ebusbar.handlerinterface.NetErrorHandlerListener;
import com.ebusbar.impl.BalancePayDaoImpl;
import com.ebusbar.impl.OrderInfoDaoImpl;
import com.ebusbar.impl.P3PayDaoImpl;
import com.ebusbar.myview.MyDialog;
import com.ebusbar.utils.ActivityControl;
import com.ebusbar.param.DefaultParam;
import com.ebusbar.utils.DialogUtil;
import com.ebusbar.utils.DoubleUtil;
import com.ebusbar.param.NetErrorEnum;
import com.ebusbar.utils.PayUtil;
import com.ebusbar.utils.PopupWindowUtil;
import com.ebusbar.utils.ResourceUtil;
import com.ebusbar.utils.WindowUtil;
import com.jellycai.service.ThreadManage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 支付页面
 * Created by Jelly on 2016/3/11.
 */
public class PayActivity extends UtilActivity implements View.OnClickListener , NetErrorHandlerListener{
    /**
     * TAG
     */
    private Intent intent;
    /**
     * 支付种类
     */
    private TextView payType_text;
    /**
     * 支付价格
     */
    private TextView payPrice_text;
    /**
     * 支付按钮
     */
    private Button pay_btn;
    /**
     * 余额按钮
     */
    private ImageView tran_btn;
    /**
     * 支付宝按钮
     */
    private ImageView alipay_btn;
    /**
     * 微信支付按钮
     */
    private ImageView wchatpay_btn;
    /**
     * 输入支付密码
     */
    private PopupWindow payPw;
    /**
     * 弹出窗的操作事件
     */
    private PopupWindowUtil popupWindowUtil;
    /**
     * 资源操作工具
     */
    private ResourceUtil resourceUtil;
    /**
     * 窗体操作工具
     */
    private WindowUtil windowUtil;
    /**
     * 支付密码
     */
    private String payPassword = "";
    /**
     * 密码输入框
     */
    private ImageView[] input_ets;
    /**
     * 用户选择的支付方式
     */
    private ImageView selectPay;
    /**
     * 余额
     */
    private TextView tran_text;

    /**
     * 请求码,设置支付密码
     */
    private static final int PayPwd = 0x001;
    /**
     * Application
     */
    private MyApplication application;

    /**
     * OrderInfoDaoImpl
     */
    private OrderInfoDaoImpl orderInfoDao;
    /**
     * 获取详情消息
     */
    private final int msgInfo = 0x002;
    /**
     * 需要支付的金额
     */
    private String price;
    /**
     * BalancePayDaoImpl
     */
    private BalancePayDaoImpl balancePayDao;
    /**
     * 余额支付消息
     */
    private final int msgBalancePay = 0x003;
    /**
     * 支付测试
     */
    private boolean isTest = true;
    /**
     * 支付订单ID
     */
    private String tradeNo;
    /**
     * P3PayDaoImpl
     */
    private P3PayDaoImpl p3PayDao;
    /**
     * 第3方支付消息
     */
    private final int msgP3Pay = 0x004;
    /**
     * Dialog操作工具
     */
    private DialogUtil dialogUtil = DialogUtil.getInstance();
    /**
     * 充值Dialog
     */
    private MyDialog dialogRecharge;

    /**
     * 充值
     */
    private static final int RECHARGE = 0x005;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.pay);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {
        payPrice_text = (TextView) this.findViewById(R.id.payPrice_text);
        payType_text = (TextView) this.findViewById(R.id.payType_text);
        pay_btn = (Button) this.findViewById(R.id.pay_btn);
        tran_btn = (ImageView) this.findViewById(R.id.tran_btn);
        alipay_btn = (ImageView) this.findViewById(R.id.alipay_btn);
        wchatpay_btn = (ImageView) this.findViewById(R.id.wchatpay_btn);
        tran_text = (TextView) this.findViewById(R.id.tran_text);
    }

    @Override
    public void loadObjectAttribute() {
        intent = getIntent();
        popupWindowUtil = PopupWindowUtil.getInstance();
        windowUtil = WindowUtil.getInstance();
        resourceUtil = ResourceUtil.getInstance();
        application = (MyApplication) getApplication();
        orderInfoDao = new OrderInfoDaoImpl(this,handler,msgInfo);
        balancePayDao = new BalancePayDaoImpl(this,handler,msgBalancePay);
        p3PayDao = new P3PayDaoImpl(this,handler,msgP3Pay);
    }

    @Override
    public void setListener() {
        setPayRadioBtnListener();
    }

    @Override
    public void setActivityView() {
        LoginDao.CrmLoginEntity entity = application.getLoginDao().getCrm_login();
        orderInfoDao.getOrderInfoDaoImpl(entity.getToken(), intent.getStringExtra("OrderNo"), entity.getCustID());
        if(!TextUtils.isEmpty(entity.getBalanceAmt())){
            tran_text.setText(resourceUtil.getResourceString(PayActivity.this, R.string.money_sign)+entity.getBalanceAmt());
        }
    }

    /**
     * 设置支付选择的监听事件
     */
    public void setPayRadioBtnListener(){
        tran_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPay(((ImageView) v));
            }
        });
        alipay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPay(((ImageView) v));
            }
        });
        wchatpay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPay(((ImageView) v));
            }
        });
    }

    /**
     * 设置支付
     */
    public void setPay(ImageView pay){
        if(selectPay != null){ //如果选择的支付方式不为空，改变为未选中
            selectPay.setImageResource(R.drawable.pay_noselect_rb);
        }
        pay.setImageResource(R.drawable.pay_select_rb);
        selectPay = pay;
    }

    /**
     * 支付
     * @param pay
     * @return
     */
    public View pay(View pay){
        if(selectPay == null){ //请选择支付方式
            Toast.makeText(this,R.string.nopayId_hint,Toast.LENGTH_SHORT).show();
            return pay;
        }else if(selectPay.getId() == R.id.alipay_btn){ //支付宝支付
            String orderInfo = "";
            tradeNo = PayUtil.getOutTradeNo();
            if(isTest) {
                orderInfo = PayUtil.getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01", tradeNo);
            }else{
                orderInfo = PayUtil.getOrderInfo("支付充电订单","支付巴斯巴充电订单",price,tradeNo);
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
                    PayTask alipay = new PayTask(PayActivity.this);
                    // 调用支付接口，获取支付结果
                    String result = alipay.pay(payInfo, true);
                    Message msg = new Message();
                    msg.what = PayUtil.SDK_PAY_FLAG;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }
            });
        }else if(selectPay.getId() == R.id.wchatpay_btn){ //微信支付
            Toast.makeText(this,"对不起，暂不支持微信支付!",Toast.LENGTH_SHORT).show();
        }else if(selectPay.getId() == R.id.tran_btn){ //余额支付
            //模拟没有设置支付密码
            LoginDao.CrmLoginEntity entity = application.getLoginDao().getCrm_login();
            if(TextUtils.equals(entity.getExistsPayPassword(),"0")) { //0 :未设置 1：已经设置
                dialogUtil.showSureListenerDialog(this, "您还没有设置支付密码，是否前往设置?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SetPayPwdActivity.startAppActivity(PayActivity.this, PayPwd);
                        dialog.dismiss();
                    }
                });
            }else {
                showPayPw(pay);
            }
        }
        return pay;
    }



    /**
     * 弹出支付PopupWindow
     */
    public void showPayPw(View parent){
        View root = getLayoutInflater().inflate(R.layout.pay_keyboard,null);
        Button num0 = (Button) root.findViewById(R.id.num0);
        Button num1 = (Button) root.findViewById(R.id.num1);
        Button num2 = (Button) root.findViewById(R.id.num2);
        Button num3 = (Button) root.findViewById(R.id.num3);
        Button num4 = (Button) root.findViewById(R.id.num4);
        Button num5 = (Button) root.findViewById(R.id.num5);
        Button num6 = (Button) root.findViewById(R.id.num6);
        Button num7 = (Button) root.findViewById(R.id.num7);
        Button num8 = (Button) root.findViewById(R.id.num8);
        Button num9 = (Button) root.findViewById(R.id.num9);
        Button delete_btn = (Button) root.findViewById(R.id.delete_btn);
        Button dismiss_btn = (Button) root.findViewById(R.id.dismiss_btn);
        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
        dismiss_btn.setOnClickListener(this);
        input_ets = new ImageView[DefaultParam.PAYPASSWOEDSUM];
        input_ets[0] = (ImageView) root.findViewById(R.id.input_et0);
        input_ets[1] = (ImageView) root.findViewById(R.id.input_et1);
        input_ets[2] = (ImageView) root.findViewById(R.id.input_et2);
        input_ets[3] = (ImageView) root.findViewById(R.id.input_et3);
        input_ets[4] = (ImageView) root.findViewById(R.id.input_et4);
        input_ets[5] = (ImageView) root.findViewById(R.id.input_et5);
        payPw = popupWindowUtil.getPopupWindow(this, root, windowUtil.getScreenWidth(this), windowUtil.getScreenHeight(this));
        payPw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                payPassword = "";
            }
        });
        payPw.setAnimationStyle(R.style.paypw_anim);
        payPw.showAtLocation(parent, Gravity.BOTTOM,0,0);
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        switch (v.getId()){
            case R.id.dismiss_btn: //取消按钮
                payPw.dismiss();
                break;
            case R.id.delete_btn: //删除一位支付密码的方法
                if(payPassword.length() == 0){ //已经没有支付密码了，不能再减少了
                    return;
                }
                payPassword = payPassword.substring(0,payPassword.length()-1);
                reInputEt();
                break;
            default:
                if(payPassword.length() == 6){ //如果支付密码已经有6位，不能继续输入
                    return;
                }
                payPassword += btn.getText();
                reInputEt();
                if(payPassword.length() == 6){ //如果是最后一位输入6位，直接支付
                    LoginDao.CrmLoginEntity entity = application.getLoginDao().getCrm_login();
                    balancePayDao.getBalancePayDao(entity.getToken(),intent.getStringExtra("OrderNo"),payPassword,entity.getCustID());
                }
        }
    }

    /**
     * 更改密码框状态
     */
    public void reInputEt(){
        int index = payPassword.length();
        for(int i=0;i<index;i++){
            input_ets[i].setImageResource(R.drawable.paypassword_input);
        }
        for(int i=index;i<DefaultParam.PAYPASSWOEDSUM;i++){
            input_ets[i].setImageResource(R.drawable.paypassword_noinput);
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PayUtil.SDK_PAY_FLAG:
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
                        LoginDao.CrmLoginEntity entity = application.getLoginDao().getCrm_login();
                        p3PayDao.getP3PayDao(entity.getToken(),intent.getStringExtra("OrderNo"),tradeNo,"2",entity.getCustID());
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case msgInfo:
                    if(orderInfoDao.orderInfoDao == null || TextUtils.equals(orderInfoDao.orderInfoDao.getEvc_order_get().getIsSuccess(),"N")){
                        ErrorDao errorDao = errorParamUtil.checkReturnState(orderInfoDao.orderInfoDao.getEvc_order_get().getReturnStatus());
                        toastUtil.toastError(context, errorDao, null);
                        ActivityControl.finishAct(PayActivity.this);
                        return;
                    }
                    OrderInfoDao.EvcOrderGetEntity entity = orderInfoDao.orderInfoDao.getEvc_order_get();
                    price = DoubleUtil.add(entity.getChargingAmt(),entity.getServiceAmt());
                    payPrice_text.setText(resourceUtil.getResourceString(PayActivity.this, R.string.money_sign) + price);
                    payType_text.setText("电桩充电");
                    pay_btn.setText(resourceUtil.getResourceString(PayActivity.this, R.string.pay_btn_text) + price + "元");
                    break;
                case msgBalancePay:
                    if(balancePayDao.balancePayDao == null || TextUtils.equals(balancePayDao.balancePayDao.getEvc_order_pay().getIsSuccess(), "N")){
                        ErrorDao errorDao = errorParamUtil.checkReturnState(balancePayDao.balancePayDao.getEvc_order_pay().getReturnStatus());
                        toastUtil.toastError(context,errorDao,PayActivity.this);
                        payPassword = "";
                        reInputEt();
                        return;
                    }
                    Toast.makeText(PayActivity.this, "付款成功", Toast.LENGTH_SHORT).show();
                    //更新缓存中的余额
                    String balance = DoubleUtil.delete(application.getLoginDao().getCrm_login().getBalanceAmt(),price);
                    application.getLoginDao().getCrm_login().setBalanceAmt(balance);
                    application.cacheLogin();
                    payPw.dismiss();
                    ActivityControl.finishExcept(FragmentTabHostActivity.STAG);
                    break;
                case msgP3Pay:
                    if(p3PayDao.p3PayDao == null || TextUtils.equals(p3PayDao.p3PayDao.getEvc_order_pay().getIsSuccess(),"N")){
                        ErrorDao errorDao = errorParamUtil.checkReturnState(p3PayDao.p3PayDao.getEvc_order_pay().getReturnStatus());
                        toastUtil.toastError(context,errorDao,null);
                        return;
                    }
                    Toast.makeText(PayActivity.this,"付款成功",Toast.LENGTH_SHORT).show();
                    ActivityControl.finishExcept(FragmentTabHostActivity.STAG);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void handlerError(String returnState) {
        if(TextUtils.equals(returnState,NetErrorEnum.余额不足.getState())){
            dialogUtil.showSureListenerDialog(PayActivity.this, "您的余额不足，是否充值！", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    RechargeActivity.startAppActivity(PayActivity.this, RECHARGE);
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    payPw.dismiss();
                }
            });
        }
    }

    /**
     * 返回结果
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PayPwd:
                if(resultCode == SetPayPwdActivity.SUCCESS){
                    showPayPw(tran_btn);
                }else if(resultCode == SetPayPwdActivity.FAILURE){
                    Toast.makeText(this,"支付密码设置失败!",Toast.LENGTH_SHORT).show();
                }
                break;
            case RECHARGE:
                if(resultCode == RechargeActivity.SUCCESS){
                    Toast.makeText(this,"充值成功，请输入支付密码支付!",Toast.LENGTH_SHORT).show();
                    tran_text.setText(application.getLoginDao().getCrm_login().getBalanceAmt());
                    payPassword = "";
                    reInputEt();
                }else if(resultCode == RechargeActivity.FAILURE){
                    Toast.makeText(this,"充值失败，请选择其他操作方式!",Toast.LENGTH_SHORT).show();
                    payPw.dismiss();
                }
        }

    }

    /**
     * 开启支付界面
     */
    public static void startPayActivity(Context context,String OrderNo){
        Intent intent = new Intent(context,PayActivity.class);
        intent.putExtra("OrderNo",OrderNo);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
