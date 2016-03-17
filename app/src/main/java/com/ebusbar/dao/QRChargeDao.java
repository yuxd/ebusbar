package com.ebusbar.dao;

/**
 * Created by Jelly on 2016/3/9.
 */
public class QRChargeDao {

    /**
     * message : 1
     * oid : 20160309142537
     */

    private String message;
    private String oid;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getMessage() {
        return message;
    }

    public String getOid() {
        return oid;
    }
}
