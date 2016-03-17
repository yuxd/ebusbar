package com.ebusbar.utils;

import android.text.TextUtils;
import android.util.Log;

import com.ebusbar.pile.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity的控制器
 * Created by Jelly on 2016/3/1.
 */
public class ActivityControl {
    /**
     * TAG
     */
    public static String TAG="ActivityControl";
    /**
     * 保存Activity的集合
     */
    private static List<BaseActivity> list = new ArrayList<BaseActivity>();

    /**
     * 增加Activity
     * @param activity
     */
    public static void add(BaseActivity activity){
        if(!list.contains(activity))
            list.add(activity);
        Log.v(TAG,"List中还有"+list.size()+"个Activity");
        logActivity();
    }


    /**
     * 删除Activity
     * @param activity
     */
    public static void remove(BaseActivity activity){
        list.remove(activity);
        for(int i=list.size()-1;i>=0;i--){
            if(list.get(i).getTAG().equals(activity.getTAG())){
                list.remove(i);
            }
        }
    }

    /**
     * 打印List中的Activity
     */
    public static void logActivity(){
        for(BaseActivity act:list){
             Log.v(TAG,act.getTAG());
        }
    }

    /**
     * 结束某个Activity
     * @param activity
     */
    public static void finishAct(BaseActivity activity){
        remove(activity);
        activity.finish();
    }

    public static void finishAct(String tag){
        for(int i=list.size()-1;i>=0;i--){
            if(TextUtils.equals(list.get(i).getTAG(),tag)){
                list.get(i).finish();
                return;
            }
        }
    }

    /**
     * 结束除了这个Activity以外的其他Activity
     * @param tag
     */
    public static void finishExcept(String tag){
        for(int i=list.size()-1;i>=0;i--){
            if(!list.get(i).getTAG().equals(tag)){
                list.get(i).finish();
                list.remove(i);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAll(){
        for(BaseActivity activity : list){
            list.remove(activity);
            activity.finish();
        }
    }
}
