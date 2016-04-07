package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.adpater.SelectPileListAdapter;
import com.ebusbar.bean.PileListItem;
import com.ebusbar.impl.PileListItemDaoImpl;
import com.ebusbar.utils.ActivityControl;

/**
 * Created by Jelly on 2016/3/15.
 */
public class SelectPileActivity extends UtilActivity {
    /**
     * TAG
     */
    public String TAG = "SelectPileActivity";
    /**
     * ListView
     */
    private ListView pile_list;
    /**
     * PileListItemDaoImpl
     */
    private PileListItemDaoImpl pileListItemDao;
    /**
     * 充电桩详情消息
     */
    public static final int msgPiles = 0x001;
    /**
     * Intent
     */
    private Intent intent;
    /**
     * 预约
     */
    private static final int APPOINT = 0x002;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.selectpile);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void init() {
        pile_list = (ListView) this.findViewById(R.id.pile_list);
    }

    @Override
    public void loadObjectAttribute() {
        intent = getIntent();
        pileListItemDao = new PileListItemDaoImpl(this,handler,msgPiles);
    }

    @Override
    public void setListener() {
        setOnItemClickListener();
    }

    @Override
    public void setActivityView() {
        pileListItemDao.getPiles(intent.getStringExtra("OrgId"));
    }

    /**
     * 设置列表的点击监听
     */
    public void setOnItemClickListener(){
        pile_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PileListItem.EvcFacilitiesGetEntity data = pileListItemDao.piles.get(position).getEvc_facilities_get();
                if (!TextUtils.equals("0",data.getFacilityStatus())) {
                    Toast.makeText(SelectPileActivity.this,"有人正在使用，请您稍等片刻",Toast.LENGTH_SHORT).show();
                    return;
                }
                AppointActivity.startAppActivity(SelectPileActivity.this, data.getOrgName(), data.getFacilityID(), data.getFacilityName(), APPOINT);
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgPiles: //获取充电点中的电桩列表
                    if(pileListItemDao.piles.size() == 0){
                        return;
                    }
                    pile_list.setAdapter(new SelectPileListAdapter(SelectPileActivity.this,pileListItemDao.piles));
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case APPOINT:
                if(resultCode == AppointActivity.SUCCESS){
                    ActivityControl.finishAct(SelectPileActivity.this);
                    MyAppointActivity.startAppActivity(SelectPileActivity.this);
                }else if(resultCode == AppointActivity.FAILURE){
                    setActivityView();
                }
                break;
        }
    }

    /**
     * 开启界面
     */
    public static void startAppActivity(Context context,String OrgId){
        Intent intent = new Intent(context,SelectPileActivity.class);
        intent.putExtra("OrgId",OrgId);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
