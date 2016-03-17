package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 充值
 * Created by Jelly on 2016/3/16.
 */
public class RechargeActivity extends BaseActivity implements View.OnClickListener{
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
        return view;
    }

    /**
     * 选价格
     * @param price
     */
    public void selectPrice(TextView price){
        if(selectPrice != null){ //如果选择的价格不为空，改变背景为未选中
            selectPrice.setBackgroundResource(R.drawable.deleteorder_btn_bg);
            selectPrice.setTextColor(Color.RED);
        }
        price.setBackgroundResource(R.drawable.select_price_btn_bg); //设置背景为选中
        price.setTextColor(Color.WHITE);
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


    /**
     * 开启界面
     * @param context
     */
    public static void startAppActivity(Context context){
        Intent intent = new Intent(context,RechargeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
