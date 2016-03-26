package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ebusbar.adpater.ChargeCardListAdapter;
import com.ebusbar.dao.AddChargeCardDao;
import com.ebusbar.dao.ChargeCardItemDao;

import java.util.ArrayList;
import java.util.List;

/**
 * 充电卡显示
 * Created by Jelly on 2016/3/23.
 */
public class ChargeCardActivity extends BaseActivity{

    public String TAG = "ChargeCardActivity";
    /**
     * 显示列表
     */
    private ListView list;
    /**
     * 充电卡数据集合
     */
    private List<ChargeCardItemDao> daos;
    /**
     * Application
     */
    private MyApplication application;
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
                DeleteChargeCardActivity.startAppActivity(ChargeCardActivity.this, daos.get(position));
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
        AddChargeCardActivity.startAppActivity(this,DELETE);
        return view;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case DELETE:
                if(resultCode == AddChargeCardActivity.SUCCESS){
                    Toast.makeText(ChargeCardActivity.this, "添加充电卡成功！", Toast.LENGTH_SHORT).show();
                    AddChargeCardDao dao = data.getParcelableExtra("AddChargeCardDao");
                    AddChargeCardDao.CrmAccountsInsertEntity insertEntity = dao.getCrm_accounts_insert();
                    ChargeCardItemDao chargeCardItemDao = new ChargeCardItemDao();
                    ChargeCardItemDao.CrmAccountsGetEntity entity = new ChargeCardItemDao.CrmAccountsGetEntity();
                    entity.setAccountID(insertEntity.getAccountID());
                    entity.setAccountNo(insertEntity.getAccountNo());
                    entity.setAccountType(insertEntity.getAccountType());
                    entity.setCustId(insertEntity.getCustId());
                    entity.setIsSuccess(insertEntity.getIsSuccess());
                    entity.setReturnStatus(insertEntity.getReturnStatus());
                    chargeCardItemDao.setCrm_accounts_get(entity);
                    daos.add(chargeCardItemDao);
                    adapter.notifyDataSetChanged(); //更新数据
                }else if(resultCode == AddChargeCardActivity.FAILURE){
                    Toast.makeText(ChargeCardActivity.this,"添加充电卡失败！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 开启界面
     */
    public static void startAppActivity(Context context,ArrayList<ChargeCardItemDao> daos){
        Intent intent = new Intent(context,ChargeCardActivity.class);
        intent.putParcelableArrayListExtra("daos",daos);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
