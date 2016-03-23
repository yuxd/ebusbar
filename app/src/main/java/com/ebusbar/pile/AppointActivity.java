package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ebusbar.dao.LoginDao;
import com.ebusbar.impl.AppointDaoImpl;
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
     * 当前选择的时间
     */
    TextView selectTime;
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
     * 电桩名称
     */
    TextView name;
    /**
     * AppointDaoImpl
     */
    private AppointDaoImpl appointDao;
    /**
     * 预约消息
     */
    private int msgAppoint = 0x001;
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
        name = (TextView) this.findViewById(R.id.name);
    }

    @Override
    public void loadObjectAttribute() {
        intent = getIntent();
        application = (MyApplication) getApplication();
        appointDao = new AppointDaoImpl(this,handler,msgAppoint);
    }

    @Override
    public void setListener() {
        setTimeBtnListener();
    }

    @Override
    public void setActivityView() {
        position_text.setText(intent.getStringExtra("OrgName"));
        EPid_text.setText(intent.getStringExtra("FacilityID"));
        name.setText(intent.getStringExtra("FacilityName").replace("号充电桩", ""));
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
            selectTime(time);
            //appoint_price.setText("¥0.8");
        }else if(time == time30){
            selectTime(time);
            //appoint_price.setText("¥1.2");
        }else if(time == time45){
            selectTime(time);
            //appoint_price.setText("¥1.5");
        }else if(time == time60){
            selectTime(time);
            //appoint_price.setText("¥2.0");
        }
    }


    /**
     * 选择时间
     * @param time
     */
    public void selectTime(TextView time){
        time.setBackgroundResource(R.color.time_select_color);
        if(selectTime != null) {
            selectTime.setBackgroundResource(R.color.time_noselect_color);
        }
        selectTime = time;
    }

    /**
     * 点击预约
     */
    public View appoint(View view){
        if(selectTime == null){
            Toast.makeText(this,"请选择预约时间",Toast.LENGTH_SHORT).show();
            return view;
        }
        LoginDao.CrmLoginEntity data= application.getLoginDao().getCrm_login();
        appointDao.getAppointDao(intent.getStringExtra("FacilityID"), data.getToken(), selectTime.getText().toString().replace("分钟", ""), data.getCustID());
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == msgAppoint){
                if(appointDao.appointDao == null || TextUtils.equals(appointDao.appointDao.getEvc_order_set().getIsSuccess(),"N")){
                    Toast.makeText(AppointActivity.this,"预约失败，请重新预约",Toast.LENGTH_SHORT).show();
                    ActivityControl.finishAct(AppointActivity.this);
                    return;
                }
                Toast.makeText(AppointActivity.this,"预约成功，请进入我的预约界面查看预约结果！",Toast.LENGTH_SHORT).show();
                ActivityControl.finishAct(AppointActivity.this); //杀掉当前界面
//                NaviEmulatorActivity.startAppActivity(AppointActivity.this,intent.getDoubleExtra("startlat",0),intent.getDoubleExtra("startlong",0),intent.getDoubleExtra("endlat",0),intent.getDoubleExtra("endlong",0));
            }
        }
    };

    /**
     * 启动界面
     */
    public static void startAppActivity(Context context,String OrgName,String FacilityID,String FacilityName){
        Intent intent = new Intent(context,AppointActivity.class);
        intent.putExtra("OrgName",OrgName);
        intent.putExtra("FacilityID",FacilityID);
        intent.putExtra("FacilityName",FacilityName);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
