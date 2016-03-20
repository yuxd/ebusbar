package com.ebusbar.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jelly on 2016/3/2.
 */
public class NetParam {
    /**
     * 网络访问
     */
    public static String path="http://www.genlex.com.cn/e848/interface/ev_app_cnt_api.do";

    /**
     * 获得参数Map
     * @param trancode 接口标示
     * @param mode 端口
     * @param timestamp 时间戳
     * @param custid 用户ID
     * @param sign_method 签名方法
     * @param sign 签名结果
     * @param fields 返回字段（用逗号隔开）
     * @param condition 条件/发送数据（用空格隔开）
     * @return map Map<String,String>
     */
    public static Map<String,String> getParamMap(String trancode,String mode,String timestamp,String custid,String sign_method,String sign,String execmode,String fields,String condition){
        Map<String,String> map = new HashMap<String,String>();
        map.put("trancode",trancode);
        map.put("mode",mode);
        map.put("timestamp",timestamp);
        map.put("custid",custid);
        map.put("sign_method",sign_method);
        map.put("sign",sign);
        map.put("execmode",execmode);
        map.put("fields",fields);
        map.put("condition",condition);
        return map;
    }

    /**
     * 获取时间
     * @return
     */
    public static String getTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 拼接条件
     * @param map Map<String,String>
     */
    public static  String spliceCondition(Map<String,String> map){
        String condition = "";
        for(String key : map.keySet()){
            condition += key + "=";
            condition += "\"" + map.get(key) + "\" ";
        }
        return condition;
    }
}
