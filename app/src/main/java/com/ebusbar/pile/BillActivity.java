package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 消费账单
 * Created by Jelly on 2016/3/23.
 */
public class BillActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.bill);
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

    /**
     * 开启界面
     */
    public static void startAppActivity(Context context){
        Intent intent = new Intent(context,BillActivity.class);
        context.startActivity(intent);
    }

}
