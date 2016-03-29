package com.ebusbar.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ebusbar.pile.R;

/**
 * PopupWindow的工具类
 * 通过资源ID或者View来初始化PopupWindow
 * Created by Jelly on 2016/2/26.
 */
public class PopupWindowUtil {

    private static PopupWindowUtil popupWindowUtil = new PopupWindowUtil();

    /**
     * 获得PopupWindow的单例对象
     * @return PopupWindowUtil
     */
    public static PopupWindowUtil getInstance(){
        return popupWindowUtil;
    }

    private PopupWindowUtil(){}

    /**
     * 通过资源ID来获取PopupWindow
     * @param context Context
     * @param rootId 资源ID
     * @param width PopupWindow的宽
     * @param height PopupWindow的高
     * @return PopupWindow PopupWindow对象
     */
    public PopupWindow getPopupWindow(Context context,int rootId,int width,int height){
        if(context == null || rootId == 0 || width == 0 || height == 0){
            return null;
        }
        View root = LayoutInflater.from(context).inflate(rootId,null);
        PopupWindow pw = getPopupWindow(context, root, width, height);
        return pw;
    }

    /**
     * 通过View获取PopupWindow
     * @param context Context
     * @param root PopupWindow需要设置的布局
     * @param width PopupWindow的宽
     * @param height PopupWindow的高
     * @return popupWindow PopupWindow对象
     */
    public PopupWindow getPopupWindow(Context context, View root, int width, int height){
        if(context == null || root == null || width == 0 || height == 0){
            return null;
        }
        PopupWindow pw = new PopupWindow(root);
        pw.setHeight(height); //设置高度，高度为筛选的高度相同
        pw.setWidth(width); //设置宽度
        pw.setOutsideTouchable(true);// 设置触摸别的地方可以弹出
        pw.setBackgroundDrawable(new BitmapDrawable(context.getResources()));  //设置PopupWindow的背景为屏幕背景
        return pw;
    }

    /**
     * 读取数据的进度条
     * @param context Context
     * @param hint 提示文字
     * @return
     */
    public PopupWindow startLoading(Context context,View parent,String hint){
        WindowUtil windowUtil = WindowUtil.getInstance();
        View root = LayoutInflater.from(context).inflate(R.layout.loading, null);
        TextView hint_text = (TextView) root.findViewById(R.id.hint);
        hint_text.setText(hint);
        PopupWindow loading = getPopupWindow(context,root,windowUtil.getScreenWidth((Activity) context),windowUtil.getScreenHeight((Activity) context));
        loading.setOutsideTouchable(false);
        loading.showAtLocation(parent, Gravity.CENTER,0,0);
        return loading;
    }

}
