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
import android.widget.EditText;
import android.widget.ListView;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.adpater.SearchListAdapter;
import com.ebusbar.dao.AllStationDao;
import com.ebusbar.dao.ErrorDao;
import com.ebusbar.impl.AllStationDaoImpl;
import com.ebusbar.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索电桩
 * Created by Jelly on 2016/3/28.
 */
public class SearchActivity extends UtilActivity {
    /**
     * TAG
     */
    public String TAG = "SearchActivity";
    /**
     * 列表
     */
    private ListView list;
    /**
     * 搜索输入框
     */
    private EditText search_et;
    /**
     * 数据包
     */
    private Intent intent;
    /**
     * 搜索集合
     */
    private List<AllStationDao> daos;
    /**
     * 搜索出来的数据
     */
    private List<AllStationDao> searchs;
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
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {
        list = (ListView) this.findViewById(R.id.list);
        search_et = (EditText) this.findViewById(R.id.search_et);
    }

    @Override
    public void loadObjectAttribute() {
        intent = getIntent();
        daos = intent.getParcelableArrayListExtra("daos");
        if(daos != null){
            daos = setCondition(daos);
        }
        allStationDao = new AllStationDaoImpl(this,handler,msgAllStation);
    }

    @Override
    public void setListener() {
        setInputListener();
    }

    @Override
    public void setActivityView() {
        if(daos == null){
            allStationDao.getDaos();
        }
    }

    /**
     * 设置输入监听
     */
    public void setInputListener(){
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = search_et.getText().toString();
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
    public List<AllStationDao> setCondition(List<AllStationDao> daos){
        for(int i=0;i<daos.size();i++){
            AllStationDao dao = daos.get(i);
            AllStationDao.EvcStationsGetallEntity entity = dao.getEvc_stations_getall();
            dao.setCondition(entity.getOrgName() + entity.getAddr() + entity.getCity());
        }
        return daos;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(allStationDao.daos == null){
                return;
            }
            if(TextUtils.equals("N",allStationDao.daos.get(0).getEvc_stations_getall().getIsSuccess())){
                ErrorDao errorDao = errorParamUtil.checkReturnState(allStationDao.daos.get(0).getEvc_stations_getall().getReturnStatus());
                toastUtil.toastError(context,errorDao,null);
                return;
            }
            daos = setCondition(allStationDao.daos);
        }
    };

    /**
     * 开启界面
     * @param context
     */
    public static void startAppActivity(Context context,ArrayList<AllStationDao> daos){
        Intent intent = new Intent(context,SearchActivity.class);
        intent.putParcelableArrayListExtra("daos",daos);
        context.startActivity(intent);
    }

    /**
     * 从附近界面跳入到搜索界面
     * @param context
     */
    public static void startAppActivity(Context context){
        Intent intent = new Intent(context,SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
