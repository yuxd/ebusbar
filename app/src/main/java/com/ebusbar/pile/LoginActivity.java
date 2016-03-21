package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ebusbar.impl.CodeDaoImpl;
import com.ebusbar.impl.LoginDaoImpl;
import com.ebusbar.utils.ActivityControl;
import com.ebusbar.utils.CountDownUtil;
import com.ebusbar.utils.DefaultParam;
import com.ebusbar.utils.RegExpUtil;


/**
 * 登录界面，点击ActionBar上面的注册按钮直接进入注册界面
 * Created by Jelly on 2016/3/1.
 */
public class LoginActivity extends BaseActivity {
    /**
     * TAG
     */
    public String TAG = "LoginActivity";
    /**
     * 普通登录
     */
    private TextView normal_btn;
    /**
     * 快速登录
     */
    private TextView quick_login_btn;
    /**
     * 正常登录界面
     */
    private LinearLayout normal_layout;
    /**
     * 快速登录界面
     */
    private LinearLayout quick_login_layout;
    /**
     * ActionBar上面的注册按钮
     */
    private TextView reg_btn;
    /**
     * 正常登录的手机输入框
     */
    private EditText normal_phone_et;
    /**
     * 正常登录的手机输入框内容清除按钮
     */
    private ImageView normal_phone_clear;
    /**
     * 快速登录的验证码输入框
     */
    private EditText quick_code_et;
    /**
     * 快速登录的手机验证码清除按钮
     */
    private ImageView quick_code_clear;
    /**
     * 正常登录密码输入框
     */
    private EditText normal_password_et;
    /**
     * 快速登录手机输入框
     */
    private EditText quick_phone_et;
    /**
     * 获取验证码的按钮
     */
    private Button quick_code_btn;
    /**
     * 切换密码输入状态
     */
    private ImageView normal_pwd_see;
    /**
     * loginDaoImpl
     */
    private LoginDaoImpl loginDao;
    /**
     * 普通登录的消息
     */
    private int msgLogin = 0x001;
    /**
     * CodeDaoImpl
     */
    private CodeDaoImpl codeDao;

