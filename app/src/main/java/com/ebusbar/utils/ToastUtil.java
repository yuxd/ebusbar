package com.ebusbar.utils;

import android.content.Context;
import android.widget.Toast;

import com.ebusbar.dao.ErrorDao;
import com.ebusbar.handlerinterface.NetErrorHandlerListener;

/**
 * Created by Jelly on 2016/3/29.
 */
public class ToastUtil {
    /**
     * 单例对象
     */
    private static ToastUtil toastUtil = new ToastUtil();

    /**
     * 获得单例对象
     * @return
     */
    public static ToastUtil getInstance(){
        return toastUtil;
    }

    private ToastUtil(){}

    /**
     * 输出错误信息
     * @param context
     * @param errorDao
     * @return 返回是否已经提示
     */
    public boolean toastError(Context context,ErrorDao errorDao,NetErrorHandlerListener netErrorHandlerListener){
        boolean isToast = false;
        if(errorDao.isToast()){
            isToast = true;
            Toast.makeText(context,errorDao.getMsg(),Toast.LENGTH_SHORT).show();
        }
        if(netErrorHandlerListener != null) {
            netErrorHandlerListener.handlerError(errorDao.getReturnState());
        }
        return isToast;
    }



}
