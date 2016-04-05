package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.adpater.SearchListAdapter;
import com.ebusbar.dao.AllStationDao;
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
     *
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
        daos = setCondition(daos);
    }

    @Override
    public void setListener() {
        setInputListener();
    }

    @Override
    public void setActivityView() {

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
                LogUtil.v(TAG,"开始搜索：" + searchText);
                try {
                    searchs = searchUtil.searchListOnRegExp(searchText, daos);
                }catch (Exception e){
                    Log.e(TAG,e.getMessage());
                }
                if(searchs != null){
                    adapter = new SearchListAdapter(context,application,searchs);
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

    /**
     * 开启界面
     * @param context
     */
    public static void startAppActivity(Context context,ArrayList<AllStationDao> daos){
        Intent intent = new Intent(context,SearchActivity.class);
        intent.putParcelableArrayListExtra("daos",daos);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
