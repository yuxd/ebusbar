package com.ebusbar.dao;

/**
 * Created by Jelly on 2016/3/9.
 */
public class PayAppointDao {

    /**
     * message : 1
     * payId : 20160309190626
     */

    private String message;
    private String payId;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getMessage() {
        return message;
    }

    public String getPayId() {
        return payId;
    }
}
