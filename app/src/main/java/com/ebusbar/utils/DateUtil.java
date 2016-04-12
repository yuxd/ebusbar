package com.ebusbar.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jelly on 2016/4/9.
 */
public class DateUtil {

    /**
     * 根据字符串获取日期对象
     * @param dateStr
     * @return
     */
    public static Date getDate(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取指定格式的日期字符串
     * @param date
     * @param sdfStr
     * @return
     */
    public static String getSdfDate(Date date,String sdfStr){
        SimpleDateFormat sdf = new SimpleDateFormat(sdfStr);
        String dateStr = sdf.format(sdf);
        return dateStr;
    }


    /**
     * 获取指定格式的日期字符串
     * @param dateStr
     * @param sdfStr
     * @return
     */
    public static String getSdfDate(String dateStr,String sdfStr){
        SimpleDateFormat sdf = new SimpleDateFormat(sdfStr);
        String result = sdf.format(getDate(dateStr));
        return result;
    }


    /**
     * 计算两个日期之差
     * @param dateStr1
     * @param dateStr2
     * @return
     */
    public static String DifferDate(String dateStr1,String dateStr2){
        Date date1 = getDate(dateStr1);
        Date date2 = getDate(dateStr2);
        Long dateL = date1.getTime() - date2.getTime();
        Date dateResult = new Date(dateL);
        SimpleDateFormat sdf = new SimpleDateFormat("mm");
        String result = sdf.format(dateResult);
        if(TextUtils.equals(result,"00")){
            result = "60";
        }
        return result;
    }

}
