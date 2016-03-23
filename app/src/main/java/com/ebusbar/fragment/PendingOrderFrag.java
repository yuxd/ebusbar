package com.ebusbar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ebusbar.adpater.PendingOrderListAdapter;
import com.ebusbar.dao.LoginDao;
import com.ebusbar.dao.PendingOrderDao;
import com.ebusbar.pile.ChargeActivity;
import com.ebusbar.pile.MyApplication;
import com.ebusbar.pile.R;
import com.ebusbar.impl.PendingOrderImpl;

/**
 * 未完成订单
 * Created by Jelly on 2016/3/10.
 */
public class PendingOrderFrag extends BaseFrag{
    /**
     * TAG
     */
    public String TAG = "PendingOrderFrag";
    /**
     * 根布局
     */
    private View root;
    /**
     * 待处理的List
     */
    private ListView pending_list;
    /**
     * Adapter
     */
    private PendingOrderListAdapter adapter;
    /**
     * PendingOrderImpl
     */
    private PendingOrderImpl pendingOrder;
    /**
     * 获取未处理订单的消息
     */
    private int msgPenging = 0x001;
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
        root = inflater.inflate(R.layout.pendingorder,container,false);
        pending_list = (ListView) root.findViewById(R.id.pending_list);
    }

    @Override
    public void loadObjectAttribute() {
        context = getActivity();
        application = (MyApplication) getActivity().getApplication();
        pendingOrder = new PendingOrderImpl(context,handler,msgPenging);
    }

    @Override
    public void setListener() {
        setListItemClickListener();
    }

    @Override
    public void setFragView() {
        LoginDao.CrmLoginEntity data = application.getLoginDao().getCrm_login();
        pendingOrder.getNetPendingOrderList(data.getToken(),data.getCustID());
    }

    /**
     * 设置列表的点击事件
     */
    public void setListItemClickListener(){
        pending_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PendingOrderDao.EvcOrdersGetEntity data = pendingOrder.pendingOrderDaos.get(position).getEvc_orders_get();
                ChargeActivity.startAppActivity(context,data.getOrgName(),data.getFacilityID(),data.getOrderStatus(),data.getOrderNo());
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == msgPenging){
                if(pendingOrder.pendingOrderDaos.size() == 0){
                    return;
                }
                adapter = new PendingOrderListAdapter(context,pendingOrder.pendingOrderDaos);
                pending_list.setAdapter(adapter);
            }
        }
    };

    @Override
    public String getTAG() {
        return super.getTAG();
    }
}

