package com.ebusbar.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ebusbar.fragment.CarAppointFrag;
import com.ebusbar.fragment.ChargeAppointFrag;
import com.ebusbar.fragment.FixAppointFrag;

/**
 * Created by Jelly on 2016/3/10.
 */
public class MyAppointPageAdapter extends FragmentPagerAdapter{

    public MyAppointPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return  new ChargeAppointFrag();
        }else if(position == 1){
            return  new CarAppointFrag();
        }else if(position == 2){
            return  new FixAppointFrag();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
