package com.ebusbar.adpater;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ebusbar.dao.CompleteOrderDao;
import com.ebusbar.dao.LoginDao;
import com.ebusbar.fragment.FinishOrderFrag;
import com.ebusbar.impl.DeleteOrderDaoImpl;
import com.ebusbar.pile.R;

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

    private FinishOrderFrag frag;

    public AllOrderListAdapter(Context context,List<CompleteOrderDao> list,LoginDao loginDao,FinishOrderFrag frag) {
        this.context = context;
        this.list = list;
        this.loginDao = loginDao;
        this.frag = frag;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View item = LayoutInflater.from(context).inflate(R.layout.finishorder_list_item,null);
        TextView type_text = (TextView) item.findViewById(R.id.type_text);
        TextView state_text = (TextView) item.findViewById(R.id.state_text);
        TextView position_text = (TextView) item.findViewById(R.id.position_text);
        TextView order_time = (TextView) item.findViewById(R.id.order_time);
        TextView EPId_text = (TextView) item.findViewById(R.id.EPId_text);
        final TextView delete = (TextView) item.findViewById(R.id.delete);
        final CompleteOrderDao.EvcOrdersGetEntity data = ((CompleteOrderDao)getItem(position)).getEvc_orders_get();
        if(TextUtils.equals(data.getOrderType(),"2")){
            type_text.setText("电桩充电");
        }
        if(TextUtils.equals(data.getOrderStatus(),"8")){
            state_text.setText("充电完成");
        }else{
            state_text.setText("已取消");
        }
        position_text.setText(data.getOrgName());
        order_time.setText(data.getPlanEndDateTime());
        EPId_text.setText(data.getOrgID());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //删除订单
                LoginDao.CrmLoginEntity entity = loginDao.getCrm_login();
                deletePosition = position;
                deleteOrderDao.getDeleteOrderDao(entity.getToken(),data.getOrderNo(),entity.getCustID());
            }
        });
        return item;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgDelete:
                    if(deleteOrderDao.deleteOrderDao == null|| TextUtils.equals(deleteOrderDao.deleteOrderDao.getEvc_order_change().getIsSuccess(),"N")){
                        return;
                    }
                    list.remove(deletePosition);
                    notifyDataSetChanged();
                    break;
            }
        }
    };

}
