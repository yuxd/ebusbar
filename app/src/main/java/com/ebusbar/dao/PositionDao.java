package com.ebusbar.dao;

/**
 * Created by Jelly on 2016/3/7.
 */
public class PositionDao {

    /**
     * pid : 1
     * name : 深圳大学
     * latitude : 22.540693
     * longitude : 113.943025
     * enabled : true
     * free : false
     * position : 深圳大学南山校区
     * open : true
     * sum : 10
     * enablednum : 8
     * price : 1.2
     */
    private int pid;
    private String name;
    private double latitude;
    private double longitude;
    private boolean enabled;
    private boolean free;
    private String position;
    private boolean open;
    private int sum;
    private int enablednum;
    private double price;

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setEnablednum(int enablednum) {
        this.enablednum = enablednum;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isFree() {
        return free;
    }

    public String getPosition() {
        return position;
    }

    public boolean isOpen() {
        return open;
    }

    public int getSum() {
        return sum;
    }

    public int getEnablednum() {
        return enablednum;
    }

    public double getPrice() {
        return price;
    }
}
