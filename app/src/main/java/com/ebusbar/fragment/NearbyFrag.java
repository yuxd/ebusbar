package com.ebusbar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ebusbar.impl.PositionDaoImpl;
import com.ebusbar.pile.MyApplication;
import com.ebusbar.pile.R;

/**
 * Created by Jelly on 2016/3/24.
 */
public class NearbyFrag extends BaseFrag{
    /**
     * TAG
     */
    public String TAG = "NearbyFrag";
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
     * PositionDaoImpl
     */
    private PositionDaoImpl positionDao;
    /**
     * 获取电桩位置集合消息
     */
    private final int msgPositionList = 0x001;
    /**
     * Application
     */
    private MyApplication application;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init(inflater, container);
        loadObjectAttribute();
        setListener();
        setFragView();
        return root;
    }

    @Override
    public void init(LayoutInflater inflater,ViewGroup container) {
        root = inflater.inflate(R.layout.nearby,container,false);
        list = (ListView) root.findViewById(R.id.list);
    }

    @Override
    public void loadObjectAttribute() {
        context = getContext();
        application = (MyApplication) getActivity().getApplication();
        positionDao = new PositionDaoImpl(context,handler,msgPositionList);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setFragView() {
        positionDao.getNetPositionListDao("shenzhen");
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgPositionList:
                    if(positionDao.positionDaoList.size() == 0){
                        return;
                    }

                    break;
            }
        }
    };

    @Override
    public String getTAG() {
        return TAG;
    }
}
