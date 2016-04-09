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
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.bean.AppointCost;
import com.ebusbar.bean.Error;
import com.ebusbar.bean.Login;
import com.ebusbar.bean.PayingAppointOrder;
import com.ebusbar.bean.PileListItem;
import com.ebusbar.handlerinterface.NetErrorHandlerListener;
import com.ebusbar.impl.AppointCostDaoImpl;
import com.ebusbar.impl.AppointDaoImpl;
import com.ebusbar.impl.CancelAppointPayDaoImpl;
import com.ebusbar.impl.OrderInfoDaoImpl;
import com.ebusbar.impl.PayingAppointOrderDaoImpl;
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
    private TextView time15;
    /**
     * 30
     */
    private TextView time30;
    /**
     * 45
     */
    private TextView time45;
    /**
     * 60
     */
    private TextView time60;
    /**
     * 充电点名称
     */
    private TextView orgName;
    /**
     * 地址
     */
    private TextView addr;
    /**
     * 电桩名称
     */
    private TextView facilityName;
    /**
     * 电桩类型
     */
    private TextView facilityType;
    /**
     * 电桩模式
     */
    private TextView facilityMode;
    /**
     * 使用
     */
    private TextView applicableCar;
    /**
     * 预约按钮
     */
    private Button appoint;
    /**
     * 选择的时间
     */
    private TextView selectTime;

    private TextView appoint_price;

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
    /**
     * AppointCostDaoImpl
     */
    private AppointCostDaoImpl appointCostDao;
    /**
     * 获取预约金额的消息
     */
    private final int msgAppointCost = 0x006;
    /**
     * PayingAppointOrderDaoImpl
     */
    private PayingAppointOrderDaoImpl payingAppointOrderDao;
    /**
     * 生成待支付的预约订单
     */
    private final int msgPayingAppoint = 0x007;
    /**
     * 支付请求
     */
    public static final int PAYREQUEST = 0x008;
    /**
     * 取消预约支付
     */
    private CancelAppointPayDaoImpl cancelAppointPayDao;
    /**
     * 取消预约支付
     */
    private final int msgCancelAppointPay = 0x009;

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
        orgName = (TextView) this.findViewById(R.id.orgName);
        addr = (TextView) this.findViewById(R.id.addr);
        facilityName = (TextView) this.findViewById(R.id.facilityName);
        facilityType = (TextView) this.findViewById(R.id.facilityType);
        facilityMode = (TextView) this.findViewById(R.id.facilityMode);
        applicableCar = (TextView) this.findViewById(R.id.applicableCar);
        appoint = (Button) this.findViewById(R.id.appoint);
        appoint_price = (TextView) this.findViewById(R.id.appoint_price);
    }

    @Override
    public void loadObjectAttribute() {
        intent = getIntent();
        application = (MyApplication) getApplication();
        appointDao = new AppointDaoImpl(this,handler,msgAppoint);
        orderInfoDao = new OrderInfoDaoImpl(this,handler,msgInfo);
        appointCostDao = new AppointCostDaoImpl(this,handler,msgAppointCost);
        payingAppointOrderDao = new PayingAppointOrderDaoImpl(this,handler,msgPayingAppoint);
        cancelAppointPayDao = new CancelAppointPayDaoImpl(this,handler,msgCancelAppointPay);
    }

    @Override
    public void setListener() {
        setTimeBtnListener();
    }

    @Override
    public void setActivityView() {
        PileListItem.EvcFacilitiesGetEntity entity = intent.getParcelableExtra("Entity");
        orgName.setText(entity.getOrgName());
        addr.setText(intent.getStringExtra("Addr"));
        facilityName.setText(entity.getFacilityName());
        if(TextUtils.equals("1",entity.getFacilityType())){
            facilityType.setText("直流桩");
        }else if(TextUtils.equals("0",entity.getFacilityType())){
            facilityType.setText("交流桩");
        }
        applicableCar.setText(entity.getApplicableCar());
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
        Login.DataEntity entity = application.getLoginDao().getData();
        TextView time = (TextView) v;
        //暂时不设定预约金额
        if(time == time15){
            selectTime(time);
        }else if(time == time30){
            selectTime(time);
        }else if(time == time45){
            selectTime(time);
        }else if(time == time60){
            selectTime(time);
        }
        appoint.setEnabled(false);
        appointCostDao.getDao(entity.getToken(),entity.getCustID(),selectTime.getText().toString().replace("分钟", ""));
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
        Login.DataEntity entity = application.getLoginDao().getData();
        PileListItem.EvcFacilitiesGetEntity entity1 = intent.getParcelableExtra("Entity");
        payingAppointOrderDao.getDao(entity.getToken(),entity.getCustID(),entity1.getFacilityID(),selectTime.getText().toString().replace("分钟", ""),appoint_price.getText().toString().replace("¥", ""));
        appoint.setEnabled(false);
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgAppoint:
                    if(TextUtils.equals(appointDao.dao.getIsSuccess(),"N")){
                        Error errorDao = errorParamUtil.checkReturnState(appointDao.dao.getReturnStatus());
                        toastUtil.toastError(context,errorDao,null);
                        setResult(FAILURE);
                        ActivityControl.finishAct(AppointActivity.this);
                        return;
                    }
                    loading = popupWindowUtil.startLoading(AppointActivity.this,appoint,"预约中");
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
                    Login.DataEntity entity = application.getLoginDao().getData();
                    orderInfoDao.getOrderInfoDaoImpl(entity.getToken(), appointDao.dao.getData().getOrderNo(), entity.getCustID());
                    break;
                case msgInfo:
                    if(orderInfoDao.orderInfoDao == null || TextUtils.equals(orderInfoDao.orderInfoDao.getEvc_order_get().getIsSuccess(),"N")){
                        Error errorDao = errorParamUtil.checkReturnState(orderInfoDao.orderInfoDao.getEvc_order_get().getReturnStatus());
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
                case msgAppointCost: //获取预约金额
                    if(TextUtils.equals(appointCostDao.dao.getIsSuccess(),"N")){
                        Error errorDao = errorParamUtil.checkReturnState(appointCostDao.dao.getReturnStatus());
                        toastUtil.toastError(context,errorDao,null);
                        return;
                    }
                    AppointCost.DataEntity dataEntity = appointCostDao.dao.getData();
                    appoint_price.setText("¥" + dataEntity.getCost());
                    appoint.setEnabled(true);
                    break;
                case msgPayingAppoint:
                    if(TextUtils.equals(payingAppointOrderDao.dao.getIsSuccess(),"N")){
                        Error errorDao = errorParamUtil.checkReturnState(payingAppointOrderDao.dao.getReturnStatus());
                        toastUtil.toastError(context,errorDao,null);
                        appoint.setEnabled(true);
                        return;
                    }
                    PayingAppointOrder.DataEntity dataEntity1 = payingAppointOrderDao.dao.getData();
                    PayActivity.startPayActivity(context,dataEntity1.getOrderNo(),PayActivity.CHARGE,PAYREQUEST);
                    break;
                case msgCancelAppointPay:
                    if(TextUtils.equals(cancelAppointPayDao.dao.getIsSuccess(), "N")){
                        Error errorDao = errorParamUtil.checkReturnState(cancelAppointPayDao.dao.getReturnStatus());
                        toastUtil.toastError(context,errorDao,null);
                        return;
                    }
                    Toast.makeText(AppointActivity.this,"成功取消预约支付",Toast.LENGTH_SHORT).show();
                    ActivityControl.finishAct(AppointActivity.this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PAYREQUEST:
                Login.DataEntity entity = application.getLoginDao().getData();
                if(resultCode == PayActivity.SUCCESS){ //支付成功
                    Toast.makeText(context,"付款成功,正在预约中，请不要退出界面",Toast.LENGTH_SHORT).show();
                    appointDao.getAppointDao(entity.getToken(),data.getStringExtra("OrderNo"), entity.getCustID());
                }else if(resultCode == PayActivity.FAILURE){ //支付失败
                    cancelAppointPayDao.getDao(entity.getToken(),entity.getCustID(),data.getStringExtra("OrderNo"));
                }
                break;

        }
    }

    /**
     * 启动界面
     */
    public static void startAppActivity(Context context, PileListItem.EvcFacilitiesGetEntity entity,String addr,int requestCode){
        Intent intent = new Intent(context,AppointActivity.class);
        intent.putExtra("Entity",entity);
        intent.putExtra("Addr",addr);
        Activity activity = (Activity) context;
        activity.startActivityForResult(intent,requestCode);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
