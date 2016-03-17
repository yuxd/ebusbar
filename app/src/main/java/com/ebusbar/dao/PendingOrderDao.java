package com.ebusbar.dao;

/**
 * Created by Jelly on 2016/3/10.
 */
public class PendingOrderDao {

    /**
     * id : 1
     * type : 电桩充电
     * state : 充电中
     * position : 深圳大学
     * EDId : 101657
     * time : 2016-3-10 16:11:48
     */

    private String id;
    private String type;
    private String state;
    private String position;
    private String EDId;
    private String time;

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setEDId(String EDId) {
        this.EDId = EDId;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getState() {
        return state;
    }

    public String getPosition() {
        return position;
    }

    public String getEDId() {
        return EDId;
    }

    public String getTime() {
        return time;
    }
}
