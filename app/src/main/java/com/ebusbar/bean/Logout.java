package com.ebusbar.bean;

/**
 * 注销
 * Created by Jelly on 2016/3/25.
 */
public class Logout {

    /**
     * returnStatus : 100
     * isSuccess : Y
     */

    private CrmLogoutEntity crm_logout;

    public CrmLogoutEntity getCrm_logout() {
        return crm_logout;
    }

    public void setCrm_logout(CrmLogoutEntity crm_logout) {
        this.crm_logout = crm_logout;
    }

    public static class CrmLogoutEntity {
        private String returnStatus;
        private String isSuccess;

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
    }
}
