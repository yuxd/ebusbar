package com.ebusbar.bean;

/**
 * 获取验证码
 * Created by Jelly on 2016/3/5.
 */
public class Code {
    /**
     * returnStatus : 100
     * isSuccess : Y
     * Mobile : 18617050557
     * Code : 67660
     * BeginTime : 2016-03-18T15:35:50
     * CodeType : 1
     */

    private CrmValidationEntity crm_validation;

    public CrmValidationEntity getCrm_validation() {
        return crm_validation;
    }

    public void setCrm_validation(CrmValidationEntity crm_validation) {
        this.crm_validation = crm_validation;
    }

    public static class CrmValidationEntity {
        private String returnStatus;
        private String isSuccess;
        private String Mobile;
        private String Code;
        private String BeginTime;
        private String CodeType;

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

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getBeginTime() {
            return BeginTime;
        }

        public void setBeginTime(String BeginTime) {
            this.BeginTime = BeginTime;
        }

        public String getCodeType() {
            return CodeType;
        }

        public void setCodeType(String CodeType) {
            this.CodeType = CodeType;
        }
    }
}
