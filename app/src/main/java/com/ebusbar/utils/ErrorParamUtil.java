package com.ebusbar.utils;

import android.text.TextUtils;
import android.util.Log;

import com.ebusbar.dao.ErrorDao;

/**
 * 错误参数
 * Created by Jelly on 2016/3/3.
 */
public class ErrorParamUtil {

    /**
     * 单例对象
     */
    private static ErrorParamUtil errorParamUtil = new ErrorParamUtil();

    /**
     * 获得单例对象
     * @return
     */
    public static ErrorParamUtil getInstance(){
        return errorParamUtil;
    }

    private ErrorParamUtil(){}

    /**
     * 校验错误码
     * @param returnState
     * @return
     */
    public ErrorDao checkReturnState(String returnState){
        ErrorDao errorDao = new ErrorDao();
        errorDao.setReturnState(returnState);
        Log.v("msg",returnState);
        for(NetErrorEnum errorEnum : NetErrorEnum.values()){ //遍历枚举集合获取msg
            Log.v("msg",errorEnum.getState());
            if(TextUtils.equals(errorEnum.getState(),returnState)){
                errorDao.setMsg(errorEnum.getMsg());
                break;
            }
        }
        if(TextUtils.equals(returnState,NetErrorEnum.操作成功.getState())){ //当返回状态为操作成功时不会提示
            errorDao.setIsToast(false);
        }
        return errorDao;
    }


}
