package com.ebusbar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ebusbar.dao.GetChargeAppointDao;
import com.ebusbar.dao.LoginDao;
import com.ebusbar.dao.StartChargeDao;
import com.ebusbar.impl.FinishOrderDaoImpl;
import com.ebusbar.impl.GetChargeAppointDaoImpl;
import com.ebusbar.impl.StartChargeDaoImpl;
import com.ebusbar.pile.ChargeActivity;
import com.ebusbar.pile.MyApplication;
import com.ebusbar.pile.R;

/**
 * 充电预约
 * Created by Jelly on 2016/3/10.
 */
public class ChargeAppointFrag extends BaseFrag implements View.OnClickListener{
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
     * Context
     */
    private Context context;
    /**
     * MyApplication
     */
    private MyApplication application;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init(inflater,container);
        loadObjectAttribute();
        setListener();
        setFragView();
        return root;
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
    }

    @Override
    public void loadObjectAttribute() {
        context = getActivity();
        application = (MyApplication) getActivity().getApplication();
        getChargeAppointDao = new GetChargeAppointDaoImpl(context,handler,msgGetAppoint);
        finishOrderDao = new FinishOrderDaoImpl(context,handler,msgFinish);
        startChargeDao = new StartChargeDaoImpl(context,handler,msgStart);
    }

    @Override
    public void setListener() {
        setBtnOnClickListener();
    }

    @Override
    public void setFragView() {
        LoginDao.CrmLoginEntity data = application.getLoginDao().getCrm_login();
        getChargeAppointDao.getNetGetChargeAppointDao(data.getToken(), data.getCustID());
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
        LoginDao.CrmLoginEntity data = application.getLoginDao().getCrm_login(); //用户数据
        switch (v.getId()){
            case R.id.start: //开始充电
                if(getChargeAppointDao.getChargeAppointDao == null ||TextUtils.equals(getChargeAppointDao.getChargeAppointDao.getEvc_orders_get().getIsSuccess(),"N")){
                    Toast.makeText(context,"对不起，暂时无法开始充电！",Toast.LENGTH_SHORT).show();
                    return;
                }
                startChargeDao.getStartChargeDao(data.getToken(),getChargeAppointDao.getChargeAppointDao.getEvc_orders_get().getOrderNo(),data.getCustID());
                break;
            case R.id.cancel: //取消预约
                if(getChargeAppointDao.getChargeAppointDao == null ||TextUtils.equals(getChargeAppointDao.getChargeAppointDao.getEvc_orders_get().getIsSuccess(),"N")){
                    Toast.makeText(context,"对不起，此订单暂时无法结束！",Toast.LENGTH_SHORT).show();
                    return;
                }
                finishOrderDao.getFinishOrderDao(data.getToken(),getChargeAppointDao.getChargeAppointDao.getEvc_orders_get().getOrderNo(),data.getCustID());
                break;
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgGetAppoint:
                    if(getChargeAppointDao.getChargeAppointDao == null || TextUtils.equals(getChargeAppointDao.getChargeAppointDao.getEvc_orders_get().getIsSuccess(),"N")){
                        Toast.makeText(context,"获取数据失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    GetChargeAppointDao.EvcOrdersGetEntity data = getChargeAppointDao.getChargeAppointDao.getEvc_orders_get();
                    position_text.setText(data.getOrgName());
                    EPId_text.setText(data.getFacilityID());
                    appointcode_text.setText(data.getOrderNo());
                    appoint_time.setText(data.getPlanBeginDateTime());
                    appoint_price_text.setText("¥0.00");
                    break;
                case msgFinish:
                    if(finishOrderDao.finishOrderDao == null || TextUtils.equals(finishOrderDao.finishOrderDao.getEvc_order_cancel().getIsSuccess(),"N")){
                        Toast.makeText(context,"对不起，此订单暂时无法结束！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(context,"成功取消预约！",Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    break;
                case msgStart:
                    if(startChargeDao.startChargeDao == null || TextUtils.equals(startChargeDao.startChargeDao.getEvc_order_change().getOrderStatus(), "N")){
                        return;
                    }
                    StartChargeDao.EvcOrderChangeEntity entity =  startChargeDao.startChargeDao.getEvc_order_change();
                    ChargeActivity.startAppActivity(context,entity.getOrgName(),entity.getFacilityID(),entity.getOrderStatus(),entity.getOrderNo());
                    break;
            }
        }
    };

    @Override
    public String getTAG() {
        return TAG;
    }
}
