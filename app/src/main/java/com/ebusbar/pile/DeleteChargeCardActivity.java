package com.ebusbar.pile;

import android.app.Activity;
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
import com.ebusbar.dao.DeleteChargeCardDao;
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
    /**
     * 删除成功
     */
    public static final int SUCCESS = 0x002;
    /**
     * 删除失败
     */
    public static final int FAILURE = 0x003;


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
                        DeleteChargeCardDao.CrmAccountsDeleteEntity entity = deleteChargeCardDao.deleteChargeCardDao.getCrm_accounts_delete();
                        if(TextUtils.equals(entity.getReturnStatus(),"123")){
                            Toast.makeText(DeleteChargeCardActivity.this,"充电卡密码错误，请重新输入!",Toast.LENGTH_SHORT).show();
                            password.setText("");
                            return;
                        }
                        setResult(FAILURE);
                        return;
                    }
                    Intent intent = new Intent();
                    intent.putExtra("DeleteChargeCardDao",DeleteChargeCardActivity.this.intent.getParcelableExtra("ChargeCardItemDao"));
                    setResult(SUCCESS,intent);
                    ActivityControl.finishAct(DeleteChargeCardActivity.this);
                    break;
            }
        }
    };

    /**
     * 开启界面
     */
    public static void startAppActivity(Context context,ChargeCardItemDao dao,int resquestCode){
        Intent intent = new Intent(context, DeleteChargeCardActivity.class);
        intent.putExtra("ChargeCardItemDao", dao);
        Activity activity = (Activity) context;
        activity.startActivityForResult(intent,resquestCode);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
