package com.ebusbar.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.ebusbar.utils.ActivityControl;

/**
 * BaseActivity
 * Created by Jelly on 2016/3/1.
 */
public abstract class BaseActivity extends FragmentActivity{
    /**
     * TAG
     */
    public String TAG="BaseActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityControl.add(this); //添加到管理中心
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //禁止横屏
    }

    public String getTAG() {
        return TAG;
    }

}
