package com.ebusbar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ebusbar.fragments.BaseFragment;
import com.ebusbar.fragments.SimpleFragment;
import com.ebusbar.pile.MainActivity;
import com.ebusbar.pile.R;
import com.ebusbar.pile.SearchActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jelly on 2016/3/24.
 */
public class Tab1Fragment extends SimpleFragment {
    /**
     * TAG
     */
    public String TAG = "Tab1Fragment";
    @Bind(R.id.actionBar_layout)
    RelativeLayout actionBarLayout;
    /**
     * 根布局
     */
    private View root;


    /**
     * 所有电桩的fragment
     */
    private StationFragment stationFragment = new StationFragment();
    /**
     * 附近的电桩的Fragment
     */
    private NearbyStationFragment nearbyStationFragment = new NearbyStationFragment();
    /**
     * ActionBar的高度
     */
    private int actionBarHeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        root = inflater.inflate(R.layout.tab1, container, false);
        init();
        loadObjectAttribute();
        setListener();
        setFragView();
        ButterKnife.bind(this, root);
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
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        if (stationFragment.isAdded()) {
            ft.show(stationFragment);
        } else if (nearbyStationFragment.isAdded()) {
            ft.show(nearbyStationFragment);
        } else {
            ft.add(R.id.content, stationFragment);
        }
        ft.commit();
    }

    @OnClick({R.id.member, R.id.map, R.id.search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.member:
                MainActivity activity = (MainActivity) getActivity();
                activity.drawerLayout.openDrawer(Gravity.LEFT); //打开左边抽屉
                break;
            case R.id.map:
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                if (stationFragment.isVisible()) {
                    ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
                    switchFragment(ft,stationFragment, nearbyStationFragment);
                } else {
                    ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_out);
                    switchFragment(ft,nearbyStationFragment, stationFragment);
                }
                break;
            case R.id.search:
                SearchActivity.startAppActivity(context);
                break;
        }
    }

    /**
     * 不需重新加载切换Fragment
     * @param ft
     * @param from
     * @param to
     */
    public void switchFragment(FragmentTransaction ft,BaseFragment from, BaseFragment to) {
        if (!to.isAdded()) {
            ft.hide(from);
            ft.add(R.id.content, to);
        } else {
            ft.hide(from);
            ft.show(to);
        }
        ft.commit();
    }

    /**
     * 获得ActionBar的高度
     * @return
     */
    public int getActionBarHeight() {
        if (actionBarHeight != 0) {
            return actionBarHeight;
        }
        if(actionBarLayout != null){
            actionBarHeight = actionBarLayout.getHeight();
        }
        return actionBarHeight;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
