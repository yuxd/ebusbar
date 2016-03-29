package com.ebusbar.pile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebusbar.dao.LoginDao;
import com.ebusbar.impl.LoginDaoImpl;
import com.ebusbar.impl.SetPayPasswordDaoImpl;
import com.ebusbar.utils.ActivityControl;
import com.ebusbar.utils.DefaultParam;

/**
 * 设置支付密码
 * Created by Jelly on 2016/3/14.
 */
public class SetPayPwdActivity extends BaseActivity implements View.OnClickListener {
    /**
     * TAG
     */
    public String TAG = "SetPayPwdActivity";
    /**
     * 数字0
     */
    private Button num0;
    /**
     * 数字1
     */
    private Button num1;
    /**
     * 数字2
     */
    private Button num2;
    /**
     * 数字3
     */
    private Button num3;
    /**
     * 数字4
     */
    private Button num4;
    /**
     * 数字5
     */
    private Button num5;
    /**
     * 数字6
     */
    private Button num6;
    /**
     * 数字7
     */
    private Button num7;
    /**
     * 数字8
     */
    private Button num8;
    /**
     * 数字9
     */
    private Button num9;
    /**
     * 删除
     */
    private Button delete_btn;
    /**
     * 取消
     */
    Button cancel_btn;
    /**
     * 密码输入框
     */
    private ImageView[] input_ets;
    /**
     * 第一次输入的密码
     */
    private String payPassword1;
    /**
     * 支付密码
     */
    private String payPassword = "";
    /**
     * 提示
     */
    private TextView setpaypwd_hint;
    /**
     * 结果码,设置支付密码成功
     */
    public static int SUCCESS = 0x001;
    /**
     * 结果码，设置支付密码失败
     */
    public static int FAILURE = 0x002;
    /**
     * SetPayPasswordDaoImpl
     */
    private SetPayPasswordDaoImpl setPayPasswordDao;
    /**
     * 设置密码消息
     */
    private final int msgSet = 0x003;

    /**
     * Application
     */
    private MyApplication application;
    /**
     * LoginDaoImpl
     */
    private LoginDaoImpl loginDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.setpaypwd);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {
        setpaypwd_hint = (TextView) this.findViewById(R.id.setpaypwd_hint);
        num0 = (Button) this.findViewById(R.id.num0);
        num1 = (Button) this.findViewById(R.id.num1);
        num2 = (Button) this.findViewById(R.id.num2);
        num3 = (Button) this.findViewById(R.id.num3);
        num4 = (Button) this.findViewById(R.id.num4);
        num5 = (Button) this.findViewById(R.id.num5);
        num6 = (Button) this.findViewById(R.id.num6);
        num7 = (Button) this.findViewById(R.id.num7);
        num8 = (Button) this.findViewById(R.id.num8);
        num9 = (Button) this.findViewById(R.id.num9);
        delete_btn = (Button) this.findViewById(R.id.delete_btn);
        cancel_btn = (Button) this.findViewById(R.id.cancel_btn);
        input_ets = new ImageView[DefaultParam.PAYPASSWOEDSUM];
        input_ets[0] = (ImageView) this.findViewById(R.id.input_et0);
        input_ets[1] = (ImageView) this.findViewById(R.id.input_et1);
        input_ets[2] = (ImageView) this.findViewById(R.id.input_et2);
        input_ets[3] = (ImageView) this.findViewById(R.id.input_et3);
        input_ets[4] = (ImageView) this.findViewById(R.id.input_et4);
        input_ets[5] = (ImageView) this.findViewById(R.id.input_et5);
    }

    @Override
    public void loadObjectAttribute() {
        setPayPasswordDao = new SetPayPasswordDaoImpl(this,handler,msgSet);
        application = (MyApplication) getApplication();
        loginDao = new LoginDaoImpl(this);
    }

    @Override
    public void setListener() {
        setNumClickListener();
    }

    @Override
    public void setActivityView() {

    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        switch (v.getId()) {
            case R.id.cancel_btn: //重新输入支付密码
                payPassword1 = "";
                payPassword = "";
                setpaypwd_hint.setText("请输入支付密码");
                reInputEt();
                break;
            case R.id.delete_btn: //删除一位支付密码的方法
                if (payPassword.length() == 0) { //已经没有支付密码了，不能再减少了
                    return;
                }
                payPassword = payPassword.substring(0, payPassword.length() - 1);
                reInputEt();
                break;
            default:
                if (payPassword.length() == 6) { //如果支付密码已经有6位，不能继续输入
                    return;
                }
                payPassword += btn.getText();
                reInputEt();
                if (payPassword.length() == 6) { //如果是最后一位输入6位，直接支付
                    Log.v(TAG, "支付");
                }
        }
    }

    /**
     * 设置数字键盘的监听
     */
    public void setNumClickListener() {
        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
    }

    /**
     * 确定按钮
     * @param view
     * @return
     */
    public View set(View view){
        if(TextUtils.isEmpty(payPassword) || payPassword.length() != DefaultParam.PAYPASSWOEDSUM) {
            Toast.makeText(this, R.string.input_paypassword_hint,Toast.LENGTH_SHORT).show(); //提示用户请输入6位数的支付密码
            return view;
        }
        if(TextUtils.isEmpty(payPassword1)){ //第一次输入
            payPassword1 = payPassword;
            payPassword = "";
            setpaypwd_hint.setText("请再次输入支付密码");
            reInputEt();
        }else if(!TextUtils.equals(payPassword,payPassword1)){ //第二次输入和第一次输入的密码不同
            Toast.makeText(this,"请和第一次输入的密码相同或点击取消按钮重新输入!",Toast.LENGTH_SHORT).show();
            payPassword = "";
            reInputEt();
        }else{ //确认支付密码
            LoginDao.CrmLoginEntity entity = application.getLoginDao().getCrm_login();
            setPayPasswordDao.getSetPasswordDao(entity.getToken(),payPassword,entity.getCustID());
        }
        return view;
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgSet:
                    if(setPayPasswordDao.setPayPasswordDao == null || TextUtils.equals(setPayPasswordDao.setPayPasswordDao.getCrm_paypassword_set().getIsSuccess(),"N")){
                        setResult(FAILURE);
                        return;
                    }
                    application.getLoginDao().getCrm_login().setExistsPayPassword("1");
                    application.cacheLogin();
                    setResult(SUCCESS);
                    Toast.makeText(SetPayPwdActivity.this,"支付密码设置成功",Toast.LENGTH_SHORT).show();
                    ActivityControl.finishAct(SetPayPwdActivity.this);
                    break;
            }
        }
    };

    /**
     * 更改密码框状态
     */
    public void reInputEt(){
        int index = payPassword.length();
        for(int i=0;i<index;i++){
            input_ets[i].setImageResource(R.drawable.paypassword_input_white);
        }
        for(int i=index;i< DefaultParam.PAYPASSWOEDSUM;i++){
            input_ets[i].setImageResource(R.drawable.paypassword_noinput_white);
        }
    }

    /**
     * 开启设置支付密码界面,设置请求码
     */
    public static void startAppActivity(Context context,int requestCode){
        Intent intent = new Intent(context,SetPayPwdActivity.class);
        Activity activity = (Activity) context;
        activity.startActivityForResult(intent,requestCode);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
