package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.dao.LoginDao;
import com.ebusbar.fragment.CarFrag;
import com.ebusbar.fragment.FixFrag;
import com.ebusbar.fragment.ShopFrag;
import com.ebusbar.fragment.Tab1Fragment;
import com.ebusbar.impl.BitmapImpl;


/**
 * 主框架界面
 * Created by Jelly on 2016/2/25.
 */
public class FragmentTabHostActivity extends UtilActivity {
    /**
     * TAG
     */
    public String TAG="FragmentTabHostActivity";
    /**
     * STAG
     */
    public static String STAG = "FragmentTabHostActivity";

    /**
     * 下方的菜单栏
     */
    private FragmentTabHost tabHost;
    /**
     * 栏目标题
     */
    private String[] lables = new String[]{
            "电桩","租车","服务","商城"
    };
    /**
     * 图标
     */
    private int[] icons = new int[]{R.drawable.dianzhuan,R.drawable.car,R.drawable.fix,R.drawable.shop};
    /**
     * 当前的模块的图标
     */
    private int[] onicons = new int[]{R.drawable.ondianzhuan,R.drawable.oncar,R.drawable.onfix,R.drawable.onshop};
    /**
     * 内容区
     */
    private Fragment[] contents = new Fragment[]{new Tab1Fragment(),new CarFrag(),new FixFrag(),new ShopFrag()};
    /**
     * 侧滑栏,设为public是为了在Fragment中能够直接调用
     */
    public DrawerLayout drawerLayout;
    /**
     * 用户布局
     */
    private RelativeLayout user_layout;
    /**
     * 我的预约
     */
    private LinearLayout appoint_layout;
    /**
     * 我的订单
     */
    private LinearLayout order_layout;
    /**
     * 用户名称
     */
    private TextView draw_user_name;
    /**
     * 用户手机
     */
    private TextView draw_user_phone;
    /**
     * 用户头像
     */
    private ImageView draw_user_icon;
    /**
     * 侧滑栏余额
     */
    private TextView money;
    /**
     * bitmapImpl
     */
    private  BitmapImpl bitmapImpl;
    /**
     * 获取头像的消息
     */
    private final int msgIcon = 0x001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fragment);
        loadObjectAttribute();
        init();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDrawLayout(); //在获取焦点之前，加载侧滑栏数据，如果有数据的话
    }

    @Override
    public void loadObjectAttribute() {
        application = (MyApplication) this.getApplication();
    }

    @Override
    public void init(){
        money = (TextView) this.findViewById(R.id.money);
        order_layout = (LinearLayout) this.findViewById(R.id.order_layout);
        appoint_layout = (LinearLayout) this.findViewById(R.id.appoint_layout);
        draw_user_icon = (ImageView) this.findViewById(R.id.user_icon);
        draw_user_name = (TextView) this.findViewById(R.id.user_name);
        draw_user_phone = (TextView) this.findViewById(R.id.user_phone);
        drawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        user_layout = (RelativeLayout) this.findViewById(R.id.user_layout);
        //获得TabHost
        tabHost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
        //关联真正的正文区
        tabHost.setup(this, getSupportFragmentManager(), R.id.real_content);
        //设置下面的栏目
        for(int i=0;i<lables.length;i++){
            TabHost.TabSpec tab = tabHost.newTabSpec(lables[i]);
            tab.setIndicator(getItem(lables[i], icons[i]));
            tabHost.addTab(tab,contents[i].getClass(), null);
        }
        //去掉按钮之间的分割线
        tabHost.getTabWidget().setDividerDrawable(null);
        tabHost.setBackgroundResource(R.drawable.tab_bg);
    }

    @Override
    public void setListener() {
        setTabChangedListener(); //设置Tab切换的监听器
    }

    @Override
    public void setActivityView() {

    }

    /**
     * 加载侧滑栏里面的数据，如果用户没有登录直返回,在这里加载用户头像
     */
    public void loadDrawLayout(){
        application = (MyApplication) getApplication();
        if(!application.isLogin()){ //没有登录
            draw_user_name.setText(R.string.slide_login_hint);
            draw_user_phone.setText(R.string.slide_login_phone_hint);
            draw_user_icon.setImageResource(R.drawable.slide_portrait);
            money.setText("¥0.00");
            return;
        }
        LoginDao loginDao = application.getLoginDao();
        if(TextUtils.isEmpty(loginDao.getCrm_login().getCustName())){ //昵称
            draw_user_name.setText("昵称");
        }else{
            draw_user_name.setText(loginDao.getCrm_login().getCustName());
        }
        if(!TextUtils.isEmpty(loginDao.getCrm_login().getBalanceAmt())){ //余额
            money.setText("¥"+loginDao.getCrm_login().getBalanceAmt());
        }
        draw_user_phone.setText(loginDao.getCrm_login().getMobile());
        bitmapImpl = new BitmapImpl(this,handler,msgIcon);
        if(!TextUtils.isEmpty(loginDao.getCrm_login().getUsericon())) {
            bitmapImpl.getBitmap(loginDao.getCrm_login().getUsericon());
        }
    }

    /**
     * 设置Tab切换的监听器
     */
    public void setTabChangedListener(){
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (TextUtils.equals(tabId, lables[0])) {
                    updateTab(0);
                } else if (TextUtils.equals(tabId, lables[1])) {
                    updateTab(1);
                } else if (TextUtils.equals(tabId, lables[2])) {
                    updateTab(2);
                } else {
                    updateTab(3);
                }
            }
        });
    }

    /**
     * 更新Tab的文字的颜色
     */
    public void updateTab(int tabId){
        //将所有的模块按钮都设为默认
        for(int i=0;i<lables.length;i++){
            TextView label = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(R.id.label);
            ImageView icon = (ImageView) tabHost.getTabWidget().getChildAt(i).findViewById(R.id.icon);
            icon.setImageResource(icons[i]);
            label.setTextColor(this.getResources().getColor(R.color.defaultTabColor));
        }
        //设置当前选中的模块按钮为红色
        TextView curLabel = (TextView) tabHost.getTabWidget().getChildAt(tabId).findViewById(R.id.label);
        ImageView curIcon = (ImageView) tabHost.getTabWidget().getChildAt(tabId).findViewById(R.id.icon);
        curIcon.setImageResource(onicons[tabId]);
        curLabel.setTextColor(this.getResources().getColor(R.color.onTabColor));
    }

    /**
     * 加载item.xml，并且填充数据后以View的方式返回
     * @param lable
     * @param drawable
     * @return
     */
    private View getItem(String lable, int drawable){
        View v = this.getLayoutInflater().inflate(R.layout.item_layout,
                null);
        //找到ImageView
        ImageView icon = (ImageView) v.findViewById(R.id.icon);
        icon.setImageResource(drawable);
        //找到TextView
        TextView label = (TextView) v.findViewById(R.id.label);
        label.setText(lable);
        if(TextUtils.equals(lable,lables[0])){ //把当前模块的标题颜色变成红色
            icon.setImageResource(onicons[0]);
            label.setTextColor(this.getResources().getColor(R.color.onTabColor));
        }
        return v;
    }

    /**
     * 用户是否可以登录
     * @param view
     * @return
     */
    public View login(View view){
        if (application.isLogin()) { //已经登录,进入用户详情
            AccountManageActivity.startAppActivity(FragmentTabHostActivity.this);
            return view;
        }
        //没有登录
        Log.v(TAG, "用户没有登录，直接进入登录界面");
        LoginActivity.startAppActivity(FragmentTabHostActivity.this);
        return view;
    }

    /**
     * 进入我的预约界面
     * @param view
     * @return
     */
    public View myAppoint(View view){
        if(!application.isLogin()){ //用户没有登录，进入登录界面
            LoginActivity.startAppActivity(FragmentTabHostActivity.this);
            return view;
        }
        MyAppointActivity.startAppActivity(FragmentTabHostActivity.this);
        return view;
    }

    /**
     * 进入我的钱包
     * @param view
     * @return
     */
    public View myWallet(View view){
        if(!application.isLogin()){ //用户没有登录，进入登录界面
            LoginActivity.startAppActivity(FragmentTabHostActivity.this);
            return view;
        }
        MyWalletActivity.staticAppActivity(this);
        return view;
    }

    /**
     * 进入我的订单界面
     * @param view
     * @return
     */
    public View myOrder(View view){
        if(!application.isLogin()){ //用户没有登录，进入登录界面
            LoginActivity.startAppActivity(FragmentTabHostActivity.this);
            return view;
        }
        MyOrderActivity.startAppActivity(FragmentTabHostActivity.this);
        return view;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgIcon:
                    if(bitmapImpl.img == null){
                        return;
                    }
                    draw_user_icon.setImageBitmap(bitmapUtil.toRoundBitmap(bitmapImpl.img));
                    break;
            }
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * 开启界面
     */
    public static void startFragmentTabHostActivity(Context context){
        Intent intent = new Intent(context,FragmentTabHostActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
