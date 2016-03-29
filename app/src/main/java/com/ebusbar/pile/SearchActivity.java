package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.dao.PositionListItemDao;

import java.util.ArrayList;

/**
 * 搜索电桩
 * Created by Jelly on 2016/3/28.
 */
public class SearchActivity extends UtilActivity {
    /**
     * TAG
     */
    public String TAG = "SearchActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.search);
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

    /**
     * 开启界面
     * @param context
     */
    public static void startAppActivity(Context context,ArrayList<PositionListItemDao> daos){
        Intent intent = new Intent(context,SearchActivity.class);
        intent.putParcelableArrayListExtra("daos",daos);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
