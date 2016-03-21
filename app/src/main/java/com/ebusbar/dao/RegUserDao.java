package com.ebusbar.dao;

/**
 * Created by Jelly on 2016/3/4.
 */
public class RegUserDao {


    /**
     * returnStatus : 100
     * isSuccess : N
     * CustID : EV_20160321_00000071
     * Mobile : 18617050558
     */

    private CrmRegisterEntity crm_register;

    public CrmRegisterEntity getCrm_register() {
        return crm_register;
    }

    public void setCrm_register(CrmRegisterEntity crm_register) {
        this.crm_register = crm_register;
    }

    public static class CrmRegisterEntity {
        private String returnStatus;
        private String isSuccess;
        private String CustID;
        private String Mobile;

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

        public String getCustID() {
            return CustID;
        }

        public void setCustID(String CustID) {
            this.CustID = CustID;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }
    }
}
