package com.ebusbar.adpater;

import android.content.Context;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View root = LayoutInflater.from(context).inflate(R.layout.nearby_item,null);
        TextView name = (TextView) root.findViewById(R.id.name);
        TextView position_text = (TextView) root.findViewById(R.id.position_text);
        TextView open_text = (TextView) root.findViewById(R.id.open_text);
        TextView pile_text = (TextView) root.findViewById(R.id.pile_text);
        TextView appoint = (TextView) root.findViewById(R.id.appoint);
        return root;
    }
}
