package com.ebusbar.dao;

/**
 * 余额充值
 * Created by Jelly on 2016/3/23.
 */
public class ReChargeDao {


    /**
     * returnStatus : 100
     * isSuccess : Y
     * BalanceAmt : 150.00
     */

    private CrmRechargeEntity crm_recharge;

    public CrmRechargeEntity getCrm_recharge() {
        return crm_recharge;
    }

    public void setCrm_recharge(CrmRechargeEntity crm_recharge) {
        this.crm_recharge = crm_recharge;
    }

    public static class CrmRechargeEntity {
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
