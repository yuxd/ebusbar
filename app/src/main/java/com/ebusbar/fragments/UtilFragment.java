package com.ebusbar.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebusbar.utils.DialogUtil;
import com.ebusbar.utils.ErrorParamUtil;
import com.ebusbar.utils.PopupWindowUtil;
import com.ebusbar.utils.ResourceUtil;
import com.ebusbar.utils.SPCacheUtil;
import com.ebusbar.utils.ToastUtil;
import com.ebusbar.utils.WindowUtil;

/**
 * Created by Jelly on 2016/3/30.
 */
public abstract class UtilFragment extends SimpleFragment{
    /**
     * PopupWindow操作工具
     */
    public PopupWindowUtil popupWindowUtil = PopupWindowUtil.getInstance();
    /**
     * 资源操作工具
     */
    public ResourceUtil resourceUtil = ResourceUtil.getInstance();
    /**
     * Window操作工具
     */
    public WindowUtil windowUtil = WindowUtil.getInstance();
    /**
     * 错误码操作工具
     */
    public ErrorParamUtil errorParamUtil = ErrorParamUtil.getInstance();
    /**
     * Toast操作工具
     */
    public ToastUtil toastUtil = ToastUtil.getInstance();
    /**
     * Dialog操作工具
     */
    public DialogUtil dialogUtil = DialogUtil.getInstance();
    /**
     * SharePreference缓存操作工具
     */
    public SPCacheUtil spCacheUtil = SPCacheUtil.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
