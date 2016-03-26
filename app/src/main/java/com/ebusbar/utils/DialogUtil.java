package com.ebusbar.utils;

import android.content.Context;
import android.content.DialogInterface;

import com.ebusbar.myview.MyDialog;

/**
 * Created by Jelly on 2016/3/26.
 */
public class DialogUtil {

    private static DialogUtil dialogUtil = new DialogUtil();

    public static DialogUtil getInstance(){
        return dialogUtil;
    }

    /**
     * 显示确认按钮监听事件的Dialog
     * @param context
     * @param hint
     * @param onClickListener
     */
    public void showSureListenerDialog(Context context,String hint,DialogInterface.OnClickListener onClickListener){
        MyDialog.Builder builder = new MyDialog.Builder(context).setMessage(hint);
        builder.setPositiveButton("确定", onClickListener); //确定按钮的监听事件
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        MyDialog sureDialog = builder.create();
        sureDialog.show();
    }

}
