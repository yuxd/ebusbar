package com.ebusbar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ebusbar.adpater.AllOrderListAdapter;
import com.ebusbar.pile.MyApplication;
import com.ebusbar.pile.R;
import com.ebusbar.impl.OrderDaoImpl;

/**
 * 已完成订单
 * Created by Jelly on 2016/3/10.
 */
public class FinishOrderFrag extends BaseFrag{
    /**
     * TAG
     */
    public String TAG = "FinishOrderFrag";
    /**
     * 根界面
     */
    private View root;
    /**
     * 列表
     */
    private ListView finish_list;
    /**
     *
     */
    private AllOrderListAdapter adapter;
    /**
     * OrderDaoImpl
     */
    private OrderDaoImpl orderDao;
    /**
     * 获取所有订单消息
     */
    private int msgOrder = 0x001;
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
        root = inflater.inflate(R.layout.finishorder,container,false);
        finish_list = (ListView) root.findViewById(R.id.finish_list);
    }

    @Override
    public void loadObjectAttribute() {
        context = getActivity();
        application = (MyApplication) getActivity().getApplication();
        orderDao = new OrderDaoImpl(context,handler,msgOrder);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setFragView() {
        orderDao.getNetOrderDaos(application.getLoginDao().getCrm_login().getToken(),application.getLoginDao().getCrm_login().getCustID());
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == msgOrder){
                if(orderDao.orderDaos.size() == 0){
                    return;
                }
                adapter = new AllOrderListAdapter(context,orderDao.orderDaos);
                finish_list.setAdapter(adapter);
            }
        }
    };

    @Override
    public String getTAG() {
        return TAG;
    }
}

