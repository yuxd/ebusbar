package com.ebusbar.utils;

import android.util.Log;

/**
 * 日志工具
 * Created by Jelly on 2016/3/30.
 */
public class LogUtil{

    /**
     * 师是否是调试输出
     */
    private static boolean isDebug = true;


    /**
     * 打印最详细的信息
     * @param tag
     * @param msg
     */
    public static void v(String tag,String msg){
        if(isDebug){
            Log.v(tag,msg);
        }
    }

    /**
     * 打印Debug日志
     * @param tag
     * @param msg
     */
    public static void d(String tag,String msg){
        if(isDebug){
            Log.d(tag,msg);
        }
    }

    /**
     * 打印Info的日志
     * @param tag
     * @param msg
     */
    public static void i(String tag,String msg){
        if(isDebug){
            Log.i(tag,msg);
        }
    }

    /**
     * 打印警告
     * @param tag
     * @param msg
     */
    public static void w(String tag,String msg){
        if(isDebug){
            Log.w(tag,msg);
        }
    }

    /**
     * 打印错误
     * @param tag
     * @param msg
     */
    public static void e(String tag,String msg){
        if(isDebug){
            Log.e(tag,msg);
        }
    }

}
