package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ebusbar.activities.UtilActivity;
import com.ebusbar.utils.DateUtil;
import com.weidongjian.meitu.wheelviewdemo.view.LoopView;
import com.weidongjian.meitu.wheelviewdemo.view.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择租车时间
 * Created by Jelly on 2016/4/14.
 */
public class RentCarTimeActivity extends UtilActivity {
    /**
     * TAG
     */
    public String TAG = "RentCarTimeActivity";
    @Bind(R.id.pickCarDate)
    TextView pickCarDate;
    @Bind(R.id.pickCarTime)
    TextView pickCarTime;
    @Bind(R.id.returnCarDate)
    TextView returnCarDate;
    @Bind(R.id.returnCarTime)
    TextView returnCarTime;
    @Bind(R.id.time)
    TextView time;
    /**
     * 选择时间
     */
    private PopupWindow selectTimePw;
    /**
     * 取车时间
     */
    private Date pickDate;
    /**
     * 还车时间
     */
    private Date returnDate;
    /**
     * 选择时间的集合
     */
    private List<Date> hourList;
    /**
     * 选择日期的集合
     */
    private List<Date> weekList;
    /**
     * 选择时间的PopupWindow的View
     */
    private View pwView;
    /**
     * 缓存PopupWindow视图
     */
    private ViewHolder viewHolder;
    /**
     * 用于操作时间
     */
    private Calendar calendarTemp;
    /**
     * 最长租车时间4小时
     */
    private final int BestLowRentTime = 4;
    /**
     * 最长租车时间60天
     */
    private final int BestLongRentTime = 60;
    /**
     * 默认租车时间7天
     */
    private final int InitRentTime = 7;
    /**
     * 默显示分钟00
     */
    private final int InitMinute = 00;
    /**
     * 最长预约时间7周
     */
    private final int BestLongAppointTime = 7;
    /**
     * 一天的最后一刻显示的时间
     */
    private final String LastTimeOfDay = "23:59";
    /**
     * 今天
     */
    private final String ToDay = "今天";
    /**
     * 视图上显示的日期格式
     */
    private final String ViewDateFormat = "MM-dd";
    /**
     * 视图和PopupWindow上显示的时间格式
     */
    private final String ViewTimeFormat = "HH:mm";
    /**
     * PopupWindow上的时间格式
     */
    private final String PopupWindowDateFormat = "MM月dd日";
    /**
     * 天与天的临界时间
     */
    private final String ConflictTime = "00:00";
    /**
     * 取车时间
     */
    private final String PickCarTimeTitle = "取车时间";
    /**
     * 还车时间
     */
    private final String ReturnCarTimeTitle = "还车时间";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.rentcar_time);
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
        setInitPickCarDate();//设置默认的取车时间
        setInitReturnCarDate(); //设置默认的还车时间
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setActivityView() {
        setViewPickCarTime();
        setViewReturnCarTime();
        setRentCarTime();
    }

    /**
     * 设置租车时间
     */
    public void setRentCarTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pickDate);
        calendarTemp.setTime(returnDate);
        long result = calendarTemp.getTimeInMillis() - calendar.getTimeInMillis();
        calendarTemp.setTimeInMillis(result);
        if(calendarTemp.get(Calendar.DAY_OF_MONTH) -1 == 0){
            time.setText(calendarTemp.get(Calendar.DAY_OF_MONTH) + "");
        }else {
            time.setText(calendarTemp.get(Calendar.DAY_OF_MONTH) - 1 + "");
        }
    }

    /**
     * 设置取车时间在界面上
     */
    public void setViewPickCarTime() {
        if (pickDate == null) {
            setInitPickCarDate();
        }
        calendarTemp.setTime(pickDate);
        pickCarDate.setText(DateUtil.getSdfDate(pickDate, ViewDateFormat));
        pickCarTime.setText(getDayOfWeek(calendarTemp) + " " + DateUtil.getSdfDate(pickDate, ViewTimeFormat));
    }

    /**
     * 设置还车时间在界面上
     */
    public void setViewReturnCarTime() {
        if (returnDate == null) {
            setInitReturnCarDate();
        }
        calendarTemp.setTime(returnDate);
        returnCarDate.setText(DateUtil.getSdfDate(returnDate, ViewDateFormat));
        returnCarTime.setText(getDayOfWeek(calendarTemp) + " " + DateUtil.getSdfDate(returnDate, ViewTimeFormat));
    }

    /**
     * 设置默认的取车时间
     */
    public void setInitPickCarDate() {
        if (calendarTemp == null) {
            calendarTemp = Calendar.getInstance();
            calendarTemp.setTimeZone(TimeZone.getDefault());
        }
        calendarTemp.add(Calendar.HOUR_OF_DAY, 1);
        calendarTemp.set(Calendar.MINUTE, InitMinute);
        pickDate = calendarTemp.getTime();
    }

    /**
     * 设置默认的还车时间
     */
    public void setInitReturnCarDate() {
        if (pickCarDate == null) {
            setInitPickCarDate();
        }
        calendarTemp.setTime(pickDate);
        calendarTemp.add(Calendar.DAY_OF_MONTH, InitRentTime);
        returnDate = calendarTemp.getTime();
    }

    @OnClick({R.id.pickCarTimeLayout, R.id.returnCarTimeLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pickCarTimeLayout:
                if (pwView == null) {
                    viewHolder = new ViewHolder();
                    pwView = LayoutInflater.from(context).inflate(R.layout.select_time_pw, null);
                    viewHolder.cancel = (TextView) pwView.findViewById(R.id.cancel);
                    viewHolder.ok = (TextView) pwView.findViewById(R.id.ok);
                    viewHolder.title = (TextView) pwView.findViewById(R.id.title);
                    viewHolder.time1 = (LoopView) pwView.findViewById(R.id.time1);
                    viewHolder.time2 = (LoopView) pwView.findViewById(R.id.time2);
                    pwView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) pwView.getTag();
                }
                viewHolder.title.setText("取车时间");
                viewHolder.time1.invalidate();
                viewHolder.time1.setNotLoop();
                viewHolder.time1.setViewPadding(80, 0, 80, 0);
                viewHolder.time2.invalidate();
                viewHolder.time2.setNotLoop();
                viewHolder.time2.setViewPadding(80, 0, 80, 0);
                weekList = getWeek(BestLongAppointTime);
                final List<String> item1 = new ArrayList<>();
                for (int i = 0; i < weekList.size(); i++) {
                    if (i == 0) {
                        item1.add(ToDay);
                    } else {
                        item1.add(DateUtil.getSdfDate(weekList.get(i), PopupWindowDateFormat));
                    }
                }
                final List<String> item2 = new ArrayList<>();
                hourList = getHour(weekList.get(0));
                for (int i=0;i<hourList.size();i++) {
                    if (TextUtils.equals(DateUtil.getSdfDate(hourList.get(i), ViewTimeFormat), ConflictTime)) {
                        calendarTemp.setTime(hourList.get(i));
                        calendarTemp.add(Calendar.DAY_OF_MONTH,-1);
                        calendarTemp.set(Calendar.HOUR_OF_DAY,23);
                        calendarTemp.set(Calendar.MINUTE,59);
                        hourList.get(i).setTime(calendarTemp.getTimeInMillis());
                        item2.add(LastTimeOfDay);
                    }else {
                        item2.add(DateUtil.getSdfDate(hourList.get(i), ViewTimeFormat));
                    }
                }
                viewHolder.time1.setItems(item1);
                viewHolder.time2.setItems(item2);

                viewHolder.time1.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        hourList = getHour(weekList.get(index));
                        item2.clear();
                        for (int i=0;i<hourList.size();i++) {
                            if (TextUtils.equals(DateUtil.getSdfDate(hourList.get(i), ViewTimeFormat), ConflictTime)) {
                                calendarTemp.setTime(hourList.get(i));
                                calendarTemp.add(Calendar.DAY_OF_MONTH,-1);
                                calendarTemp.set(Calendar.HOUR_OF_DAY,23);
                                calendarTemp.set(Calendar.MINUTE,59);
                                hourList.get(i).setTime(calendarTemp.getTimeInMillis());
                                item2.add(LastTimeOfDay);
                            } else {
                                item2.add(DateUtil.getSdfDate(hourList.get(i), ViewTimeFormat));
                            }
                        }
                        viewHolder.time2.setItems(item2);
                        viewHolder.time2.invalidate();
                    }
                });


                viewHolder.cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindowUtil.dismissPopupWindow(selectTimePw);
                    }
                });

                viewHolder.ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickDate = DateUtil.getDate(DateUtil.getSdfDate(hourList.get(viewHolder.time2.getSelectedItem()), DateUtil.dateFormat));
                        popupWindowUtil.dismissPopupWindow(selectTimePw);
                        setViewPickCarTime();
                        setRentCarTime();
                    }
                });
                selectTimePw = popupWindowUtil.getPopupWindow(context, pwView, windowUtil.getScreenWidth(this), windowUtil.getScreenHeight(this));
                selectTimePw.showAtLocation(view, Gravity.BOTTOM, 0, 0);

                break;
            case R.id.returnCarTimeLayout:
                showSelectTimePw(view,ReturnCarTimeTitle);
                break;
        }
    }


    public void showSelectTimePw(View view, final String title){
        if (pwView == null) {
            viewHolder = new ViewHolder();
            pwView = LayoutInflater.from(context).inflate(R.layout.select_time_pw, null);
            viewHolder.cancel = (TextView) pwView.findViewById(R.id.cancel);
            viewHolder.ok = (TextView) pwView.findViewById(R.id.ok);
            viewHolder.title = (TextView) pwView.findViewById(R.id.title);
            viewHolder.time1 = (LoopView) pwView.findViewById(R.id.time1);
            viewHolder.time2 = (LoopView) pwView.findViewById(R.id.time2);
            pwView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) pwView.getTag();
        }
        viewHolder.title.setText(title);
        viewHolder.time1.invalidate();
        viewHolder.time1.setNotLoop();
        viewHolder.time1.setViewPadding(80, 0, 80, 0);
        viewHolder.time2.invalidate();
        viewHolder.time2.setNotLoop();
        viewHolder.time2.setViewPadding(80, 0, 80, 0);
        if(TextUtils.equals(title,PickCarTimeTitle)){
            weekList = getWeek(BestLongAppointTime);
        }else{
            calendarTemp.setTime(pickDate);
            calendarTemp.add(Calendar.HOUR_OF_DAY,BestLowRentTime);
            weekList = getWeek(calendarTemp.getTime(),BestLongRentTime);
        }
        final List<String> item1 = new ArrayList<>();
        for (int i = 0; i < weekList.size(); i++) {
            if (i == 0) {
                if(TextUtils.equals(PickCarTimeTitle,title)){
                    item1.add(ToDay);
                }else{
                    item1.add(DateUtil.getSdfDate(weekList.get(i), PopupWindowDateFormat));
                }
            } else {
                item1.add(DateUtil.getSdfDate(weekList.get(i), PopupWindowDateFormat));
            }
        }
        final List<String> item2 = new ArrayList<>();
        hourList = getHour(weekList.get(0));
        for (int i=0;i<hourList.size();i++) {
            if (TextUtils.equals(DateUtil.getSdfDate(hourList.get(i), ViewTimeFormat), ConflictTime)) {
                calendarTemp.setTime(hourList.get(i));
                calendarTemp.add(Calendar.DAY_OF_MONTH,-1);
                calendarTemp.set(Calendar.HOUR_OF_DAY,23);
                calendarTemp.set(Calendar.MINUTE,59);
                hourList.get(i).setTime(calendarTemp.getTimeInMillis());
                item2.add(LastTimeOfDay);
            }else {
                item2.add(DateUtil.getSdfDate(hourList.get(i), ViewTimeFormat));
            }
        }
        viewHolder.time1.setItems(item1);
        viewHolder.time2.setItems(item2);

        viewHolder.time1.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                hourList = getHour(weekList.get(index));
                item2.clear();
                for (int i=0;i<hourList.size();i++) {
                    if (TextUtils.equals(DateUtil.getSdfDate(hourList.get(i), ViewTimeFormat), ConflictTime)) {
                        calendarTemp.setTime(hourList.get(i));
                        calendarTemp.add(Calendar.DAY_OF_MONTH,-1);
                        calendarTemp.set(Calendar.HOUR_OF_DAY,23);
                        calendarTemp.set(Calendar.MINUTE,59);
                        hourList.get(i).setTime(calendarTemp.getTimeInMillis());
                        item2.add(LastTimeOfDay);
                    } else {
                        item2.add(DateUtil.getSdfDate(hourList.get(i), ViewTimeFormat));
                    }
                }
                viewHolder.time2.setItems(item2);
                viewHolder.time2.invalidate();
            }
        });


        viewHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowUtil.dismissPopupWindow(selectTimePw);
            }
        });

        viewHolder.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.equals(title,PickCarTimeTitle)){
                    pickDate = DateUtil.getDate(DateUtil.getSdfDate(hourList.get(viewHolder.time2.getSelectedItem()), DateUtil.dateFormat));
                    setViewPickCarTime();
                    setRentCarTime();
                }else{
                    returnDate = DateUtil.getDate(DateUtil.getSdfDate(hourList.get(viewHolder.time2.getSelectedItem()), DateUtil.dateFormat));
                    setViewReturnCarTime();
                    setRentCarTime();
                }
                popupWindowUtil.dismissPopupWindow(selectTimePw);

            }
        });
        selectTimePw = popupWindowUtil.getPopupWindow(context, pwView, windowUtil.getScreenWidth(this), windowUtil.getScreenHeight(this));
        selectTimePw.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    private class ViewHolder {
        TextView cancel;
        TextView ok;
        TextView title;
        LoopView time1;
        LoopView time2;
    }

    /**
     * 开启界面
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RentCarTimeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    /**
     * 获得一周
     */
    public static List<Date> getWeek(int day) {
        List<Date> weekList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        weekList.add(calendar.getTime());
        for (int i = 1; i < day; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 00);
            calendar.set(Calendar.MINUTE,00);
            weekList.add(calendar.getTime());
        }
        return weekList;
    }

    public static List<Date> getWeek(Date date, int day) {
        List<Date> weekList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        weekList.add(calendar.getTime());
        for (int i = 1; i < day; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 00);
            calendar.set(Calendar.MINUTE,00);
            weekList.add(calendar.getTime());
        }
        return weekList;
    }

    public static List<Date> getHour(Date date) {
        List<Date> hourList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currHour = calendar.get(Calendar.HOUR_OF_DAY);
        for (int i = 0; i < 24 - currHour; i++) {
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            calendar.set(Calendar.MINUTE,00);
            hourList.add(calendar.getTime());
        }
        return hourList;
    }

    public static String getDayOfWeek(Calendar calendar) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        String result = "";
        switch (dayOfWeek) {
            case 1:
                result = "一";
                break;
            case 2:
                result = "二";
                break;
            case 3:
                result = "三";
                break;
            case 4:
                result = "四";
                break;
            case 5:
                result = "五";
                break;
            case 6:
                result = "六";
                break;
            case 0:
                result = "日";
                break;
        }
        return "周" + result;
    }

}
