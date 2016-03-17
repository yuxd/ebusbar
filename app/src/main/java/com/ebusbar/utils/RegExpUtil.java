package com.ebusbar.utils;

/**
 * 正则表达式工具
 * Created by Jelly on 2016/3/3.
 */
public class RegExpUtil {
    public static final String RPHONE="(^(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7})$";


    /**
     * 校验手机号码
     * @return
     */
    public static boolean RegPhone(String phone){
        boolean flag =  phone.matches(RPHONE);
        if(flag){
            return true;
        }else{
            return false;
        }
    }

}
