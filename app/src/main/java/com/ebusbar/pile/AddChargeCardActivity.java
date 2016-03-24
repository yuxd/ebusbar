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

import com.ebusbar.dao.LoginDao;
import com.ebusbar.impl.AddChargeCardDaoImpl;
import com.ebusbar.utils.ActivityControl;

/**
 * Created by Jelly on 2016/3/23.
 */
public class AddChargeCardActivity extends BaseActivity{

    /**
     * TAG
     */
    public String TAG = "AddChargeCardActivity";
    /**
     * AddChargeCardDaoImpl
     */
    private AddChargeCardDaoImpl addChargeCardDao;
    /**
     * 增加消息
     */
    private final int msgAdd = 0x001;
    /**
     * 卡号
     */
    private EditText no;
    /**
     * 卡密码
     */
    private EditText pwd;
    /**
     * 确定
     */
    private TextView ok;
    /**
     * Application
     */
    private MyApplication application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.addchargecard);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {
        no = (EditText) this.findViewById(R.id.no);
        pwd = (EditText) this.findViewById(R.id.pwd);
        ok = (TextView) this.findViewById(R.id.ok);
    }

    @Override
    public void loadObjectAttribute() {
        application = (MyApplication) getApplication();
        addChargeCardDao = new AddChargeCardDaoImpl(this,handler,msgAdd);
    }

    @Override
    public void setListener() {
        setOKListener();
    }

    @Override
    public void setActivityView() {

    }


    public void setOKListener(){
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nos = no.getText().toString();
                String pwds = pwd.getText().toString();
                if(TextUtils.isEmpty(nos) || TextUtils.isEmpty(pwds)){
                    Toast.makeText(AddChargeCardActivity.this,"卡号和密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                LoginDao.CrmLoginEntity entity = application.getLoginDao().getCrm_login();
                addChargeCardDao.addAddChargeCardDao(entity.getToken(),nos,pwds,entity.getCustID());
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgAdd:
                    if(addChargeCardDao.addChargeCardDao == null || TextUtils.equals(addChargeCardDao.addChargeCardDao.getCrm_accounts_insert().getIsSuccess(), "N")){
                        Toast.makeText(AddChargeCardActivity.this,"添加充电卡失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(AddChargeCardActivity.this,"添加充电卡成功！",Toast.LENGTH_SHORT).show();
                    ActivityControl.finishAct(AddChargeCardActivity.this);
                    break;
            }
        }
    };


    public static void startAppActivity(Context context){
        Intent intent = new Intent(context,AddChargeCardActivity.class);
        context.startActivity(intent);
    }


    @Override
    public String getTAG() {
        return TAG;
    }
}
