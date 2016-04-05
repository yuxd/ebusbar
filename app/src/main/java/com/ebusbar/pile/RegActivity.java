package com.ebusbar.pile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.dao.ErrorDao;
import com.ebusbar.impl.CodeDaoImpl;
import com.ebusbar.impl.RegUserDaoImpl;
import com.ebusbar.utils.ActivityControl;
import com.ebusbar.utils.CountDownUtil;
import com.ebusbar.param.DefaultParam;
import com.ebusbar.utils.RegExpUtil;

/**
 * 注册界面
 * Created by Jelly on 2016/3/1.
 */
public class RegActivity extends UtilActivity {
    /**
     * TAG
     */
    public String TAG="RegActivity";
    /**
     * 注册界面验证码输入框
     */
    private EditText reg_code_et;
    /**
     * 注册界面验证码输入框内容清除按钮
     */
    private ImageView reg_code_clear;
    /**
     * 注册界面手机号码输入框
     */
    private EditText reg_phone_et;
    /**
     * 获取注册验证码
     */
    private Button reg_code_btn;
    /**
     * 密码输入框
     */
    private EditText reg_pwd_et;
    /**
     * CodeDaoImpl
     */
    private CodeDaoImpl codeDao;
    /**
     * regUserDaoImpl
     */
    private RegUserDaoImpl regUserDao;
    /**
     * 获取验证码的消息
     */
    private int msgCode=0x001;
    /**
     * 倒计时
     */
    private int msgCountDown=0x002;
    /**
     * 注册
     */
    private int msgReg=0x003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.reg);
        loadObjectAttribute();
        init();
        setListener();
    }

    @Override
    public void loadObjectAttribute() {
        regUserDao = new RegUserDaoImpl(this,handler,msgReg);
        codeDao = new CodeDaoImpl(this,handler,msgCode);
    }

    @Override
    public void init() {
        reg_pwd_et = (EditText) this.findViewById(R.id.reg_pwd_et);
        reg_code_et = (EditText) this.findViewById(R.id.reg_code_et);
        reg_code_btn = (Button) this.findViewById(R.id.reg_code_btn);
        reg_phone_et = (EditText) this.findViewById(R.id.reg_phone_et);
        reg_code_et = (EditText) this.findViewById(R.id.reg_code_et);
        reg_code_clear = (ImageView) this.findViewById(R.id.reg_code_clear);
    }

    @Override
    public void setListener() {
        setBackBtnClickListener();
        setLoginBtnClickListener();
        setEtClearListener();
    }

    @Override
    public void setActivityView() {

    }

    /**
     * 设置返回按钮的点击监听事件
     */
    public void setBackBtnClickListener() {
        ImageView back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityControl.finishAct(RegActivity.this);
            }
        });
    }

    /**
     * 设置登录按钮的点击监听事件
     */
    public void setLoginBtnClickListener() {
        TextView login_btn = (TextView) this.findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.startAppActivity(RegActivity.this);
            }
        });
    }

    /**
     * 设置输入框清除按钮的监听事件
     */
    public void setEtClearListener(){
        reg_code_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = reg_code_et.getText().length();
                if (length > 0) {
                    reg_code_clear.setVisibility(View.VISIBLE);
                    reg_code_clear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reg_code_et.setText("");
                        }
                    });
                } else {
                    reg_code_clear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 获得注册验证码
     */
    public View getRegCode(View view){
        String phone = reg_phone_et.getText().toString();
        if(!RegExpUtil.regPhone(phone)){
            Log.v(TAG,"手机号码错误");
            Toast.makeText(this,"手机号码格式错误",Toast.LENGTH_SHORT).show();
            return view;
        }
        codeDao.getNetCodeDao(DefaultParam.REGCODE, phone);
        return view;
    }

    /**
     * 注册
     * @param view
     * @return
     */
    public View reg(View view){
        String phone = reg_phone_et.getText().toString();
        String password = reg_pwd_et.getText().toString();
        String code = reg_code_et.getText().toString();
        if(!RegExpUtil.regPhone(phone)){
            Toast.makeText(this,"手机号码格式错误",Toast.LENGTH_SHORT).show();
            reg_phone_et.setText("");
            return view;
        }
        if(TextUtils.isEmpty(phone) || TextUtils.isEmpty(password) || TextUtils.isEmpty(code)) {
            return view;
        }
        regUserDao.getNetRegUserDao(phone, password, code);
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
            if(msg.what == msgCode){ //获取验证码
                reg_code_btn.setEnabled(false);
                countDown();
                if(TextUtils.equals(codeDao.codeDao.getCrm_validation().getIsSuccess(),"N")){ //保存验证码失败
                    Toast.makeText(RegActivity.this,"发送验证码失败，请60秒后重新获取！",Toast.LENGTH_SHORT).show();
                    return;
                }
            }else if(msg.what == msgCountDown){ //验证码倒计时结束
                int count = (int) msg.obj;
                reg_code_btn.setText(count+"");
                if(count == 0){
                    reg_code_btn.setEnabled(true);
                    reg_code_btn.setText("获取验证码");
                }
            }else if(msg.what == msgReg){ //注册
                if(TextUtils.equals(regUserDao.regUserDao.getCrm_register().getIsSuccess(), "N")){
                    ErrorDao errorDao = errorParamUtil.checkReturnState(regUserDao.regUserDao.getCrm_register().getReturnStatus());
                    toastUtil.toastError(context,errorDao,null);
                    return;
                }
                ActivityControl.finishAct(RegActivity.this);
            }
        }
    };

    /**
     * 打开注册界面
     */
    public static void startAppActivity(Activity activity) {
        Intent intent = new Intent(activity, RegActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
