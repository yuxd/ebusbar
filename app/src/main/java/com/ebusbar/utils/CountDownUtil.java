package com.ebusbar.utils;


import android.os.Handler;
import android.os.Message;

import com.jellycai.service.ThreadManage;

/**
 * 倒计时工具，一般用于获取手机验证码时候的倒计时
 * Created by Jelly on 2016/3/3.
 */
public class CountDownUtil {
    /**
     * 通知对象
     */
    private Handler handler;
    /**
     * 通知消息
     */
    private int msg;
    /**
     * 开始倒计时的数
     */
    private int count;

    public CountDownUtil(Handler handler,int msg,int count){
        this.handler = handler;
        this.msg = msg;
        this.count = count;
    }

    /**
     * 开始倒计时
     */
    public void startCounrtDown(){
        ThreadManage.start(new Runnable() {
            @Override
            public void run() {
                for(int i = count ; i>=0 ;i--){
                    try {
                        Thread.sleep(1000);
                        Message message = handler.obtainMessage();
                        message.what = msg;
                        message.obj = i; //倒计时后的数字通过message来传递
                        handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
