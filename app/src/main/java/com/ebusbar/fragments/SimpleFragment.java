package com.ebusbar.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebusbar.pile.MyApplication;

/**
 * Created by Jelly on 2016/3/30.
 */
public abstract class SimpleFragment extends BaseFragment{
    /**
     * Application
     */
    public MyApplication application;
    /**
     * Context
     */
    public Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        initFragment();
        return root;
    }

    /**
     * 初始化数据
     */
    public void initFragment(){
        application = (MyApplication) getActivity().getApplication();
        context = getContext();
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
    public abstract void setFragView();


}
