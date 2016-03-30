package com.ebusbar.pile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.dao.ErrorDao;
import com.ebusbar.dao.LoginDao;
import com.ebusbar.handlerinterface.NetErrorHandlerListener;
import com.ebusbar.impl.AppointDaoImpl;
import com.ebusbar.impl.OrderInfoDaoImpl;
import com.ebusbar.utils.ActivityControl;
import com.jellycai.service.ThreadManage;

/**
 * 预约界面
 * Created by Jelly on 2016/3/9.
 */
public class AppointActivity extends UtilActivity implements View.OnClickListener , NetErrorHandlerListener{
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
     * 预约
     */
    TextView appoint;
    /**
     * AppointDaoImpl
     */
    private AppointDaoImpl appointDao;
    /**
     * 预约消息
     */
    private final int msgAppoint = 0x001;
    /**
     * 预约进度条
     */
    private PopupWindow loading;
    /**
     * OrderInfoDaoImpl
     */
    private OrderInfoDaoImpl orderInfoDao;
    /**
     * 获取详情消息
     */
    private final int msgInfo = 0x002;
    /**
     * 加载进度条
     */
    private final int msgLoading = 0x003;
    /**
     * 是否失去焦点,用于用户在退出界面之后的，PopupWindow不能正确结束
     */
    private boolean isPause = false;
    /**
     * 是否是第二次请求
     */
    private boolean isSecond = false;
    /**
     * 是否已经预约成功
     */
    private boolean isSuccess = false;
    /**
     * 成功
     */
    public static final int SUCCESS = 0x004;
    /**
     * 失败
     */
    public static final int FAILURE = 0x005;

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
        appoint = (TextView) this.findViewById(R.id.appoint);
    }

    @Override
    public void loadObjectAttribute() {
        intent = getIntent();
        application = (MyApplication) getApplication();
        appointDao = new AppointDaoImpl(this,handler,msgAppoint);
        orderInfoDao = new OrderInfoDaoImpl(this,handler,msgInfo);
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

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    /**
     * 设置时间选择
     */
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
        time.setBackgroundResource(R.drawable.actionbar_color_btn_bg);
        if(selectTime != null && selectTime != time ) {
            selectTime.setBackgroundResource(R.drawable.gray_color_btn_bg);
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
        LoginDao.CrmLoginEntity entity = application.getLoginDao().getCrm_login();
        appointDao.getAppointDao(intent.getStringExtra("FacilityID"), entity.getToken(), selectTime.getText().toString().replace("分钟", ""), entity.getCustID());
        appoint.setEnabled(false);
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgAppoint:
                    if(appointDao.appointDao == null || TextUtils.equals(appointDao.appointDao.getEvc_order_set().getIsSuccess(),"N")){
                        ErrorDao errorDao = errorParamUtil.checkReturnState(appointDao.appointDao.getEvc_order_set().getReturnStatus());
                        toastUtil.toastError(context,errorDao,null);
                        setResult(FAILURE);
                        ActivityControl.finishAct(AppointActivity.this);
                        return;
                    }
                    loading = popupWindowUtil.startLoading(AppointActivity.this,appoint,"正在排队");
                    ThreadManage.start(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(5000);
                                handler.sendEmptyMessage(msgLoading);
                                Thread.sleep(5000);
                                if(!isSuccess) {
                                    isSecond = true;
                                    handler.sendEmptyMessage(msgLoading);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
                case msgLoading:
                    LoginDao.CrmLoginEntity entity = application.getLoginDao().getCrm_login();
                    orderInfoDao.getOrderInfoDaoImpl(entity.getToken(), appointDao.appointDao.getEvc_order_set().getOrderNo(), entity.getCustID());
                    break;
                case msgInfo:
                    if(orderInfoDao.orderInfoDao == null || TextUtils.equals(orderInfoDao.orderInfoDao.getEvc_order_get().getIsSuccess(),"N")){
                        ErrorDao errorDao = errorParamUtil.checkReturnState(orderInfoDao.orderInfoDao.getEvc_order_get().getReturnStatus());
                        toastUtil.toastError(context,errorDao,null);
                        return;
                    }
                    if(TextUtils.equals(orderInfoDao.orderInfoDao.getEvc_order_get().getOrderStatus(), "1")){
                        isSuccess = true;
                        Toast.makeText(AppointActivity.this,"预约成功，请进入我的预约界面查看预约结果！",Toast.LENGTH_SHORT).show();
                        setResult(SUCCESS);
                        appointSuccess();
                    }else{
                        if(!isSecond){
                            return;
                        }
                        isSecond = false;
                        setResult(FAILURE);
                        appointSuccess();
                    }
                    break;
            }
        }
    };

    @Override
    public void handlerError(String returnState) {

    }

    /**
     * 预约充电成功，结束进度条和当前界面
     */
    public void appointSuccess(){
        ActivityControl.finishAct(AppointActivity.this); //杀掉当前界面
        if(loading.isShowing() && !isPause){
            loading.dismiss();
        }
    }

    /**
     * 启动界面
     */
    public static void startAppActivity(Context context,String OrgName,String FacilityID,String FacilityName,int requestCode){
        Intent intent = new Intent(context,AppointActivity.class);
        intent.putExtra("OrgName",OrgName);
        intent.putExtra("FacilityID",FacilityID);
        intent.putExtra("FacilityName", FacilityName);
        Activity activity = (Activity) context;
        activity.startActivityForResult(intent,requestCode);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
