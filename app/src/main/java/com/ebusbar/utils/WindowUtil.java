package com.ebusbar.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * 窗体操作工具
 * 界面背景颜色的修改，控件x,y坐标的获取，屏幕宽和高的获取
 * Created by Jelly on 2016/3/12.
 */
public class WindowUtil {
    /**
     * 窗体操作工具对象
     */
    private static WindowUtil windowUtil = new WindowUtil();

    /**
     * 获得单例对象
     * @return
     */
    public static WindowUtil getInstance(){
        return windowUtil;
    }


    /**
     * 获取屏幕的宽
     * @param context Context
     * @return 屏幕的宽
     */
    public int getScreenWidth(Activity context){
        if(context == null){
            return 0;
        }
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高
     * @param context Context
     * @return 屏幕的高
     */
    public int getScreenHeight(Activity context){
        if(context == null){
            return 0;
        }
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取控件的位置
     * @param view 控件View
     * @return int[] x,y
     */
    public int[] getViewLocation(View view){
        int[] location = new int[2]; //获取筛选按钮的x坐标
        view.getLocationOnScreen(location);
        return location;
    }
}
