package com.ebusbar.pile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.bean.ChargeCardItem;
import com.ebusbar.bean.Error;
import com.ebusbar.bean.Login;
import com.ebusbar.handlerinterface.NetErrorHandlerListener;
import com.ebusbar.impl.DeleteChargeCardDaoImpl;
import com.ebusbar.utils.ActivityControl;
import com.ebusbar.param.NetErrorEnum;

/**
 *
 * Created by Jelly on 2016/3/23.
 */
public class DeleteChargeCardActivity extends UtilActivity implements NetErrorHandlerListener{
    /**
     * TAG
     */
    public String TAG = "DeleteChargeCardActivity";
    /**
     * 数据包
     */
    private Intent intent;
    /**
     * 列表数据
     */
    private ChargeCardItem dao;
    /**
     * 密码输入框
     */
    private EditText password;
    /**
     * 充电卡
     */
    private TextView no;
    /**
     * 删除
     */
    private ImageView delete;
    /**
     * DeleteChargeCardDaoImpl
     */
    private DeleteChargeCardDaoImpl deleteChargeCardDao;
    /**
     * 删除消息
     */
    private final int msgDelete = 0x001;
    /**
     * 删除成功
     */
    public static final int SUCCESS = 0x002;
    /**
     * 删除失败
     */
    public static final int FAILURE = 0x003;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.deletechargecard);
        init();
        loadObjectAttribute();
        setListener();
        setActivityView();
    }

    @Override
    public void init() {
        no = (TextView) this.findViewById(R.id.no);
        password = (EditText) this.findViewById(R.id.password);
        delete = (ImageView) this.findViewById(R.id.delete_btn);
    }

    @Override
    public void loadObjectAttribute() {
        intent = getIntent();
        dao = intent.getParcelableExtra("ChargeCardItemDao");
        application = (MyApplication) getApplication();
        deleteChargeCardDao = new DeleteChargeCardDaoImpl(this,handler,msgDelete);
    }

    @Override
    public void setListener() {
        setDeleteListener();
    }

    @Override
    public void setActivityView() {
        no.setText(dao.getCrm_accounts_get().getAccountNo());
    }

    /**
     * 设置删除监听器
     */
    public void setDeleteListener(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwds = password.getText().toString();
                if (TextUtils.isEmpty(pwds)) {
                    Toast.makeText(DeleteChargeCardActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                Login.DataEntity entity = application.getLoginDao().getData();
                deleteChargeCardDao.getDeleteChargeCardDao(entity.getToken(), dao.getCrm_accounts_get().getAccountID(), pwds, entity.getCustID());
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgDelete:
                    if(deleteChargeCardDao.deleteChargeCardDao == null || TextUtils.equals(deleteChargeCardDao.deleteChargeCardDao.getCrm_accounts_delete().getIsSuccess(), "N")){
                        Error errorDao = errorParamUtil.checkReturnState(deleteChargeCardDao.deleteChargeCardDao.getCrm_accounts_delete().getReturnStatus());
                        if(toastUtil.toastError(context,errorDao,DeleteChargeCardActivity.this)){
                            return;
                        }
                        setResult(FAILURE);
                        return;
                    }
                    Intent intent = new Intent();
                    intent.putExtra("DeleteChargeCardDao",DeleteChargeCardActivity.this.intent.getParcelableExtra("ChargeCardItemDao"));
                    setResult(SUCCESS,intent);
                    ActivityControl.finishAct(DeleteChargeCardActivity.this);
                    break;
            }
        }
    };

    @Override
    public void handlerError(String returnState) {
        if(TextUtils.equals(returnState, NetErrorEnum.充电卡密码错误.getState())){
            password.setText("");
        }
    }

    /**
     * 开启界面
     */
    public static void startAppActivity(Context context,ChargeCardItem dao,int resquestCode){
        Intent intent = new Intent(context, DeleteChargeCardActivity.class);
        intent.putExtra("ChargeCardItemDao", dao);
        Activity activity = (Activity) context;
        activity.startActivityForResult(intent,resquestCode);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
