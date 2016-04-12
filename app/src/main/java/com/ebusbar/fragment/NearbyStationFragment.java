package com.ebusbar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.ebusbar.adpater.NearbyListItemAdapter;
import com.ebusbar.bean.NearbyStation;
import com.ebusbar.fragments.UtilFragment;
import com.ebusbar.impl.NearbyStationDaoImpl;
import com.ebusbar.pile.MyApplication;
import com.ebusbar.pile.R;
import com.ebusbar.utils.LogUtil;
import com.ebusbar.view.SlideSwitch;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近电桩
 * Created by Jelly on 2016/3/24.
 */
public class NearbyStationFragment extends UtilFragment {
    /**
     * TAG
     */
    public String TAG = "NearbyStationFragment";
    /**
     * 上下文
     */
    private Context context;
    /**
     * 充电点的列表
     */
    private ListView list;
    /**
     * 筛选
     */
    private RelativeLayout screen;
    /**
     * 箭头
     */
    private ImageView arrow;
    /**
     * ActionBar
     */
    private RelativeLayout actionBar_layout;
    /**
     * PositionDaoImpl
     */
    private NearbyStationDaoImpl nearbyStationDao;
    /**
     * 获取电桩位置集合消息
     */
    private final int msgPositionList = 0x001;
    /**
     * Application
     */
    private MyApplication application;
    /**
     * Adapter
     */
    private NearbyListItemAdapter adapter;
    /**
     * 筛选弹出框
     */
    private PopupWindow screenPw;
    /**
     * 是否弹出
     */
    private boolean isShow = false;
    /**
     * 隐藏的充电点
     */
    private List<NearbyStation> dismissList = new ArrayList<>();
    /**
     * 筛选可用的是否打开
     */
    private boolean isUse = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        root = inflater.inflate(R.layout.nearby,container,false);
        init();
        loadObjectAttribute();
        setListener();
        setFragView();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void init() {

        list = (ListView) root.findViewById(R.id.list);
        screen = (RelativeLayout) root.findViewById(R.id.screen);
        actionBar_layout = (RelativeLayout) root.findViewById(R.id.actionBar_layout);
        arrow = (ImageView) root.findViewById(R.id.arrow);
    }

    @Override
    public void loadObjectAttribute() {
        context = getContext();
        application = (MyApplication) getActivity().getApplication();
        nearbyStationDao = new NearbyStationDaoImpl(context,handler,msgPositionList);
    }

    @Override
    public void setListener() {
        setScreenListener();
    }

    @Override
    public void setFragView() {
        if(application.getLocation() != null){
            String adCode = application.getLocation().getAdCode();
            adCode = adCode.substring(0,adCode.length()-2) + "00";
            LogUtil.v(TAG,adCode);
            nearbyStationDao.getDaos(adCode);
        }
    }

    /**
     * 设置筛选按钮的监听器
     */
    public void setScreenListener(){
        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(screenPw != null && isShow){
                    isShow = popupWindowUtil.dismissPopupWindow(screenPw,isShow);
                    arrow.setImageResource(R.drawable.down);
                    return;
                }else if(screenPw != null && !isShow){
                    LogUtil.v(TAG,"复用PopupWindow");
                    screenPw.showAsDropDown(v);
                    arrow.setImageResource(R.drawable.up);
                    isShow = !isShow;
                    return;
                }
                View root = LayoutInflater.from(context).inflate(R.layout.nearby_screen_layout,null);
                final RelativeLayout use = (RelativeLayout) root.findViewById(R.id.use);
                RelativeLayout enough = (RelativeLayout) root.findViewById(R.id.enough);
                final SlideSwitch use_switch = (SlideSwitch) root.findViewById(R.id.use_switch);
                final SlideSwitch enough_switch = (SlideSwitch) root.findViewById(R.id.enough_switch);
                use_switch.setEnabled(false);
                enough_switch.setEnabled(false);
                if(isUse){
                    use_switch.changeSwitchStatus();
                }
                use.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        use_switch.changeSwitchStatus();
                    }
                });

                enough.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        enough_switch.changeSwitchStatus();
                    }
                });

                use_switch.setOnSwitchChangedListener(new SlideSwitch.OnSwitchChangedListener() {
                    @Override
                    public void onSwitchChanged(SlideSwitch obj, int status) {
                        if (status == 1) {
                            isUse = true;
                            nearbyStationDao.daos = screenUse(nearbyStationDao.daos);
                            LogUtil.v(TAG,nearbyStationDao.daos.size()+"");
                            adapter = new NearbyListItemAdapter(context,nearbyStationDao.daos);
                            list.setAdapter(adapter);
                        } else {
                            isUse = false;
                            nearbyStationDao.daos.addAll(dismissList);
                            adapter = new NearbyListItemAdapter(context,nearbyStationDao.daos);
                            list.setAdapter(adapter);
                        }
                    }
                });

                ImageView bg = (ImageView) root.findViewById(R.id.bg);
                bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (screenPw != null && screenPw.isShowing()) {
                            screenPw.dismiss();
                            isShow = false;
                        }
                    }
                });
                arrow.setImageResource(R.drawable.up);
                Tab1Fragment tab1Fragment = (Tab1Fragment) getParentFragment();
                screenPw = popupWindowUtil.getPopupWindow(context, root, windowUtil.getScreenWidth(getActivity()), windowUtil.getScreenHeight(getActivity()) - tab1Fragment.getActionBarHeight() - screen.getHeight() - windowUtil.getStateBarHeight(context));
                screenPw.showAsDropDown(v);
                isShow = true;
            }
        });
    }


    public List<NearbyStation> screenUse(List<NearbyStation> list){
        dismissList.clear();
        List<NearbyStation> show = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(TextUtils.equals("1", list.get(i).getEvc_stations_get().getIsAvailable())){
                show.add(list.get(i));
            }else{
                dismissList.add(list.get(i));
            }
        }
        return show;
    }





    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgPositionList:
                    if(nearbyStationDao.daos == null){
                        return;
                    }
                    adapter = new NearbyListItemAdapter(context, nearbyStationDao.daos);
                    list.setAdapter(adapter);
                    break;
            }
        }
    };

    @Override
    public String getTAG() {
        return TAG;
    }
}
