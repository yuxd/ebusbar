package com.ebusbar.impl;

import android.content.Context;
import android.os.Handler;

import com.ebusbar.param.NetParam;
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
     * 访问路径
     */
    protected String path = NetParam.path;
    /**
     * 接口标示
     */
    protected String trancode = "APPAPI000";
    /**
     * 端口
     */
    protected String mode = "4";
    /**
     * 时间戳
     */
    protected String timestamp;
    /**
     * 签名方法
     */
    protected String sign_method = "MD5";
    /**
     * 签名结果
     */
    protected String sign = "sign";
    /**
     * 接口名称
     */
    protected String execmode;
    /**
     * 返回字段
     */
    protected String fields = "fields";
    /**
     * 请求条件
     */
    protected String condition;
    /**
     * 拼接条件的Map
     */
    protected Map<String,String> conditionMap;
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
        param = new HashMap<String,String>(); //这里是多余的初始化，以后必须去掉
        conditionMap = new HashMap<String,String>(); //拼接条件
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
