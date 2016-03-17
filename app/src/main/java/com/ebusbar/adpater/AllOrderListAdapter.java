package com.ebusbar.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ebusbar.dao.OrderDao;
import com.ebusbar.pile.R;

import java.util.List;

/**
 * Created by Jelly on 2016/3/10.
 */
public class AllOrderListAdapter extends BaseAdapter{

    private List<OrderDao> list;

    private Context context;

    public AllOrderListAdapter(Context context,List<OrderDao> list) {
        this.context = context;
        this.list = list;
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
        View item = LayoutInflater.from(context).inflate(R.layout.finishorder_list_item,null);
        TextView type_text = (TextView) item.findViewById(R.id.type_text);
        TextView state_text = (TextView) item.findViewById(R.id.state_text);
        TextView position_text = (TextView) item.findViewById(R.id.position_text);
        TextView order_time = (TextView) item.findViewById(R.id.order_time);
        TextView EPId_text = (TextView) item.findViewById(R.id.EPId_text);
        OrderDao orderDao = (OrderDao) getItem(position);
        type_text.setText(orderDao.getType());
        state_text.setText(orderDao.getType());
        position_text.setText(orderDao.getPosition());
        order_time.setText(orderDao.getTime());
        EPId_text.setText(orderDao.getEDId());
        return item;
    }
}
