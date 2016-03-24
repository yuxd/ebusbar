package com.ebusbar.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ebusbar.dao.ChargeCardItemDao;
import com.ebusbar.pile.R;

import java.util.List;

/**
 * Created by Jelly on 2016/3/23.
 */
public class ChargeCardListAdapter extends BaseAdapter{

    private List<ChargeCardItemDao> list;

    private Context context;

    public ChargeCardListAdapter(Context context,List<ChargeCardItemDao> list) {
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
        View root = LayoutInflater.from(context).inflate(R.layout.chargecard_item,null);
        TextView no = (TextView) root.findViewById(R.id.no);
        ChargeCardItemDao dao = (ChargeCardItemDao) getItem(position);
        no.setText(dao.getCrm_accounts_get().getAccountNo());
        return root;
    }
}
