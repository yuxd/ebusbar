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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View root = LayoutInflater.from(context).inflate(R.layout.selectpile_item,null);
        TextView pile_name = (TextView) root.findViewById(R.id.pile_name);
        TextView money = (TextView) root.findViewById(R.id.money);
        TextView charge_type = (TextView) root.findViewById(R.id.charge_type);
        TextView pile_type = (TextView) root.findViewById(R.id.pile_type);
        PileListItemDao.EvcFacilitiesGetEntity data = ((PileListItemDao) getItem(position)).getEvc_facilities_get();
        pile_name.setText(data.getFacilityName());
        money.setText(data.getPrice());
        charge_type.setText(data.getFacilityModel());
        if(TextUtils.equals(data.getFacilityStatus(),"1")){
            pile_type.setText("空闲中");
        }
        return root;
    }
}
