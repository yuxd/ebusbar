package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ebusbar.adpater.SelectPileListAdapter;

/**
 * Created by Jelly on 2016/3/15.
 */
public class SelectPileActivity extends BaseActivity{
    /**
     * TAG
     */
    public String TAG = "SelectPileActivity";
    /**
     * ListView
     */
    private ListView pile_list;

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
    public void init() {
        pile_list = (ListView) this.findViewById(R.id.pile_list);
    }

    @Override
    public void loadObjectAttribute() {

    }

    @Override
    public void setListener() {
        setOnItemClickListener();
    }

    @Override
    public void setActivityView() {
        pile_list.setAdapter(new SelectPileListAdapter(this));
    }

    /**
     * 设置列表的点击监听
     */
    public void setOnItemClickListener(){
        pile_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppointActivity.startAppActivity(SelectPileActivity.this);
            }
        });
    }

    /**
     * 开启界面
     */
    public static void startAppActivity(Context context){
        Intent intent = new Intent(context,SelectPileActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
