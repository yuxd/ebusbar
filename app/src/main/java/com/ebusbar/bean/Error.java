package com.ebusbar.bean;

/**
 * Created by Jelly on 2016/3/29.
 */
public class Error {
    /**
     * 返回码
     */
    private String returnState = "100";
    /**
     * 信息
     */
    private String msg;
    /**
     * 是否提示
     */
    private boolean isToast = true;

    public Error() {
    }

    public String getReturnState() {
        return returnState;
    }

    public void setReturnState(String returnState) {
        this.returnState = returnState;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isToast() {
        return isToast;
    }

    public void setIsToast(boolean isToast) {
        this.isToast = isToast;
    }
}
