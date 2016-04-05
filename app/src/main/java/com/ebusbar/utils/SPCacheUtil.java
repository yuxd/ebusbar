package com.ebusbar.utils;

import android.content.Context;

import com.amap.api.maps.model.LatLng;
import com.ebusbar.map.MyLocation;
import com.ebusbar.param.SParam;

/**
 * Created by Jelly on 2016/3/30.
 */
public class SPCacheUtil {
    /**
     * 单例对象
     */
    private static SPCacheUtil spCacheUtil = new SPCacheUtil();
    /**
     * 单例对象
     */
    private SharedPreferencesUtil sharedPreferencesUtil = SharedPreferencesUtil.getInstance();
    /**
     * 城市代码KEY
     */
    private final String AdCodeKey = "AdCode";
    /**
     * 纬度Key
     */
    private final String LatKey = "Lat";
    /**
     * 经度key
     */
    private final String LongKey = "Long";

    /**
     * 获取单例对象
     * @return
     */
    public static SPCacheUtil getInstance(){
        return spCacheUtil;
    }


    /**
     * 缓存当前位置的对象
     * @param context
     * @param location
     */
    public void cacheMyLocation(Context context,MyLocation location){
        sharedPreferencesUtil.saveObject(context,location);
    }

    /**
     * 读取缓存中的位置对象
     * @param context
     */
    public MyLocation getMyLocation(Context context){
        LogUtil.v("fileName",MyLocation.class.getName());
        MyLocation location = (MyLocation) sharedPreferencesUtil.readObject(context, MyLocation.class.getName());
        return  location;
    }

    /**
     * 保存城市代码
     * @param context
     * @param AdCode 城市代码
     */
    public void cacheAdCode(Context context,String AdCode){
        sharedPreferencesUtil.saveString(context,SParam.位置缓存.getFileName(),AdCodeKey,AdCode);
    }

    /**
     * 获取缓存中的城市代码
     * @param context
     * @return
     */
    public String getAdCode(Context context){
        return sharedPreferencesUtil.readString(context,SParam.位置缓存.getFileName(),AdCodeKey);
    }

    /**
     * 保存当前位置的经纬度
     * @param context
     * @param latitude
     * @param longitude
     */
    public void cacheLatLong(Context context,String latitude,String longitude){
        sharedPreferencesUtil.saveString(context,SParam.位置缓存.getFileName(),LatKey,latitude);
        sharedPreferencesUtil.saveString(context,SParam.位置缓存.getFileName(),LongKey,longitude);
    }

    /**
     * 读取缓存中的经纬度
     * @param context
     * @return
     */
    public LatLng getLatLong(Context context){
        Double latitude = Double.parseDouble(sharedPreferencesUtil.readString(context, SParam.位置缓存.getFileName(), LatKey));
        Double longitude = Double.parseDouble(sharedPreferencesUtil.readString(context,SParam.位置缓存.getFileName(),LongKey));
        return new LatLng(latitude,longitude);
    }

}
