package com.ebusbar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebusbar.fragments.UtilFragment;
import com.ebusbar.pile.R;

/**
 * 附近租车
 * Created by Jelly on 2016/4/12.
 */
public class NearbyRentCarFragment extends UtilFragment{

    public String TAG = "NearbyRentCarFragment";

    private View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.nearby_rentcar,container,false);
        return root;
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
    public void setFragView() {

    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
