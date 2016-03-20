package com.ebusbar.dao;

import java.io.Serializable;

/**
 * 登录数据
 * Created by Jelly on 2016/3/2.
 */
public class LoginDao implements Serializable{


    /**
     * returnStatus : 100
     * isSuccess : Y
     * CustID : EV_20160319_00000057
     * Mobile : 18617050557
     * Token : tk20160319CD1016AE
     * Verified : 0
     */

    private CrmLoginEntity crm_login;

    public CrmLoginEntity getCrm_login() {
        return crm_login;
    }

    public void setCrm_login(CrmLoginEntity crm_login) {
        this.crm_login = crm_login;
    }

    public static class CrmLoginEntity {
        private String returnStatus;
        private String isSuccess;
        private String CustID;
        private String Mobile;
        private String Token;
        private String Verified;

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

        public String getToken() {
            return Token;
        }

        public void setToken(String Token) {
            this.Token = Token;
        }

        public String getVerified() {
            return Verified;
        }

        public void setVerified(String Verified) {
            this.Verified = Verified;
        }
    }
}
