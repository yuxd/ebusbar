package com.ebusbar.pile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.bean.Error;
import com.ebusbar.bean.Login;
import com.ebusbar.handlerinterface.NetErrorHandlerListener;
import com.ebusbar.impl.BitmapImpl;
import com.ebusbar.impl.LogoutDaoImpl;
import com.ebusbar.impl.UpdateUserInfoDaoImpl;
import com.ebusbar.param.NetErrorEnum;
import com.ebusbar.utils.ActivityControl;
import com.ebusbar.utils.LogUtil;
import com.weidongjian.meitu.wheelviewdemo.view.LoopView;
import com.weidongjian.meitu.wheelviewdemo.view.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 账户管理
 * Created by Jelly on 2016/3/7.
 */
public class AccountManageActivity extends UtilActivity implements NetErrorHandlerListener {
    /**
     * TAG
     */
    public String TAG = "AccountManageActivity";
    @Bind(R.id.avatar_icon)
    ImageView avatarIcon;
    @Bind(R.id.nickname_text)
    TextView nicknameText;
    @Bind(R.id.sex_text)
    TextView sexText;
    @Bind(R.id.phone_text)
    TextView phoneText;
    @Bind(R.id.certification_text)
    TextView certificationText;
    /**
     * BitmapImpl
     */
    private BitmapImpl bitmap;
    /**
     * 用户头像消息
     */
    private final int msgIcon = 0x001;
    /**
     * LogoutDaoImpl
     */
    private LogoutDaoImpl logoutDao;
    /**
     * 注销消息
     */
    private final int msgLogout = 0x002;
    /**
     * 修改昵称
     */
    public static final int NICKNAMEREQUEST = 0x003;
    /**
     * 性别选择框
     */
    private PopupWindow sexPw;
    /**
     * 性别选择框是否弹出
     */
    private boolean isSex = false;
    /**
     * 当前选择的性别
     */
    private String currSex;
    /**
     * 更新用户信息
     */
    private UpdateUserInfoDaoImpl updateUserInfoDao;
    /**
     * 更新用户信息
     */
    private final int msgSex = 0x004;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.accountmanage);
        ButterKnife.bind(this);
        init();
        loadObjectAttribute();
        setActivityView();
        setListener();
    }

    @Override
    public void init() {
    }

    @Override
    public void loadObjectAttribute() {
        bitmap = new BitmapImpl(this, handler, msgIcon);
        logoutDao = new LogoutDaoImpl(this, handler, msgLogout);
        updateUserInfoDao = new UpdateUserInfoDaoImpl(this, handler, msgSex);

    }

    @Override
    public void setListener() {

    }

    @Override
    public void setActivityView() {
        Login loginDao = application.getLoginDao();
        if (!TextUtils.isEmpty(loginDao.getData().getUsericon())) {
            bitmap.getBitmap(loginDao.getData().getUsericon());
        }
        if (!TextUtils.isEmpty(loginDao.getData().getCustName())) {
            nicknameText.setText(loginDao.getData().getCustName());
        }
        sexText.setText(loginDao.getData().getSex());
        phoneText.setText(loginDao.getData().getMobile());
        if (TextUtils.equals(loginDao.getData().getVerified(), "1")) {
            certificationText.setText("是");
        } else {
            certificationText.setText("否");
        }
    }

    /**
     * 进入修改昵称界面
     *
     * @param view
     * @return
     */
    public View nickName(View view) {
        ModCustNameActivity.startAppActivity(context, NICKNAMEREQUEST);
        return view;
    }

    /**
     * 性别
     *
     * @param view
     * @return
     */
    public View sex(View view) {
        if (sexPw != null && isSex) {
            sexPw.dismiss();
            isSex = false;
            return view;
        } else if (sexPw != null && !isSex) {
            sexPw.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
            isSex = true;
            return view;
        }
        LogUtil.v(TAG, "重新构造一个SexPopupWindow");
        View root = LayoutInflater.from(context).inflate(R.layout.sexpw_layout, null);
        LoopView loopView = (LoopView) root.findViewById(R.id.loopView);
        ImageView bg = (ImageView) root.findViewById(R.id.bg);
        TextView cancel = (TextView) root.findViewById(R.id.cancel);
        TextView ok = (TextView) root.findViewById(R.id.ok);
        final List<String> items = new ArrayList<>();
        items.add("男");
        items.add("女");
        loopView.setItems(items);
        loopView.setNotLoop();
        loopView.setTextSize(20);
        loopView.setViewPadding(100,0,100,0);
        Login.DataEntity entity = application.getLoginDao().getData();
        if (TextUtils.equals("男", entity.getSex())) {
            loopView.setInitPosition(0);
        } else {
            loopView.setInitPosition(1);
        }
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                currSex = items.get(index);
            }
        });
        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexPw.dismiss();
                isSex = false;
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexPw.dismiss();
                isSex = false;
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(currSex) || TextUtils.equals(currSex, application.getLoginDao().getData().getSex())) {
                    sexPw.dismiss();
                    isSex = false;
                    return;
                }
                //修改性别
                Login.DataEntity entity = application.getLoginDao().getData();
                updateUserInfoDao.getDao(entity.getToken(), entity.getCustID(), "", currSex, "", "");
            }
        });
        sexPw = popupWindowUtil.getPopupWindow(context, root, windowUtil.getScreenWidth(this), windowUtil.getScreenHeight(this));
        sexPw.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
        isSex = true;
        return view;
    }

    /**
     * 注销
     */
    public View loginOut(View view) {
        dialogUtil.showSureListenerDialog(this, "是否要退出账户！", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Login.DataEntity entity = application.getLoginDao().getData();
                logoutDao.getLogoutDao(entity.getToken(), entity.getCustID());
            }
        });
        return view;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case msgIcon: //获取用户头像
                    if (bitmap.img == null) { //用户头像获取失败
                        return;
                    }
                    avatarIcon.setImageBitmap(bitmapUtil.toRoundBitmap(bitmap.img));
                    break;
                case msgLogout: //注销
                    if (logoutDao.logoutDao == null || TextUtils.equals(logoutDao.logoutDao.getCrm_logout().getIsSuccess(), "N")) {
                        Error errorDao = errorParamUtil.checkReturnState(logoutDao.logoutDao.getCrm_logout().getReturnStatus());
                        toastUtil.toastError(context, errorDao, AccountManageActivity.this);
                        return;
                    }
                    application.loginOut();
                    ActivityControl.finishAct(AccountManageActivity.this);
                    break;
                case msgSex:
                    if (TextUtils.equals("N", updateUserInfoDao.dao.getIsSuccess())) {
                        Error errorDao = errorParamUtil.checkReturnState(updateUserInfoDao.dao.getReturnStatus());
                        toastUtil.toastError(context, errorDao, null);
                        return;
                    }
                    application.getLoginDao().getData().setSex(currSex);
                    application.cacheLogin();
                    sexText.setText(currSex);
                    sexPw.dismiss();
                    isSex = false;
                    break;
            }
        }
    };

    @Override
    public void handlerError(String returnState) {
        if (TextUtils.equals(returnState, NetErrorEnum.Token失效.getState())) {
            application.loginOut();
            ActivityControl.finishAct(AccountManageActivity.this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case NICKNAMEREQUEST:
                if (resultCode == ModCustNameActivity.SUCCESS) {
                    Login.DataEntity entity = application.getLoginDao().getData();
                    nicknameText.setText(entity.getCustName());
                } else if (resultCode == ModCustNameActivity.FAILURE) {

                }
                break;
        }
    }

    /**
     * 启动AccountManageActivity
     *
     * @param context
     */
    public static void startAppActivity(Context context) {
        Intent intent = new Intent(context, AccountManageActivity.class);
        context.startActivity(intent);
    }

}
