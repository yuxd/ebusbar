package com.ebusbar.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ebusbar.activities.BaseActivity;
import com.ebusbar.bean.Error;
import com.ebusbar.bean.GetChargeAppoint;
import com.ebusbar.bean.Login;
import com.ebusbar.bean.PileInfo;
import com.ebusbar.bean.StartCharge;
import com.ebusbar.fragments.UtilFragment;
import com.ebusbar.impl.FinishOrderDaoImpl;
import com.ebusbar.impl.GetChargeAppointDaoImpl;
import com.ebusbar.impl.PileInfoDaoImpl;
import com.ebusbar.impl.StartChargeDaoImpl;
import com.ebusbar.map.MyLocation;
import com.ebusbar.pile.ChargeActivity;
import com.ebusbar.pile.NaviEmulatorActivity;
import com.ebusbar.pile.R;
import com.ebusbar.utils.ActivityControl;
import com.ebusbar.utils.DateUtil;
import com.ebusbar.utils.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的预约充电预约
 * Created by Jelly on 2016/3/10.
 */
public class ChargeAppointFragment extends UtilFragment implements View.OnClickListener {
    /**
     * TAG
     */
    public String TAG = "ChargeAppointFrag";
    @Bind(R.id.orgName)
    TextView orgName;
    @Bind(R.id.addr)
    TextView addr;
    @Bind(R.id.facilityName)
    TextView facilityName;
    @Bind(R.id.facilityType)
    TextView facilityType;
    @Bind(R.id.orderNo)
    TextView orderNo;
    @Bind(R.id.facilityMode)
    TextView facilityMode;
    @Bind(R.id.appoint_price_text)
    TextView appointPriceText;
    @Bind(R.id.phone_text)
    TextView phoneText;
    @Bind(R.id.start)
    TextView start;
    @Bind(R.id.nodata_show)
    LinearLayout nodataShow;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.startTime)
    TextView startTime;
    @Bind(R.id.endTime)
    TextView endTime;

    /**
     * GetChargeAppointDaoImpl
     */
    private GetChargeAppointDaoImpl getChargeAppointDao;
    /**
     * 获取预约消息
     */
    private final int msgGetAppoint = 0x001;
    /**
     * 结束订单
     */
    private FinishOrderDaoImpl finishOrderDao;
    /**
     * 结束订单消息
     */
    private final int msgFinish = 0x002;
    /**
     * StartChargeDaoImpl
     */
    private StartChargeDaoImpl startChargeDao;
    /**
     * 开始充电消息
     */
    private final int msgStart = 0x003;
    /**
     * 加载数据PopupWindow
     */
    private PopupWindow loading;
    /**
     * 电桩详情
     */
    private PileInfoDaoImpl pileInfoDao;
    /**
     * 电桩详情消息
     */
    private static final int msgPileInfo = 0x005;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        init(inflater, container);
        loadObjectAttribute();
        setListener();
        if (!loadSavedInstanceState()) {
            LogUtil.v(TAG, "从网络加载预约充电数据");
            setFragView();
        }
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        LogUtil.v(TAG, "销毁界面");
        saveInstanceState(getArguments());
    }

    public void saveInstanceState(Bundle outState) {
        LogUtil.v(TAG, "保存预约充电数据");
        Bundle bundle = new Bundle();
        bundle.putParcelable("OrderData", getChargeAppointDao.getChargeAppointDao);
        bundle.putParcelable("PileData", pileInfoDao.pileInfoDao);
        outState.putBundle("saveData", bundle);
    }


    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.chargeappoint, container, false);
        ButterKnife.bind(this, root);
    }


    @Override
    public void loadObjectAttribute() {
        getChargeAppointDao = new GetChargeAppointDaoImpl(context, handler, msgGetAppoint);
        finishOrderDao = new FinishOrderDaoImpl(context, handler, msgFinish);
        startChargeDao = new StartChargeDaoImpl(context, handler, msgStart);
        pileInfoDao = new PileInfoDaoImpl(context, handler, msgPileInfo);
    }

    @Override
    public void setListener() {
    }

    @Override
    public void setFragView() {
        loading = popupWindowUtil.startLoading(context, start, "加载中");
        Login.DataEntity data = application.getLoginDao().getData();
        getChargeAppointDao.getNetGetChargeAppointDao(data.getToken(), data.getCustID());
    }

    /**
     * 加载
     */
    public boolean loadSavedInstanceState() {
        if (getArguments() == null) {
            return false;
        }
        if (getArguments().getBundle("saveData") == null) {
            return false;
        }
        Bundle savedInstanceState = getArguments().getBundle("saveData");
        if (savedInstanceState == null) {
            return false;
        }
        getChargeAppointDao.getChargeAppointDao = (GetChargeAppoint) savedInstanceState.get("OrderData");
        pileInfoDao.pileInfoDao = (PileInfo) savedInstanceState.get("PileData");
        if (getChargeAppointDao.getChargeAppointDao == null || pileInfoDao.pileInfoDao == null) {
            nodataShow.setVisibility(View.VISIBLE);
            return true;
        }
        GetChargeAppoint.EvcOrdersGetEntity evcOrdersGetEntity = getChargeAppointDao.getChargeAppointDao.getEvc_orders_get();
        PileInfo.EvcFacilityGetEntity evcFacilityGetEntity = pileInfoDao.pileInfoDao.getEvc_facility_get();
        orderNo.setText(evcOrdersGetEntity.getOrderNo());
        phoneText.setText(evcOrdersGetEntity.getTel());
        appointPriceText.setText("¥" + evcOrdersGetEntity.getPlanCost());
        startTime.setText(DateUtil.getSdfDate(evcOrdersGetEntity.getPlanBeginDateTime(), "HH:mm"));
        endTime.setText(DateUtil.getSdfDate(evcOrdersGetEntity.getPlanEndDateTime(), "HH:mm"));
        if (TextUtils.equals("00", DateUtil.DifferDate(evcOrdersGetEntity.getPlanEndDateTime(), evcOrdersGetEntity.getPlanBeginDateTime()))) {
            time.setText("60");
        } else {
            time.setText(DateUtil.DifferDate(evcOrdersGetEntity.getPlanEndDateTime(), evcOrdersGetEntity.getPlanBeginDateTime()));
        }
        orgName.setText(evcFacilityGetEntity.getOrgName());
        facilityName.setText(evcFacilityGetEntity.getFacilityName());
        if (TextUtils.equals("1", evcFacilityGetEntity.getFacilityType())) {
            facilityType.setText("直流桩");
        } else if (TextUtils.equals("2", evcFacilityGetEntity.getFacilityType())) {
            facilityType.setText("交流桩");
        }
        addr.setText(evcFacilityGetEntity.getAddr());
        return true;
    }

    @OnClick({R.id.start, R.id.cancel, R.id.navigation, R.id.phone_btn})
    public void onClick(View view) {
        Login.DataEntity entity = application.getLoginDao().getData(); //用户数据
        switch (view.getId()) {
            case R.id.start:
                //开始充电,在开始充电之前
                if (getChargeAppointDao.getChargeAppointDao == null || TextUtils.equals(getChargeAppointDao.getChargeAppointDao.getEvc_orders_get().getIsSuccess(), "N")) {
                    Toast.makeText(context, "对不起，暂时无法开始充电！", Toast.LENGTH_SHORT).show();
                    return;
                }
                startChargeDao.getStartChargeDao(entity.getToken(), getChargeAppointDao.getChargeAppointDao.getEvc_orders_get().getOrderNo(), entity.getCustID());
                break;
            case R.id.cancel:
                if (getChargeAppointDao.getChargeAppointDao == null || TextUtils.equals(getChargeAppointDao.getChargeAppointDao.getEvc_orders_get().getIsSuccess(), "N")) {
                    Toast.makeText(context, "对不起，此订单暂时无法结束！", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialogUtil.showSureListenerDialog(context, "是否需要取消预约！", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Login.DataEntity entity = application.getLoginDao().getData();
                        finishOrderDao.getFinishOrderDao(entity.getToken(), getChargeAppointDao.getChargeAppointDao.getEvc_orders_get().getOrderNo(), entity.getCustID());
                    }
                });
                break;
            case R.id.navigation:
                MyLocation location = application.getLocation();
                GetChargeAppoint.EvcOrdersGetEntity entity1 = getChargeAppointDao.getChargeAppointDao.getEvc_orders_get();
                NaviEmulatorActivity.startAppActivity(context, Double.parseDouble(location.getLatitude()), Double.parseDouble(location.getLongitude()), Double.parseDouble(entity1.getLatitude()), Double.parseDouble(entity1.getLongitude()));
                break;
            case R.id.phone_btn:
                Intent intent=new Intent("android.intent.action.CALL", Uri.parse("tel:"+phoneText.getText().toString()));
                startActivity(intent);
                break;
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case msgGetAppoint: //获得预约数据
                    if (getChargeAppointDao.getChargeAppointDao == null || TextUtils.equals(getChargeAppointDao.getChargeAppointDao.getEvc_orders_get().getIsSuccess(), "N")) {
                        nodataShow.setVisibility(View.VISIBLE);
                        if (loading != null) {
                            loading.dismiss();
                        }
                        return;
                    }
                    GetChargeAppoint.EvcOrdersGetEntity entity = getChargeAppointDao.getChargeAppointDao.getEvc_orders_get();
                    orderNo.setText(entity.getOrderNo());
                    phoneText.setText(entity.getTel());
                    appointPriceText.setText("¥" + entity.getPlanCost());
                    pileInfoDao.getPileInfoDao(entity.getFacilityID(), PileInfoDaoImpl.FACILITYID);
                    startTime.setText(DateUtil.getSdfDate(entity.getPlanBeginDateTime(), "HH:mm"));
                    endTime.setText(DateUtil.getSdfDate(entity.getPlanEndDateTime(), "HH:mm"));
                    if (TextUtils.equals("00", DateUtil.DifferDate(entity.getPlanEndDateTime(), entity.getPlanBeginDateTime()))) {
                        time.setText("60");
                    } else {
                        time.setText(DateUtil.DifferDate(entity.getPlanEndDateTime(), entity.getPlanBeginDateTime()));
                    }
                    break;
                case msgFinish: //结束预约
                    if (finishOrderDao.finishOrderDao == null) {
                        return;
                    }
                    if (TextUtils.equals(finishOrderDao.finishOrderDao.getEvc_order_cancel().getIsSuccess(), "N")) {
                        Error errorDao = errorParamUtil.checkReturnState(finishOrderDao.finishOrderDao.getEvc_order_cancel().getReturnStatus());
                        toastUtil.toastError(context, errorDao, null);
                        return;
                    }
                    Toast.makeText(context, "成功取消预约！", Toast.LENGTH_SHORT).show();
                    ActivityControl.finishAct((BaseActivity) getActivity());
                    break;
                case msgStart: //开始充电
                    if (startChargeDao.startChargeDao == null || TextUtils.equals(startChargeDao.startChargeDao.getEvc_order_change().getIsSuccess(), "N")) {
                        Error errorDao = errorParamUtil.checkReturnState(startChargeDao.startChargeDao.getEvc_order_change().getReturnStatus());
                        toastUtil.toastError(context, errorDao, null);
                        return;
                    }
                    ActivityControl.finishAct((BaseActivity) getActivity());
                    StartCharge.EvcOrderChangeEntity entity1 = startChargeDao.startChargeDao.getEvc_order_change();
                    ChargeActivity.startAppActivity(context, entity1);
                    break;
                case msgPileInfo:
                    if (loading != null) {
                        loading.dismiss();
                    }
                    if (TextUtils.equals("N", pileInfoDao.pileInfoDao.getEvc_facility_get().getIsSuccess())) {
                        Error error = errorParamUtil.checkReturnState(pileInfoDao.pileInfoDao.getEvc_facility_get().getReturnStatus());
                        toastUtil.toastError(context, error, null);
                        return;
                    }
                    PileInfo.EvcFacilityGetEntity evcFacilityGetEntity = pileInfoDao.pileInfoDao.getEvc_facility_get();
                    orgName.setText(evcFacilityGetEntity.getOrgName());
                    facilityName.setText(evcFacilityGetEntity.getFacilityName());
                    if (TextUtils.equals("1", evcFacilityGetEntity.getFacilityType())) {
                        facilityType.setText("直流桩");
                    } else if (TextUtils.equals("2", evcFacilityGetEntity.getFacilityType())) {
                        facilityType.setText("交流桩");
                    }
                    addr.setText(evcFacilityGetEntity.getAddr());
                    break;
            }
        }
    };

    @Override
    public String getTAG() {
        return TAG;
    }


}
