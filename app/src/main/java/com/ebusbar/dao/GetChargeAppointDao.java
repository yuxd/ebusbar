package com.ebusbar.dao;

/**
 * Created by Jelly on 2016/3/10.
 */
public class GetChargeAppointDao {

    /**
     * position : 天明科技大厦
     * aid : 123
     * EPId : 10123
     * price : 0.0
     * time : 2016-3-10 10:50:45
     */

    private String position;
    private String aid;
    private String EPId;
    private String price;
    private String time;

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public void setEPId(String EPId) {
        this.EPId = EPId;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPosition() {
        return position;
    }

    public String getAid() {
        return aid;
    }

    public String getEPId() {
        return EPId;
    }

    public String getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }
}
