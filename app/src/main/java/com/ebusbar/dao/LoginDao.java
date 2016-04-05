package com.ebusbar.dao;

import java.io.Serializable;

/**
 * 登录数据
 * Created by Jelly on 2016/3/2.
 */
public class LoginDao implements Serializable{

    /**
     * interfaceName : crm_login
     * returnStatus : 100
     * isSuccess : Y
     * data : {"CustID":"86201603290000000665","CustName":"KUNKKA","Mobile":"15062464932","Token":"0x746b32303136303430313642434432353843","Sex":"男","BalanceAmt":"10.00","Usericon":"http://www.jf258.com/uploads/2014-09-05/233921364.jpg","ExistsPayPassword":"0","Verified":"0","Addr":"123"}
     */

    private String interfaceName;
    private String returnStatus;
    private String isSuccess;
    /**
     * CustID : 86201603290000000665
     * CustName : KUNKKA
     * Mobile : 15062464932
     * Token : 0x746b32303136303430313642434432353843
     * Sex : 男
     * BalanceAmt : 10.00
     * Usericon : http://www.jf258.com/uploads/2014-09-05/233921364.jpg
     * ExistsPayPassword : 0
     * Verified : 0
     * Addr : 123
     */

    private DataEntity data;

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

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable{
        private String CustID;
        private String CustName;
        private String Mobile;
        private String Token;
        private String Sex;
        private String BalanceAmt;
        private String Usericon;
        private String ExistsPayPassword;
        private String Verified;
        private String Addr;

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

        public String getExistsPayPassword() {
            return ExistsPayPassword;
        }

        public void setExistsPayPassword(String ExistsPayPassword) {
            this.ExistsPayPassword = ExistsPayPassword;
        }

        public String getVerified() {
            return Verified;
        }

        public void setVerified(String Verified) {
            this.Verified = Verified;
        }

        public String getAddr() {
            return Addr;
        }

        public void setAddr(String Addr) {
            this.Addr = Addr;
        }
    }
}
