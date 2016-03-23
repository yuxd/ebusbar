package com.ebusbar.pile;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 充电点详情
 * Created by Jelly on 2016/3/23.
 */
public class OrgInfoActivity extends BaseActivity{

    public String TAG = "OrgInfoActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.orginfo);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {

    }

    @Override
    public void loadObjectAttribute() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void setActivityView() {

    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
