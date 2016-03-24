package com.ebusbar.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ebusbar.dao.PositionListItemDao;
import com.ebusbar.pile.R;

import java.util.List;

/**
 *
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View root = LayoutInflater.from(context).inflate(R.layout.nearby_item,null);
//        TextView name =

        return null;
    }
}
