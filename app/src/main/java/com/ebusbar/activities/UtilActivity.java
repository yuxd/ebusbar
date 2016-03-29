package com.ebusbar.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ebusbar.utils.DialogUtil;
import com.ebusbar.utils.BitmapUtil;
import com.ebusbar.utils.ErrorParamUtil;
import com.ebusbar.utils.PopupWindowUtil;
import com.ebusbar.utils.ToastUtil;
import com.ebusbar.utils.WindowUtil;

/**
 * 抽象出工具
 * Created by Jelly on 2016/3/29.
 */
public abstract class UtilActivity extends SimpleActivity{

    /**
     * Dialog操作工具
     */
    public DialogUtil dialogUtil = DialogUtil.getInstance();
    /**
     * 圆形图像操作工具
     */
    public BitmapUtil bitmapUtil = BitmapUtil.getInstance();
    /**
     * 错误代码工具
     */
    public ErrorParamUtil errorParamUtil = ErrorParamUtil.getInstance();
    /**
     * Toast操作工具
     */
    public ToastUtil toastUtil = ToastUtil.getInstance();
    /**
     * PopupWindow操作工具
     */
    public PopupWindowUtil popupWindowUtil = PopupWindowUtil.getInstance();
    /**
     * Window操作工具
     */
    public WindowUtil windowUtil = WindowUtil.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}
