package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.dao.ErrorDao;
import com.ebusbar.dao.LoginDao;
import com.ebusbar.impl.BalanceDaoImpl;
import com.ebusbar.impl.BillDaoImpl;
import com.ebusbar.impl.BitmapImpl;
import com.ebusbar.impl.ChargeCardItemDaoImpl;

import java.util.ArrayList;

/**
 * 我的钱包
 * Created by Jelly on 2016/3/16.
 */
public class MyWalletActivity extends UtilActivity {
    /**
     * TAG
     */
    public String TAG = "MyWalletActivity";
    /**
     * 昵称
     */
    private TextView nickname_text;
    /**
     * 用户图标
     */
    private ImageView usericon;
    /**
     * 余额
     */
    private TextView money_text;
    /**
     * 充电卡数
     */
    private TextView card_text;
    /**
     * 账单数
     */
    TextView bill_text;
    /**
     * bitmapImpl
     */
    private BitmapImpl bitmapImpl;
    /**
     * 获取头像的消息
     */
    private final int msgIcon = 0x001;
    /**
     * BalanceDaoImpl
     */
    private BalanceDaoImpl balanceDao;
    /**
     * 余额消息
     */
    private final int msgBalance = 0x002;
    /**
     * ChargeCardItemDaoImpl
     */
    private ChargeCardItemDaoImpl chargeCardItemDao;
    /**
     * 充电卡消息
     */
    private final int msgChargeCard = 0x003;
    /**
     * BillDaoImpl
     */
    private BillDaoImpl billDao;
    /**
     * 账户消息
     */
    private final int msgBill = 0x004;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.mywallet);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {
        nickname_text = (TextView) this.findViewById(R.id.nickname_text);
        usericon = (ImageView) this.findViewById(R.id.usericon);
        money_text = (TextView) this.findViewById(R.id.money_text);
        card_text = (TextView) this.findViewById(R.id.card_text);
        bill_text = (TextView) this.findViewById(R.id.bill_text);
    }

    @Override
    public void loadObjectAttribute() {
        bitmapImpl = new BitmapImpl(this,handler,msgIcon);
        balanceDao = new BalanceDaoImpl(this,handler,msgBalance);
        chargeCardItemDao = new ChargeCardItemDaoImpl(this,handler,msgChargeCard);
        billDao = new BillDaoImpl(this,handler,msgBill);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setActivityView() {
        LoginDao.CrmLoginEntity entity = application.getLoginDao().getCrm_login();
        if(!TextUtils.isEmpty(entity.getCustName())){
            nickname_text.setText(entity.getCustName());
        }
        if(!TextUtils.isEmpty(entity.getUsericon())){
            bitmapImpl.getBitmap(entity.getUsericon());
        }
        balanceDao.getBalanceDao(entity.getToken(), entity.getCustID());
        chargeCardItemDao.getChargeCardItemDao(entity.getToken(), entity.getCustID());
        billDao.getBillDaos(entity.getToken(),entity.getCustID());
    }

    /**
     * 当界面获取焦点
     */
    @Override
    protected void onResume() {
        super.onResume();
        setActivityView();
    }

    /**
     * 进入账户余额界面
     * @param view
     * @return
     */
    public View balance(View view){
        if(balanceDao.balanceDao == null || TextUtils.equals(balanceDao.balanceDao.getCrm_balanceamt_get().getReturnStatus(), "110")){
            return view;
        }
        BalanceActivity.startAppActivity(this);
        return view;
    }

    /**
     * 进入充电卡界面
     * @param view
     * @return
     */
    public View chargeCard(View view){
        if(chargeCardItemDao.chargeCardItemDaos.size()!=0 && TextUtils.equals(chargeCardItemDao.chargeCardItemDaos.get(0).getCrm_accounts_get().getReturnStatus(),"110")){
            return view;
        }
        ChargeCardActivity.startAppActivity(this,(ArrayList)chargeCardItemDao.chargeCardItemDaos);
        return view;
    }


    /**
     * 打开订单界面
     * @param view
     * @return
     */
    public View bill(View view){
        if(billDao.billDaos.size() != 0 && TextUtils.equals(billDao.billDaos.get(0).getCrm_balancelog_get().getReturnStatus(), "110")){
            return view;
        }
        BillActivity.startAppActivity(this,(ArrayList)billDao.billDaos);
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgIcon:
                    if(bitmapImpl.img == null){
                        return;
                    }
                    usericon.setImageBitmap(bitmapUtil.toRoundBitmap(bitmapImpl.img));
                    break;
                case msgBalance:
                    if(balanceDao.balanceDao == null || TextUtils.equals(balanceDao.balanceDao.getCrm_balanceamt_get().getIsSuccess(),"N")){
                        ErrorDao errorDao = errorParamUtil.checkReturnState(balanceDao.balanceDao.getCrm_balanceamt_get().getReturnStatus());
                        toastUtil.toastError(context,errorDao,null);
                        return;
                    }
                    application.getLoginDao().getCrm_login().setBalanceAmt(balanceDao.balanceDao.getCrm_balanceamt_get().getBalanceAmt());
                    application.cacheLogin();
                    money_text.setText(application.getLoginDao().getCrm_login().getBalanceAmt());
                    break;
                case msgChargeCard:
                    if(chargeCardItemDao.chargeCardItemDaos.size() == 0 || TextUtils.equals(chargeCardItemDao.chargeCardItemDaos.get(0).getCrm_accounts_get().getIsSuccess(),"N")){
                        return;
                    }
                    card_text.setText(chargeCardItemDao.chargeCardItemDaos.size()+"");
                    break;
                case msgBill:
                    if(billDao.billDaos.size() == 0){
                        return;
                    }
                    bill_text.setText(billDao.billDaos.size()+"");
                    break;
            }
        }
    };

    /**
     * 打开界面
     * @param context
     */
    public static void staticAppActivity(Context context){
        Intent intent = new Intent(context,MyWalletActivity.class);
        context.startActivity(intent);
    }


    @Override
    public String getTAG() {
        return TAG;
    }
}
