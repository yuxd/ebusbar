package com.ebusbar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jelly on 2016/3/2.
 */
public abstract class BaseFrag extends Fragment{
    /**
     * TAG
     */
    public String TAG="BaseFrag";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 初始化控件
     */
    public abstract void init(LayoutInflater inflater,ViewGroup container);

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


    public String getTAG() {
        return TAG;
    }
}