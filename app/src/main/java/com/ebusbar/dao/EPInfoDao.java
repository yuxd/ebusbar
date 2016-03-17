package com.ebusbar.dao;

/**
 * Created by Jelly on 2016/3/9.
 */
public class EPInfoDao{


    /**
     * position : 深圳大学
     * EPId : 101657
     */

    private String position;
    private int EPId;

    public void setPosition(String position) {
        this.position = position;
    }

    public void setEPId(int EPId) {
        this.EPId = EPId;
    }

    public String getPosition() {
        return position;
    }

    public int getEPId() {
        return EPId;
    }
}
