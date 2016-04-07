package com.ebusbar.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.ebusbar.pile.R;

/**
 * 自定义Dialog
 * Created by Jelly on 2016/3/15.
 */
public class MyDialog extends Dialog{

    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public MyDialog(Context context) {
        super(context);
    }

    public static class Builder{
        /**
         * 上下文
         */
        private Context context;
        /**
         * 消息
         */
        private String message;
        /**
         * 左边按钮的文字
         */
        private String positiveButtonText;
        /**
         * 右边按钮的文字
         */
        private String negativeButtonText;
        /**
         * 左边按钮的监听事件
         */
        private OnClickListener positiveButtonClickListener;
        /**
         * 右边按钮的监听事件
         */
        private OnClickListener negativeButtonClickListener;

        /**
         * 构造函数
         * @param context
         */
        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置提示消息
         * @param message 提示消息
         * @return
         */
        public Builder setMessage(String message){
            this.message = message;
            return this;
        };

        /**
         * 设置左边按钮的文字
         * @param text
         * @return
         */
        public Builder setPositiveButton(String text,OnClickListener positiveButtonClickListener){
            this.positiveButtonText = text;
            this.positiveButtonClickListener = positiveButtonClickListener;
            return this;
        }

        /**
         * 设置右边按钮的文字
         * @param text
         * @return
         */
        public Builder setNegativeButton(String text,OnClickListener negativeButtonClickListener){
            this.negativeButtonText = text;
            this.negativeButtonClickListener = negativeButtonClickListener;
            return this;
        }

        /**
         * 创建Dialog
         * @return
         */
        public MyDialog create(){
            View root = LayoutInflater.from(context).inflate(R.layout.suredialog,null);
            final MyDialog dialog = new MyDialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.addContentView(root, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView pw_hint = (TextView) root.findViewById(R.id.message);
            TextView positiveButton = (TextView) root.findViewById(R.id.positiveButton);
            TextView negativeButton = (TextView) root.findViewById(R.id.negativeButton);
            if(TextUtils.isEmpty(message) || TextUtils.isEmpty(positiveButtonText) || TextUtils.isEmpty(negativeButtonText))
            {
                return dialog;
            }
            pw_hint.setText(message);
            positiveButton.setText(positiveButtonText);
            negativeButton.setText(negativeButtonText);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (positiveButtonClickListener == null) return;
                    positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                }
            });
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(negativeButtonClickListener == null) return;
                    negativeButtonClickListener.onClick(dialog,DialogInterface.BUTTON_NEGATIVE);
                }
            });
            dialog.setContentView(root);
            return dialog;
        }

    }
}
