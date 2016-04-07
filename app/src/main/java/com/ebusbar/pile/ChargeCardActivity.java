package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.adpater.ChargeCardListAdapter;
import com.ebusbar.bean.AddChargeCard;
import com.ebusbar.bean.ChargeCardItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 充电卡显示
 * Created by Jelly on 2016/3/23.
 */
public class ChargeCardActivity extends UtilActivity {

    public String TAG = "ChargeCardActivity";
    /**
     * 显示列表
     */
    private ListView list;
    /**
     * 充电卡数据集合
     */
    private List<ChargeCardItem> daos;
    /**
     * 适配器
     */
    private ChargeCardListAdapter adapter;
    /**
     * 添加充电卡
     */
    public static final int ADD = 0x001;
    /**
     * 删除充电卡
     */
    public static final int DELETE = 0x002;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.chargecard);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {
        list = (ListView) this.findViewById(R.id.list);
    }

    @Override
    public void loadObjectAttribute() {
        application = (MyApplication) getApplication();
        daos = getIntent().getParcelableArrayListExtra("daos");
        adapter = new ChargeCardListAdapter(this,daos);
    }

    @Override
    public void setListener() {
        setCardItemClickListener();
    }

    @Override
    public void setActivityView() {
        list.setAdapter(adapter);
    }

    /**
     * 设置单个列表的点击事件
     */
    public void setCardItemClickListener(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteChargeCardActivity.startAppActivity(ChargeCardActivity.this, daos.get(position), DELETE);
            }
        });
    }

    /**
     * 在每次获取焦点的时候更新列表
     */
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged(); //更新列表
    }

    /**
     * 增加充电卡
     * @param view
     * @return
     */
    public View addCard(View view){
        AddChargeCardActivity.startAppActivity(this, ADD);
        return view;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case ADD:
                if(resultCode == AddChargeCardActivity.SUCCESS){ //添加充电卡成功
                    Toast.makeText(ChargeCardActivity.this, "添加充电卡成功！", Toast.LENGTH_SHORT).show();
                    AddChargeCard dao = data.getParcelableExtra("AddChargeCardDao");
                    AddChargeCard.CrmAccountsInsertEntity insertEntity = dao.getCrm_accounts_insert();
                    ChargeCardItem chargeCardItemDao = new ChargeCardItem();
                    ChargeCardItem.CrmAccountsGetEntity entity = new ChargeCardItem.CrmAccountsGetEntity();
                    entity.setAccountID(insertEntity.getAccountID());
                    entity.setAccountNo(insertEntity.getAccountNo());
                    entity.setAccountType(insertEntity.getAccountType());
                    entity.setCustId(insertEntity.getCustId());
                    entity.setIsSuccess(insertEntity.getIsSuccess());
                    entity.setReturnStatus(insertEntity.getReturnStatus());
                    chargeCardItemDao.setCrm_accounts_get(entity);
                    daos.add(chargeCardItemDao);
                    adapter.notifyDataSetChanged(); //更新数据
                }else if(resultCode == AddChargeCardActivity.FAILURE){ //添加充电卡失败
                    Toast.makeText(ChargeCardActivity.this,"添加充电卡失败！",Toast.LENGTH_SHORT).show();
                }
                break;
            case DELETE:
                if(resultCode == DeleteChargeCardActivity.SUCCESS){//删除充电卡成功
                    Toast.makeText(ChargeCardActivity.this,"删除充电卡成功",Toast.LENGTH_SHORT).show();
                    ChargeCardItem chargeCardItemDao = data.getParcelableExtra("DeleteChargeCardDao");
                    int index = getIndexFromList(chargeCardItemDao);
                    daos.remove(index);
                    adapter.notifyDataSetChanged();
                }else if(resultCode == DeleteChargeCardActivity.FAILURE){ //删除充电卡失败
                    Toast.makeText(ChargeCardActivity.this,"删除充电卡失败",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 通过数据Dao获取数据集合中的Index
     * @param chargeCardItemDao
     * @return
     */
    public int getIndexFromList(ChargeCardItem chargeCardItemDao){
        for(int i=0;i<daos.size();i++){
            if(TextUtils.equals(daos.get(i).getCrm_accounts_get().getAccountID(),chargeCardItemDao.getCrm_accounts_get().getAccountID())){
                return i;
            }
        }
        return 0;
    }

    /**
     * 开启界面
     */
    public static void startAppActivity(Context context,ArrayList<ChargeCardItem> daos){
        Intent intent = new Intent(context,ChargeCardActivity.class);
        intent.putParcelableArrayListExtra("daos",daos);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
