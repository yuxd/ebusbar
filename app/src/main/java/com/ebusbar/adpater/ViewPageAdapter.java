package com.ebusbar.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ebusbar.fragments.BaseFragment;

/**
 * Created by Jelly on 2016/3/10.
 */
public class ViewPageAdapter extends FragmentPagerAdapter{

    private BaseFragment[] fragments;

    public ViewPageAdapter(FragmentManager fm,BaseFragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if(fragments != null){
            return fragments[position];
        }
        return null;
    }

    @Override
    public int getCount() {
        if(fragments != null){
            return fragments.length;
        }
        return 0;
    }
}
