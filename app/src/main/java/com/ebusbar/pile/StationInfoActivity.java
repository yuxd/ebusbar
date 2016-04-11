package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.adpater.ViewPageAdapter;
import com.ebusbar.fragment.StationCommentFragment;
import com.ebusbar.fragment.StationDetailFragment;
import com.ebusbar.fragment.StationPhotoFragment;
import com.ebusbar.fragments.BaseFragment;
import com.ebusbar.utils.AnimationUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jelly on 2016/4/10.
 */
public class StationInfoActivity extends UtilActivity {

    public String TAG = "StationInfoActivity";
    @Bind(R.id.detail_icon)
    ImageView detailIcon;
    @Bind(R.id.detail_text)
    TextView detailText;
    @Bind(R.id.photo_icon)
    ImageView photoIcon;
    @Bind(R.id.photo_text)
    TextView photoText;
    @Bind(R.id.comment_icon)
    ImageView commentIcon;
    @Bind(R.id.comment_text)
    TextView commentText;
    @Bind(R.id.vp)
    ViewPager vp;
    @Bind(R.id.tabLine)
    TextView tabLine;
    /**
     * 视图适配器
     */
    private ViewPageAdapter pageAdapter;
    /**
     * 添加到Fragment中的数组
     */
    private BaseFragment[] fragments;
    /**
     * 线条指示器起始位置
     */
    private int previousPosition;
    /**
     * 当前位置
     */
    private TextView currText = null;
    private ImageView currIcon = null;
    /**
     * 标签选中时的图片
     */
    private int[] onIcons = new int[]{R.drawable.ondetail,R.drawable.onphoto,R.drawable.oncomment};
    /**
     * 标签未选中时的图片
     */
    private int[] icons = new int[]{R.drawable.detail,R.drawable.photo,R.drawable.comment};

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
        this.setContentView(R.layout.stationinfo);
        ButterKnife.bind(this);
    }

    @Override
    public void loadObjectAttribute() {
        fragments = new BaseFragment[]{new StationDetailFragment(), new StationPhotoFragment(), new StationCommentFragment()};
        pageAdapter = new ViewPageAdapter(getSupportFragmentManager(),fragments);
        currText = detailText;
        currIcon = detailIcon;
    }

    @Override
    public void setListener() {
        setViewPageListener();
    }

    @Override
    public void setActivityView() {
        tabLine.getLayoutParams().width = windowUtil.getScreenWidth(this)/3;
        vp.setAdapter(pageAdapter);
    }


    public void setViewPageListener(){
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(currText != null && currIcon != null){
                    currText.setTextColor(resourceUtil.getResourceColor(context,R.color.defaultTabColor));
                    currIcon.setImageResource(icons[Integer.parseInt(currIcon.getTag().toString())]);
                }
                switch (position){
                    case 0:
                        currText = detailText;
                        currIcon = detailIcon;
                        break;
                    case 1:
                        currText = photoText;
                        currIcon = photoIcon;
                        break;
                    case 2:
                        currText = commentText;
                        currIcon = commentIcon;
                        break;
                }
                currIcon.setImageResource(onIcons[position]);
                currText.setTextColor(resourceUtil.getResourceColor(context,R.color.tab_select_color));

                Animation animation = AnimationUtil.startTabLineAnimation(windowUtil,StationInfoActivity.this,previousPosition,position,fragments.length);
                tabLine.startAnimation(animation);
                previousPosition = position * windowUtil.getScreenWidth(StationInfoActivity.this)/fragments.length;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 开启界面
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, StationInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
