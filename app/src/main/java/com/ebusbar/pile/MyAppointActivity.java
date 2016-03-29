package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.adpater.MyAppointPageAdapter;

/**
 * Created by Jelly on 2016/3/10.
 */
public class MyAppointActivity extends UtilActivity {
    /**
     * TAG
     */
    public String TAG="MyAppointActivity";
    /**
     * 充电预约
     */
    private TextView charge_appoint_btn;
    /**
     * 租车预约
     */
    private TextView car_appoint_btn;
    /**
     * 售后预约
     */
    private TextView fix_appoint_btn;
    /**
     * 指示线条
     */
    private ImageView tab_line;
    /**
     * 页面
     */
    private ViewPager myappoint_vp;
    /**
     * 加载页面的Adapter
     */
    private MyAppointPageAdapter pageAdapter;
    /**
     * 线条移动的位置
     */
    private int previousPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.myappoint);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {
        charge_appoint_btn = (TextView) this.findViewById(R.id.charge_appoint_btn);
        car_appoint_btn = (TextView) this.findViewById(R.id.car_appoint_btn);
        fix_appoint_btn = (TextView) this.findViewById(R.id.fix_appoint_btn);
        tab_line = (ImageView) this.findViewById(R.id.tab_line);
        myappoint_vp = (ViewPager) this.findViewById(R.id.myappoint_vp);
    }

    @Override
    public void loadObjectAttribute() {
        pageAdapter = new MyAppointPageAdapter(getSupportFragmentManager());
    }

    @Override
    public void setListener() {
        setPageSelectListener();
        setTabChangeListener();
    }

    @Override
    public void setActivityView() {
        tab_line.getLayoutParams().width = windowUtil.getScreenWidth(this)/3; //设置线条指示器的宽度
        myappoint_vp.setAdapter(pageAdapter);
    }

    /**
     * 设置ViewPage的页面改变事件
     */
    public void setPageSelectListener(){
        myappoint_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        charge_appoint_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myappoint_vp.setCurrentItem(0);
            }
        });

        car_appoint_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myappoint_vp.setCurrentItem(1);
            }
        });

        fix_appoint_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myappoint_vp.setCurrentItem(2);
            }
        });
    }

    public void selectTab(int position){
        Resources resources = getResources();
        if (position == 0) {
            charge_appoint_btn.setTextColor(resources.getColor(R.color.tab_select_color));
            car_appoint_btn.setTextColor(resources.getColor(R.color.defaultTabColor));
            fix_appoint_btn.setTextColor(resources.getColor(R.color.defaultTabColor));
        } else if (position == 1) {
            charge_appoint_btn.setTextColor(resources.getColor(R.color.defaultTabColor));
            car_appoint_btn.setTextColor(resources.getColor(R.color.tab_select_color));
            fix_appoint_btn.setTextColor(resources.getColor(R.color.defaultTabColor));
        } else if (position == 2) {
            charge_appoint_btn.setTextColor(resources.getColor(R.color.defaultTabColor));
            car_appoint_btn.setTextColor(resources.getColor(R.color.defaultTabColor));
            fix_appoint_btn.setTextColor(resources.getColor(R.color.tab_select_color));
        }
        //设置线条移动的动画
        int screenWidth = windowUtil.getScreenWidth(this);
        TranslateAnimation ta = new TranslateAnimation(previousPosition,position*screenWidth/3,0,0);
        ta.setDuration(200);
        ta.setFillAfter(true);
        ta.setInterpolator(new LinearInterpolator());
        tab_line.startAnimation(ta);
        previousPosition = position*screenWidth/3;

    }

    /**
     * 开启我的预约界面
     * @param context
     */
    public static void startAppActivity(Context context){
        Intent intent = new Intent(context,MyAppointActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
