package com.ebusbar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ebusbar.adpater.NearbyListItemAdapter;
import com.ebusbar.fragments.UtilFragment;
import com.ebusbar.impl.NearbyStationDaoImpl;
import com.ebusbar.pile.MainActivity;
import com.ebusbar.pile.MyApplication;
import com.ebusbar.pile.R;
import com.ebusbar.utils.LogUtil;

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
     * 根布局
     */
    private View root;
    /**
     * 充电点的列表
     */
    private ListView list;
    /**
     * 地图切换
     */
    private TextView map;
    /**
     * 会员头像
     */
    private ImageView member;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        init(inflater, container);
        loadObjectAttribute();
        setListener();
        setFragView();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        setFragView();
    }

    @Override
    public void init(LayoutInflater inflater,ViewGroup container) {
        root = inflater.inflate(R.layout.nearby,container,false);
        list = (ListView) root.findViewById(R.id.list);
        map = (TextView) root.findViewById(R.id.map);
        member = (ImageView) root.findViewById(R.id.member);
    }

    @Override
    public void loadObjectAttribute() {
        context = getContext();
        application = (MyApplication) getActivity().getApplication();
        nearbyStationDao = new NearbyStationDaoImpl(context,handler,msgPositionList);
    }

    @Override
    public void setListener() {
        setMapListener();
        setOpenDrawerListener();
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
     * 当点击会员头像的时候,打开抽屉
     */
    public void setOpenDrawerListener(){
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.drawerLayout.openDrawer(Gravity.LEFT); //打开左边抽屉
            }
        });
    }

    /**
     * 设置切换Map的点击事件
     */
    public void setMapListener(){
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.remove(NearbyStationFragment.this);
                ft.add(R.id.content, new AllStationFragment());
                ft.commit();
            }
        });
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
