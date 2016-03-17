package com.ebusbar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ebusbar.pile.MyApplication;
import com.ebusbar.pile.R;
import com.ebusbar.impl.GetChargeAppointDaoImpl;

/**
 * Created by Jelly on 2016/3/10.
 */
public class ChargeAppointFrag extends BaseFrag{
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
     * GetChargeAppointDaoImpl
     */
    private GetChargeAppointDaoImpl getChargeAppointDao;
    /**
     * 获取预约消息
     */
    private int msgGetAppoint = 0x001;
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
    }

    @Override
    public void loadObjectAttribute() {
        context = getActivity();
        application = (MyApplication) getActivity().getApplication();
        getChargeAppointDao = new GetChargeAppointDaoImpl(context,handler,msgGetAppoint);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setFragView() {
        getChargeAppointDao.getNetGetChargeAppointDao(application.getLoginDao().getToken(),application.getLoginDao().getUid()+"");
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == msgGetAppoint){
                if(getChargeAppointDao.getChargeAppointDao == null){
                    return;
                }
                position_text.setText(getChargeAppointDao.getChargeAppointDao.getPosition());
                EPId_text.setText(getChargeAppointDao.getChargeAppointDao.getEPId());
                appointcode_text.setText(getChargeAppointDao.getChargeAppointDao.getAid());
                appoint_price_text.setText("¥"+getChargeAppointDao.getChargeAppointDao.getPrice());
            }
        }
    };

    @Override
    public String getTAG() {
        return TAG;
    }
}
