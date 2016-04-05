package com.ebusbar.pile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.dao.LoginDao;

/**
 * Created by Jelly on 2016/3/31.
 */
public class ModCustNameActivity extends UtilActivity{
    /**
     * TAG
     */
    public String TAG = "ModCustNameActivity";
    /**
     * 昵称输入框
     */
    private EditText custName_et;
    /**
     * 成功
     */
    public static final int SUCCESS = 0x001;
    /**
     * 失败
     */
    public static final int FAILURE = 0x002;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.modcustname);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {
        custName_et = (EditText) this.findViewById(R.id.custName_et);
    }

    @Override
    public void loadObjectAttribute() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void setActivityView() {
        LoginDao.DataEntity entity = application.getLoginDao().getData();
        custName_et.setText(entity.getCustName());
    }

    /**
     * 开启界面
     */
    public static void startAppActivity(Context context,int requestCode){
        Intent intent = new Intent(context,ModCustNameActivity.class);
        Activity activity = (Activity) context;
        activity.startActivityForResult(intent,requestCode);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
