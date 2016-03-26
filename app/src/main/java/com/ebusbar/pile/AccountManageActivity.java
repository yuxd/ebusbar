package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebusbar.dao.LoginDao;
import com.ebusbar.impl.BitmapImpl;
import com.ebusbar.impl.LogoutDaoImpl;
import com.ebusbar.utils.ActivityControl;
import com.ebusbar.utils.RoundBitmapUtil;

/**
 * 账户管理
 * Created by Jelly on 2016/3/7.
 */
public class AccountManageActivity extends BaseActivity{
    /**
     * TAG
     */
    public String TAG = "AccountManageActivity";
    /**
     * 头像
     */
    private ImageView avatar_icon;
    /**
     * 昵称
     */
    private TextView nickname_text;
    /**
     * 性别
     */
    private TextView sex_text;
    /**
     * 年龄
     */
    private TextView age_text;
    /**
     * 手机号码
     */
    private TextView phone_text;
    /**
     * 实名认证
     */
    private TextView certification_text;
    /**
     * BitmapImpl
     */
    private BitmapImpl bitmap;
    /**
     *
     */
    private final int msgIcon = 0x001;
    /**
     * Application
     */
    private MyApplication application;
    /**
     * LogoutDaoImpl
     */
    private LogoutDaoImpl logoutDao;
    /**
     * 注销消息
     */
    private final int msgLogout = 0x002;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.accountmanage);
        init();
        loadObjectAttribute();
        setActivityView();
        setListener();
    }

    @Override
    public void init() {
        avatar_icon = (ImageView) this.findViewById(R.id.avatar_icon);
        nickname_text = (TextView) this.findViewById(R.id.nickname_text);
        sex_text = (TextView) this.findViewById(R.id.sex_text);
        age_text = (TextView) this.findViewById(R.id.age_text);
        phone_text = (TextView) this.findViewById(R.id.phone_text);
        certification_text = (TextView) this.findViewById(R.id.certification_text);
    }

    @Override
    public void loadObjectAttribute() {
        application = (MyApplication) this.getApplication();
        bitmap = new BitmapImpl(this,handler,msgIcon);
        logoutDao = new LogoutDaoImpl(this,handler,msgLogout);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setActivityView() {
        LoginDao loginDao = application.getLoginDao();
        if(!TextUtils.isEmpty(loginDao.getCrm_login().getUsericon())){
            bitmap.getBitmap(loginDao.getCrm_login().getUsericon());
        }
        if(!TextUtils.isEmpty(loginDao.getCrm_login().getCustName())){
            nickname_text.setText(loginDao.getCrm_login().getCustName());
        }
        sex_text.setText(loginDao.getCrm_login().getSex());
        age_text.setText(loginDao.getCrm_login().getAge());
        phone_text.setText(loginDao.getCrm_login().getMobile());
        if(TextUtils.equals(loginDao.getCrm_login().getVerified(),"1")){
            certification_text.setText("是");
        }else{
            certification_text.setText("否");
        }
    }

    /**
     * 注销
     */
    public View loginOut(View view){
        LoginDao.CrmLoginEntity entity = application.getLoginDao().getCrm_login();
        logoutDao.getLogoutDao(entity.getToken(), entity.getCustID());
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgIcon:
                    if(bitmap.img == null){
                        Log.v(TAG,"获取头像失败");
                        return;
                    }
                    avatar_icon.setImageBitmap(RoundBitmapUtil.toRoundBitmap(bitmap.img));
                    break;
                case msgLogout:
                    if(logoutDao.logoutDao == null || TextUtils.equals(logoutDao.logoutDao.getCrm_logout().getIsSuccess(),"N")){
                        if(TextUtils.equals(logoutDao.logoutDao.getCrm_logout().getReturnStatus(), "110")){
                            Toast.makeText(AccountManageActivity.this,"您已经注销,无需重复注销！",Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(AccountManageActivity.this,"注销失败，请检查您的网络连接",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    application.loginOut();
                    ActivityControl.finishAct(AccountManageActivity.this);
                    break;
            }
        }
    };

    /**
     * 启动AccountManageActivity
     * @param context
     */
    public static void startAppActivity(Context context){
        Intent intent = new Intent(context,AccountManageActivity.class);
        context.startActivity(intent);
    }

}
