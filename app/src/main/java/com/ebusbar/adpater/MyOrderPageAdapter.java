package com.ebusbar.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ebusbar.fragment.FinishOrderFrag;
import com.ebusbar.fragment.PendingOrderFrag;

/**
 * Created by Jelly on 2016/3/10.
 */
public class MyOrderPageAdapter extends FragmentPagerAdapter{

    public MyOrderPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new PendingOrderFrag();
        }else if(position == 1){
            return  new FinishOrderFrag();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
