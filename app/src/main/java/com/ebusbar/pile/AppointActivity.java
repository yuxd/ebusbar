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
import android.widget.TextView;

import com.ebusbar.impl.AppointDaoImpl;
import com.ebusbar.impl.FreeEPDaoImpl;
import com.ebusbar.impl.PayAppointDaoImpl;
import com.ebusbar.utils.ActivityControl;

/**
 * 预约界面
 * Created by Jelly on 2016/3/9.
 */
public class AppointActivity extends BaseActivity implements View.OnClickListener{
    /**
     * TAG
     */
    public String TAG = "AppointActivity";
    /**
     * 从上一个界面传过来的参数
     */
    private Intent intent;
    /**
     * 15
     */
    TextView time15;
    /**
     * 30
     */
    TextView time30;
    /**
     * 45
     */
    TextView time45;
    /**
     * 60
     */
    TextView time60;
    /**
     * 预约价格
     */
    TextView appoint_price;
    /**
     * 充电点
     */
    TextView position_text;
    /**
     * 充电桩
     */
    TextView EPid_text;
    /**
     * FreeEPDaoImpl
     */
    private FreeEPDaoImpl freeEPDao;
    /**
     * 请求空闲电桩的访问消息
     */
    private int msgFreeEP = 0x001;
    /**
     *
     */
    private PayAppointDaoImpl payAppointDao;
    /**
     *
     */
    private int msgPay = 0x002;
    /**
     * AppointDaoImpl
     */
    private AppointDaoImpl appointDao;
    /**
     * 预约消息
     */
    private int msgAppoint = 0x003;
    /**
     *
     */
    private MyApplication application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.appoint);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {
        time15 = (TextView) this.findViewById(R.id.time15);
        time30 = (TextView) this.findViewById(R.id.time30);
        time45 = (TextView) this.findViewById(R.id.time45);
        time60 = (TextView) this.findViewById(R.id.time60);
        appoint_price = (TextView) this.findViewById(R.id.appoint_price);
        position_text = (TextView) this.findViewById(R.id.position_text);
        EPid_text = (TextView) this.findViewById(R.id.EPid_text);
    }

    @Override
    public void loadObjectAttribute() {
        intent = getIntent();
        freeEPDao = new FreeEPDaoImpl(this,handler,msgFreeEP);
        payAppointDao = new PayAppointDaoImpl(this,handler,msgPay);
        application = (MyApplication) getApplication();
        appointDao = new AppointDaoImpl(this,handler,msgAppoint);
    }

    @Override
    public void setListener() {
        setTimeBtnListener();
    }

    @Override
    public void setActivityView() {
    }


    public void setTimeBtnListener(){
        time15.setOnClickListener(this);
        time30.setOnClickListener(this);
        time45.setOnClickListener(this);
        time60.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TextView time = (TextView) v;
        //暂时不设定预约金额
        if(time == time15){
            time15.setBackgroundResource(R.color.time_select_color);
            time30.setBackgroundResource(R.color.time_noselect_color);
            time45.setBackgroundResource(R.color.time_noselect_color);
            time60.setBackgroundResource(R.color.time_noselect_color);
            //appoint_price.setText("¥0.8");
        }else if(time == time30){
            time15.setBackgroundResource(R.color.time_noselect_color);
            time30.setBackgroundResource(R.color.time_select_color);
            time45.setBackgroundResource(R.color.time_noselect_color);
            time60.setBackgroundResource(R.color.time_noselect_color);
            //appoint_price.setText("¥1.2");
        }else if(time == time45){
            time15.setBackgroundResource(R.color.time_noselect_color);
            time30.setBackgroundResource(R.color.time_noselect_color);
            time45.setBackgroundResource(R.color.time_select_color);
            time60.setBackgroundResource(R.color.time_noselect_color);
            //appoint_price.setText("¥1.5");
        }else if(time == time60){
            time15.setBackgroundResource(R.color.time_noselect_color);
            time30.setBackgroundResource(R.color.time_noselect_color);
            time45.setBackgroundResource(R.color.time_noselect_color);
            time60.setBackgroundResource(R.color.time_select_color);
            //appoint_price.setText("¥2.0");
        }
    }

    /**
     * 点击预约
     */
    public View appoint(View view){
        //payAppointDao.getNetPayAppointDao(application.getLoginDao().getUid()+"",application.getLoginDao().getToken(),appoint_price.getText().toString());
        finish();
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == msgFreeEP){
                if(freeEPDao.freeEPDao == null || !TextUtils.equals(freeEPDao.freeEPDao.getMessage(), "1")){
                    return;
                }
                EPid_text.setText(freeEPDao.freeEPDao.getEPId());
            }else if(msg.what == msgPay){
                if(payAppointDao.payAppointDao == null || !TextUtils.equals(payAppointDao.payAppointDao.getMessage(),"1")){
                    return;
                }
                Log.v(TAG,"开始预约");
                appointDao.getNetAppointDao(freeEPDao.freeEPDao.getEPId(),application.getLoginDao().getCrm_login().getCustID(),payAppointDao.payAppointDao.getPayId(),application.getLoginDao().getCrm_login().getToken());
            }else if(msg.what == msgAppoint){
                if(appointDao.appointDao == null || !TextUtils.equals(appointDao.appointDao.getMessage(),"1")){
                    return;
                }
                ActivityControl.finishAct(AppointActivity.this); //杀掉当前界面
                NaviEmulatorActivity.startAppActivity(AppointActivity.this,intent.getDoubleExtra("startlat",0),intent.getDoubleExtra("startlong",0),intent.getDoubleExtra("endlat",0),intent.getDoubleExtra("endlong",0));
            }
        }
    };

    /**
     * 启动界面
     */
    public static void startAppActivity(Context context){
        Intent intent = new Intent(context,AppointActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
