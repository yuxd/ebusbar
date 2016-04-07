package com.ebusbar.bean;

import java.util.List;

/**
 * Created by Jelly on 2016/3/28.
 */
public class Test {

    /**
     * interfaceName : crm_login
     * returnStatus : 100
     * isSuccess : Y
     * data : [{"CustID":"86201603290000000656","Mobile":"15062464932","Token":"0x746b32303136303332394438323837354543","BalanceAmt":"97.56","ExistsPayPassword":"1"},{"CustID":"86201603290000000656","Mobile":"15062464932","Token":"0x746b32303136303332394438323837354543","BalanceAmt":"97.56","ExistsPayPassword":"1"}]
     */
    private String interfaceName;
    private String returnStatus;
    private String isSuccess;
    /**
     * CustID : 86201603290000000656
     * Mobile : 15062464932
     * Token : 0x746b32303136303332394438323837354543
     * BalanceAmt : 97.56
     * ExistsPayPassword : 1
     */

    private List<DataEntity> data;

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

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        private String CustID;
        private String Mobile;
        private String Token;
        private String BalanceAmt;
        private String ExistsPayPassword;

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

        public String getToken() {
            return Token;
        }

        public void setToken(String Token) {
            this.Token = Token;
        }

        public String getBalanceAmt() {
            return BalanceAmt;
        }

        public void setBalanceAmt(String BalanceAmt) {
            this.BalanceAmt = BalanceAmt;
        }

        public String getExistsPayPassword() {
            return ExistsPayPassword;
        }

        public void setExistsPayPassword(String ExistsPayPassword) {
            this.ExistsPayPassword = ExistsPayPassword;
        }
    }
}
