package com.ebusbar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebusbar.fragments.UtilFragment;
import com.ebusbar.pile.R;

/**
 * 服务模块
 * Created by Jelly on 2016/2/25.
 */
public class FixFragment extends UtilFragment {
    /**
     * TAG
     */
    private String TAG = "FixFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        root = inflater.inflate(R.layout.fix,container,false);
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
