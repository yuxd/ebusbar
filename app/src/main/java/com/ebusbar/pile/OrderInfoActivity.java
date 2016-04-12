package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.bean.Error;
import com.ebusbar.bean.Login;
import com.ebusbar.bean.OrderInfo;
import com.ebusbar.impl.OrderInfoDaoImpl;
import com.ebusbar.utils.DateUtil;
import com.ebusbar.utils.DoubleUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 充电订单详情
 * Created by Jelly on 2016/4/9.
 */
public class OrderInfoActivity extends UtilActivity {

    public String TAG = "OrderInfoActivity";


    @Bind(R.id.orgName)
    TextView orgName;
    @Bind(R.id.facilityName)
    TextView facilityName;
    @Bind(R.id.facilityType)
    TextView facilityType;
    @Bind(R.id.facilityMode)
    TextView facilityMode;
    @Bind(R.id.facilityId)
    TextView facilityId;
    @Bind(R.id.startTime)
    TextView startTime;
    @Bind(R.id.endTime)
    TextView endTime;
    @Bind(R.id.chargingQty)
    TextView chargingQty;
    @Bind(R.id.amt)
    TextView amt;
    @Bind(R.id.payWay)
    TextView payWay;
    @Bind(R.id.mobile)
    TextView mobile;
    @Bind(R.id.use_time)
    TextView useTime;
    @Bind(R.id.use_time_title)
    TextView useTimeTitle;
    @Bind(R.id.orgSign)
    ImageView orgSign;

    /**
     * 订单详情
     */
    private OrderInfoDaoImpl orderInfoDao;
    /**
     * 订单详情消息
     */
    private final int msgOrderInfo = 0x001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void init() {
        this.setContentView(R.layout.orderinfo);
        ButterKnife.bind(this);
    }

    @Override
    public void loadObjectAttribute() {
        orderInfoDao = new OrderInfoDaoImpl(this, handler, msgOrderInfo);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setActivityView() {
        Login.DataEntity dataEntity = application.getLoginDao().getData();
        orderInfoDao.getOrderInfo(dataEntity.getToken(), intent.getStringExtra("OrderNo"), dataEntity.getCustID());
    }

    @OnClick(R.id.call)
    public void onClick() {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + mobile.getText().toString()));
        startActivity(intent);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case msgOrderInfo:
                    if (orderInfoDao.orderInfo == null) {
                        return;
                    }
                    if (TextUtils.equals("N", orderInfoDao.orderInfo.getEvc_order_get().getIsSuccess())) {
                        Error error = errorParamUtil.checkReturnState(orderInfoDao.orderInfo.getEvc_order_get().getReturnStatus());
                        toastUtil.toastError(context, error, null);
                    }
                    OrderInfo.EvcOrderGetEntity evcOrderGetEntity = orderInfoDao.orderInfo.getEvc_order_get();
                    orgName.setText(evcOrderGetEntity.getOrgName());
                    facilityName.setText(evcOrderGetEntity.getFacilityName());
                    facilityId.setText(evcOrderGetEntity.getFacilityID());
                    if (TextUtils.equals(OrderInfoDaoImpl.CANCEL, evcOrderGetEntity.getOrderStatus())) {
                        startTime.setText(DateUtil.getSdfDate(evcOrderGetEntity.getPlanBeginDateTime(), "MM月dd日 HH:mm"));
                        endTime.setText(DateUtil.getSdfDate(evcOrderGetEntity.getPlanEndDateTime(), "MM月dd日 HH:mm"));
                        useTimeTitle.setText("预约时间：");
                        useTime.setText(DateUtil.DifferDate(evcOrderGetEntity.getPlanEndDateTime(), evcOrderGetEntity.getPlanBeginDateTime()) + "分钟");
                        orgSign.setImageResource(R.drawable.ordercancel);
                    } else if (TextUtils.equals(OrderInfoDaoImpl.COMPLETE, evcOrderGetEntity.getOrderStatus()) && TextUtils.isEmpty(evcOrderGetEntity.getPlanBeginDateTime())) {
                        startTime.setText(DateUtil.getSdfDate(evcOrderGetEntity.getRealBeginDateTime(), "MM月dd日 HH:mm"));
                        endTime.setText(DateUtil.getSdfDate(evcOrderGetEntity.getRealEndDateTime(), "MM月dd日 HH:mm"));
                        useTimeTitle.setText("充电时间：");
                        useTime.setText(evcOrderGetEntity.getChargingTime() + "分钟");
                        orgSign.setImageResource(R.drawable.orderfinish);
                    } else if (TextUtils.equals(OrderInfoDaoImpl.COMPLETE, evcOrderGetEntity.getOrderStatus()) && !TextUtils.isEmpty(evcOrderGetEntity.getPlanBeginDateTime())) {
                        startTime.setText(DateUtil.getSdfDate(evcOrderGetEntity.getPlanBeginDateTime(), "MM月dd日 HH:mm"));
                        endTime.setText(DateUtil.getSdfDate(evcOrderGetEntity.getRealEndDateTime(), "MM月dd日 HH:mm"));
                        useTimeTitle.setText("充电时间：");
                        useTime.setText(evcOrderGetEntity.getChargingTime() + "分钟");
                        orgSign.setImageResource(R.drawable.orderfinish);
                    }

                    chargingQty.setText(evcOrderGetEntity.getChargingQty() + "度");
                    amt.setText(DoubleUtil.add(evcOrderGetEntity.getChargingAmt(), evcOrderGetEntity.getServiceAmt()) + "元");
                    mobile.setText(evcOrderGetEntity.getTel());
                    break;
            }
        }
    };

    /**
     * 开启界面
     *
     * @param context
     * @param OrderNo
     */
    public static void startActivity(Context context, String OrderNo) {
        Intent intent = new Intent(context, OrderInfoActivity.class);
        intent.putExtra("OrderNo", OrderNo);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }

}
