package com.ebusbar.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * 资源操作工具
 * 获取资源文件中和字符串，颜色等
 * Created by Jelly on 2016/3/12.
 */
public class ResourceUtil {
    /**
     * 资源操作工具
     */
    private static ResourceUtil resourceUtil = new ResourceUtil();

    /**
     * 获得单例对象
     * @return
     */
    public static ResourceUtil getInstance(){
        return resourceUtil;
    }

    /**
     * 获得资源文件中的字符串
     * @return s 资源字符串
     */
    public String getResourceString(Context context,int stringId){
        if(context == null || stringId == 0){
            return null;
        }
        Resources resources = context.getResources();
        return resources.getString(stringId);
    }

    /**
     * 获取资源文件中的颜色
     * @return
     */
    public int getResourceColor(Context context,int colorId){
        if(context == null || colorId == 0){
            return 0;
        }
        Resources resources = context.getResources();
        return resources.getColor(colorId);
    }


}
