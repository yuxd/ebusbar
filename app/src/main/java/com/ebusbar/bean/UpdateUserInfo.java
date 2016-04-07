package com.ebusbar.bean;

/**
 * Created by Jelly on 2016/4/5.
 */
public class UpdateUserInfo {

    /**
     * interfaceName : crm_customer_update
     * returnStatus : 100
     * isSuccess : Y
     * data : {"CustID":"86201604010000000709"}
     */

    private String interfaceName;
    private String returnStatus;
    private String isSuccess;
    /**
     * CustID : 86201604010000000709
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
        private String CustID;

        public String getCustID() {
            return CustID;
        }

        public void setCustID(String CustID) {
            this.CustID = CustID;
        }
    }
}
