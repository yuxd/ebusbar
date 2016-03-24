package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ebusbar.dao.ChargeCardItemDao;
import com.ebusbar.dao.LoginDao;
import com.ebusbar.impl.DeleteChargeCardDaoImpl;
import com.ebusbar.utils.ActivityControl;

/**
 *
 * Created by Jelly on 2016/3/23.
 */
public class DeleteChargeCardActivity extends BaseActivity{
    /**
     * TAG
     */
    public String TAG = "DeleteChargeCardActivity";
    /**
     * 数据包
     */
    private Intent intent;
    /**
     * 列表数据
     */
    private ChargeCardItemDao dao;
    /**
     * 密码输入框
     */
    private EditText password;
    /**
     * 充电卡
     */
    private TextView no;
    /**
     * Application
     */
    private MyApplication application;
    /**
     * DeleteChargeCardDaoImpl
     */
    private DeleteChargeCardDaoImpl deleteChargeCardDao;
    /**
     * 删除消息
     */
    private final int msgDelete = 0x001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.deletechargecard);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {
        no = (TextView) this.findViewById(R.id.no);
        password = (EditText) this.findViewById(R.id.password);
    }

    @Override
    public void loadObjectAttribute() {
        intent = getIntent();
        dao = intent.getParcelableExtra("ChargeCardItemDao");
        application = (MyApplication) getApplication();
        deleteChargeCardDao = new DeleteChargeCardDaoImpl(this,handler,msgDelete);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setActivityView() {
        no.setText(dao.getCrm_accounts_get().getAccountNo());
    }

    /**
     * 删除
     * @param view
     * @return
     */
    public View delete(View view){
        String pwds = password.getText().toString();
        if(TextUtils.isEmpty(pwds)){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
            return view;
        }
        LoginDao.CrmLoginEntity entity = application.getLoginDao().getCrm_login();
        deleteChargeCardDao.getDeleteChargeCardDao(entity.getToken(),dao.getCrm_accounts_get().getAccountID(),pwds,entity.getCustID());
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgDelete:
                    if(deleteChargeCardDao.deleteChargeCardDao == null || TextUtils.equals(deleteChargeCardDao.deleteChargeCardDao.getCrm_accounts_delete().getIsSuccess(), "N")){
                        Toast.makeText(DeleteChargeCardActivity.this,"密码错误，请重新输入!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(DeleteChargeCardActivity.this,"解绑充电卡成功!",Toast.LENGTH_SHORT).show();
                    ActivityControl.finishAct(DeleteChargeCardActivity.this);
                    break;
            }
        }
    };

    /**
     * 开启界面
     */
    public static void startAppActivity(Context context,ChargeCardItemDao dao){
        Intent intent = new Intent(context, DeleteChargeCardActivity.class);
        intent.putExtra("ChargeCardItemDao",dao);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
