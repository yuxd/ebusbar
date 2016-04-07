package com.ebusbar.utils;

import com.ebusbar.bean.AllStation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jelly on 2016/3/31.
 */
public class SearchUtil {
    private static String TAG = "SearchUtil";
    /**
     * 单例对象
     */
    private static SearchUtil searchUtil = new SearchUtil();
    /**
     * 获得单例对象
     * @return
     */
    public static SearchUtil getInstance(){
        return searchUtil;
    }

    public List<AllStation> searchListOnRegExp(String condition , List<AllStation> list){
        List<AllStation> daos = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            AllStation dao = list.get(i);
            LogUtil.v(TAG,"开始匹配:"+dao.getCondition()+"条件："+condition);
            if(RegExpUtil.regChar(dao.getCondition(),condition)){
                LogUtil.v(TAG,"结果:成功");
                daos.add(dao);
            }
        }
        return daos;
    }

}
