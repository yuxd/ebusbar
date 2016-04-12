package com.ebusbar.bean;

/**
 * Created by Jelly on 2016/3/22.
 */
public class CompleteOrder {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * OrderNo : EV_20160322_00000118
     * OrderStatus : 16
     * OrderType : 1
     * CompanyID : C01
     * CustID : EV_20160321_00000077
     * OrgID : C00000001
     * OrgName : 科兴科学园
     * FacilityID : F000001
     * PlanBeginDateTime : 2016-03-22 14:12:35
     * PlanEndDateTime : 2016-03-22 14:42:35
     *
     */

    private EvcOrdersGetEntity evc_orders_get;

    public EvcOrdersGetEntity getEvc_orders_get() {
        return evc_orders_get;
    }

    public void setEvc_orders_get(EvcOrdersGetEntity evc_orders_get) {
        this.evc_orders_get = evc_orders_get;
    }

    public static class EvcOrdersGetEntity {
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
        private String RealEndDateTime;

        public String getRealBeginDateTime() {
            return RealBeginDateTime;
        }

        public void setRealBeginDateTime(String realBeginDateTime) {
            RealBeginDateTime = realBeginDateTime;
        }

        public String getRealEndDateTime() {
            return RealEndDateTime;
        }

        public void setRealEndDateTime(String realEndDateTime) {
            RealEndDateTime = realEndDateTime;
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
    }
}
