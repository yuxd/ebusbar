package com.ebusbar.bean;

/**
 * Created by Jelly on 2016/3/24.
 */
public class DeleteChargeCard {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * CustId : EV_20160319_00000057
     * AccountId : 86201603240000000266
     */

    private CrmAccountsDeleteEntity crm_accounts_delete;

    public CrmAccountsDeleteEntity getCrm_accounts_delete() {
        return crm_accounts_delete;
    }

    public void setCrm_accounts_delete(CrmAccountsDeleteEntity crm_accounts_delete) {
        this.crm_accounts_delete = crm_accounts_delete;
    }

    public static class CrmAccountsDeleteEntity {
        private String returnStatus;
        private String isSuccess;
        private String CustId;
        private String AccountId;

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

        public String getCustId() {
            return CustId;
        }

        public void setCustId(String CustId) {
            this.CustId = CustId;
        }

        public String getAccountId() {
            return AccountId;
        }

        public void setAccountId(String AccountId) {
            this.AccountId = AccountId;
        }
    }
}
