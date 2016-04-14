package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.bean.Login;
import com.ebusbar.fragment.FixFragment;
import com.ebusbar.fragment.Tab1Fragment;
import com.ebusbar.impl.BitmapImpl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 主框架界面
 * Created by Jelly on 2016/2/25.
 */
public class MainActivity extends UtilActivity {
    /**
     * TAG
     */
    public String TAG = "MainActivity";
    /**
     * STAG
     */
    public static String STAG = "MainActivity";
    /**
     * 侧滑栏,设为public是为了在Fragment中能够直接调用
     */
    @Bind(R.id.user_icon)
    ImageView userIcon;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.user_phone)
    TextView userPhone;
    @Bind(R.id.money)
    TextView money;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.station_icon)
    ImageView stationIcon;
    @Bind(R.id.station_title)
    TextView stationTitle;
    @Bind(R.id.rentCar_icon)
    ImageView rentCarIcon;
    @Bind(R.id.rentCar_title)
    TextView rentCarTitle;
    @Bind(R.id.fix_icon)
    ImageView fixIcon;
    @Bind(R.id.fix_text)
    TextView fixText;
    /**
     * bitmapImpl
     */
    private BitmapImpl bitmapImpl;
    /**
     * 获取头像的消息
     */
    private final int msgIcon = 0x001;
    /**
     * 地图模块
     */
    private Tab1Fragment tab1Fragment;
    /**
     * FixFragment
     */
    private FixFragment fixFragment;
    /**
     * FragmentManager
     */
    private FragmentManager fm;
    /**
     * 当前Tab图片
     */
    private ImageView currTabImage;
    /**
     * 当前Tab文字
     */
    private TextView currTabText;
    /**
     * 当前Tab
     */
    private int currTab;
    /**
     * 没有选中是的Tab图片
     */
    private int[] tabImage = new int[]{R.drawable.dianzhuan,R.drawable.car,R.drawable.fix};

    /**
     * 选中时的Tab图片
     */
    private int[] onTabImage = new int[]{R.drawable.ondianzhuan,R.drawable.oncar,R.drawable.onfix};

    /**
     * 充电Tab
     */
    public static final int stationTab = 0;
    /**
     * 租车Tab
     */
    public static final int rentCarTab = 1;

    /**
     * Tab改变监听
     */
    public interface TabChangeListener{
        void tabChange(int tab);
    }

    /**
     * Tab改变监听
     */
    private TabChangeListener tabChangeListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        ButterKnife.bind(this);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadDrawLayout(); //在获取焦点之前，加载侧滑栏数据，如果有数据的话
    }

    @Override
    public void init() {

    }

    @Override
    public void loadObjectAttribute() {
        if(currTabImage == null){
            currTabImage = stationIcon;
        }
        if(currTabText == null){
            currTabText = stationTitle;
        }
        if(currTab == 0){
            currTab = R.id.station;
        }
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setActivityView() {
        if (tab1Fragment == null) {
            tab1Fragment = new Tab1Fragment();
        }
        if (fm == null) {
            fm = getSupportFragmentManager();
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.real_content, tab1Fragment);
        ft.commit();
    }

    /**
     * 设置Tab监听器
     * @param tabChangeListener
     */
    public void setTabChangeListener(TabChangeListener tabChangeListener) {
        this.tabChangeListener = tabChangeListener;
    }

    @OnClick({R.id.user_layout, R.id.wallet_layout, R.id.appoint_layout, R.id.order_layout, R.id.forum_layout, R.id.news_layout, R.id.collect_layout, R.id.set_layout, R.id.station, R.id.rentCar,R.id.fix})
    public void onClick(View view) {
        if (fm == null) {
            fm = getSupportFragmentManager();
        }
        FragmentTransaction ft = fm.beginTransaction();
        switch (view.getId()) {
            case R.id.user_layout:
                if (!application.isLogin()) { //没有登录，进入登录界面
                    LoginActivity.startAppActivity(context);
                    return;
                }
                AccountManageActivity.startAppActivity(context);
                break;
            case R.id.wallet_layout:
                if (!application.isLogin()) { //没有登录，进入登录界面
                    LoginActivity.startAppActivity(context);
                    return;
                }
                MyWalletActivity.staticAppActivity(this);
                break;
            case R.id.appoint_layout:
                if (!application.isLogin()) { //没有登录，进入登录界面
                    LoginActivity.startAppActivity(context);
                    return;
                }
                MyAppointActivity.startAppActivity(MainActivity.this);
                break;
            case R.id.order_layout:
                if (!application.isLogin()) { //没有登录，进入登录界面
                    LoginActivity.startAppActivity(context);
                    return;
                }
                MyOrderActivity.startAppActivity(MainActivity.this);
                break;
            case R.id.forum_layout:
                break;
            case R.id.news_layout:
                break;
            case R.id.collect_layout:
                break;
            case R.id.set_layout:
                break;
            case R.id.station:
                if(R.id.station == currTab){
                    return;
                }
                if (tab1Fragment == null) {
                    tab1Fragment = new Tab1Fragment();
                }
                if (fixFragment != null && !fixFragment.isHidden()) {
                    ft.hide(fixFragment);
                }
                if (!tab1Fragment.isAdded()) {
                    ft.add(R.id.real_content, tab1Fragment);
                } else {
                    ft.show(tab1Fragment);
                }
                ft.commit();
                currTab = R.id.station;
                currTabImage.setImageResource(tabImage[Integer.parseInt(currTabImage.getTag().toString())]);
                currTabImage = stationIcon;
                currTabImage.setImageResource(onTabImage[Integer.parseInt(currTabImage.getTag().toString())]);
                currTabText.setTextColor(resourceUtil.getResourceColor(context,R.color.defaultTabColor));
                currTabText = stationTitle;
                currTabText.setTextColor(resourceUtil.getResourceColor(context,R.color.actionbar_bg));
                if(tabChangeListener != null){
                    tabChangeListener.tabChange(stationTab);
                }
                break;
            case R.id.rentCar:
                currTab = R.id.rentCar;
                currTabImage.setImageResource(tabImage[Integer.parseInt(currTabImage.getTag().toString())]);
                currTabImage = rentCarIcon;
                currTabImage.setImageResource(onTabImage[Integer.parseInt(currTabImage.getTag().toString())]);
                currTabText.setTextColor(resourceUtil.getResourceColor(context,R.color.defaultTabColor));
                currTabText = rentCarTitle;
                currTabText.setTextColor(resourceUtil.getResourceColor(context,R.color.actionbar_bg));
                if(tabChangeListener != null){
                    tabChangeListener.tabChange(rentCarTab);
                }
                break;
            case R.id.fix:
                if(R.id.fix == currTab){
                    return;
                }
                if (fixFragment == null) {
                    fixFragment = new FixFragment();
                }
                if (tab1Fragment != null && !tab1Fragment.isHidden()) {
                    ft.hide(tab1Fragment);
                }
                if (!fixFragment.isAdded()) {
                    ft.add(R.id.real_content, fixFragment);
                } else {
                    ft.show(fixFragment);
                }
                ft.commit();
                currTab = R.id.fix;
                currTabImage.setImageResource(tabImage[Integer.parseInt(currTabImage.getTag().toString())]);
                currTabImage = fixIcon;
                currTabImage.setImageResource(onTabImage[Integer.parseInt(currTabImage.getTag().toString())]);
                currTabText.setTextColor(resourceUtil.getResourceColor(context,R.color.defaultTabColor));
                currTabText = fixText;
                currTabText.setTextColor(resourceUtil.getResourceColor(context,R.color.actionbar_bg));
                break;
        }
    }


    /**
     * 加载侧滑栏里面的数据，如果用户没有登录直返回,在这里加载用户头像
     */
    public void loadDrawLayout() {
        application = (MyApplication) getApplication();
        if (!application.isLogin()) { //没有登录
            userName.setText(R.string.slide_login_hint);
            userName.setText(R.string.slide_login_phone_hint);
            userIcon.setImageResource(R.drawable.slide_portrait);
            money.setText("¥0.00");
            return;
        }
        Login loginDao = application.getLoginDao();
        if (TextUtils.isEmpty(loginDao.getData().getCustName())) { //昵称
            userName.setText("昵称");
        } else {
            userName.setText(loginDao.getData().getCustName());
        }
        if (!TextUtils.isEmpty(loginDao.getData().getBalanceAmt())) { //余额
            money.setText("¥" + loginDao.getData().getBalanceAmt());
        }
        userPhone.setText(loginDao.getData().getMobile());
        bitmapImpl = new BitmapImpl(this, handler, msgIcon);
        if (!TextUtils.isEmpty(loginDao.getData().getUsericon())) {
            bitmapImpl.getBitmap(loginDao.getData().getUsericon());
        }
    }


    /**
     * 切换侧滑栏
     */
    public void switchDrawLayout(boolean isSwitch) {
        if (isSwitch) {
            drawerLayout.openDrawer(Gravity.LEFT);
        } else {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case msgIcon:
                    if (bitmapImpl.img == null) {
                        return;
                    }
                    userIcon.setImageBitmap(bitmapUtil.toRoundBitmap(bitmapImpl.img));
                    break;
            }
        }
    };


    /**
     * 开启界面
     */
    public static void startFragmentTabHostActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }


}
