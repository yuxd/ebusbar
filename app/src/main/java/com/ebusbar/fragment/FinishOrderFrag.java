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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ebusbar.adpater.AllOrderListAdapter;
import com.ebusbar.dao.CompleteOrderDao;
import com.ebusbar.dao.ErrorDao;
import com.ebusbar.dao.LoginDao;
import com.ebusbar.fragments.UtilFragment;
import com.ebusbar.impl.CompleteOrderDaoImpl;
import com.ebusbar.pile.MyApplication;
import com.ebusbar.pile.R;
import com.ebusbar.utils.PopupWindowUtil;

import java.util.ArrayList;

/**
 * 已完成订单
 * Created by Jelly on 2016/3/10.
 */
public class FinishOrderFrag extends UtilFragment {
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
     * 没有数据时显示
     */
    private LinearLayout nodata_show;
    /**
     *
     */
    private AllOrderListAdapter adapter;
    /**
     * CompleteOrderDao
     */
    private CompleteOrderDaoImpl completeOrderDao;
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
        super.onCreateView(inflater,container,savedInstanceState);
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
        root = inflater.inflate(R.layout.finishorder, container, false);
        finish_list = (ListView) root.findViewById(R.id.finish_list);
        nodata_show = (LinearLayout) root.findViewById(R.id.nodata_show);
    }

    @Override
    public void loadObjectAttribute() {
        context = getActivity();
        application = (MyApplication) getActivity().getApplication();
        completeOrderDao = new CompleteOrderDaoImpl(context,handler,msgOrder);
    }

    @Override
    public void setListener() {
    }

    @Override
    public void setFragView() {
        loading = popupWindowUtil.startLoading(context,root,"加载中");
        LoginDao.DataEntity data = application.getLoginDao().getData();
        completeOrderDao.getCompleteOrderDaos(data.getToken(), data.getCustID());
    }



    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == msgOrder){
                loading.dismiss();
                if(completeOrderDao.completeOrderDaos == null || completeOrderDao.completeOrderDaos.size() == 0){
                    return;
                }
                if(TextUtils.equals(completeOrderDao.completeOrderDaos.get(0).getEvc_orders_get().getIsSuccess(),"N")){
                    ErrorDao errorDao = errorParamUtil.checkReturnState(completeOrderDao.completeOrderDaos.get(0).getEvc_orders_get().getReturnStatus());
                    toastUtil.toastError(context,errorDao,null);
                    nodata_show.setVisibility(View.VISIBLE);
                    return;
                }
                ArrayList<CompleteOrderDao> arrayList = new ArrayList<CompleteOrderDao>();
                arrayList.addAll(completeOrderDao.completeOrderDaos);
                adapter = new AllOrderListAdapter(context,arrayList,application.getLoginDao());
                finish_list.setAdapter(adapter);
            }
        }
    };

    @Override
    public String getTAG() {
        return TAG;
    }
}

