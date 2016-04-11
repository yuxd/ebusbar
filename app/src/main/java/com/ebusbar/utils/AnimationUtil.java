package com.ebusbar.utils;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * Created by Jelly on 2016/4/10.
 */
public class AnimationUtil {


    public static Animation startTabLineAnimation(WindowUtil windowUtil, Activity context, int previousPosition, int position, int fragmentCount){
        //设置线条移动的动画
        TranslateAnimation translateAnimation = new TranslateAnimation(previousPosition,position*windowUtil.getScreenWidth(context)/fragmentCount,0,0);
        translateAnimation.setDuration(500);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new LinearInterpolator());
        return translateAnimation;
    }

}
