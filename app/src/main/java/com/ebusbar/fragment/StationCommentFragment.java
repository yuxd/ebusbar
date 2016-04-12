package com.ebusbar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebusbar.fragments.UtilFragment;
import com.ebusbar.pile.R;

/**
 * 电站评论
 * Created by Jelly on 2016/4/10.
 */
public class StationCommentFragment extends UtilFragment{

    public String TAG = "StationCommentFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        root = inflater.inflate(R.layout.station_comment_fragment,container,false);
        init();
        loadObjectAttribute();
        setListener();
        setFragView();
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
}
