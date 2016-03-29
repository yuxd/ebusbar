package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.adpater.BillListAdapter;
import com.ebusbar.dao.BillDao;

import java.util.ArrayList;
import java.util.List;

/**
 * 消费账单
 * Created by Jelly on 2016/3/23.
 */
public class BillActivity extends UtilActivity {
    /**
     * TAG
     */
    public String TAG = "BillActivity";
    /**
     * LstView
     */
    private ListView list;
    /**
     * 适配器
     */
    private BillListAdapter adapter;
    /**
     * 账单数据集合
     */
    private List<BillDao> daos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.bill);
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
        application = (MyApplication) this.getApplication();
        daos = getIntent().getParcelableArrayListExtra("daos");
        adapter = new BillListAdapter(this,daos);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setActivityView() {
        list.setAdapter(adapter);
    }

    /**
     * 开启界面
     */
    public static void startAppActivity(Context context,ArrayList<BillDao> daos){
        Intent intent = new Intent(context,BillActivity.class);
        intent.putParcelableArrayListExtra("daos",daos);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
