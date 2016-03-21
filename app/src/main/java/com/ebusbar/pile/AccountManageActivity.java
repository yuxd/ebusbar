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

import com.ebusbar.dao.LoginDao;
import com.ebusbar.impl.BitmapImpl;
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
    private int msgIcon = 0x001;
    /**
     * Application
     */
    private MyApplication application;

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
        application.loginOut();
        Log.v(TAG, "注销");
        ActivityControl.finishAct(AccountManageActivity.this);
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == msgIcon){
                avatar_icon.setImageBitmap(RoundBitmapUtil.toRoundBitmap(bitmap.img));
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
