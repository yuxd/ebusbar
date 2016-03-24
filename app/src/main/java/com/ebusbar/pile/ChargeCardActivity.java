package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ebusbar.adpater.ChargeCardListAdapter;
import com.ebusbar.dao.ChargeCardItemDao;
import com.ebusbar.dao.LoginDao;
import com.ebusbar.impl.ChargeCardItemDaoImpl;

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
     * ChargeCardItemDaoImpl
     */
    private ChargeCardItemDaoImpl chargeCardItemDao;
    /**
     * 获取充电卡消息
     */
    private final int msgCharge = 0x001;
    /**
     * Application
     */
    private MyApplication application;
    /**
     * 适配器
     */
    private ChargeCardListAdapter adapter;

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
        chargeCardItemDao = new ChargeCardItemDaoImpl(this,handler,msgCharge);
    }

    @Override
    public void setListener() {
        setCardItemClickListener();
    }

    @Override
    public void setActivityView() {
        LoginDao.CrmLoginEntity entity = application.getLoginDao().getCrm_login();
        chargeCardItemDao.getChargeCardItemDao(entity.getToken(),entity.getCustID());
    }

    /**
     * 设置单个列表的点击事件
     */
    public void setCardItemClickListener(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteChargeCardActivity.startAppActivity(ChargeCardActivity.this,chargeCardItemDao.chargeCardItemDaos.get(position));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setActivityView();
    }

    /**
     * 增加充电卡
     * @param view
     * @return
     */
    public View addCard(View view){
        AddChargeCardActivity.startAppActivity(this);
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgCharge:
                    if(chargeCardItemDao.chargeCardItemDaos.size() == 0){
                        return;
                    }
                    List<ChargeCardItemDao> daos = new ArrayList<>(chargeCardItemDao.chargeCardItemDaos);
                    adapter = new ChargeCardListAdapter(ChargeCardActivity.this,daos);
                    list.setAdapter(adapter);
                    break;
            }
        }
    };

    /**
     * 开启界面
     */
    public static void startAppActivity(Context context){
        Intent intent = new Intent(context,ChargeCardActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
