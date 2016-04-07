package com.ebusbar.pile;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.bean.Error;
import com.ebusbar.bean.Login;
import com.ebusbar.impl.UpdateUserInfoDaoImpl;
import com.ebusbar.utils.ActivityControl;
import com.ebusbar.utils.LogUtil;

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
     * 完成按钮
     */
    private TextView ok;
    /**
     * 成功
     */
    public static final int SUCCESS = 0x001;
    /**
     * 失败
     */
    public static final int FAILURE = 0x002;
    /**
     * 修改用户信息
     */
    private UpdateUserInfoDaoImpl updateUserInfoDao;
    /**
     * 修改用户信息的消息
     */
    private final int msgUpdate = 0x003;

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
        ok = (TextView) this.findViewById(R.id.ok);
    }

    @Override
    public void loadObjectAttribute() {
        updateUserInfoDao = new UpdateUserInfoDaoImpl(this,handler,msgUpdate);
    }

    @Override
    public void setListener() {
        setOkListener();
    }

    @Override
    public void setActivityView() {
        Login.DataEntity entity = application.getLoginDao().getData();
        custName_et.setText(entity.getCustName());
    }


    @Override
    public View back(View view) {
        String custName = custName_et.getText().toString();
        Login.DataEntity entity = application.getLoginDao().getData();
        if(!TextUtils.equals(custName,entity.getCustName()) && !TextUtils.isEmpty(custName)){
            dialogUtil.showSureListenerDialog(context, "是否取消本次修改?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setResult(FAILURE);
                    ActivityControl.finishAct(ModCustNameActivity.this);
                }
            });
        }
        ActivityControl.finishAct(ModCustNameActivity.this);
        return view;
    }

    public void setOkListener(){
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String custName = custName_et.getText().toString();
                LogUtil.v(TAG,custName);
                if (TextUtils.isEmpty(custName)) {
                    Toast.makeText(ModCustNameActivity.this, "昵称不能为空！", Toast.LENGTH_SHORT).show();
                }
                Login.DataEntity entity = application.getLoginDao().getData();
                updateUserInfoDao.getDao(entity.getToken(), entity.getCustID(), custName,"", "", "");
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgUpdate:
                    if(TextUtils.equals("N",updateUserInfoDao.dao.getIsSuccess())){
                        Error errorDao = errorParamUtil.checkReturnState(updateUserInfoDao.dao.getReturnStatus());
                        toastUtil.toastError(context,errorDao,null);
                        setResult(FAILURE);
                        ActivityControl.finishAct(ModCustNameActivity.this);
                        return;
                    }
                    String custName = custName_et.getText().toString();
                    setResult(SUCCESS);
                    application.getLoginDao().getData().setCustName(custName);
                    application.cacheLogin();
                    ActivityControl.finishAct(ModCustNameActivity.this);
                    break;
            }
        }
    };

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
