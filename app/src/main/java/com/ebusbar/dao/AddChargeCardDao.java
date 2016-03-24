package com.ebusbar.dao;

/**
 * Created by Jelly on 2016/3/23.
 */
public class AddChargeCardDao {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * CustId : EV_20160319_00000057
     * AccountID : 86201603240000000259
     * AccountNo : 464646
     * AccountType : ChargeType
     */

    private CrmAccountsInsertEntity crm_accounts_insert;

    public CrmAccountsInsertEntity getCrm_accounts_insert() {
        return crm_accounts_insert;
    }

    public void setCrm_accounts_insert(CrmAccountsInsertEntity crm_accounts_insert) {
        this.crm_accounts_insert = crm_accounts_insert;
    }

    public static class CrmAccountsInsertEntity {
        private String returnStatus;
        private String isSuccess;
        private String CustId;
        private String AccountID;
        private String AccountNo;
        private String AccountType;

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

        public String getAccountID() {
            return AccountID;
        }

        public void setAccountID(String AccountID) {
            this.AccountID = AccountID;
        }

        public String getAccountNo() {
            return AccountNo;
        }

        public void setAccountNo(String AccountNo) {
            this.AccountNo = AccountNo;
        }

        public String getAccountType() {
            return AccountType;
        }

        public void setAccountType(String AccountType) {
            this.AccountType = AccountType;
        }
    }
}
