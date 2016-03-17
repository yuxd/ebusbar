package com.ebusbar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebusbar.pile.R;

/**
 * 商店模块
 * Created by Jelly on 2016/2/25.
 */
public class ShopFrag extends BaseFrag{
    /**
     * TAG
     */
    private String TAG = "ShopFrag";
    /**
     * 返回的界面
     */
    private View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.shop,container,false);
        return root;
    }

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {

    }

    @Override
    public void loadObjectAttribute() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void setFragView() {

    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
