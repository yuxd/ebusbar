package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Jelly on 2016/3/16.
 */
public class BalanceActivity extends BaseActivity{
    /**
     * Balance
     */
    public String TAG = "BalanceActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.balance);
    }

    @Override
    public void init() {

    }

    @Override
    public void loadObjectAttribute() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void setActivityView() {

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
