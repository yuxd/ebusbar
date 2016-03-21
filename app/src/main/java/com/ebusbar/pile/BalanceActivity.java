package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ebusbar.dao.LoginDao;

/**
 * 账户余额
 * Created by Jelly on 2016/3/16.
 */
public class BalanceActivity extends BaseActivity{
    /**
     * Balance
     */
    public String TAG = "BalanceActivity";
    /**
     * 余额
     */
    private TextView money;

    private MyApplication application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.balance);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {
        money = (TextView) this.findViewById(R.id.money);
    }

    @Override
    public void loadObjectAttribute() {
        application = (MyApplication) getApplication();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setActivityView() {
        LoginDao loginDao = application.getLoginDao();
        if(!TextUtils.isEmpty(loginDao.getCrm_login().getBalanceAmt())){
            money.setText("¥"+loginDao.getCrm_login().getBalanceAmt());
        }
    }

    public View recharge(View view){
        RechargeActivity.startAppActivity(this);
        return view;
    }

    /**
     * 启动界面
     */
    public static void startAppActivity(Context context){
        Intent intent = new Intent(context,BalanceActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
