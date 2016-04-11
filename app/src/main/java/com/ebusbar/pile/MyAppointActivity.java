package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.adpater.ViewPageAdapter;
import com.ebusbar.fragment.CarAppointFragment;
import com.ebusbar.fragment.ChargeAppointFragment;
import com.ebusbar.fragment.FixAppointFragment;
import com.ebusbar.fragments.BaseFragment;
import com.ebusbar.utils.AnimationUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jelly on 2016/3/10.
 */
public class MyAppointActivity extends UtilActivity {
    /**
     * TAG
     */
    public String TAG = "MyAppointActivity";
    @Bind(R.id.charge_appoint_btn)
    TextView chargeAppointBtn;
    @Bind(R.id.car_appoint_btn)
    TextView carAppointBtn;
    @Bind(R.id.fix_appoint_btn)
    TextView fixAppointBtn;
    @Bind(R.id.tab_line)
    ImageView tabLine;
    @Bind(R.id.myappoint_vp)
    ViewPager myappointVp;
    /**
     * 加载页面的Adapter
     */
    private ViewPageAdapter pageAdapter;
    /**
     * 线条移动的位置
     */
    private int previousPosition;
    /**
     * 添加到ViewPage中的Fragment
     */
    private BaseFragment[] fragments;
    /**
     * 当前标签
     */
    private TextView currText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {
        this.setContentView(R.layout.myappoint);
        ButterKnife.bind(this);
    }

    @Override
    public void loadObjectAttribute() {
        fragments = new BaseFragment[]{new ChargeAppointFragment(), new CarAppointFragment(), new FixAppointFragment()};
        pageAdapter = new ViewPageAdapter(getSupportFragmentManager(),fragments);
        currText = chargeAppointBtn;
    }

    @Override
    public void setListener() {
        setPageSelectListener();
        setTabChangeListener();
    }

    @Override
    public void setActivityView() {
        tabLine.getLayoutParams().width = windowUtil.getScreenWidth(this) / 3; //设置线条指示器的宽度
        myappointVp.setAdapter(pageAdapter);
    }

    /**
     * 设置ViewPage的页面改变事件
     */
    public void setPageSelectListener() {
        myappointVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (currText != null) {
                    currText.setTextColor(resourceUtil.getResourceColor(context, R.color.defaultTabColor));
                }
                switch (position) {
                    case 0:
                        currText = chargeAppointBtn;
                        break;
                    case 1:
                        currText = carAppointBtn;
                        break;
                    case 2:
                        currText = fixAppointBtn;
                        break;
                }
                currText.setTextColor(resourceUtil.getResourceColor(context, R.color.tab_select_color));
                //设置线条移动的动画
                Animation animation = AnimationUtil.startTabLineAnimation(windowUtil, MyAppointActivity.this, previousPosition, position, fragments.length);
                tabLine.startAnimation(animation);
                previousPosition = position * windowUtil.getScreenWidth(MyAppointActivity.this) / fragments.length;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /**
     * 设置Tab的点击事件
     */
    public void setTabChangeListener() {
        chargeAppointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myappointVp.setCurrentItem(0);
            }
        });

        carAppointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myappointVp.setCurrentItem(1);
            }
        });

        fixAppointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myappointVp.setCurrentItem(2);
            }
        });
    }


    /**
     * 开启我的预约界面
     *
     * @param context
     */
    public static void startAppActivity(Context context) {
        Intent intent = new Intent(context, MyAppointActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
