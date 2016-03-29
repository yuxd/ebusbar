package com.ebusbar.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 错误参数
 * Created by Jelly on 2016/3/3.
 */
public class ErrorParam {
    static{
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("100","操作成功!");
        errorMap.put("101","用户已经注册!");
        errorMap.put("102","登录密码格式错误!");
        errorMap.put("103","支付密码格式错误!");
        errorMap.put("104","用户已经加入黑名单!");
        errorMap.put("105","验证码错误!");
        errorMap.put("106","手机号码格式错误!");
        errorMap.put("500","访问超时!");
        errorMap.put("600","参数错误!");
        errorMap.put("108","登录验证码错误!");
        errorMap.put("109","账户不存在!");
        errorMap.put("110","用户已经注销!");
        errorMap.put("111","新增失败!");
        errorMap.put("112","删除失败!");
        errorMap.put("113","更新失败!");
        errorMap.put("114","余额不足!");
        errorMap.put("115","支付密码错误!");
        errorMap.put("116","充电接口!");
        errorMap.put("117","电桩不存在!");
        errorMap.put("118","登录密码错误!");
        errorMap.put("119","充电卡已经被绑定！");
        errorMap.put("120","支付金额正确！");
        errorMap.put("121","订单状态不可支付！");
    }
}
