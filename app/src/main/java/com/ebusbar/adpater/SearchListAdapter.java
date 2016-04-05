package com.ebusbar.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.ebusbar.dao.AllStationDao;
import com.ebusbar.map.MyLocation;
import com.ebusbar.pile.MyApplication;
import com.ebusbar.pile.R;
import com.ebusbar.utils.FloatUtil;

import java.util.List;

/**
 * 搜索列表适配器
 * Created by Jelly on 2016/3/31.
 */
public class SearchListAdapter extends BaseAdapter{

    /**
     * 显示的数据集合
     */
    private List<AllStationDao> list;

    private Context context;

    private MyApplication application;

    public SearchListAdapter(Context context,MyApplication application,List<AllStationDao> list){
        this.context = context;
        this.list = list;
        this.application = application;
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
        TextView orgName;
        TextView addr;
        TextView city;
        TextView distance_text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.searchlistitem,null);
            viewHolder.orgName = (TextView) convertView.findViewById(R.id.orgName);
            viewHolder.addr = (TextView) convertView.findViewById(R.id.addr);
            viewHolder.city = (TextView) convertView.findViewById(R.id.city);
            viewHolder.distance_text = (TextView) convertView.findViewById(R.id.distance_text);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        AllStationDao.EvcStationsGetallEntity entity = ((AllStationDao)getItem(position)).getEvc_stations_getall();
        viewHolder.orgName.setText(entity.getOrgName());
        viewHolder.addr.setText(entity.getAddr());
        viewHolder.city.setText(entity.getCity());
        LatLng latLng1 = new LatLng(Double.parseDouble(entity.getLatitude()),Double.parseDouble(entity.getLongitude()));
        MyLocation location = application.getLocation();
        LatLng latLng2 = new LatLng(Double.parseDouble(location.getLatitude()),Double.parseDouble(location.getLongitude()));
        viewHolder.distance_text.setText(FloatUtil.mToKm(AMapUtils.calculateLineDistance(latLng1,latLng2))+"km");
        return convertView;
    }


}
