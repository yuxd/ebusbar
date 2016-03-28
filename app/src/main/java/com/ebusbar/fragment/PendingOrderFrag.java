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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ebusbar.adpater.PendingOrderListAdapter;
import com.ebusbar.dao.LoginDao;
import com.ebusbar.dao.PendingOrderDao;
import com.ebusbar.impl.PendingOrderImpl;
import com.ebusbar.pile.BaseActivity;
import com.ebusbar.pile.ChargeActivity;
import com.ebusbar.pile.MyApplication;
import com.ebusbar.pile.R;
import com.ebusbar.utils.ActivityControl;
import com.ebusbar.utils.PopupWindowUtil;

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
     * 没有数据的显示
     */
    private LinearLayout nodata_show;

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
    /**
     * PopupWindow操作工具
     */
    private PopupWindowUtil popupWindowUtil = PopupWindowUtil.getInstance();
    /**
     * Loading
     */
    private PopupWindow loading;

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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.pendingorder, container, false);
        pending_list = (ListView) root.findViewById(R.id.pending_list);
        nodata_show = (LinearLayout) root.findViewById(R.id.nodata_show);
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
        loading = popupWindowUtil.startLoading(context,root,"加载中");
        LoginDao.CrmLoginEntity data = application.getLoginDao().getCrm_login();
        pendingOrder.getNetPendingOrderList(data.getToken(), data.getCustID());
    }

    /**
     * 设置列表的点击事件
     */
    public void setListItemClickListener(){
        pending_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityControl.finishAct((BaseActivity) getActivity());
                PendingOrderDao.EvcOrdersGetEntity data = pendingOrder.pendingOrderDaos.get(position).getEvc_orders_get();
                ChargeActivity.startAppActivity(context,data.getOrgName(),data.getFacilityID(),data.getOrderStatus(),data.getOrderNo());
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == msgPenging){
                loading.dismiss();
                if(pendingOrder.pendingOrderDaos.size() == 0 || TextUtils.equals(pendingOrder.pendingOrderDaos.get(0).getEvc_orders_get().getIsSuccess(),"N")){
                    nodata_show.setVisibility(View.VISIBLE);
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