    /**
     * 获取验证码对的消息
     */
    private int msgSmsCode = 0x002;
    /**
     * 倒计时的消息
     */
    private int msgCountDown = 0x003;
    /**
     * Application
     */
    private MyApplication application;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.login);
        loadObjectAttribute(); //加载属性
        init(); //初始化控件
        setListener(); //设置监听器
    }

    @Override
    public void init() {
        normal_btn = (TextView) this.findViewById(R.id.normal_btn);
        quick_login_btn = (TextView) this.findViewById(R.id.quick_login_btn);
        normal_layout = (LinearLayout) this.findViewById(R.id.normal_layout);
        quick_login_layout = (LinearLayout) this.findViewById(R.id.quick_login_layout);
        reg_btn = (TextView) this.findViewById(R.id.reg_btn);
        normal_phone_et = (EditText) this.findViewById(R.id.normal_phone_et);
        normal_phone_clear = (ImageView) this.findViewById(R.id.normal_phone_clear);
        quick_code_et = (EditText) this.findViewById(R.id.quick_code_et);
        quick_code_clear = (ImageView) this.findViewById(R.id.quick_code_clear);
        normal_password_et = (EditText) this.findViewById(R.id.normal_pwd_et);
        quick_phone_et = (EditText) this.findViewById(R.id.quick_phone_et);
        quick_code_btn = (Button) this.findViewById(R.id.quick_code_btn);
        normal_pwd_see = (ImageView) this.findViewById(R.id.normal_pwd_see);
    }

    @Override
    public void loadObjectAttribute(){
        loginDao = new LoginDaoImpl(this,handler,msgLogin);
        codeDao = new CodeDaoImpl(this,handler,msgSmsCode);
        application = (MyApplication) getApplication();
    }

    @Override
    public void setListener() {
        setNormalBtnClickListener();
        setQuickLoginBtnClickListener();
        setRegBtnClickListener();
        setForgetPwdBtnClickListener();
        setEtClearListener();
    }

    @Override
    public void setActivityView() {

    }

    /**
     * 设置正常登录按钮的点击事件
     */
    public void setNormalBtnClickListener() {
        normal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normal_layout.setVisibility(View.VISIBLE);
                quick_login_layout.setVisibility(View.GONE);
                Resources resources = LoginActivity.this.getResources();
                normal_btn.setTextColor(resources.getColor(R.color.tab_select_color));
                quick_login_btn.setTextColor(resources.getColor(R.color.defaultTabColor));
            }
        });
    }

    /**
     * 设置手机快速登录的点击事件
     */
    public void setQuickLoginBtnClickListener() {
        quick_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quick_login_layout.setVisibility(View.VISIBLE);
                normal_layout.setVisibility(View.GONE);
                Resources resources = LoginActivity.this.getResources();
                quick_login_btn.setTextColor(resources.getColor(R.color.tab_select_color));
                normal_btn.setTextColor(resources.getColor(R.color.defaultTabColor));
            }
        });
    }

    /**
     * 设置注册按钮的点击监听事件
     */
    public void setRegBtnClickListener() {
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegActivity.startAppActivity(LoginActivity.this);
            }
        });
    }

    /**
     * 设置忘记密码的点击事件
     */
    public void setForgetPwdBtnClickListener() {
        TextView forget_pwd_btn = (TextView) this.findViewById(R.id.forget_pwd_btn);
        forget_pwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPwdActivity.startForgetPwdActivity(LoginActivity.this);
            }
        });
    }

    /**
     * 设置手机输入是出现清除输入按钮
     */
    public void setEtClearListener() {
        normal_phone_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = normal_phone_et.getText().length();
                if (length > 0) {
                    normal_phone_clear.setVisibility(View.VISIBLE);
                    normal_phone_clear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            normal_phone_et.setText(""); //情况输入框中的内容
                        }
                    });
                } else {
                    normal_phone_clear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        quick_code_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = quick_code_et.getText().length();
                if (length > 0) {
                    quick_code_clear.setVisibility(View.VISIBLE);
                    quick_code_clear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quick_code_et.setText(""); //情况输入框中的内容
                        }
                    });
                } else {
                    quick_code_clear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 正常登录
     * @param view
     * @return
     */
    public View normalLogin(View view){
        String phone = normal_phone_et.getText().toString();
        String password = normal_password_et.getText().toString();
        if(!RegExpUtil.RegPhone(phone)){
            Toast.makeText(this,"手机号码格式错误，请重新输入",Toast.LENGTH_SHORT).show();
            normal_phone_et.setText("");
            return view;
        }
        loginDao.getNetLoginDao(phone, password, null, "1");
        return view;
    }


    /**
     * 获取快速登录的验证码
     * @param view
     * @return
     */
    public View getQuickCode(View view){
        String phone = quick_phone_et.getText().toString();
        if(!RegExpUtil.RegPhone(phone)){
            Toast.makeText(this,"手机号码格式错误，请重新输入",Toast.LENGTH_SHORT).show();
            quick_phone_et.setText("");
            return view;
        }
        codeDao.getNetCodeDao(DefaultParam.LOGINCODE,phone,"-1");
        return  view;
    }

    /**
     * 快速登录
     * @param view
     * @return
     */
    public View quickLogin(View view){
        Log.v(TAG,"快速登录");
        String phone = quick_phone_et.getText().toString();
        String code = quick_code_et.getText().toString();
        if(!RegExpUtil.RegPhone(phone)){
            Toast.makeText(this,"手机号码格式错误",Toast.LENGTH_SHORT).show();
            quick_phone_et.setText("");
            return view;
        }
        loginDao.getNetLoginDao(phone, null, code,"1");
        return view;
    }

    /**
     * 开启倒计时
     */
    public void countDown(){
        CountDownUtil countDownUtil = new CountDownUtil(handler,msgCountDown, DefaultParam.COUNTDOWN);
        countDownUtil.startCounrtDown();
    }

    /**
     * 进行网络访问后需要执行的方法
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == msgLogin){
                if(loginDao.loginDao == null || TextUtils.isEmpty(loginDao.loginDao.getCrm_login().getToken())){
                    //用户名和密码错误需要执行的方法
                    Toast.makeText(LoginActivity.this,"用户名和密码有误，请重新登录",Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.v(TAG,"登录成功");
                application.setLoginDao(loginDao.loginDao);
                loginDao.cacheObject();
                ActivityControl.finishExcept(FragmentTabHostActivity.STAG);
            }else if(msg.what == msgSmsCode){ //获取验证码
                quick_code_btn.setEnabled(false);
                countDown();
                if(TextUtils.equals(codeDao.codeDao.getCrm_validation().getIsSuccess(),"N")){ //保存验证码失败
                    Toast.makeText(LoginActivity.this,"发送验证码失败，请60秒后重新获取！",Toast.LENGTH_SHORT).show();
                    return;
                }
            }else if(msg.what == msgCountDown){
                int count = (int) msg.obj;
                quick_code_btn.setText(count+"");
                if(count == 0){
                    quick_code_btn.setEnabled(true);
                    quick_code_btn.setText("获取验证码");
                }
            }
        }
    };

    /**
     * 开启LoginActivity
     */
    public static void startAppActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
