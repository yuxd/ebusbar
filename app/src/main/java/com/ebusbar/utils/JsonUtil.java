package com.ebusbar.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Json的工具类
 * Created by Jelly on 2016/3/4.
 */
public class JsonUtil {


    /**
     * 将Json字符串转成对象
     * @param json
     * @param tClass
     * @param <T>
     * @return Object
     */
    public static <T> T objectFromJson(String json,Class<T> tClass){
        if(TextUtils.isEmpty(json) || tClass == null) return null;
        Gson gson = new Gson();
        return gson.fromJson(json, tClass);
    }

    /**
     * 将Json字符串解析成集合
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> arrayFormJson(String json,Class<T[]> tClass){
        T[] list = new Gson().fromJson(json,tClass);
        ArrayList<T> arrayList = new ArrayList<>(Arrays.asList(list));
        return arrayList;
    }

    /**
     * 请大神告诉我为什么会出错？？ QQ：2574266978
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> errorFormJson(String json,Class<T> tClass){
        ArrayList<T> list = new Gson().fromJson(json,new TypeToken<ArrayList<T>>(){}.getType());
        return list;
    }
}
