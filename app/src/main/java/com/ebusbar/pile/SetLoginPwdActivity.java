package com.ebusbar.pile;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.impl.ResetLoginPwdDaoImpl;

/**
 * 设置密码
 * Created by Jelly on 2016/3/1.
 */
public class SetLoginPwdActivity extends UtilActivity {
    /**
     * TAG
     */
    public String TAG = "SetLoginPwdActivity";
    /**
     * 密码输入框1
     */
    private EditText set_pwd_et01;
    /**
     * 密码输入框2
     */
    private EditText set_pwd_et02;
    /**
     * 清空密码输入框1的按钮
     */
    private ImageView set_pwd_et01_clear;
    /**
     * 清空密码输入框2的按钮
     */
    private ImageView set_pwd_et02_clear;
    /**
     * SetPasswordDaoImpl
     */
    private ResetLoginPwdDaoImpl resetLoginPwdDao;
    /**
     * 设置密码消息
     */
    private final int msgSetPwd=0x001;
    /**
     * 需要修改密码的手机号
     */
    private String phone;
    /**
     * 验证码
     */
    private String code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.set_pwd);
        loadObjectAttribute();
        init();
        setListener();
    }

    @Override
    public void loadObjectAttribute() {
        resetLoginPwdDao = new ResetLoginPwdDaoImpl(this,handler,msgSetPwd);
        phone = getIntent().getStringExtra("phone");
        code = getIntent().getStringExtra("code");
    }

    @Override
    public void init(){
        set_pwd_et01 = (EditText) this.findViewById(R.id.set_pwd_et01);
        set_pwd_et02 = (EditText) this.findViewById(R.id.set_pwd_et02);
        set_pwd_et01_clear = (ImageView) this.findViewById(R.id.set_pwd_et01_clear);
        set_pwd_et02_clear = (ImageView) this.findViewById(R.id.set_pwd_et02_clear);
    }


    @Override
    public void setListener() {
        setEtClearListener();
    }

    @Override
    public void setActivityView() {

    }

    /**
     * 设置输入框的清空按钮监听事件
     */
    public void setEtClearListener(){
        set_pwd_et01.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = set_pwd_et01.getText().length();
                if (length > 0) {
                    set_pwd_et01_clear.setVisibility(View.VISIBLE);
                    set_pwd_et01_clear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            set_pwd_et01.setText("");
                        }
                    });
                } else {
                    set_pwd_et01_clear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        set_pwd_et02.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = set_pwd_et02.getText().length();
                if (length > 0) {
                    set_pwd_et02_clear.setVisibility(View.VISIBLE);
                    set_pwd_et02_clear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            set_pwd_et02.setText("");
                        }
                    });
                } else {
                    set_pwd_et02_clear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    /**
     * 设置密码
     * @param view
     * @return
     */
    public View setPassword(View view){
        String password1 = set_pwd_et01.getText().toString();
        String password2 = set_pwd_et02.getText().toString();
        if(TextUtils.equals(password1,password2)){
            Log.v(TAG,"两次输入的密码有错误");
        }
        resetLoginPwdDao.getData(phone,code,password1);
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgSetPwd:
                    if(TextUtils.equals("N",resetLoginPwdDao.bean.getIsSuccess())){
                        com.ebusbar.bean.Error error = errorParamUtil.checkReturnState(resetLoginPwdDao.bean.getReturnStatus());
                        toastUtil.toastError(context,error,null);
                        return;
                    }
                    Toast.makeText(SetLoginPwdActivity.this,"重置密码成功",Toast.LENGTH_SHORT).show();
                    LoginActivity.startAppActivity(context);
                    break;
            }
        }
    };

    /**
     * 打开SetPwdActivity
     * @param context
     */
    public static void startSetPwdActivity(Context context,String phone,String code){
        Intent intent = new Intent(context,SetLoginPwdActivity.class);
        intent.putExtra("phone",phone);
        intent.putExtra("code",code);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
