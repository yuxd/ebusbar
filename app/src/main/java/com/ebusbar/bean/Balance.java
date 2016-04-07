package com.ebusbar.bean;

/**
 * 余额查询
 * Created by Jelly on 2016/3/23.
 */
public class Balance {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * BalanceAmt : 210.00
     */

    private CrmBalanceamtGetEntity crm_balanceamt_get;

    public CrmBalanceamtGetEntity getCrm_balanceamt_get() {
        return crm_balanceamt_get;
    }

    public void setCrm_balanceamt_get(CrmBalanceamtGetEntity crm_balanceamt_get) {
        this.crm_balanceamt_get = crm_balanceamt_get;
    }

    public static class CrmBalanceamtGetEntity {
        private String returnStatus;
        private String isSuccess;
        private String BalanceAmt;

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

        public String getBalanceAmt() {
            return BalanceAmt;
        }

        public void setBalanceAmt(String BalanceAmt) {
            this.BalanceAmt = BalanceAmt;
        }
    }
}
