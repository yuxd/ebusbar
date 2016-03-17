package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebusbar.adpater.MyOrderPageAdapter;

/**
 * Created by Jelly on 2016/3/10.
 */
public class MyOrderActivity extends BaseActivity{
    /**
     * TAG
     */
    public String TAG = "MyOrderActivity";
    /**
     * 待处理
     */
    private TextView pending_btn;
    /**
     * 已完成
     */
    private TextView finish_btn;
    /**
     * 线条指示器
     */
    private ImageView tab_line;
    /**
     * 页面
     */
    private ViewPager myorder_vp;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;
    /**
     * 页面适配器
     */
    private MyOrderPageAdapter pageAdapter;
    /**
     * 指示器的位置
     */
    private int previousPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.myorder);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {
        pending_btn = (TextView) this.findViewById(R.id.pending_btn);
        finish_btn = (TextView) this.findViewById(R.id.finish_btn);
        tab_line = (ImageView) this.findViewById(R.id.tab_line);
        myorder_vp = (ViewPager) this.findViewById(R.id.myorder_vp);
    }

    @Override
    public void loadObjectAttribute() {
        getScreenSize();
        pageAdapter = new MyOrderPageAdapter(getSupportFragmentManager());
    }

    @Override
    public void setListener() {
        setPageSelectListener();
        setTabChangeListener();
    }

    @Override
    public void setActivityView() {
        tab_line.getLayoutParams().width = screenWidth/2; //设置线条指示器的宽度
        myorder_vp.setAdapter(pageAdapter);
    }

    /**
     * 设置ViewPage的页面改变事件
     */
    public void setPageSelectListener(){
        myorder_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 设置Tab的点击事件
     */
    public void setTabChangeListener(){
        pending_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myorder_vp.setCurrentItem(0);
            }
        });

        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myorder_vp.setCurrentItem(1);
            }
        });
    }

    public void selectTab(int position){
        Resources resources = getResources();
        if (position == 0) {
            pending_btn.setTextColor(resources.getColor(R.color.tab_select_color));
            finish_btn.setTextColor(resources.getColor(R.color.defaultTabColor));
        } else if (position == 1) {
            pending_btn.setTextColor(resources.getColor(R.color.defaultTabColor));
            finish_btn.setTextColor(resources.getColor(R.color.tab_select_color));
        }
        //设置线条移动的动画
        TranslateAnimation ta = new TranslateAnimation(previousPosition,position*screenWidth/2,0,0);
        ta.setDuration(200);
        ta.setFillAfter(true);
        ta.setInterpolator(new LinearInterpolator());
        tab_line.startAnimation(ta);
        previousPosition = position*screenWidth/2;

    }

    /**
     * 获取屏幕的宽度
     */
    private void getScreenSize(){
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
    }

    /**
     * 开启界面
     */
    public static void startAppActivity(Context context){
        Intent intent = new Intent(context,MyOrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }

}
