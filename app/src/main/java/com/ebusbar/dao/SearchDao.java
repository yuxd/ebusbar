package com.ebusbar.dao;

/**
 * 搜索父类
 * Created by Jelly on 2016/3/31.
 */
public class SearchDao {
    /**
     * 搜索条件
     */
    private String condition;

    public SearchDao(){}

    public SearchDao(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
