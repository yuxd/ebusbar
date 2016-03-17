package com.ebusbar.adpater;

import android.content.Context;
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
        PendingOrderDao pendingOrderDao = (PendingOrderDao) getItem(position);
        type_text.setText(pendingOrderDao.getType());
        state_text.setText(pendingOrderDao.getState());
        position_text.setText(pendingOrderDao.getPosition());
        order_time.setText(pendingOrderDao.getTime());
        EPId_text.setText(pendingOrderDao.getEDId());
        return root;
    }
}
