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

import com.ebusbar.dao.AddChargeCardDao;
import com.ebusbar.dao.LoginDao;
import com.ebusbar.impl.AddChargeCardDaoImpl;
import com.ebusbar.utils.ActivityControl;

/**
 * 绑定充电卡
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
    /**
     * SUCCESS
     */
    public static final int SUCCESS = 0x002;
    /**
     * FAILURE
     */
    public static final int FAILURE = 0x003;

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
                        AddChargeCardDao.CrmAccountsInsertEntity entity = addChargeCardDao.addChargeCardDao.getCrm_accounts_insert();
                        if(TextUtils.equals(entity.getReturnStatus(),"119")){
                            Toast.makeText(AddChargeCardActivity.this,"充电卡已经被绑定，请检查充电卡卡号!",Toast.LENGTH_SHORT).show();
                            clearET();
                            return;
                        }else if(TextUtils.equals(entity.getReturnStatus(),"122")){
                            Toast.makeText(AddChargeCardActivity.this,"充电卡不存在，请输入正确卡号!",Toast.LENGTH_SHORT).show();
                            clearET();
                            return;
                        }else if(TextUtils.equals(entity.getReturnStatus(),"123")){
                            Toast.makeText(AddChargeCardActivity.this,"充电卡密码错误，请重新输入密码!",Toast.LENGTH_SHORT).show();
                            pwd.setText("");
                            return;
                        }
                        setResult(FAILURE);
                        return;
                    }
                    AddChargeCardDao dao = addChargeCardDao.addChargeCardDao;
                    Intent intent = getIntent();
                    intent.putExtra("AddChargeCardDao",dao);
                    setResult(SUCCESS,intent);
                    ActivityControl.finishAct(AddChargeCardActivity.this);
                    break;
            }
        }
    };

    /**
     * 清空输入框
     */
    public void clearET(){
        no.setText("");
        pwd.setText("");
    }


    public static void startAppActivity(Context context,int requestCode){
        Intent intent = new Intent(context,AddChargeCardActivity.class);
        Activity activity = (Activity) context;
        activity.startActivityForResult(intent,requestCode);
    }


    @Override
    public String getTAG() {
        return TAG;
    }
}
