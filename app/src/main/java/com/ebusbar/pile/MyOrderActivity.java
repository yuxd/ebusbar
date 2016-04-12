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
import com.ebusbar.fragment.FinishOrderFragment;
import com.ebusbar.fragment.PendingOrderFragment;
import com.ebusbar.fragments.BaseFragment;
import com.ebusbar.utils.AnimationUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jelly on 2016/3/10.
 */
public class MyOrderActivity extends UtilActivity {
    /**
     * TAG
     */
    public String TAG = "MyOrderActivity";
    @Bind(R.id.pending_btn)
    TextView pendingBtn;
    @Bind(R.id.finish_btn)
    TextView finishBtn;
    @Bind(R.id.tab_line)
    ImageView tabLine;
    @Bind(R.id.myorder_vp)
    ViewPager myorderVp;
    /**
     * 页面适配器
     */
    private ViewPageAdapter pageAdapter;
    /**
     * 指示器的位置
     */
    private int previousPosition;
    /**
     * 添加的Fragment
     */
    private BaseFragment[] fragments;
    /**
     * 当前标签
     */
    private TextView currText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.myorder);
        ButterKnife.bind(this);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {

    }

    @Override
    public void loadObjectAttribute() {
        fragments = new BaseFragment[]{new PendingOrderFragment(), new FinishOrderFragment()};
        pageAdapter = new ViewPageAdapter(getSupportFragmentManager(), fragments);
    }

    @Override
    public void setListener() {
        setPageSelectListener();
        setTabChangeListener();
    }

    @Override
    public void setActivityView() {
        tabLine.getLayoutParams().width = windowUtil.getScreenWidth(this) / fragments.length; //设置线条指示器的宽度
        myorderVp.setAdapter(pageAdapter);
        currText = pendingBtn;
    }


    /**
     * 设置ViewPage的页面改变事件
     */
    public void setPageSelectListener() {
        myorderVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                        currText = pendingBtn;
                        break;
                    case 1:
                        currText = finishBtn;
                        break;
                }
                currText.setTextColor(resourceUtil.getResourceColor(context, R.color.tab_select_color));
                //设置线条移动的动画
                Animation animation = AnimationUtil.startTabLineAnimation(windowUtil, MyOrderActivity.this, previousPosition, position, fragments.length);
                tabLine.startAnimation(animation);
                previousPosition = position * windowUtil.getScreenWidth(MyOrderActivity.this) / fragments.length;
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
        pendingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myorderVp.setCurrentItem(0);
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myorderVp.setCurrentItem(1);
            }
        });
    }


    /**
     * 开启界面
     */
    public static void startAppActivity(Context context) {
        Intent intent = new Intent(context, MyOrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }

}
