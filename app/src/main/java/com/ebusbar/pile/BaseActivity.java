package com.ebusbar.pile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.ebusbar.utils.ActivityControl;

/**
 * BaseActivity
 * Created by Jelly on 2016/3/1.
 */
public abstract class BaseActivity extends FragmentActivity{
    /**
     * TAG
     */
    public String TAG="BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityControl.add(this);
    }

    /**
     * 初始化控件
     */
    public abstract void init();

    /**
     * 加载Activity中的属性,比如：Application和数据持久化的对象
     */
    public abstract void loadObjectAttribute();

    /**
     * 在这里设置各种各样的控件监听器
     */
    public abstract void setListener();

    /**
     * 设置Activity需要显示的数据
     */
    public abstract void setActivityView();


    /**
     * 返回上一界面
     * @param view
     * @return
     */
    public View back(View view){
        ActivityControl.finishAct(BaseActivity.this);
        return view;
    }

    public String getTAG() {
        return TAG;
    }
}
