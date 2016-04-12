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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.ebusbar.bean.Error;
import com.ebusbar.bean.StationInfo;
import com.ebusbar.fragments.UtilFragment;
import com.ebusbar.impl.StationInfoDaoImpl;
import com.ebusbar.pile.LoginActivity;
import com.ebusbar.pile.NaviEmulatorActivity;
import com.ebusbar.pile.R;
import com.ebusbar.pile.SelectPileActivity;
import com.ebusbar.pile.StationInfoActivity;
import com.ebusbar.utils.FloatUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 电站详情
 * Created by Jelly on 2016/4/10.
 */
public class StationDetailFragment extends UtilFragment {

    public String TAG = "StationDetailFragment";
    @Bind(R.id.icon)
    ImageView icon;
    @Bind(R.id.orgName)
    TextView orgName;
    @Bind(R.id.open_text)
    TextView openText;
    @Bind(R.id.distance_text)
    TextView distanceText;
    @Bind(R.id.addr)
    TextView addr;
    @Bind(R.id.nav_layout)
    LinearLayout navLayout;
    @Bind(R.id.appoint_layout)
    LinearLayout appointLayout;
    @Bind(R.id.sum_text)
    TextView sumText;
    @Bind(R.id.spare_text)
    TextView spareText;
    @Bind(R.id.company)
    TextView company;
    @Bind(R.id.company_text)
    TextView companyText;
    @Bind(R.id.phone_text)
    TextView phoneText;

    /**
     * 获取充电站详情
     */
    private StationInfoDaoImpl stationInfoDao;
    /**
     * 充电站详情消息
     */
    private static final int msgStationInfo = 0x001;
    /**
     * 获取电站Id的接口
     */
    private OrgIdFragmentListener orgIdFragmentListener;

    /**
     * 获取充电点Id的接口
     */
    public static interface OrgIdFragmentListener {
        String getOrgId();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        init(inflater, container);
        loadObjectAttribute();
        setListener();
        setFragView();
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        StationInfoActivity activity = (StationInfoActivity) context;
        orgIdFragmentListener = activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.station_detail_fragment, container, false);
        ButterKnife.bind(this, root);
    }

    @Override
    public void loadObjectAttribute() {
        stationInfoDao = new StationInfoDaoImpl(context, handler, msgStationInfo);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setFragView() {
        stationInfoDao.getStationInfo(orgIdFragmentListener.getOrgId());
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case msgStationInfo:
                    if (TextUtils.equals("N", stationInfoDao.stationInfo.getIsSuccess())) {
                        Error error = errorParamUtil.checkReturnState(stationInfoDao.stationInfo.getReturnStatus());
                        toastUtil.toastError(context, error, null);
                        return;
                    }
                    final StationInfo.DataEntity dataEntity = stationInfoDao.stationInfo.getData();
                    orgName.setText(dataEntity.getOrgName());

                    if(TextUtils.equals(dataEntity.getIsAvailable(),"1")){
                        openText.setText("有空闲");
                    }else{
                        openText.setText("繁忙中");
                    }

                    LatLng startLatLng = new LatLng(Double.parseDouble(application.getLocation().getLatitude()),Double.parseDouble(application.getLocation().getLongitude()));
                    LatLng endLatLng = new LatLng(Double.parseDouble(dataEntity.getLatitude()),Double.parseDouble(dataEntity.getLongitude()));
                    distanceText.setText(FloatUtil.mToKm(AMapUtils.calculateLineDistance(startLatLng,endLatLng)) + "km");

                    addr.setText(dataEntity.getAddr());

                    String sum = "0";
                    if(!TextUtils.isEmpty(dataEntity.getAvailableNum()) && !TextUtils.isEmpty(dataEntity.getUnavailableNum())){
                        sum = Integer.parseInt(dataEntity.getAvailableNum()) + Integer.parseInt(dataEntity.getUnavailableNum())+"";
                    }
                    sumText.setText(sum);
                    if(!TextUtils.equals(sum,"0")){
                        spareText.setText(dataEntity.getAvailableNum());
                    }else{
                        spareText.setText("0");
                    }

                    company.setText(dataEntity.getCompanyName());
                    companyText.setText("该充电点由"+dataEntity.getCompanyName()+"负责运营");
                    phoneText.setText(dataEntity.getTel());

                    navLayout.setOnClickListener(new View.OnClickListener() { //导航
                        @Override
                        public void onClick(View v) {
                            NaviEmulatorActivity.startAppActivity(context, Double.parseDouble(application.getLocation().getLatitude()),Double.parseDouble(application.getLocation().getLongitude()), Double.parseDouble(dataEntity.getLatitude()), Double.parseDouble(dataEntity.getLongitude()));
                        }
                    });

                    appointLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) { //预约
                            if(!application.isLogin()){
                                LoginActivity.startAppActivity(context);
                            }else {
                                SelectPileActivity.startAppActivity(context, dataEntity.getOrgId(),dataEntity.getAddr());
                            }
                        }
                    });

                    break;
            }
        }
    };

    @Override
    public String getTAG() {
        return TAG;
    }
}
