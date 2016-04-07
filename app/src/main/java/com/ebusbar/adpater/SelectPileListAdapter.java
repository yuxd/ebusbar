package com.ebusbar.adpater;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebusbar.bean.PileListItem;
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
    private List<PileListItem> piles;

    public SelectPileListAdapter(Context context,List<PileListItem> piles) {
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
        TextView facilityName;
        TextView facilityType;
        TextView facilityModel;
        TextView applicableCar;
        ImageView appoint_btn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.selectpile_item,null);
            viewHolder.facilityName = (TextView) convertView.findViewById(R.id.facilityName);
            viewHolder.applicableCar = (TextView) convertView.findViewById(R.id.applicableCar);
            viewHolder.facilityType = (TextView) convertView.findViewById(R.id.facilityType);
            viewHolder.facilityModel = (TextView) convertView.findViewById(R.id.facilityModel);
            viewHolder.appoint_btn = (ImageView) convertView.findViewById(R.id.appoint_btn);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PileListItem.EvcFacilitiesGetEntity entity = ((PileListItem) getItem(position)).getEvc_facilities_get();
        viewHolder.facilityName.setText(entity.getFacilityName().replace("号桩","").replace("号充电桩",""));
        if(!TextUtils.isEmpty(entity.getApplicableCar())){
            viewHolder.applicableCar.setText(entity.getApplicableCar().replace("-","、"));
        }
        if(TextUtils.equals(entity.getFacilityType(),"1")){
            viewHolder.facilityType.setText("直流桩");
        }else if(TextUtils.equals(entity.getFacilityModel(),"0")){
            viewHolder.facilityType.setText("交流桩");
        }
        if(!TextUtils.equals(entity.getFacilityStatus(),"0")){
            viewHolder.appoint_btn.setImageResource(R.drawable.selectpilenoappointbtn);
        }
        return convertView;
    }
}
