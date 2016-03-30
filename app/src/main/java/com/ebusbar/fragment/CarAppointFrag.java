package com.ebusbar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebusbar.fragments.UtilFragment;
import com.ebusbar.pile.R;

/**
 * Created by Jelly on 2016/3/10.
 */
public class CarAppointFrag extends UtilFragment {
    /**
     * TAG
     */
    public String TAG = "CarAppointFrag";
    /**
     * 根界面
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
        root = inflater.inflate(R.layout.carappoint,container,false);
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
}
