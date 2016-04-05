package com.ebusbar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebusbar.fragments.SimpleFragment;
import com.ebusbar.pile.R;

/**
 * Created by Jelly on 2016/3/24.
 */
public class Tab1Fragment extends SimpleFragment {
    /**
     * TAG
     */
    public String TAG = "Tab1Fragment";
    /**
     * 根布局
     */
    private View root;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        init(inflater,container);
        loadObjectAttribute();
        setListener();
        setFragView();
        return root;
    }

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.tab1,container,false);
    }

    @Override
    public void loadObjectAttribute() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void setFragView() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.content,new AllStationFragment());
        ft.commit();
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
