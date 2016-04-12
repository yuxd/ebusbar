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
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.bean.Error;
import com.ebusbar.bean.Login;
import com.ebusbar.bean.OrderInfo;
import com.ebusbar.bean.PendingOrder;
import com.ebusbar.bean.PileInfo;
import com.ebusbar.bean.StartCharge;
import com.ebusbar.handlerinterface.NetErrorHandlerListener;
import com.ebusbar.impl.ChargeOrderDaoImpl;
import com.ebusbar.impl.FinishChargeDaoImpl;
import com.ebusbar.impl.OrderInfoDaoImpl;
import com.ebusbar.impl.PileInfoDaoImpl;
import com.ebusbar.utils.ActivityControl;
import com.ebusbar.utils.DoubleUtil;
import com.jellycai.service.ThreadManage;


/**
 * 充电界面
 * Created by Jelly on 2016/3/9.
 */
public class ChargeActivity extends UtilActivity implements NetErrorHandlerListener{
    /**
     * TAG
     */
    public String TAG="ChargeActivity";
    /**
     * 充电点名称
     */
    private TextView orgName;
    /**
     * 电桩类型
     */
    private TextView facilityType;
    /**
     * 电桩ID
     */
    private TextView facilityId;
    /**
     * 电桩模式
     */
    private TextView facilityMode;
    /**
     * 电桩号
     */
    private TextView facilityName;
    /**
     * 充电时间
     */
    private TextView charge_time_text;
    /**
     * 充电度数
     */
    private TextView charge_degress_text;
    /**
     * 充电花费
     */
    private TextView charge_money_text;

    /**
     * 开始充电按钮
     */
    private ImageView charge_btn;
    /**
     * 标题
     */
    private TextView title;
    /**
     * 从上一个界面传递下来的数据
     */
    private Intent intent;
    /**
     * FinishChargeDaoImpl
     */
    private FinishChargeDaoImpl finishChargeDao;
    /**
     * 完成充电的消息
     */
    private final int msgFinishCharge = 0x002;
    /**
     * 准备充电，点击开始充电
     */
    public static final String NOCHARGE = "开始充电";
    /**
     * 正在充电，点击结束充电
     */
    public static final String CHARGEING = "正在充电";
    /**
     * 完成充电，点击支付
     */
    public static final String FINISHCHARGE = "完成充电";
    /**
     * 完成支付，点击无效，显示状态
     */
    public static final String FINISHPAY = "完成支付";
    /**
     * 充电状态
     */
    private String chargeState = NOCHARGE;
    /**
     * 请求码，支付
     */
    private final int PAYREQUEST = 0x001;
    /**
     * PileInfoDaoImpl
     */
    private PileInfoDaoImpl pileInfoDao;
    /**
     * 二维码获取电桩详情消息
     */
    private final int msgPileInfo = 0x003;
    /**
     * ChargeOrderDaoImpl
     */
    private ChargeOrderDaoImpl chargeOrderDao;
    /**
     * 充电消息
     */
    private final int msgCharge = 0x004;
    /**
     * 订单号
     */
    private String OrderNo;
    /**
     * 电桩号
     */
    private String FacilityID;
    /**
     * 进度条
     */
    private PopupWindow loading;
    /**
     * 通知进度条消失
     */
    private final int msgLoading = 0x005;
    /**
     * OrderInfoDaoImpl
     */
    private OrderInfoDaoImpl orderInfoDao;
    /**
     * 获取详情消息
     */
    private final int msgInfo = 0x006;
    /**
     * OrderInfoDaoImpl
     */
    private OrderInfoDaoImpl startOrderInfoDao;
    /**
     * 在刚进来界面时获取订单详情的消息
     */
    private final int startOrderInfo = 0x007;

