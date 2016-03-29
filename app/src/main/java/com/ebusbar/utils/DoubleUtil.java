package com.ebusbar.utils;

import java.text.DecimalFormat;

/**
 * Created by Jelly on 2016/3/28.
 */
public class DoubleUtil {

    public static String add(String... args){
        DecimalFormat df   = new DecimalFormat("######0.00");
        Double sum = 0.0;
        for(int i=0;i<args.length;i++){
            sum += Double.parseDouble(args[i]);
        }
        return df.format(sum);
    }

    public static String delete(String... args){
        DecimalFormat df   = new DecimalFormat("######0.00");
        Double sum = Double.parseDouble(args[0]);
        for(int i=1;i<args.length;i++){
            sum -= Double.parseDouble(args[i]);
        }
        return df.format(sum);
    }

}
