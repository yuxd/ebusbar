package com.ebusbar.utils;

import java.text.DecimalFormat;

/**
 * Created by Jelly on 2016/3/29.
 */
public class FloatUtil {

    /**
     * 把M转换为KM
     * @param m
     * @return
     */
    public static String mToKm(float m){
        DecimalFormat df   = new DecimalFormat("######0.00");
        float km = m/1000;
        return df.format(km);
    }

}
