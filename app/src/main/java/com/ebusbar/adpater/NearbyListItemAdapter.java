package com.ebusbar.adpater;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ebusbar.dao.PositionListItemDao;
import com.ebusbar.pile.R;

import java.util.List;

/**
 * 附近电桩列表的显示器
 * Created by Jelly on 2016/3/24.
 */
public class NearbyListItemAdapter extends BaseAdapter{

    private Context context;

    private List<PositionListItemDao> list;

    public NearbyListItemAdapter(Context context,List<PositionListItemDao> list) {
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

    private static class ViewHolder{
        TextView name;
        TextView position_text;
        TextView open_text;
        TextView pile_text;
        TextView appoint;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.nearby_item,null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.position_text = (TextView) convertView.findViewById(R.id.position_text);
            holder.open_text = (TextView) convertView.findViewById(R.id.open_text);
            holder.pile_text = (TextView) convertView.findViewById(R.id.pile_text);
            holder.appoint = (TextView) convertView.findViewById(R.id.appoint);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        PositionListItemDao.EvcStationsGetEntity entity = ((PositionListItemDao)getItem(position)).getEvc_stations_get();
        holder.name.setText(entity.getOrgName());
        holder.position_text.setText(entity.getAddr());
        if(TextUtils.equals(entity.getIsAvailable(),"1")){
            holder.open_text.setText("对外开放");
        }else{
            holder.open_text.setText("对外封闭");
        }
        String sum = "0";
        if(!TextUtils.isEmpty(entity.getAvailableNum()) || !TextUtils.isEmpty(entity.getUnavailableNum())){
            sum  = Integer.parseInt(entity.getAvailableNum()) + Integer.parseInt(entity.getUnavailableNum())+"";
        }
        String pileInfo = sum+"个电桩，0个空闲";;
        if(!TextUtils.equals(sum,"0")){
            pileInfo = sum+"个电桩，"+entity.getAvailableNum()+"个空闲";
        }
        holder.pile_text.setText(pileInfo);
        return convertView;
    }
}
