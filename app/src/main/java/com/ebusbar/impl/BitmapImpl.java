package com.ebusbar.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.jellycai.service.ResponseResultHandler;

/**
 * BitmapImpl
 * Created by Jelly on 2016/3/4.
 */
public class BitmapImpl extends BaseImpl{
    /**
     * 操作对象
     */
    public Bitmap img;

    public BitmapImpl(Context context, Handler handler, int msg) {
        super(context, handler, msg);
    }

    public Bitmap getBitmap(String imgPath) {
        service.loadImg(imgPath, 100, 100, new ResponseResultHandler() {
            @Override
            public void response(boolean b, String json) {

            }

            @Override
            public void responseBitmap(boolean b, Bitmap bitmap) {
                if(b||bitmap == null) return;
                img = bitmap;
                handler.sendEmptyMessage(msg);
            }
        });
        return img;
    }
}
