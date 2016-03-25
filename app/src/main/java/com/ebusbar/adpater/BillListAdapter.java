package com.ebusbar.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ebusbar.dao.BillDao;
import com.ebusbar.pile.R;

import java.util.List;

/**
 * Created by Jelly on 2016/3/24.
 */
public class BillListAdapter extends BaseAdapter{
    /**
     * Context
     */
    private Context context;
    /**
     * 数据集合
     */
    private List<BillDao> list;

    public BillListAdapter(Context context,List<BillDao> list){
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

    /**
     * 缓存视图
     */
    private class ViewHolder{
        TextView type;
        TextView price;
        TextView time;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.bill_list_item,null);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        BillDao.CrmBalancelogGetEntity entity = ((BillDao)getItem(position)).getCrm_balancelog_get();
        switch (entity.getLogType()){
            case "1":
                holder.type.setText("支付宝充值");
                break;
            case "2":
                holder.type.setText("微信充值");
                break;
            case "3":
                holder.type.setText("余额支付");
                break;
            case "4":
                holder.type.setText("支付宝支付");
                break;
            case "5":
                holder.type.setText("微信支付");
                break;
        }
        holder.price.setText(entity.getAmt()+"元");
        holder.time.setText(entity.getCreateDate());
        return convertView;
    }
}
