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

    private class ViewHolder{
        TextView type_text;
        TextView state_text;
        TextView position_text;
        TextView order_time;
        TextView EPId_text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.pendingorder_list_item,null);
            viewHolder.type_text = (TextView) convertView.findViewById(R.id.type_text);
            viewHolder.state_text = (TextView) convertView.findViewById(R.id.state_text);
            viewHolder.position_text = (TextView) convertView.findViewById(R.id.position_text);
            viewHolder.order_time = (TextView) convertView.findViewById(R.id.order_time);
            viewHolder.EPId_text = (TextView) convertView.findViewById(R.id.EPId_text);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PendingOrderDao.EvcOrdersGetEntity data = ((PendingOrderDao) getItem(position)).getEvc_orders_get();
        if(TextUtils.equals(data.getOrderStatus(),"2")){
            viewHolder.state_text.setText("充电中");
        }else if(TextUtils.equals(data.getOrderStatus(),"4")){
            viewHolder.state_text.setText("待支付");
        }
        viewHolder.position_text.setText(data.getOrgName());
        viewHolder.order_time.setText(data.getPlanBeginDateTime());
        viewHolder.EPId_text.setText(data.getOrgID());
        return convertView;
    }
}
