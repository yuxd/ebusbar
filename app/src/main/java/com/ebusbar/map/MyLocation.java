package com.ebusbar.map;

import java.io.Serializable;

/**
 * Created by Jelly on 2016/3/30.
 */
public class MyLocation implements Serializable{
    /**
     * 城市代码
     */
    private String adCode;
    /**
     * 地址
     */
    private String address;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 经度
     */
    private String longitude;

    public MyLocation(){}

    public MyLocation(String adCode, String latitude, String address, String longitude) {
        this.adCode = adCode;
        this.latitude = latitude;
        this.address = address;
        this.longitude = longitude;
    }


    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
