package com.ebusbar.pile;

import android.content.Context;
import android.content.DialogInterface;
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
import com.ebusbar.handlerinterface.NetErrorHandlerListener;
import com.ebusbar.impl.BitmapImpl;
import com.ebusbar.impl.LogoutDaoImpl;
import com.ebusbar.utils.ActivityControl;
import com.ebusbar.param.NetErrorEnum;

/**
 * 账户管理
 * Created by Jelly on 2016/3/7.
 */
public class AccountManageActivity extends UtilActivity implements NetErrorHandlerListener{
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
     * LogoutDaoImpl
     */
    private LogoutDaoImpl logoutDao;
    /**
     * 注销消息
     */
    private final int msgLogout = 0x002;
    /**
     * 修改昵称
     */
    public static final int nickName = 0x003;

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
        bitmap = new BitmapImpl(this,handler,msgIcon);
        logoutDao = new LogoutDaoImpl(this,handler,msgLogout);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setActivityView() {
        LoginDao loginDao = application.getLoginDao();
        if(!TextUtils.isEmpty(loginDao.getData().getUsericon())){
            bitmap.getBitmap(loginDao.getData().getUsericon());
        }
        if(!TextUtils.isEmpty(loginDao.getData().getCustName())){
            nickname_text.setText(loginDao.getData().getCustName());
        }
        sex_text.setText(loginDao.getData().getSex());
        phone_text.setText(loginDao.getData().getMobile());
        if(TextUtils.equals(loginDao.getData().getVerified(),"1")){
            certification_text.setText("是");
        }else{
            certification_text.setText("否");
        }
    }

    /**
     * 进入修改昵称界面
     * @param view
     * @return
     */
    public View nickName(View view){
        ModCustNameActivity.startAppActivity(context,nickName);
        return view;
    }

    /**
     * 注销
     */
    public View loginOut(View view){
        dialogUtil.showSureListenerDialog(this, "是否要退出账户！", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                LoginDao.DataEntity entity = application.getLoginDao().getData();
                logoutDao.getLogoutDao(entity.getToken(), entity.getCustID());
            }
        });
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgIcon: //获取用户头像
                    if(bitmap.img == null){ //用户头像获取失败
                        return;
                    }
                    avatar_icon.setImageBitmap(bitmapUtil.toRoundBitmap(bitmap.img));
                    break;
                case msgLogout: //注销
                    if(logoutDao.logoutDao == null || TextUtils.equals(logoutDao.logoutDao.getCrm_logout().getIsSuccess(),"N")){
                        ErrorDao errorDao = errorParamUtil.checkReturnState(logoutDao.logoutDao.getCrm_logout().getReturnStatus());
                        toastUtil.toastError(context, errorDao, AccountManageActivity.this);
                        return;
                    }
                    application.loginOut();
                    ActivityControl.finishAct(AccountManageActivity.this);
                    break;
            }
        }
    };

    @Override
    public void handlerError(String returnState) {
        if(TextUtils.equals(returnState, NetErrorEnum.Token失效.getState())){
            application.loginOut();
            ActivityControl.finishAct(AccountManageActivity.this);
        }
    }

    /**
     * 启动AccountManageActivity
     * @param context
     */
    public static void startAppActivity(Context context){
        Intent intent = new Intent(context,AccountManageActivity.class);
        context.startActivity(intent);
    }

}
