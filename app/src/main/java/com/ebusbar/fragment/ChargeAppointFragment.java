package com.ebusbar.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ebusbar.activities.BaseActivity;
import com.ebusbar.bean.Error;
import com.ebusbar.bean.GetChargeAppoint;
import com.ebusbar.bean.Login;
import com.ebusbar.bean.StartCharge;
import com.ebusbar.fragments.UtilFragment;
import com.ebusbar.impl.FinishOrderDaoImpl;
import com.ebusbar.impl.GetChargeAppointDaoImpl;
import com.ebusbar.impl.StartChargeDaoImpl;
import com.ebusbar.map.MyLocation;
import com.ebusbar.pile.ChargeActivity;
import com.ebusbar.pile.NaviEmulatorActivity;
import com.ebusbar.pile.R;
import com.ebusbar.utils.ActivityControl;

/**
 * 我的预约充电预约
 * Created by Jelly on 2016/3/10.
 */
public class ChargeAppointFragment extends UtilFragment implements View.OnClickListener{
    /**
     * TAG
     */
    public String TAG = "ChargeAppointFrag";
    /**
     * 显示的根界面
     */
    private View root;
    /**
     * 充电点
     */
    private TextView position_text;
    /**
     * 电桩号
     */
    private TextView EPId_text;
    /**
     * 预约码
     */
    private TextView appointcode_text;
    /**
     * 预约价格
     */
    private TextView appoint_price_text;
    /**
     * 预约时间
     */
    private TextView appoint_time;
    /**
     * 开始充电
     */
    private TextView start;
    /**
     * 结束预约
     */
    private TextView cancel;
    /**
     * 联系电话
     */
    private TextView phone_text;
    /**
     * 导航
     */
    private LinearLayout navigation;
    /**
     * 没有数据时的显示
     */
    private LinearLayout nodata_show;
    /**
     * GetChargeAppointDaoImpl
     */
    private GetChargeAppointDaoImpl getChargeAppointDao;
    /**
     * 获取预约消息
     */
    private final int msgGetAppoint = 0x001;
    /**
     * 结束订单
     */
    private FinishOrderDaoImpl finishOrderDao;
    /**
     * 结束订单消息
     */
    private final int msgFinish = 0x002;
    /**
     * StartChargeDaoImpl
     */
    private StartChargeDaoImpl startChargeDao;
    /**
     * 开始充电消息
     */
    private final int msgStart = 0x003;
    /**
     * 加载数据PopupWindow
     */
    private PopupWindow loading;
    /**
     * 请求支付
     */
    private static final int requestPay = 0x004;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        init(inflater, container);
        loadObjectAttribute();
        setListener();
        setFragView();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.chargeappoint,container,false);
        position_text = (TextView) root.findViewById(R.id.position_text);
        EPId_text = (TextView) root.findViewById(R.id.EPId_text);
        appointcode_text = (TextView) root.findViewById(R.id.appointcode_text);
        appoint_price_text = (TextView) root.findViewById(R.id.appoint_price_text);
        appoint_time = (TextView) root.findViewById(R.id.appoint_time);
        start = (TextView) root.findViewById(R.id.start);
        cancel = (TextView) root.findViewById(R.id.cancel);
        phone_text = (TextView) root.findViewById(R.id.phone_text);
        nodata_show = (LinearLayout) root.findViewById(R.id.nodata_show);
        navigation = (LinearLayout) root.findViewById(R.id.navigation);
    }

    @Override
    public void loadObjectAttribute() {
        getChargeAppointDao = new GetChargeAppointDaoImpl(context,handler,msgGetAppoint);
        finishOrderDao = new FinishOrderDaoImpl(context,handler,msgFinish);
        startChargeDao = new StartChargeDaoImpl(context,handler,msgStart);
    }

    @Override
    public void setListener() {
        setBtnOnClickListener();
        setNavigationListener();
    }

    @Override
    public void setFragView() {
        loading = popupWindowUtil.startLoading(context,start,"加载中");
        Login.DataEntity data = application.getLoginDao().getData();
        getChargeAppointDao.getNetGetChargeAppointDao(data.getToken(), data.getCustID());
    }

    /**
     * 设置导航点击
     */
    public void setNavigationListener(){
        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLocation location = application.getLocation();
                GetChargeAppoint.EvcOrdersGetEntity entity = getChargeAppointDao.getChargeAppointDao.getEvc_orders_get();
                NaviEmulatorActivity.startAppActivity(context,Double.parseDouble(location.getLatitude()),Double.parseDouble(location.getLongitude()),Double.parseDouble(entity.getLatitude()),Double.parseDouble(entity.getLongitude()));
            }
        });
    }

    /**
     * 设置按钮的点击监听
     */
    public void setBtnOnClickListener(){
        start.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Login.DataEntity entity = application.getLoginDao().getData(); //用户数据
        switch (v.getId()){
            case R.id.start:
                //开始充电,在开始充电之前
                if(getChargeAppointDao.getChargeAppointDao == null ||TextUtils.equals(getChargeAppointDao.getChargeAppointDao.getEvc_orders_get().getIsSuccess(), "N")){
                    Toast.makeText(context,"对不起，暂时无法开始充电！",Toast.LENGTH_SHORT).show();
                    return;
                }
                startChargeDao.getStartChargeDao(entity.getToken(), getChargeAppointDao.getChargeAppointDao.getEvc_orders_get().getOrderNo(), entity.getCustID());
                break;
            case R.id.cancel: //取消预约
                if(getChargeAppointDao.getChargeAppointDao == null ||TextUtils.equals(getChargeAppointDao.getChargeAppointDao.getEvc_orders_get().getIsSuccess(), "N")){
                    Toast.makeText(context,"对不起，此订单暂时无法结束！",Toast.LENGTH_SHORT).show();
                    return;
                }
                dialogUtil.showSureListenerDialog(context, "是否需要取消预约！", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Login.DataEntity entity = application.getLoginDao().getData();
                        finishOrderDao.getFinishOrderDao(entity.getToken(),getChargeAppointDao.getChargeAppointDao.getEvc_orders_get().getOrderNo(),entity.getCustID());
                    }
                });
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgGetAppoint: //获得预约数据
                    if(loading != null){
                        loading.dismiss();
                    }
                    if(getChargeAppointDao.getChargeAppointDao == null || TextUtils.equals(getChargeAppointDao.getChargeAppointDao.getEvc_orders_get().getIsSuccess(), "N")){
                        nodata_show.setVisibility(View.VISIBLE);
                        return;
                    }
                    GetChargeAppoint.EvcOrdersGetEntity entity = getChargeAppointDao.getChargeAppointDao.getEvc_orders_get();
                    position_text.setText(entity.getOrgName());
                    EPId_text.setText(entity.getFacilityID());
                    appointcode_text.setText(entity.getOrderNo());
                    appoint_time.setText(entity.getPlanBeginDateTime());
                    phone_text.setText(entity.getTel());
                    appoint_price_text.setText("¥"+entity.getPlanCost());
                    break;
                case msgFinish: //结束预约
                    if(finishOrderDao.finishOrderDao == null || TextUtils.equals(finishOrderDao.finishOrderDao.getEvc_order_cancel().getIsSuccess(),"N")){
                        Error errorDao = errorParamUtil.checkReturnState(finishOrderDao.finishOrderDao.getEvc_order_cancel().getReturnStatus());
                        toastUtil.toastError(context,errorDao,null);
                        return;
                    }
                    Toast.makeText(context, "成功取消预约！", Toast.LENGTH_SHORT).show();
                    ActivityControl.finishAct((BaseActivity) getActivity());
                    break;
                case msgStart: //开始充电
                    if(startChargeDao.startChargeDao == null || TextUtils.equals(startChargeDao.startChargeDao.getEvc_order_change().getIsSuccess(), "N")){
                        Error errorDao = errorParamUtil.checkReturnState(startChargeDao.startChargeDao.getEvc_order_change().getReturnStatus());
                        toastUtil.toastError(context, errorDao, null);
                        return;
                    }
                    ActivityControl.finishAct((BaseActivity)getActivity());
                    StartCharge.EvcOrderChangeEntity entity1 =  startChargeDao.startChargeDao.getEvc_order_change();
                    ChargeActivity.startAppActivity(context, entity1.getOrgName(), entity1.getFacilityID(), entity1.getOrderStatus(), entity1.getOrderNo());
                    break;
            }
        }
    };

    @Override
    public String getTAG() {
        return TAG;
    }
}
