package com.ebusbar.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ebusbar.pile.MyApplication;
import com.ebusbar.utils.ActivityControl;

/**
 * 在这里提出一些公用的参数
 * Created by Jelly on 2016/3/29.
 */
public abstract class SimpleActivity extends BaseActivity {
    /**
     * 上下文
     */
    public Context context;
    /**
     * Application
     */
    public MyApplication application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
    }
    /**
     * 初始化BaseActivity
     */
    public void initActivity(){
        context = this; //在这里把上This转换为Context
        application = (MyApplication) getApplication();
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
        ActivityControl.finishAct(SimpleActivity.this);
        return view;
    }
}
