package com.ebusbar.bean;

/**
 * 搜索父类
 * Created by Jelly on 2016/3/31.
 */
public class Search {
    /**
     * 搜索条件
     */
    private String condition;

    public Search(){}

    public Search(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
