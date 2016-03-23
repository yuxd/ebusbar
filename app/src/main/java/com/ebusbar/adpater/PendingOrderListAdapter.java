package com.ebusbar.adpater;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ebusbar.dao.PendingOrderDao;
import com.ebusbar.pile.R;

import java.util.List;

/**
 * Created by Jelly on 2016/3/10.
 */
public class PendingOrderListAdapter extends BaseAdapter{

    private List<PendingOrderDao> list;

    private  Context context;

    public PendingOrderListAdapter(Context context,List<PendingOrderDao> list) {
        this.list = list;
        this.context = context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View root = LayoutInflater.from(context).inflate(R.layout.pendingorder_list_item,null);
        TextView type_text = (TextView) root.findViewById(R.id.type_text);
        TextView state_text = (TextView) root.findViewById(R.id.state_text);
        TextView position_text = (TextView) root.findViewById(R.id.position_text);
        TextView order_time = (TextView) root.findViewById(R.id.order_time);
        TextView EPId_text = (TextView) root.findViewById(R.id.EPId_text);
        PendingOrderDao.EvcOrdersGetEntity data = ((PendingOrderDao) getItem(position)).getEvc_orders_get();
        if(TextUtils.equals(data.getOrderType(),"2")){
            type_text.setText("充电预约");
        }
        if(TextUtils.equals(data.getOrderStatus(),"2")){
            state_text.setText("充电中");
        }else {
            state_text.setText("待支付");
        }
        position_text.setText(data.getOrgName());
        order_time.setText(data.getPlanBeginDateTime());
        EPId_text.setText(data.getOrgID());
        return root;
    }
}
