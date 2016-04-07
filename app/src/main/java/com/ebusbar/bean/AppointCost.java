package com.ebusbar.bean;

/**
 * 预约花费
 * Created by Jelly on 2016/3/31.
 */
public class AppointCost {

    /**
     * interfaceName : evc_plancost_get
     * returnStatus : 100
     * isSuccess : Y
     * data : {"CustId":"86201603290000000665","Munites":"30","Cost":"3.00"}
     */

    private String interfaceName;
    private String returnStatus;
    private String isSuccess;
    /**
     * CustId : 86201603290000000665
     * Munites : 30
     * Cost : 3.00
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
        private String CustId;
        private String Munites;
        private String Cost;

        public String getCustId() {
            return CustId;
        }

        public void setCustId(String CustId) {
            this.CustId = CustId;
        }

        public String getMunites() {
            return Munites;
        }

        public void setMunites(String Munites) {
            this.Munites = Munites;
        }

        public String getCost() {
            return Cost;
        }

        public void setCost(String Cost) {
            this.Cost = Cost;
        }
    }
}
