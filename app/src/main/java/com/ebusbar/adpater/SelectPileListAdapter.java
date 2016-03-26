package com.ebusbar.adpater;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ebusbar.dao.PileListItemDao;
import com.ebusbar.pile.R;

import java.util.List;

/**
 * 选择电桩列表适配器
 * Created by Jelly on 2016/3/15.
 */
public class SelectPileListAdapter extends BaseAdapter{
    /**
     * 上下文
     */
    private Context context;
    /**
     * 操作数据
     */
    private List<PileListItemDao> piles;

    public SelectPileListAdapter(Context context,List<PileListItemDao> piles) {
        this.context = context;
        this.piles = piles;
    }

    @Override
    public int getCount() {
        return piles.size();
    }

    @Override
    public Object getItem(int position) {
        return piles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView pile_name;
        TextView money;
        TextView charge_type;
        TextView pile_type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.selectpile_item,null);
            viewHolder.pile_name = (TextView) convertView.findViewById(R.id.pile_name);
            viewHolder.money = (TextView) convertView.findViewById(R.id.money);
            viewHolder.charge_type = (TextView) convertView.findViewById(R.id.charge_type);
            viewHolder.pile_type = (TextView) convertView.findViewById(R.id.pile_type);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PileListItemDao.EvcFacilitiesGetEntity entity = ((PileListItemDao) getItem(position)).getEvc_facilities_get();
        viewHolder.pile_name.setText(entity.getFacilityName());
        viewHolder.money.setText(entity.getPrice());
        viewHolder.charge_type.setText(entity.getFacilityModel());
        if(TextUtils.equals(entity.getFacilityStatus(),"1")){
            viewHolder.pile_type.setText("空闲中");
        }
        return convertView;
    }
}
