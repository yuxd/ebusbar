package com.ebusbar.bean;

/**
 * 结束充电
 * Created by Jelly on 2016/3/11.
 */
public class FinishCharge {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * OrderNo : EV_20160322_00000144
     * OrderStatus : 4
     * OrderType : EVR
     * CompanyID : C01
     * CustID : EV_20160319_00000057
     * OrgID : C00000002
     * OrgName : 深大站
     * FacilityID : F000004
     * PlanBeginDateTime : 2016-03-22 19:40:46
     * PlanEndDateTime : 2016-03-22 19:55:46
     * RealBeginDateTime : 2016-03-22 19:40:55
     */

    private EvcOrderChangeEntity evc_order_change;

    public EvcOrderChangeEntity getEvc_order_change() {
        return evc_order_change;
    }

    public void setEvc_order_change(EvcOrderChangeEntity evc_order_change) {
        this.evc_order_change = evc_order_change;
    }

    public static class EvcOrderChangeEntity {
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
        private String PlanBeginDateTime;
        private String PlanEndDateTime;
        private String RealBeginDateTime;

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

        public String getPlanBeginDateTime() {
            return PlanBeginDateTime;
        }

        public void setPlanBeginDateTime(String PlanBeginDateTime) {
            this.PlanBeginDateTime = PlanBeginDateTime;
        }

        public String getPlanEndDateTime() {
            return PlanEndDateTime;
        }

        public void setPlanEndDateTime(String PlanEndDateTime) {
            this.PlanEndDateTime = PlanEndDateTime;
        }

        public String getRealBeginDateTime() {
            return RealBeginDateTime;
        }

        public void setRealBeginDateTime(String RealBeginDateTime) {
            this.RealBeginDateTime = RealBeginDateTime;
        }
    }
}
