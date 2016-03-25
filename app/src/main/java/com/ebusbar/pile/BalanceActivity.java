package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    /**
     * Intent
     */
    private Intent intent;
    /**
     * Application
     */
    private MyApplication application;
    /**
     * 充值请求
     */
    public static final int RECHARGE = 0x001;

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
        intent = getIntent();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setActivityView() {
        LoginDao.CrmLoginEntity entity = application.getLoginDao().getCrm_login();
        if(!TextUtils.isEmpty(entity.getBalanceAmt())){
            money.setText("¥" + entity.getBalanceAmt());
        }
    }

    public View recharge(View view){
        RechargeActivity.startAppActivity(this,RECHARGE);
        return view;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RECHARGE){
            switch (resultCode){
                case RechargeActivity.SUCCESS:
                    Log.v(TAG,"充值成功");
                    Toast.makeText(BalanceActivity.this, "充值成功！", Toast.LENGTH_SHORT).show();
                    setActivityView();
                    break;
                case RechargeActivity.FAILURE:
                    Toast.makeText(BalanceActivity.this,"充值失败，请联系客服退款!",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
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
