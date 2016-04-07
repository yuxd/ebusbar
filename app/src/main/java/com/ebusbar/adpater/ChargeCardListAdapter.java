package com.ebusbar.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ebusbar.bean.ChargeCardItem;
import com.ebusbar.pile.R;

import java.util.List;

/**
 * Created by Jelly on 2016/3/23.
 */
public class ChargeCardListAdapter extends BaseAdapter{

    private List<ChargeCardItem> list;

    private Context context;

    public ChargeCardListAdapter(Context context,List<ChargeCardItem> list) {
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

    private class ViewHolder{
        TextView no;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.chargecard_item,null);
            viewHolder.no = (TextView) convertView.findViewById(R.id.no);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ChargeCardItem dao = (ChargeCardItem) getItem(position);
        viewHolder.no.setText(dao.getCrm_accounts_get().getAccountNo());
        return convertView;
    }
}
