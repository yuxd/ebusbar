package com.ebusbar.pile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.impl.CheckCodeDaoImpl;
import com.ebusbar.impl.CodeDao;
import com.ebusbar.param.DefaultParam;
import com.ebusbar.utils.CountDownUtil;
import com.ebusbar.utils.RegExpUtil;

/**
 * Created by Jelly on 2016/3/1.
 */
public class ForgetPwdActivity extends UtilActivity {
    /**
     * TAG
     */
    public String TAG="ForgetPwdActivity";
    /**
     * 忘记密码界面的验证码输入框
     */
    private EditText forget_pwd_code_et;
    /**
     * 忘记密码界面验证码输入框内容清除按钮
     */
    private ImageView forget_pwd_code_clear;
    /**
     * 忘记密码手机号码输入框
     */
    private EditText forget_pwd_phone_et;
    /**
     * 获取验证码按钮
     */
    private Button forget_pwd_code_btn;
    /**
     * CodeDaoImpl
     */
    private CodeDao codeDao;
    /**
     * 获取验证码消息
     */
    private final int msgCode = 0x001;
    /**
     * 倒计时消息
     */
    private final int msgCountDown = 0x002;
    /**
     * 校验验证码
     */
    private CheckCodeDaoImpl checkCodeDao;
    /**
     * 校验验证码消息
     */
    private final int msgCheckCode = 0x003;

    private String phone;

    private String code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.forget_pwd);

        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }


    @Override
    public void init(){
        forget_pwd_code_btn = (Button) this.findViewById(R.id.forget_pwd_code_btn);
        forget_pwd_phone_et = (EditText) this.findViewById(R.id.forget_pwd_phone_et);
        forget_pwd_code_et = (EditText) this.findViewById(R.id.forget_pwd_code_et);
        forget_pwd_code_clear = (ImageView) this.findViewById(R.id.forget_pwd_code_clear);
    }

    @Override
    public void loadObjectAttribute() {
        codeDao = new CodeDao(this,handler,msgCode);
        checkCodeDao = new CheckCodeDaoImpl(this,handler,msgCheckCode);
    }

    @Override
    public void setListener() {
        setEtClearListener();
    }

    @Override
    public void setActivityView() {

    }

    /**
     * 设置输入框的输入监听事件，当里面有内容是，出现清空按钮
     */
    public void setEtClearListener(){
        forget_pwd_code_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = forget_pwd_code_et.getText().length();
                if (length > 0) {
                    forget_pwd_code_clear.setVisibility(View.VISIBLE);
                    forget_pwd_code_clear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            forget_pwd_code_et.setText("");
                        }
                    });
                } else {
                    forget_pwd_code_clear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 获得重置密码的验证码
     * @param view
     * @return
     */
    public View getForgetCode(View view){
        phone = forget_pwd_phone_et.getText().toString();
        if(TextUtils.isEmpty(phone) || !RegExpUtil.regPhone(phone)){
            Log.v(TAG,"手机号码输入有误");
            return view;
        }
        codeDao.getNetCodeDao(DefaultParam.FORGETCODE, phone);
        return view;
    }

    /**
     * 进入输入密码界面
     * @param view
     */
    public View nextTip(View view){
        phone = forget_pwd_phone_et.getText().toString();
        code = forget_pwd_code_et.getText().toString();
        if(TextUtils.isEmpty(phone) || !RegExpUtil.regPhone(phone)){
            Log.v(TAG,"手机号码输入错误");
            return view;
        }
        checkCodeDao.getData(phone, code, CheckCodeDaoImpl.LOGIN);
        return view;
    }

    /**
     * 开启倒计时
     */
    public void countDown(){
        CountDownUtil countDownUtil = new CountDownUtil(handler,msgCountDown, DefaultParam.COUNTDOWN);
        countDownUtil.startCounrtDown();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgCode:
                    forget_pwd_code_btn.setEnabled(false);
                    countDown();
                    break;
                case msgCountDown:
                    int count = (int) msg.obj;
                    forget_pwd_code_btn.setText("等待" + count + "秒");
                    if(count == 0){
                        forget_pwd_code_btn.setEnabled(true);
                        forget_pwd_code_btn.setText("获取验证码");
                    }
                    break;
                case msgCheckCode:
                    if(TextUtils.equals("N",checkCodeDao.bean.getIsSuccess())){
                        com.ebusbar.bean.Error error = errorParamUtil.checkReturnState(checkCodeDao.bean.getReturnStatus());
                        toastUtil.toastError(context,error,null);
                        return;
                    }
                    SetLoginPwdActivity.startSetPwdActivity(context,phone,code);
                    break;
            }
        }
    };

    /**
     * 打开ForgetPwdActivity
     * @param activity
     */
    public static void startForgetPwdActivity(Activity activity){
        Intent intent = new Intent(activity,ForgetPwdActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}