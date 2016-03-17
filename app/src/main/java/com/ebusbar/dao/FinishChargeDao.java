package com.ebusbar.dao;

/**
 * Created by Jelly on 2016/3/11.
 */
public class FinishChargeDao {


    /**
     * pid : 1
     * message : 1
     * payType : 电桩充电
     * payPrice : 12.5
     */

    private String pid;
    private String message;
    private String payType;
    private String payPrice;

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getPid() {
        return pid;
    }

    public String getMessage() {
        return message;
    }

    public String getPayType() {
        return payType;
    }

    public String getPayPrice() {
        return payPrice;
    }
}