    /**
     * 界面是否处于失去焦点状态
     */
    private boolean isPause = false;
    /**
     * 是否处于第二次
     */
    private boolean isSecond = false;
    /**
     * 是否成功结束
     */
    private boolean isFinish = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.charge);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }


    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    public void init() {
        charge_time_text = (TextView) this.findViewById(R.id.charge_time_text);
        charge_degress_text = (TextView) this.findViewById(R.id.charge_degress_text);
        charge_money_text = (TextView) this.findViewById(R.id.charge_money_text);
        charge_btn = (ImageView) this.findViewById(R.id.charge_btn);
        title = (TextView) this.findViewById(R.id.title);
        facilityName = (TextView) this.findViewById(R.id.facilityName);
        orgName = (TextView) this.findViewById(R.id.orgName);
        facilityType = (TextView) this.findViewById(R.id.facilityType);
        facilityId = (TextView) this.findViewById(R.id.facilityId);
        facilityMode = (TextView) this.findViewById(R.id.facilityMode);
    }

    @Override
    public void loadObjectAttribute() {
        intent = getIntent();
        finishChargeDao = new FinishChargeDaoImpl(this,handler,msgFinishCharge);
        chargeOrderDao = new ChargeOrderDaoImpl(this,handler,msgCharge);
        pileInfoDao = new PileInfoDaoImpl(this,handler,msgPileInfo);
        orderInfoDao = new OrderInfoDaoImpl(this,handler,msgInfo);
        startOrderInfoDao = new OrderInfoDaoImpl(this,handler,startOrderInfo);
        application = (MyApplication) getApplication();
        pileInfoDao = new PileInfoDaoImpl(this,handler,msgPileInfo);
    }

    @Override
    public void setListener() {
    }

    @Override
    public void setActivityView() {
        if(!TextUtils.isEmpty(intent.getStringExtra("QRId"))){ //读取二维码数据
            pileInfoDao.getPileInfoDao(intent.getStringExtra("QRId"),PileInfoDaoImpl.QRCODE);
            return;
        }else{
            Login.DataEntity entity = application.getLoginDao().getData();
            startOrderInfoDao.getOrderInfo(entity.getToken(),intent.getStringExtra("OrderNo"),entity.getCustID());
        }
        if(intent.getParcelableExtra("Entity") instanceof PendingOrder.EvcOrdersGetEntity){
            PendingOrder.EvcOrdersGetEntity entity = intent.getParcelableExtra("Entity");
            pileInfoDao.getPileInfoDao(entity.getFacilityID(),PileInfoDaoImpl.FACILITYID);
            OrderNo = entity.getOrderNo();
        }else if(intent.getParcelableExtra("Entity") instanceof StartCharge.EvcOrderChangeEntity){
            StartCharge.EvcOrderChangeEntity entity = intent.getParcelableExtra("Entity");
            pileInfoDao.getPileInfoDao(entity.getFacilityID(),PileInfoDaoImpl.FACILITYID);
            OrderNo = entity.getOrderNo();
        }
        title.setText("充电中");
        chargeState = CHARGEING;
        charge_btn.setImageResource(R.drawable.click_finish);
        if(TextUtils.equals(intent.getStringExtra("OrderStatus"), "2")){ //充电中
            title.setText("充电中");
            chargeState = CHARGEING;
            charge_btn.setImageResource(R.drawable.click_finish);
        }else if(TextUtils.equals(intent.getStringExtra("OrderStatus"),"4")){ //待支付
            title.setText("待支付");
            chargeState = FINISHCHARGE;
            charge_btn.setImageResource(R.drawable.click_pay);
        }

    }

    /**
     * 充电按钮的点击事件
     * @param view
     * @return
     */
    public View charge(View view){
        if (TextUtils.equals(chargeState, NOCHARGE)) { //如果电桩还没有还是充电，点击开始充电
            if(TextUtils.isEmpty(FacilityID)){
                return view;
            }
            Login.DataEntity entity = application.getLoginDao().getData();
            chargeOrderDao.getChargeOrderDao(FacilityID,entity.getToken(),entity.getCustID());
        } else if (TextUtils.equals(chargeState, CHARGEING)) { //充电桩正在充电，结束充电
            dialogUtil.showSureListenerDialog(this, "是否结束本次充电！", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Login.DataEntity data = application.getLoginDao().getData();
                    if(!TextUtils.isEmpty(OrderNo)){
                        finishChargeDao.getFinishChargeDao(data.getToken(),OrderNo,data.getCustID());
                    }
                    dialog.dismiss();
                }
            });
        } else if (TextUtils.equals(chargeState, FINISHCHARGE)) { //已经完成充电等待支付
            PayActivity.startPayActivity(ChargeActivity.this,intent.getStringExtra("OrderNo"),PayActivity.CHARGE,PAYREQUEST);
        }
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgCharge: //点击充电
                    if(chargeOrderDao.chargeOrderDao == null || TextUtils.equals(chargeOrderDao.chargeOrderDao.getEvc_order_set().getIsSuccess(), "N")){
                        Error errorDao = errorParamUtil.checkReturnState(chargeOrderDao.chargeOrderDao.getEvc_order_set().getReturnStatus());
                        if(toastUtil.toastError(context,errorDao,null)){
                            return;
                        }
                        ActivityControl.finishAct(ChargeActivity.this);
                        return;
                    }
                    title.setText("充电中");
                    chargeState = CHARGEING;
                    charge_btn.setImageResource(R.drawable.click_finish);
                    OrderNo = chargeOrderDao.chargeOrderDao.getEvc_order_set().getOrderNo();
                    break;
                case msgFinishCharge: //完成充电
                    if(finishChargeDao.finishChargeDao == null || TextUtils.equals(finishChargeDao.finishChargeDao.getEvc_order_change().getIsSuccess(),"N")){
                        Error errorDao = errorParamUtil.checkReturnState(finishChargeDao.finishChargeDao.getEvc_order_change().getReturnStatus());
                        toastUtil.toastError(context,errorDao,null);
                        return;
                    }
                    loading = popupWindowUtil.startLoading(ChargeActivity.this,charge_btn,"系统正在处理"); //开启进度条
                    ThreadManage.start(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(5000);
                                handler.sendEmptyMessage(msgLoading);
                                Thread.sleep(5000);
                                if(!isFinish){
                                    isSecond = true;
                                    handler.sendEmptyMessage(msgLoading);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
                case msgPileInfo: //根据二维码或者电桩id获取电桩详情
                    if(pileInfoDao.pileInfoDao == null || TextUtils.equals(pileInfoDao.pileInfoDao.getEvc_facility_get().getIsSuccess(),"N")){
                        Error errorDao = errorParamUtil.checkReturnState(pileInfoDao.pileInfoDao.getEvc_facility_get().getReturnStatus());
                        toastUtil.toastError(context,errorDao,null);
                        ActivityControl.finishAct(ChargeActivity.this);
                        return;
                    }
                    PileInfo.EvcFacilityGetEntity entity = pileInfoDao.pileInfoDao.getEvc_facility_get();
                    orgName.setText(entity.getOrgName());
                    facilityName.setText(entity.getFacilityName());
                    facilityId.setText(entity.getFacilityID());
                    if(TextUtils.equals("1",entity.getFacilityType())){
                        facilityType.setText("直流桩");
                    }else if(TextUtils.equals("2",entity.getFacilityType())){
                        facilityType.setText("交流桩");
                    }
                    FacilityID = entity.getFacilityID();
                    break;
                case msgLoading:
                    Login.DataEntity loginEntity = application.getLoginDao().getData();
                    orderInfoDao.getOrderInfo(loginEntity.getToken(),finishChargeDao.finishChargeDao.getEvc_order_change().getOrderNo(),loginEntity.getCustID());
                    break;
                case msgInfo:
                    if(orderInfoDao.orderInfo == null || TextUtils.equals(orderInfoDao.orderInfo.getEvc_order_get().getIsSuccess(),"N")){
                        Error errorDao = errorParamUtil.checkReturnState(pileInfoDao.pileInfoDao.getEvc_facility_get().getReturnStatus());
                        toastUtil.toastError(context, errorDao, null);
                        return;
                    }
                    OrderInfo.EvcOrderGetEntity evcOrderGetEntity = orderInfoDao.orderInfo.getEvc_order_get();
                    if(!TextUtils.equals(evcOrderGetEntity.getOrderStatus(), "4")){
                        if(TextUtils.equals(evcOrderGetEntity.getOrderStatus(),"8") || TextUtils.isEmpty(evcOrderGetEntity.getChargingAmt())){
                            Toast.makeText(ChargeActivity.this,"您的充电时间过短，系统暂未产生金额!",Toast.LENGTH_SHORT).show();
                            isFinish = true;
//                            PayActivity.startPayActivity(ChargeActivity.this,finishChargeDao.finishChargeDao.getEvc_order_change().getOrderNo());
                            finisSuccess();
                        } else {
                            if(!isSecond){ //如果是第一次获取，不会出现提示信息
                                return;
                            }
                            Toast.makeText(ChargeActivity.this, "系统正在处理，请进入订单界面完成支付!", Toast.LENGTH_SHORT).show();
                        }
                        finisSuccess();
                        return;
                    }
                    isFinish = true;
                    if(!isPause || loading.isShowing()){ //结束进度条
                        loading.dismiss();
                    }
                    title.setText("待支付");
                    chargeState = FINISHCHARGE;
                    charge_btn.setImageResource(R.drawable.click_pay);
//                    充电完成后跳到支付界面
                    PayActivity.startPayActivity(ChargeActivity.this,finishChargeDao.finishChargeDao.getEvc_order_change().getOrderNo(),PayActivity.CHARGE,PAYREQUEST);
                    break;
                case startOrderInfo: //进入界面时获取订单详情
                    if(startOrderInfoDao.orderInfo == null || TextUtils.equals(startOrderInfoDao.orderInfo.getEvc_order_get().getIsSuccess(),"N")){
                        Error errorDao = errorParamUtil.checkReturnState(startOrderInfoDao.orderInfo.getEvc_order_get().getReturnStatus());
                        toastUtil.toastError(context,errorDao,null);
                        return;
                    }
                    OrderInfo.EvcOrderGetEntity evcOrderGetEntity1 = startOrderInfoDao.orderInfo.getEvc_order_get();
                    facilityName.setText(evcOrderGetEntity1.getFacilityName().replace("号桩","").replace("号充电桩",""));
                    charge_degress_text.setText(evcOrderGetEntity1.getChargingQty()+"度");
                    charge_time_text.setText(evcOrderGetEntity1.getChargingTime() + "分钟");
                    String price = DoubleUtil.add(evcOrderGetEntity1.getChargingAmt(),evcOrderGetEntity1.getServiceAmt());
                    charge_money_text.setText("¥" + price);
                    break;
            }
        }
    };


    @Override
    public void handlerError(String returnState) {

    }

    /**
     * 完成充电，结束进度条
     */
    public void finisSuccess(){
        ActivityControl.finishAct(ChargeActivity.this);
        if(!isPause && loading.isShowing()){
            loading.dismiss();
        }
    }

    /**
     * 返回结果
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PAYREQUEST:
                if(resultCode == PayActivity.SUCCESS){
                    chargeState = FINISHPAY;
                    charge_btn.setImageResource(R.drawable.finishpay);
                }else if(resultCode == PayActivity.FAILURE){

                }
                break;
        }
    }

    /**
     * 开启充电界面
     * @param context
     * @param entity
     */
    public static void startAppActivity(Context context, StartCharge.EvcOrderChangeEntity entity){
        Intent intent = new Intent(context,ChargeActivity.class);
        intent.putExtra("Entity",entity);
        context.startActivity(intent);
    }

    /**
     * 开启充电界面
     * @param context
     * @param entity
     */
    public static void startAppActivity(Context context, PendingOrder.EvcOrdersGetEntity entity){
        Intent intent = new Intent(context,ChargeActivity.class);
        intent.putExtra("Entity",entity);
        context.startActivity(intent);
    }

    /**
     * 开启充电界面
     * @param context
     * @param QRId 二维码编号
     */
    public static void startAppActivity(Context context,String QRId){
        Intent intent = new Intent(context,ChargeActivity.class);
        intent.putExtra("QRId", QRId);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
