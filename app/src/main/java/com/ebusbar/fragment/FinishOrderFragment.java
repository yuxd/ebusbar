package com.ebusbar.fragment;

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

import com.ebusbar.adpater.AllOrderListAdapter;
import com.ebusbar.bean.CompleteOrder;
import com.ebusbar.bean.Error;
import com.ebusbar.bean.Login;
import com.ebusbar.fragments.UtilFragment;
import com.ebusbar.impl.CompleteOrderDaoImpl;
import com.ebusbar.pile.OrderInfoActivity;
import com.ebusbar.pile.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 已完成订单
 * Created by Jelly on 2016/3/10.
 */
public class FinishOrderFragment extends UtilFragment {
    /**
     * TAG
     */
    public String TAG = "FinishOrderFragment";
    @Bind(R.id.nodata_show)
    LinearLayout nodataShow;
    @Bind(R.id.finish_list)
    ListView finishList;
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
     * Loading
     */
    private PopupWindow loading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
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
        root = inflater.inflate(R.layout.finishorder, container, false);
        ButterKnife.bind(this, root);
    }

    @Override
    public void loadObjectAttribute() {
        completeOrderDao = new CompleteOrderDaoImpl(context, handler, msgOrder);
    }

    @Override
    public void setListener() {
        setListItemClickListener();
    }

    @Override
    public void setFragView() {
        loading = popupWindowUtil.startLoading(context, root, "加载中");
        Login.DataEntity data = application.getLoginDao().getData();
        completeOrderDao.getCompleteOrderDaos(data.getToken(), data.getCustID());
    }


    /**
     * 设置List的Item的点击事件
     */
    public void setListItemClickListener(){
        finishList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CompleteOrder.EvcOrdersGetEntity evcOrdersGetEntity  = ((CompleteOrder) adapter.getItem(position)).getEvc_orders_get();
                OrderInfoActivity.startActivity(context,evcOrdersGetEntity.getOrderNo());
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == msgOrder) {
                loading.dismiss();
                if (completeOrderDao.completeOrderDaos == null || completeOrderDao.completeOrderDaos.size() == 0) {
                    return;
                }
                if (TextUtils.equals(completeOrderDao.completeOrderDaos.get(0).getEvc_orders_get().getIsSuccess(), "N")) {
                    Error errorDao = errorParamUtil.checkReturnState(completeOrderDao.completeOrderDaos.get(0).getEvc_orders_get().getReturnStatus());
                    toastUtil.toastError(context, errorDao, null);
                    nodataShow.setVisibility(View.VISIBLE);
                    return;
                }
                ArrayList<CompleteOrder> arrayList = new ArrayList<CompleteOrder>();
                arrayList.addAll(completeOrderDao.completeOrderDaos);
                adapter = new AllOrderListAdapter(context, arrayList, application.getLoginDao());
                finishList.setAdapter(adapter);
            }
        }
    };

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

