package com.ebusbar.dao;

/**
 * 开始充电
 * Created by Jelly on 2016/3/22.
 */
public class StartChargeDao {
    /**
     * returnStatus : 100
     * isSuccess : Y
     * OrderNo : 86201603230000000167
     * OrderStatus : 2
     * OrderType : EVCR
     * CompanyID : C01
     * CustID : EV_20160319_00000057
     * OrgID : C00000002
     * OrgName : 深大站
     * FacilityID : F000004
     * RealBeginDateTime : 2016-03-23 14:10:09
     * Tel : 0755-82891087
     */

    private EvcOrderSetEntity evc_order_set;

    public EvcOrderSetEntity getEvc_order_set() {
        return evc_order_set;
    }

    public void setEvc_order_set(EvcOrderSetEntity evc_order_set) {
        this.evc_order_set = evc_order_set;
    }

    public static class EvcOrderSetEntity {
        private String returnStatus;
        private String isSuccess;
        private String OrderNo;
        private String OrderStatus;
        private String OrderType;
        private String CompanyID;
        private String CustID;
        private String OrgID;
        private String OrgName;
        private String FacilityID;
        private String RealBeginDateTime;
        private String Tel;

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

        public String getOrderNo() {
            return OrderNo;
        }

        public void setOrderNo(String OrderNo) {
            this.OrderNo = OrderNo;
        }

        public String getOrderStatus() {
            return OrderStatus;
        }

        public void setOrderStatus(String OrderStatus) {
            this.OrderStatus = OrderStatus;
        }

        public String getOrderType() {
            return OrderType;
        }

        public void setOrderType(String OrderType) {
            this.OrderType = OrderType;
        }

        public String getCompanyID() {
            return CompanyID;
        }

        public void setCompanyID(String CompanyID) {
            this.CompanyID = CompanyID;
        }

        public String getCustID() {
            return CustID;
        }

        public void setCustID(String CustID) {
            this.CustID = CustID;
        }

        public String getOrgID() {
            return OrgID;
        }

        public void setOrgID(String OrgID) {
            this.OrgID = OrgID;
        }

        public String getOrgName() {
            return OrgName;
        }

        public void setOrgName(String OrgName) {
            this.OrgName = OrgName;
        }

        public String getFacilityID() {
            return FacilityID;
        }

        public void setFacilityID(String FacilityID) {
            this.FacilityID = FacilityID;
        }

        public String getRealBeginDateTime() {
            return RealBeginDateTime;
        }

        public void setRealBeginDateTime(String RealBeginDateTime) {
            this.RealBeginDateTime = RealBeginDateTime;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }
    }
}
