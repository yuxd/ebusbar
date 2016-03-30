package com.ebusbar.param;

/**
 * SharePreference的文件名
 * Created by Jelly on 2016/3/30.
 */
public enum SParam {

    位置缓存("location"), //缓存当前位置的经纬度和位置的城市代码
    配置缓存("userConfig"); //缓存用户的配置文件

    private String fileName;

    SParam(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
