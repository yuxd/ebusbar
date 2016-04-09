package com.ebusbar.bean;

/**
 * Created by Jelly on 2016/4/7.
 */
public class ResetLoginPwd {

    /**
     * interfaceName : crm_pwd_reset
     * returnStatus : 100
     * isSuccess : Y
     * data : {"Mobile":"18617050557","Code":"91750"}
     */

    private String interfaceName;
    private String returnStatus;
    private String isSuccess;
    /**
     * Mobile : 18617050557
     * Code : 91750
     */

    private DataEntity data;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private String Mobile;
        private String Code;

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }
    }
}
