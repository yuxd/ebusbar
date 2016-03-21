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
     * CustName : genlex101
     * Mobile : 18617050557
     * Token : 0x746b32303136303332313445304530373146
     * Sex : 男
     * Age : 116
     * BalanceAmt : 1000.00
     * Usericon : https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=560678108,398434082&amp;fm=116&amp;gp=0.jpg
     * Verified : 1
     */

    private CrmLoginEntity crm_login;

    public CrmLoginEntity getCrm_login() {
        return crm_login;
    }

    public void setCrm_login(CrmLoginEntity crm_login) {
        this.crm_login = crm_login;
    }

    public static class CrmLoginEntity implements  Serializable{
        private String returnStatus;
        private String isSuccess;
        private String CustID;
        private String CustName;
        private String Mobile;
        private String Token;
        private String Sex;
        private String Age;
        private String BalanceAmt;
        private String Usericon;
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

        public String getCustName() {
            return CustName;
        }

        public void setCustName(String CustName) {
            this.CustName = CustName;
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

        public String getSex() {
            return Sex;
        }

        public void setSex(String Sex) {
            this.Sex = Sex;
        }

        public String getAge() {
            return Age;
        }

        public void setAge(String Age) {
            this.Age = Age;
        }

        public String getBalanceAmt() {
            return BalanceAmt;
        }

        public void setBalanceAmt(String BalanceAmt) {
            this.BalanceAmt = BalanceAmt;
        }

        public String getUsericon() {
            return Usericon;
        }

        public void setUsericon(String Usericon) {
            this.Usericon = Usericon;
        }

        public String getVerified() {
            return Verified;
        }

        public void setVerified(String Verified) {
            this.Verified = Verified;
        }
    }
}
