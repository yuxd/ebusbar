package com.ebusbar.adpater;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ebusbar.dao.CompleteOrderDao;
import com.ebusbar.dao.LoginDao;
import com.ebusbar.impl.DeleteOrderDaoImpl;
import com.ebusbar.pile.R;
import com.ebusbar.utils.DialogUtil;

import java.util.List;

/**
 * Created by Jelly on 2016/3/10.
 */
public class AllOrderListAdapter extends BaseAdapter{
    /**
     * 数据集合
     */
    private List<CompleteOrderDao> list;
    /**
     * Context
     */
    private Context context;
    /**
     * DeleteOrderDaoImpl
     */
    private DeleteOrderDaoImpl deleteOrderDao;
    /**
     * 删除消息
     */
    private final int msgDelete = 0x001;
    /**
     * 登录记录
     */
    private LoginDao loginDao;
    /**
     * 当前需要删除的数据
     */
    private int deletePosition;
    /**
     * Dialog操作工具
     */
    private DialogUtil dialogUtil = DialogUtil.getInstance();

    public AllOrderListAdapter(Context context,List<CompleteOrderDao> list,LoginDao loginDao) {
        this.context = context;
        this.list = list;
        this.loginDao = loginDao;
        deleteOrderDao = new DeleteOrderDaoImpl(context,handler,msgDelete);
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

    private class ViewHolder {
        TextView type_text;
        TextView state_text;
        TextView position_text;
        TextView order_time;
        TextView EPId_text;
        TextView delete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.finishorder_list_item, null);
            viewHolder.type_text  = (TextView) convertView.findViewById(R.id.type_text);
            viewHolder.state_text = (TextView) convertView.findViewById(R.id.state_text);
            viewHolder.position_text = (TextView) convertView.findViewById(R.id.position_text);
            viewHolder.order_time = (TextView) convertView.findViewById(R.id.order_time);
            viewHolder.EPId_text = (TextView) convertView.findViewById(R.id.EPId_text);
            viewHolder.delete = (TextView) convertView.findViewById(R.id.delete);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final CompleteOrderDao.EvcOrdersGetEntity data = ((CompleteOrderDao)getItem(position)).getEvc_orders_get();
        if(TextUtils.equals(data.getOrderStatus(),"8")){
            viewHolder.state_text.setText("充电完成");
        }else{
            viewHolder.state_text.setText("已取消");
        }
        viewHolder.position_text.setText(data.getOrgName());
        viewHolder.order_time.setText(data.getPlanEndDateTime());
        viewHolder.EPId_text.setText(data.getOrgID());
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //删除订单

                dialogUtil.showSureListenerDialog(context, "是否确认删除订单！", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        LoginDao.DataEntity entity = loginDao.getData();
                        deletePosition = position;
                        deleteOrderDao.getDeleteOrderDao(entity.getToken(), data.getOrderNo(), entity.getCustID());
                    }
                });
            }
        });
        return convertView;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgDelete:
                    if(deleteOrderDao.deleteOrderDao == null|| TextUtils.equals(deleteOrderDao.deleteOrderDao.getEvc_order_delete().getIsSuccess(),"N")){
                        Toast.makeText(context,"对不起，删除订单失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    list.remove(deletePosition);
                    notifyDataSetChanged();
                    break;
            }
        }
    };

}
