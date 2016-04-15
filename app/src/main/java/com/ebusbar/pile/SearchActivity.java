package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.adpater.SearchListAdapter;
import com.ebusbar.bean.AllStation;
import com.ebusbar.bean.Error;
import com.ebusbar.impl.AllStationDaoImpl;
import com.ebusbar.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 搜索电桩
 * Created by Jelly on 2016/3/28.
 */
public class SearchActivity extends UtilActivity {
    /**
     * TAG
     */
    public String TAG = "SearchActivity";
    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.list)
    ListView list;
    /**
     * 数据包
     */
    private Intent intent;
    /**
     * 搜索集合
     */
    private List<AllStation> daos;
    /**
     * 搜索出来的数据
     */
    private List<AllStation> searchs;
    /**
     * 适配器
     */
    private SearchListAdapter adapter;
    /**
     * 获取所有充电桩
     */
    private AllStationDaoImpl allStationDao;
    /**
     * 获取所有电桩的消息
     */
    private final int msgAllStation = 0x001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.search);
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
        intent = getIntent();
        daos = intent.getParcelableArrayListExtra("daos");
        if (daos != null) {
            daos = setCondition(daos);
        }
        allStationDao = new AllStationDaoImpl(this, handler, msgAllStation);
    }

    @Override
    public void setListener() {
        setInputListener();
        setSearchListItemClickListener();
    }

    @Override
    public void setActivityView() {
        if (daos == null) {
            allStationDao.getDaos();
        }
    }

    /**
     * 设置搜索列表的点击监听事件
     */
    public void setSearchListItemClickListener() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (searchs.get(position) != null) {
                    StationInfoActivity.startActivity(context, searchs.get(position).getEvc_stations_getall().getOrgId());
                }
            }
        });
    }

    /**
     * 设置输入监听
     */
    public void setInputListener() {
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = searchEt.getText().toString();
                LogUtil.v(TAG, "开始搜索：" + searchText);
                if (daos == null || daos.size() == 0) {
                    return;
                }
                try {
                    searchs = searchUtil.searchListOnRegExp(searchText, daos);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
                if (searchs != null) {
                    adapter = new SearchListAdapter(context, application, searchs);
                    list.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 设置条件
     */
    public List<AllStation> setCondition(List<AllStation> daos) {
        for (int i = 0; i < daos.size(); i++) {
            AllStation dao = daos.get(i);
            AllStation.EvcStationsGetallEntity entity = dao.getEvc_stations_getall();
            dao.setCondition(entity.getOrgName() + entity.getAddr() + entity.getCity());
        }
        return daos;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (allStationDao.daos == null) {
                return;
            }
            if (TextUtils.equals("N", allStationDao.daos.get(0).getEvc_stations_getall().getIsSuccess())) {
                Error errorDao = errorParamUtil.checkReturnState(allStationDao.daos.get(0).getEvc_stations_getall().getReturnStatus());
                toastUtil.toastError(context, errorDao, null);
                return;
            }
            daos = setCondition(allStationDao.daos);
        }
    };

    /**
     * 开启界面
     *
     * @param context
     */
    public static void startAppActivity(Context context, ArrayList<AllStation> daos) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putParcelableArrayListExtra("daos", daos);
        context.startActivity(intent);
    }

    /**
     * 从附近界面跳入到搜索界面
     *
     * @param context
     */
    public static void startAppActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
