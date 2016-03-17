package com.ebusbar.impl;

import android.content.Context;
import android.os.Handler;

import com.jellycai.service.URLService;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础Impl
 * Created by Jelly on 2016/3/3.
 */
public abstract class BaseImpl {
    protected Context context;
    /**
     * 网络访问操作
     */
    protected URLService service;
    /**
     * 网络访问参数
     */
    protected Map<String,String> param;
    /**
     * 沟通消息
     */
    protected int msg;
    /**
     * Handler
     */
    protected Handler handler;

    /**
     * 需要访问网络数据，需要实现的构造函数
     * @param context
     * @param handler
     * @param msg
     */
    public BaseImpl(Context context,Handler handler,int msg){
        this.context = context;
        service = new URLService(context);
        param = new HashMap<String,String>();
        this.handler = handler;
        this.msg = msg;
    }

    /**
     * 只需要获取缓数据，需要实现的构造函数
     * @param context
     */
    public BaseImpl(Context context){
        this.context = context;
    }

}
