package com.ebusbar.bean;

/**
 * 设置支付密码
 * Created by Jelly on 2016/3/23.
 */
public class SetPayPassword {

    /**
     * returnStatus : 113
     * isSuccess : N
     */

    private CrmPaypasswordSetEntity crm_paypassword_set;

    public CrmPaypasswordSetEntity getCrm_paypassword_set() {
        return crm_paypassword_set;
    }

    public void setCrm_paypassword_set(CrmPaypasswordSetEntity crm_paypassword_set) {
        this.crm_paypassword_set = crm_paypassword_set;
    }

    public static class CrmPaypasswordSetEntity {
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
