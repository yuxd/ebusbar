package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebusbar.impl.BitmapImpl;
import com.ebusbar.utils.RoundBitmapUtil;

/**
 * 我的钱包
 * Created by Jelly on 2016/3/16.
 */
public class MyWalletActivity extends BaseActivity{
    /**
     * TAG
     */
    public String TAG = "MyWalletActivity";
    /**
     * Application
     */
    private MyApplication application;
    /**
     * 昵称
     */
    private TextView nickname_text;
    /**
     * 用户图标
     */
    private ImageView usericon;
    /**
     * bitmapImpl
     */
    private BitmapImpl bitmapImpl;
    /**
     * 获取头像的消息
     */
    private int msgIcon = 0x001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.mywallet);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {
        nickname_text = (TextView) this.findViewById(R.id.nickname_text);
        usericon = (ImageView) this.findViewById(R.id.usericon);
    }

    @Override
    public void loadObjectAttribute() {
        application = (MyApplication) getApplication();
        bitmapImpl = new BitmapImpl(this,handler,msgIcon);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setActivityView() {
//        nickname_text.setText(application.getLoginDao().getNickName());
//        bitmapImpl.getBitmap(application.getLoginDao().getUsericon());
    }

    /**
     * 进入账户余额界面
     * @param view
     * @return
     */
    public View balance(View view){
        BalanceActivity.startAppActivity(this);
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == msgIcon){
                usericon.setImageBitmap(RoundBitmapUtil.toRoundBitmap(bitmapImpl.img));
            }
        }
    };

    /**
     * 打开界面
     * @param context
     */
    public static void staticAppActivity(Context context){
        Intent intent = new Intent(context,MyWalletActivity.class);
        context.startActivity(intent);
    }


    @Override
    public String getTAG() {
        return TAG;
    }
}
