package com.ebusbar.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jelly on 2016/3/2.
 */
public abstract class BaseFragment extends Fragment{
    /**
     * TAG
     */
    public String TAG="BaseFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    public String getTAG() {
        return TAG;
    }
}